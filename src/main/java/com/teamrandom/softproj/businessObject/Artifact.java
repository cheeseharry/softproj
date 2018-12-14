package com.teamrandom.softproj.businessObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artifact")
public class Artifact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private int id;
    @Column(name = "filename")
    @NotEmpty(message = "*Please name your file")
    private String name;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Artifact(String name){
        this.name=name;
    }
}
