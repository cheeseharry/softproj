package com.teamrandom.softproj.repository;


import com.teamrandom.softproj.businessObject.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    Artifact findByName(String Name);

    @Query(value = "SELECT at FROM Artifact at")
    List<Artifact> findAllArtifacts();
}
