package com.teamrandom.softproj.controller;

import com.teamrandom.softproj.businessObject.Team;
import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.repository.RoleRepository;
import com.teamrandom.softproj.repository.UserRepository;
import com.teamrandom.softproj.role.Role;
import com.teamrandom.softproj.service.TeamService;
import com.teamrandom.softproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("alertMessage") String alertMessage){
        createAdmin();
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(auth.getName());
        User user = getCurrentUser();
        if (user == null) modelAndView.setViewName("login");
        else {
            modelAndView.addObject("alertMessage", alertMessage);
            modelAndView.addObject("user_roles", user.getRoles());
            modelAndView.addObject("user", user);
            modelAndView.addObject("team", new Team());
            List<Team> teams = teamService.getAllTeams();
            modelAndView.addObject("teams", teams);
            List<User> reviewers = userService.getReviewers();
            modelAndView.addObject("reviewers", reviewers);
            modelAndView.addObject("submitter", roleRepository.findByRole("SUBMITTER"));
            modelAndView.addObject("adminRole", roleRepository.findByRole("ADMIN"));
            modelAndView.addObject("reviewerRole", roleRepository.findByRole("REVIEWER"));
            modelAndView.setViewName("home");
            modelAndView.addObject("helloMessage", "Hello, " + user.getName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/createteam", method = RequestMethod.POST)
    public ModelAndView createNewTeam(@Valid Team team, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        Team teamExists = teamService.findByName(team.getName());
        if (teamExists != null) {
            bindingResult
                    .rejectValue("name", "error.team",
                            "There is already a team registered with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("alertMessage", "Team with given name already exists please join the team instead");

        } else {
            teamService.createTeam(team);
            modelAndView.addObject("alertMessage", "Created a team successfully. You are automatically joined");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/jointeam", method = RequestMethod.POST)
    public ModelAndView createNewTeam(@RequestParam("selectedTeam") String teamName){
        ModelAndView modelAndView = new ModelAndView("redirect:/");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(auth.getName());
        User user = getCurrentUser();
        Team team = teamService.findByName(teamName);
        user.setTeam(team);
        userRepository.save(user);
        modelAndView.addObject("alertMessage", "Successfully joined the team "+ teamName);
        return modelAndView;
    }

    @RequestMapping(value = "/addLecturer", method = RequestMethod.POST)
    public ModelAndView addLecturer(@RequestParam("selectedLecturer") String lecturerUsername){
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        User user = userService.findUserByUsername(lecturerUsername);
        Role lecturerRole = roleRepository.findByRole("LECTURER");
        user.getRoles().add(lecturerRole);
        userRepository.save(user);
        modelAndView.addObject("alertMessage", "Successfully assigned the Lecturer");
        return modelAndView;
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }

    private void createAdmin(){
        User admin = userService.findUserByUsername("admin");
        if (admin == null){
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("admin");
            admin.setEmail("admin@admin.com");
            admin.setPreferredRole("ADMIN");
            userService.saveUser(admin);
        }
    }
}
