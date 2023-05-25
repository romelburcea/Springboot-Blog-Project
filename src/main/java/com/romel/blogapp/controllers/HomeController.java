package com.romel.blogapp.controllers;

import com.romel.blogapp.mainStuff.PostBlog;
import com.romel.blogapp.servicesss.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private Services services;

    @GetMapping("/")
    public String home(Model model){
        List<PostBlog> postBlogs = services.getAll();
        model.addAttribute("posts", postBlogs);
        return "home";
    }


}
