package com.hackathon.momento.team.domain;

import com.hackathon.momento.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_info_id")
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teamInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamBuilding> teamBuildings = new ArrayList<>();

    @Builder
    private TeamInfo(String teamName) {
        this.teamName = teamName;
    }
}
