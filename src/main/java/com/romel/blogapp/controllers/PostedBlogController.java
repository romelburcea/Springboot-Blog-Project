package com.romel.blogapp.controllers;

import com.romel.blogapp.mainStuff.Account;
import com.romel.blogapp.mainStuff.PostBlog;
import com.romel.blogapp.servicesss.AccountService;
import com.romel.blogapp.servicesss.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostedBlogController {

    @Autowired
    private Services services;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Optional<PostBlog> optionalPostBlog = services.getById(id);
        if (optionalPostBlog.isPresent()) {
            PostBlog postBlog = optionalPostBlog.get();
            model.addAttribute("post", postBlog);
            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model) {
        Optional<Account> optionalAccount = accountService.findByEmail("user@gmail.com");
        if (optionalAccount.isPresent()) {
            PostBlog postBlog = new PostBlog();
            postBlog.setAccount(optionalAccount.get());
            model.addAttribute("post", postBlog);
            return "post_new";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute PostBlog postBlog) {
        services.save(postBlog);
        return "redirect:/posts/" + postBlog.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model) {
        Optional<PostBlog> optionalPostBlog = services.getById(id);
        if (optionalPostBlog.isPresent()) {
            PostBlog postBlog = optionalPostBlog.get();
            model.addAttribute("post", postBlog);
            return "post_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, PostBlog postBlog, BindingResult result, Model model) {
        Optional<PostBlog> optionalPostBlog = services.getById(id);
        if (optionalPostBlog.isPresent()) {
            PostBlog existingPost = optionalPostBlog.get();

            existingPost.setTitle(postBlog.getTitle());
            existingPost.setBody(postBlog.getBody());

            services.save(existingPost);
        }
        return "redirect:/posts/" + postBlog.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){
        Optional<PostBlog> optionalPost = services.getById(id);
        if(optionalPost.isPresent()){
            PostBlog postBlog = optionalPost.get();

            services.delete(postBlog);
            return "redirect:/";
        } else {
            return "404";
        }
    }
}

