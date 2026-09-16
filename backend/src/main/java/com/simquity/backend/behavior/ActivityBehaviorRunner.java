package com.simquity.backend.behavior;

import com.simquity.backend.Activity;
import com.simquity.backend.ActivityRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActivityBehaviorRunner {

    private final ActivityRepository activityRepository;
    private final Map<String, ActivityBehavior> behaviors;

    public ActivityBehaviorRunner(ActivityRepository activityRepository,
                                   Map<String, ActivityBehavior> behaviors) {
        this.activityRepository = activityRepository;
        this.behaviors = behaviors;
    }

    public void runTick(long day) {
        for (Activity activity : activityRepository.findAll()) {
            if (!"active".equals(activity.getStatus())) continue;
            String behaviorClass = activity.getActivityType().getBehaviorClass();
            ActivityBehavior behavior = behaviors.get(behaviorClass);
            if (behavior != null) {
                behavior.run(activity, day);
            }
        }
    }
}