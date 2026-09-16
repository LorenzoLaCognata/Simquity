package com.simquity.backend.behavior;

import com.simquity.backend.Activity;
import com.simquity.backend.Asset;
import com.simquity.backend.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component("trackHunt")
public class TrackHuntBehavior implements ActivityBehavior {

    private static final String WILD_MEAT = "Wild Meat";
    private static final double BASE_YIELD = 2.0;
    private static final double YIELD_JITTER = 1.5;

    private final AssetRepository assetRepository;

    public TrackHuntBehavior(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public void run(Activity activity, long day) {
        double yield = Math.max(0,
                BASE_YIELD + ThreadLocalRandom.current().nextDouble(-YIELD_JITTER, YIELD_JITTER));

        List<Asset> existing = assetRepository.findByOwnerAgentAndType(activity.getAgent(), WILD_MEAT);
        Asset stock = existing.isEmpty() ? newStock(activity) : existing.get(0);
        stock.setQuantity(stock.getQuantity() + yield);
        assetRepository.save(stock);
    }

    private Asset newStock(Activity activity) {
        Asset asset = new Asset();
        asset.setType(WILD_MEAT);
        asset.setQuantity(0);
        asset.setOwnerAgent(activity.getAgent());
        return asset;
    }
}