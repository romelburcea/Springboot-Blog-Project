package com.romel.blogapp.configStuff;

import com.romel.blogapp.mainStuff.Account;
import com.romel.blogapp.mainStuff.Authority;
import com.romel.blogapp.mainStuff.PostBlog;
import com.romel.blogapp.repository.AuthorityRepository;
import com.romel.blogapp.servicesss.AccountService;
import com.romel.blogapp.servicesss.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private Services services;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception{
        List<PostBlog> postBlog = services.getAll();

        if(postBlog.size() == 0){

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account account = new Account();
            Account account1 = new Account();

            account.setFirstName("user");
            account.setLastName("user");
            account.setEmail("user@gmail.com");
            account.setPassword("password");
            Set<Authority> authorities = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
            account.setAuthorities(authorities);



            account1.setFirstName("admin");
            account1.setLastName("admin");
            account1.setEmail("admin@gmail.com");
            account1.setPassword("password");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);



            accountService.save(account);
            accountService.save(account1);


            PostBlog postBlog1 = new PostBlog();
            postBlog1.setTitle("Title of post 1");
            postBlog1.setBody("This is the body of post 1");
            postBlog1.setAccount(account);

            PostBlog postBlog2 = new PostBlog();
            postBlog2.setTitle("Tile of post 2");
            postBlog2.setBody("This is the body of post 2");
            postBlog2.setAccount(account1);

            services.save(postBlog1);
            services.save(postBlog2);
        }

    }
}
