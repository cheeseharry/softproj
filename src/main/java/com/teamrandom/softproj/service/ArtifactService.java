package com.teamrandom.softproj.service;

import com.teamrandom.softproj.businessObject.Artifact;
import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.repository.ArtifactRepository;
import com.teamrandom.softproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("artifactService")
public class ArtifactService {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public void uploadArtifact(){

    }

    public Artifact findByname(String name) {return artifactRepository.findByName(name);}

    public List<Artifact> getAllArtifact(){
        return artifactRepository.findAllArtifacts();
    }

    public void createArtifact(Artifact artifact){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        artifact.setTeam(user.getTeam());
        artifactRepository.save(artifact);
    }
}
