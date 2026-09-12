package com.simquity.backend;

public record RegionSummaryDto(Long id, String name) {

    public static RegionSummaryDto from(Region region) {
        if (region == null) return null;
        return new RegionSummaryDto(region.getId(), region.getName());
    }

}
