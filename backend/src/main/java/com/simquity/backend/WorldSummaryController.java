package com.simquity.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class WorldSummaryController {

    private final RegionRepository regionRepository;
    private final AgentRepository agentRepository;
    private final OrganizationRepository organizationRepository;
    private final RelationshipRepository relationshipRepository;
    private final SectorRepository sectorRepository;
    private final ProfessionRepository professionRepository;
    private final SkillRepository skillRepository;
    private final AgentSkillRepository agentSkillRepository;
    private final AssetRepository assetRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final MarketRepository marketRepository;
    private final EmploymentRepository employmentRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityRepository activityRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventRepository eventRepository;
    private final TechnologyRepository technologyRepository;
    private final HouseholdRepository householdRepository;
    private final SchoolRepository schoolRepository;
    private final GuildRepository guildRepository;
    private final GovernmentRepository governmentRepository;
    private final PolicyRepository policyRepository;
    private final FactoryRepository factoryRepository;
    private final RouteRepository routeRepository;
    private final LanguageRepository languageRepository;

    public WorldSummaryController(
            RegionRepository regionRepository, AgentRepository agentRepository,
            OrganizationRepository organizationRepository, RelationshipRepository relationshipRepository,
            SectorRepository sectorRepository, ProfessionRepository professionRepository,
            SkillRepository skillRepository, AgentSkillRepository agentSkillRepository,
            AssetRepository assetRepository, CurrencyRepository currencyRepository,
            TransactionRepository transactionRepository, MarketRepository marketRepository,
            EmploymentRepository employmentRepository, ActivityTypeRepository activityTypeRepository,
            ActivityRepository activityRepository, EventTypeRepository eventTypeRepository,
            EventRepository eventRepository, TechnologyRepository technologyRepository,
            HouseholdRepository householdRepository, SchoolRepository schoolRepository,
            GuildRepository guildRepository, GovernmentRepository governmentRepository,
            PolicyRepository policyRepository, FactoryRepository factoryRepository,
            RouteRepository routeRepository, LanguageRepository languageRepository) {
        this.regionRepository = regionRepository;
        this.agentRepository = agentRepository;
        this.organizationRepository = organizationRepository;
        this.relationshipRepository = relationshipRepository;
        this.sectorRepository = sectorRepository;
        this.professionRepository = professionRepository;
        this.skillRepository = skillRepository;
        this.agentSkillRepository = agentSkillRepository;
        this.assetRepository = assetRepository;
        this.currencyRepository = currencyRepository;
        this.transactionRepository = transactionRepository;
        this.marketRepository = marketRepository;
        this.employmentRepository = employmentRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.activityRepository = activityRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventRepository = eventRepository;
        this.technologyRepository = technologyRepository;
        this.householdRepository = householdRepository;
        this.schoolRepository = schoolRepository;
        this.guildRepository = guildRepository;
        this.governmentRepository = governmentRepository;
        this.policyRepository = policyRepository;
        this.factoryRepository = factoryRepository;
        this.routeRepository = routeRepository;
        this.languageRepository = languageRepository;
    }

    @GetMapping("/api/world/summary")
    public Map<String, Long> summary() {
        Map<String, Long> counts = new LinkedHashMap<>();
        // World structure
        counts.put("regions", regionRepository.count());
        counts.put("agents", agentRepository.count());
        counts.put("organizations", organizationRepository.count());
        counts.put("relationships", relationshipRepository.count());
        // Economy
        counts.put("sectors", sectorRepository.count());
        counts.put("professions", professionRepository.count());
        counts.put("skills", skillRepository.count());
        counts.put("agentSkills", agentSkillRepository.count());
        counts.put("assets", assetRepository.count());
        counts.put("currencies", currencyRepository.count());
        counts.put("transactions", transactionRepository.count());
        counts.put("markets", marketRepository.count());
        counts.put("employments", employmentRepository.count());
        // Activity & events
        counts.put("activityTypes", activityTypeRepository.count());
        counts.put("activities", activityRepository.count());
        counts.put("eventTypes", eventTypeRepository.count());
        counts.put("events", eventRepository.count());
        counts.put("technologies", technologyRepository.count());
        // Organizational roles
        counts.put("households", householdRepository.count());
        counts.put("schools", schoolRepository.count());
        counts.put("guilds", guildRepository.count());
        counts.put("governments", governmentRepository.count());
        counts.put("policies", policyRepository.count());
        counts.put("factories", factoryRepository.count());
        // World connectivity
        counts.put("routes", routeRepository.count());
        counts.put("languages", languageRepository.count());
        return counts;
    }

}