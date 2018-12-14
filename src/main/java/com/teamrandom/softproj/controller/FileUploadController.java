package com.teamrandom.softproj.controller;

import com.teamrandom.softproj.businessObject.Artifact;
import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.service.ArtifactService;
import com.teamrandom.softproj.service.mail.EmailService;
import com.teamrandom.softproj.service.mail.EmailServiceImpl;
import com.teamrandom.softproj.service.storage.StorageFileNotFoundException;
import com.teamrandom.softproj.service.storage.StorageService;
import com.teamrandom.softproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final UserService userService;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
        emailService = new EmailServiceImpl();
    }

    @Autowired
    public EmailService emailService;

    @Autowired
    public ArtifactService artifactService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String listUploadedFiles(Model model) throws IOException{

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString()
        ).collect(Collectors.toList()));

        return "uploadForm";
    }

    @RequestMapping(value="/upload/files/{filename:.+}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value="/",method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file")MultipartFile file,
                                   RedirectAttributes redirectAttributes){
        storageService.store(file);

        artifactService.createArtifact(new Artifact(file.getOriginalFilename()));

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        // Sends email after uploading file successfully
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        emailService.sendSimpleMessage(user.getEmail(), "Upload File Success", "Dear " + user.getName() + ",\n\nYour file upload, " + file.getOriginalFilename() + ", was successful.\n\n-SoftProj");

        return "redirect:/upload";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }

}
