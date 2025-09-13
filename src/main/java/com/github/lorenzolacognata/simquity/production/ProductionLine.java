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
import java.util.stream.Stream;

public class ProductionLine {

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

        System.out.println("[TMP] Produce: " + agentAsset.getAsset());

        double marginalCost = 0.0;

        if (productionStatus == ProductionStatus.NOT_STARTED) {
            productionStatus = ProductionStatus.IN_PROGRESS;
        }

        List<AgentAsset> agentAssetList = Stream.of(
                    agentAsset.getAgent().getPurchasedAgentAssetList(),
                    agentAsset.getAgent().getProducedAgentAssetList()
            )
            .flatMap(List::stream)
            .toList();

        boolean consumableAssetRequirementSatisfied = true;
        // TODO: stop/undo if required quantity is not available

        for (AssetRequirement consumableAssetRequirement : assetProduction.getConsumableAssetRequirementList()) {
            double requiredQuantity = consumableAssetRequirement.getInitialQuantity();
            List<AssetInventory> requiredAssetInventoryList = getAssetInventoryLists(agentAssetList, consumableAssetRequirement.getAsset());
            for (AssetInventory requiredAssetInventory : requiredAssetInventoryList) {
                if (requiredQuantity > 0) {
                    double selectedQuantity = Math.min(requiredQuantity, requiredAssetInventory.getQuantity());
                    requiredAssetInventory.addQuantity(-selectedQuantity);
                    addConsumableProductionInventory(new ProductionInventory(requiredAssetInventory, selectedQuantity));
                    requiredQuantity = requiredQuantity - selectedQuantity;

                    double consumableAssetCost = requiredAssetInventory.getMarginalCost() * selectedQuantity;
                    marginalCost += (double) Math.round(100 * consumableAssetCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("[TMP] Marginal Cost (after Consumable Asset): " + marginalCost);
                }
            }

            if (requiredQuantity > 0) {
                consumableAssetRequirementSatisfied = false;
                break;
            }
        }

        boolean durableAssetRequirementSatisfied = true;
        // TODO: stop/undo if required quantity is not available

        for (AssetRequirement durableAssetRequirement : assetProduction.getDurableAssetRequirementList()) {
            double requiredQuantity = durableAssetRequirement.getInitialQuantity();
            List<AssetInventory> requiredAssetInventoryList = getAssetInventoryLists(agentAssetList, durableAssetRequirement.getAsset());
            for (AssetInventory requiredAssetInventory : requiredAssetInventoryList) {
                if (requiredQuantity > 0) {
                    // TODO: get durable assets cost
                    double selectedQuantity = Math.min(requiredQuantity, requiredAssetInventory.getQuantity());
                    requiredAssetInventory.addQuantity(-selectedQuantity);
                    addDurableProductionInventory(new ProductionInventory(requiredAssetInventory, selectedQuantity));
                    requiredQuantity = requiredQuantity - selectedQuantity;

                    // TODO: replace assetProduction.getDuration() with 1.0 when not all weeks are simulated together
                    double durableAssetCost = requiredAssetInventory.getMarginalCost() * selectedQuantity * (assetProduction.getDuration() / requiredAssetInventory.getAsset().getLifespan());
                    marginalCost += (double) Math.round(100 * durableAssetCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("[TMP] Marginal Cost (after Durable Asset): " + marginalCost);
                }
            }

            if (requiredQuantity > 0) {
                durableAssetRequirementSatisfied = false;
                break;
            }
        }

        Organization organization = (Organization) agentAsset.getAgent();

        boolean laborRequirementSatisfied = true;
        // TODO: stop/undo if required quantity is not available

        for (LaborRequirement laborRequirement : assetProduction.getLaborRequirementList()) {
            double requiredFtes = laborRequirement.getFtes();
            List<Employment> requiredEmploymentList = organization.getEmploymentList(laborRequirement.getJob());
            for (Employment requiredEmployment : requiredEmploymentList) {
                if (requiredFtes > 0) {
                    double selectedFtes = Math.min(requiredFtes, requiredEmployment.getFtes());
                    double selectedFtesPercentage = selectedFtes / laborRequirement.getFtes();
                    requiredEmployment.addFtes(-selectedFtes);
                    addProductionLabor(new ProductionLabor(requiredEmployment, selectedFtes));
                    requiredFtes = requiredFtes - selectedFtes;

                    double employmentCost = requiredEmployment.getCost() * laborRequirement.getHours() * selectedFtesPercentage;
                    marginalCost += (double) Math.round(100 * employmentCost / assetProduction.getOutputQuantity()) / 100;
                    System.out.println("[TMP] Marginal Cost (after Labor): " + marginalCost);
                }
            }

            if (requiredFtes > 0) {
                laborRequirementSatisfied = false;
                break;
            }
        }

        // TODO: restore line below and remove temporary instant production
        //  currentDuration++;
        currentDuration = 99999999;

        if (consumableAssetRequirementSatisfied && durableAssetRequirementSatisfied && laborRequirementSatisfied) {

            if (currentDuration >= assetProduction.getDuration()) {
                productionStatus = ProductionStatus.COMPLETE;

                outputQuantity = assetProduction.getOutputQuantity();

                double marginalCostPlusMargin = (double) Math.round(100 * marginalCost / (1 - agentAsset.getAsset().getTargetGrossMargin())) / 100;
                agentAsset.addAssetInventory(outputQuantity, marginalCostPlusMargin);

                System.out.println("[TMP] Produced: " + outputQuantity);

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