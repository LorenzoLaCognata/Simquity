package com.simquity.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {

    private final RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/api/regions")
    public List<RegionDto> listRegions() {
        return regionRepository.findAll()
                .stream()
                .map(RegionDto::from)
                .toList();
    }

}
