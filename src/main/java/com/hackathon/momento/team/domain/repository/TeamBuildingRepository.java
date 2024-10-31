package com.hackathon.momento.team.domain.repository;

import com.hackathon.momento.team.domain.TeamBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamBuildingRepository extends JpaRepository<TeamBuilding, Long> {
}
