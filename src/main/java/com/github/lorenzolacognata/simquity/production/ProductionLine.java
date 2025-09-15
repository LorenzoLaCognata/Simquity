package com.github.lorenzolacognata.simquity.production;

import com.github.lorenzolacognata.simquity.agent.Organization;
import com.github.lorenzolacognata.simquity.asset.Asset;
import com.github.lorenzolacognata.simquity.asset.AssetRequirement;
import com.github.lorenzolacognata.simquity.asset.ProductionStatus;
import com.github.lorenzolacognata.simquity.inventory.AgentAsset;
import com.github.lorenzolacognata.simquity.inventory.AssetInventory;
import com.github.lorenzolacognata.simquity.labor.Employment;
import com.github.lorenzolacognata.simquity.labor.Job;
import com.github.lorenzolacognata.simquity.labor.LaborRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ProductionLine {

    private static final double MAX_CONSUMABLE_ASSET_SAVING = 0.05;
    private static final double MAX_LABOR_HOURS_VARIANCE = 0.05;
    private static final Random random = new Random();

    private final AgentAsset agentAsset;
    private final AssetProduction assetProduction;
    private final int startDate;
    private ProductionStatus productionStatus;
    private int currentDuration;
    private double outputQuantity;
    private final List<ProductionInventory> consumableProductionInventoryList;
    private final List<ProductionInventory> durableProductionInventoryList;
    private final List<ProductionLabor> productionLaborList;

    public ProductionLine(AgentAsset agentAsset, AssetProduction assetProduction, int startDate) {
        this.agentAsset = agentAsset;
        this.assetProduction = assetProduction;
        this.startDate = startDate;
        this.productionStatus = ProductionStatus.NOT_STARTED;
        this.currentDuration = 0;
        this.outputQuantity = 0.0;
        this.consumableProductionInventoryList = new ArrayList<>();
        this.durableProductionInventoryList = new ArrayList<>();
        this.productionLaborList = new ArrayList<>();
    }

    public double getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(double outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public AssetProduction getAssetProduction() {
        return assetProduction;
    }

    public ProductionStatus getProductionStatus() {
        return productionStatus;
    }

    public void setProductionStatus(ProductionStatus productionStatus) {
        this.productionStatus = productionStatus;
    }

    public List<ProductionInventory> getConsumableProductionInventoryList() {
        return consumableProductionInventoryList;
    }

    public List<ProductionInventory> getConsumableProductionInventoryList(Asset asset) {
        return consumableProductionInventoryList.stream()
                .filter(a -> a.getAssetInventory().getAsset().equals(asset))
                .toList();
    }

    public List<ProductionInventory> getDurableProductionInventoryList() {
        return durableProductionInventoryList;
    }

    public List<ProductionInventory> getDurableProductionInventoryList(Asset asset) {
        return durableProductionInventoryList.stream()
                .filter(a -> a.getAssetInventory().getAsset().equals(asset))
                .toList();
    }

    public List<ProductionLabor> getProductionLaborList() {
        return productionLaborList;
    }

    public List<ProductionLabor> getProductionLaborList(Job job) {
        return productionLaborList.stream()
                .filter(a -> a.getEmployment().getJob().equals(job))
                .toList();
    }

    public void addProductionInventory(ProductionInventory productionInventory, List<ProductionInventory> productionInventoryList) {
        boolean alreadyExists = productionInventoryList.stream()
                .anyMatch(a -> a.getAssetInventory().equals(productionInventory.getAssetInventory()));
        if (!alreadyExists) {
            productionInventoryList.add(productionInventory);
        }
    }

    public void addConsumableProductionInventory(ProductionInventory productionInventory) {
        addProductionInventory(productionInventory, consumableProductionInventoryList);
    }


    public void addDurableProductionInventory(ProductionInventory productionInventory) {
        addProductionInventory(productionInventory, durableProductionInventoryList);
    }

    public void addProductionLabor(ProductionLabor productionLabor) {
        boolean alreadyExists = productionLaborList.stream()
                .anyMatch(a -> a.getEmployment().equals(productionLabor.getEmployment()));
        if (!alreadyExists) {
            productionLaborList.add(productionLabor);
        }
    }

    public void produce() {

        double marginalCost = 0.0;

        // TODO: restore line below and remove temporary instant production
        //  currentDuration++;
        currentDuration = 99999999;

        if (productionStatus == ProductionStatus.NOT_STARTED) {
            productionStatus = ProductionStatus.IN_PROGRESS;
        }

        List<AgentAsset> agentAssetList = Stream.of(
                    agentAsset.getAgent().getPurchasedAgentAssetList(),
                    agentAsset.getAgent().getProducedAgentAssetList()
            )
            .flatMap(List::stream)
            .toList();

        boolean consumableAssetRequirementSatisfied = checkAssetRequirements(agentAssetList, assetProduction.getConsumableAssetRequirementList());
        boolean durableAssetRequirementSatisfied = checkAssetRequirements(agentAssetList, assetProduction.getDurableAssetRequirementList());;
        boolean laborRequirementSatisfied = checkLaborRequirements(agentAssetList, assetProduction.getLaborRequirementList());

        if (consumableAssetRequirementSatisfied && durableAssetRequirementSatisfied && laborRequirementSatisfied) {

            System.out.println("\t\t" + agentAsset.getAsset());

            marginalCost += useConsumableAsset(agentAssetList, assetProduction.getConsumableAssetRequirementList());
            marginalCost += useDurableAsset(agentAssetList, assetProduction.getDurableAssetRequirementList());
            marginalCost += useLabor(agentAssetList, assetProduction.getLaborRequirementList());

            if (currentDuration >= assetProduction.getDuration()) {
                productionStatus = ProductionStatus.COMPLETE;

                outputQuantity = assetProduction.getOutputQuantity();

                double marginalCostPlusMargin = (double) Math.round(100 * marginalCost / (1 - agentAsset.getAsset().getTargetGrossMargin())) / 100;
                agentAsset.addAssetInventory(outputQuantity, marginalCostPlusMargin);

                System.out.println("\t\t\tProduced: " + outputQuantity + " @ " + marginalCostPlusMargin);

                for (ProductionInventory productionInventory : durableProductionInventoryList) {
                    productionInventory.getAssetInventory().addQuantity(productionInventory.getQuantity());
                }

                for (ProductionLabor productionLabor : productionLaborList) {
                    productionLabor.getEmployment().addFtes(productionLabor.getFtes());
                }

            }
        }

        else {
            productionStatus = ProductionStatus.ABORTED;
        }

    }

    private boolean checkAssetRequirements(List<AgentAsset> agentAssetList, List<AssetRequirement> assetRequirementList) {

        boolean assetRequirementSatisfied = true;

        for (AssetRequirement assetRequirement : assetRequirementList) {
            double requiredQuantity = assetRequirement.getInitialQuantity();
            List<AssetInventory> requiredAssetInventoryList = getAssetInventoryLists(agentAssetList, assetRequirement.getAsset());
            for (AssetInventory requiredAssetInventory : requiredAssetInventoryList) {
                if (requiredQuantity <= 0) {
                    break;
                }
                else {
                    double selectedQuantity = Math.min(requiredQuantity, requiredAssetInventory.getQuantity());
                    requiredQuantity = requiredQuantity - selectedQuantity;
                }
            }
            if (requiredQuantity > 0) {
                assetRequirementSatisfied = false;
                break;
            }
        }
        return assetRequirementSatisfied;
    }

    private boolean checkLaborRequirements(List<AgentAsset> agentAssetList, List<LaborRequirement> laborRequirementList) {

        boolean laborRequirementSatisfied = true;
        Organization organization = (Organization) agentAsset.getAgent();

        for (LaborRequirement laborRequirement : laborRequirementList) {
            double requiredFtes = laborRequirement.getFtes();
            List<Employment> requiredEmploymentList = organization.getEmploymentList(laborRequirement.getJob());
            for (Employment requiredEmployment : requiredEmploymentList) {
                if (requiredFtes <= 0) {
                    break;
                }
                else {
                    double selectedFtes = Math.min(requiredFtes, requiredEmployment.getFtes());
                    requiredFtes = requiredFtes - selectedFtes;
                }
            }
            if (requiredFtes > 0) {
                laborRequirementSatisfied = false;
                break;
            }
        }
        return laborRequirementSatisfied;
    }

   private double useConsumableAsset(List<AgentAsset> agentAssetList, List<AssetRequirement> assetRequirementList) {

        double addedMarginalCost = 0;

        for (AssetRequirement assetRequirement : assetRequirementList) {
            double randomConsumableAssetSaving = random.nextDouble(0, MAX_CONSUMABLE_ASSET_SAVING);
            double requiredQuantity = assetRequirement.getInitialQuantity() * (1 - randomConsumableAssetSaving);
            List<AssetInventory> requiredAssetInventoryList = getAssetInventoryLists(agentAssetList, assetRequirement.getAsset());
            for (AssetInventory requiredAssetInventory : requiredAssetInventoryList) {
                if (requiredQuantity <= 0) {
                    break;
                }
                else {
                    double selectedQuantity = Math.min(requiredQuantity, requiredAssetInventory.getQuantity());
                    requiredAssetInventory.addQuantity(-selectedQuantity);
                    addConsumableProductionInventory(new ProductionInventory(requiredAssetInventory, selectedQuantity));
                    requiredQuantity = requiredQuantity - selectedQuantity;

                    double consumableAssetCost = requiredAssetInventory.getMarginalCost() * selectedQuantity;
                    addedMarginalCost += (double) Math.round(100 * consumableAssetCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("\t\t\tMarginal Cost (" + assetRequirement.getAsset() + "): " + addedMarginalCost);
                }
            }
        }
        return addedMarginalCost;
    }

    private double useDurableAsset(List<AgentAsset> agentAssetList, List<AssetRequirement> assetRequirementList) {

        double addedMarginalCost = 0;

        for (AssetRequirement assetRequirement : assetRequirementList) {
            double requiredQuantity = assetRequirement.getInitialQuantity();
            List<AssetInventory> requiredAssetInventoryList = getAssetInventoryLists(agentAssetList, assetRequirement.getAsset());
            for (AssetInventory requiredAssetInventory : requiredAssetInventoryList) {
                if (requiredQuantity <= 0) {
                    break;
                }
                else {
                    double selectedQuantity = Math.min(requiredQuantity, requiredAssetInventory.getQuantity());
                    requiredAssetInventory.addQuantity(-selectedQuantity);
                    addDurableProductionInventory(new ProductionInventory(requiredAssetInventory, selectedQuantity));
                    requiredQuantity = requiredQuantity - selectedQuantity;

                    // TODO: replace assetProduction.getDuration() with 1.0 when not all weeks are simulated together
                    double durableAssetCost = requiredAssetInventory.getMarginalCost() * selectedQuantity * (assetProduction.getDuration() / requiredAssetInventory.getAsset().getLifespan());
                    addedMarginalCost += (double) Math.round(100 * durableAssetCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("\t\t\tMarginal Cost (" + assetRequirement.getAsset() + "): " + addedMarginalCost);
                }
            }
        }
        return addedMarginalCost;
    }

    private double useLabor(List<AgentAsset> agentAssetList, List<LaborRequirement> laborRequirementList) {

        double addedMarginalCost = 0;
        Organization organization = (Organization) agentAsset.getAgent();

        for (LaborRequirement laborRequirement : laborRequirementList) {
            double requiredFtes = laborRequirement.getFtes();
            List<Employment> requiredEmploymentList = organization.getEmploymentList(laborRequirement.getJob());
            for (Employment requiredEmployment : requiredEmploymentList) {
                if (requiredFtes <= 0) {
                    break;
                }
                else {
                    double selectedFtes = Math.min(requiredFtes, requiredEmployment.getFtes());
                    double selectedFtesPercentage = selectedFtes / laborRequirement.getFtes();
                    requiredEmployment.addFtes(-selectedFtes);
                    addProductionLabor(new ProductionLabor(requiredEmployment, selectedFtes));
                    requiredFtes = requiredFtes - selectedFtes;

                    double randomLaborHoursVariance = random.nextDouble(-MAX_LABOR_HOURS_VARIANCE, MAX_LABOR_HOURS_VARIANCE);
                    double requiredHours = laborRequirement.getHours() * (1 + randomLaborHoursVariance);

                    double employmentCost = requiredEmployment.getCost() * requiredHours * selectedFtesPercentage;
                    addedMarginalCost += (double) Math.round(100 * employmentCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("\t\t\tMarginal Cost (" + laborRequirement.getJob() + "): " + addedMarginalCost);
                }
            }
        }
        return addedMarginalCost;
    }

    private List<AssetInventory> getAssetInventoryLists(List<AgentAsset> agentAssetList, Asset asset) {
        return agentAssetList.stream()
            .flatMap(a -> a.getAssetInventoryList(asset).stream())
            .toList();
    }

    @Override
    public String toString() {
        return "ProductionLine{" + productionStatus + "}";
    }

}