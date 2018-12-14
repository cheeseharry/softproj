package com.teamrandom.softproj.controller;

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
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value="/admin/manageAccounts", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("alertMessage") String alertMessage){
        ModelAndView modelAndView = new ModelAndView(   );
        User user = getCurrentUser();
        if (user == null) modelAndView.setViewName("login");
        else {
            modelAndView.addObject("alertMessage", alertMessage);
            modelAndView.addObject("user_roles", user.getRoles());
            modelAndView.addObject("user", user);
            List<User> reviewers = userService.getReviewers();
            List<User> allUsers = userService.getAllUsers();
            modelAndView.addObject("users", allUsers);
            modelAndView.addObject("reviewers", reviewers);
            modelAndView.addObject("submitter", roleRepository.findByRole("SUBMITTER"));
            modelAndView.addObject("adminRole", roleRepository.findByRole("ADMIN"));
            modelAndView.setViewName("/admin/manageAccounts");
        }
        return modelAndView;
    }

    @RequestMapping(value="/access-denied", method = RequestMethod.GET)
    public ModelAndView accessDenied(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
        return modelAndView;
    }

    @RequestMapping(value="/admin/editUser", method = RequestMethod.GET)
    public ModelAndView editUser(@ModelAttribute("userName") String username){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(username);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/admin/editUser");
        return modelAndView;
    }

    @RequestMapping(value = "/saveEditedUser", method = RequestMethod.POST)
    public ModelAndView saveEditUser(@Valid User user, BindingResult bindingResult, @RequestParam("oldUser") String oldUsername) {
        ModelAndView modelAndView = new ModelAndView();
        User oldUser = userService.findUserByUsername(oldUsername);
        System.out.println("hululu: " + oldUser.getName());
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null && !userExists.equals(oldUser) ) {
            modelAndView.addObject("message", "User already exists with given username");
            modelAndView.addObject("userName", oldUser.getUsername());
            modelAndView.setViewName("/admin/editUser");
            return modelAndView;
        }
        User userForEmail = userService.findUserByEmail(user.getEmail());
        if(userForEmail != null && !userForEmail.equals(oldUser)){
            modelAndView.addObject("message", "User already exists with given email");
            modelAndView.addObject("userName", oldUser.getUsername());
            modelAndView.setViewName("/admin/editUser");
            return modelAndView;
        }
        oldUser.setName(user.getName());
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPreferredRole(user.getPreferredRole());
        Role newRole = roleRepository.findByRole(user.getPreferredRole());
        oldUser.getRoles().add(newRole);
        User savedUser = userRepository.save(oldUser);
        System.out.println("hehehe: " + savedUser.getName());
        modelAndView.addObject("message", "User has been edited successfully");
        modelAndView.addObject("userName", savedUser.getUsername());
        modelAndView.setViewName("/admin/editUser");
        return modelAndView;
    }


    @RequestMapping(value="/editUser", method = RequestMethod.POST)
    public ModelAndView getUserToBeEdited(@RequestParam("editUser")String username){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/editUser");
        modelAndView.addObject("userName", username);
        return modelAndView;
    }

    @RequestMapping(value="/admin/deleteUser", method = RequestMethod.POST)
    public ModelAndView DeleteUser(@RequestParam("username")String username){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(username);
        System.out.println("user to be deleted: " + user.getName());
        userService.deleteUser(user);
        modelAndView.addObject("alertMessage","User successfully deleted");
        modelAndView.setViewName("redirect:/admin/manageAccounts");
        return modelAndView;
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }
}
