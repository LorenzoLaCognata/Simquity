package com.simquity.backend.content;

import com.simquity.backend.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Order(2)
public class WorldBootstrapSeeder implements CommandLineRunner {

    private static final int POPULATION = 25;

    private static final List<String> PROFESSION_MIX = List.of(
            "Hunter",
            "Forager", "Forager",
            "Tracker",
            "Flintknapper"
    );

    // TODO: Tracker doesn't have its own behavior yet, so it's temporarily
    // mapped onto Track Hunt too (a tracker supporting the hunt).
    // Revisit once Tracker gets a real ActivityType behavior.
    private static final Map<String, String> STARTING_ACTIVITY = Map.of(
            "Hunter", "Track Hunt",
            "Forager", "Forage Plants",
            "Tracker", "Track Hunt",
            "Flintknapper", "Knap Flint"
    );

    private final RegionRepository regionRepository;
    private final AgentRepository agentRepository;
    private final ActivityRepository activityRepository;
    private final ProfessionRepository professionRepository;
    private final ActivityTypeRepository activityTypeRepository;

    public WorldBootstrapSeeder(RegionRepository regionRepository,
                                 AgentRepository agentRepository,
                                 ActivityRepository activityRepository,
                                 ProfessionRepository professionRepository,
                                 ActivityTypeRepository activityTypeRepository) {
        this.regionRepository = regionRepository;
        this.agentRepository = agentRepository;
        this.activityRepository = activityRepository;
        this.professionRepository = professionRepository;
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (agentRepository.count() > 0) return;

        Region region = new Region();
        region.setName("Ancestral Valley");
        region = regionRepository.save(region);

        List<Profession> professions = professionRepository.findAll();
        List<ActivityType> activityTypes = activityTypeRepository.findAll();

        for (int i = 0; i < POPULATION; i++) {
            String professionName = PROFESSION_MIX.get(i % PROFESSION_MIX.size());
            Profession profession = byName(professions, Profession::getName, professionName);
            ActivityType activityType = byName(activityTypes, ActivityType::getName, STARTING_ACTIVITY.get(professionName));

            Agent agent = new Agent();
            agent.setName(professionName + " " + (i + 1));
            agent.setBirthDate(LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(16, 50)));
            agent.setRegion(region);
            agent = agentRepository.save(agent);

            Activity activity = new Activity();
            activity.setAgent(agent);
            activity.setActivityType(activityType);
            activity.setProfession(profession);
            activity.setStartDate(LocalDate.now());
            activity.setStatus("active");
            activityRepository.save(activity);
        }
    }

    private <T> T byName(List<T> items, java.util.function.Function<T, String> nameFn, String name) {
        return items.stream()
                .filter(item -> nameFn.apply(item).equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing seed row: " + name));
    }
}