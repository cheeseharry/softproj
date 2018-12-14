package com.teamrandom.softproj.service;

import com.teamrandom.softproj.businessObject.Team;
import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.repository.TeamRepository;
import com.teamrandom.softproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teamService")
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public void createTeam(Team team){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        user.setTeam(teamRepository.save(team));
        userRepository.save(user);
    }

    public Team findByName(String name){
        return teamRepository.findByName(name);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAllTeams();
    }
}
