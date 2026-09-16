package com.simquity.backend.behavior;

import com.simquity.backend.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component("trackHunt")
public class TrackHuntBehavior implements ActivityBehavior {

    private static final String WILD_MEAT = "Wild Meat";
    private static final double BASE_YIELD = 2.0;
    private static final double YIELD_JITTER = 1.5;

    private final AssetRepository assetRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventPublisher eventPublisher;

    private EventType activityYieldType;

    public TrackHuntBehavior(AssetRepository assetRepository,
                              EventTypeRepository eventTypeRepository,
                              EventPublisher eventPublisher) {
        this.assetRepository = assetRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void run(Activity activity, long day) {
        double yield = Math.max(0,
                BASE_YIELD + ThreadLocalRandom.current().nextDouble(-YIELD_JITTER, YIELD_JITTER));

        List<Asset> existing = assetRepository.findByOwnerAgentAndType(activity.getAgent(), WILD_MEAT);
        Asset stock = existing.isEmpty() ? newStock(activity) : existing.get(0);
        stock.setQuantity(stock.getQuantity() + yield);
        assetRepository.save(stock);

        eventPublisher.publish(activityYieldType(), "%s hunted %.1f Wild Meat (Day %d)"
                .formatted(activity.getAgent().getName(), yield, day));
    }

    private Asset newStock(Activity activity) {
        Asset asset = new Asset();
        asset.setType(WILD_MEAT);
        asset.setQuantity(0);
        asset.setOwnerAgent(activity.getAgent());
        return asset;
    }

    private EventType activityYieldType() {
        if (activityYieldType == null) {
            activityYieldType = eventTypeRepository.findByName("Activity Yield")
                    .orElseThrow(() -> new IllegalStateException("Missing EventType: Activity Yield"));
        }
        return activityYieldType;
    }
}
