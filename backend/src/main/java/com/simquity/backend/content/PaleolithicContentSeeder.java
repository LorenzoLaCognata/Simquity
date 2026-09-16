package com.simquity.backend.content;

import com.simquity.backend.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class PaleolithicContentSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;
    private final ProfessionRepository professionRepository;
    private final SkillRepository skillRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final EventTypeRepository eventTypeRepository;

    public PaleolithicContentSeeder(SectorRepository sectorRepository,
                                     ProfessionRepository professionRepository,
                                     SkillRepository skillRepository,
                                     ActivityTypeRepository activityTypeRepository,
                                     EventTypeRepository eventTypeRepository) {
        this.sectorRepository = sectorRepository;
        this.professionRepository = professionRepository;
        this.skillRepository = skillRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (sectorRepository.count() > 0) return;

        Sector foraging = sector("Foraging", "FORAGING");
        Sector hunting = sector("Big Game Hunting", "HUNTING");
        Sector crafting = sector("Primitive Crafting", "CRAFTING");

        profession("Hunter", hunting);
        profession("Forager", foraging);
        profession("Flintknapper", crafting);
        profession("Tracker", hunting);

        skill("Animal Tracking", "Hunting");
        skill("Plant Recognition", "Foraging");
        skill("Flint Knapping", "Crafting");
        skill("Hide Tanning", "Crafting");

        activityType("Track Hunt", "trackHunt");
        activityType("Forage Plants", "foragePlants");
        activityType("Knap Flint", "knapFlint");
        activityType("Tan Hide", "tanHide");

        eventType("Agent Spawned");
        eventType("Activity Yield");
    }

    private Sector sector(String name, String code) {
        Sector s = new Sector();
        s.setName(name);
        s.setCode(code);
        return sectorRepository.save(s);
    }

    private void profession(String name, Sector sector) {
        Profession p = new Profession();
        p.setName(name);
        p.setSector(sector);
        professionRepository.save(p);
    }

    private void skill(String name, String category) {
        Skill s = new Skill();
        s.setName(name);
        s.setCategory(category);
        skillRepository.save(s);
    }

    private void activityType(String name, String behaviorClass) {
        ActivityType at = new ActivityType();
        at.setName(name);
        at.setBehaviorClass(behaviorClass);
        activityTypeRepository.save(at);
    }

    private void eventType(String name) {
        EventType et = new EventType();
        et.setName(name);
        eventTypeRepository.save(et);
    }
}
