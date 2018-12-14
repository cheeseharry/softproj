package com.teamrandom.softproj.repository;


import com.teamrandom.softproj.businessObject.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String Name);

    @Query(value = "SELECT t FROM Team t")
    List<Team> findAllTeams();
}
