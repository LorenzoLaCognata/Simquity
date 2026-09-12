package com.simquity.backend;

public record RegionDto(Long id, String name, Double latitude, Double longitude) {

    public static RegionDto from(Region region) {
        return new RegionDto(
                region.getId(),
                region.getName(),
                region.getLatitude(),
                region.getLongitude()
        );
    }

}
