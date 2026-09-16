package com.simquity.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByOwnerAgentAndType(Agent ownerAgent, String type);
}