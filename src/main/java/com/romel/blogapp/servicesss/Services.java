package com.romel.blogapp.servicesss;

import com.romel.blogapp.mainStuff.PostBlog;
import com.romel.blogapp.repository.PostBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class Services {

    @Autowired
    private PostBlogRepository postBlogRepository;

    public Optional<PostBlog> getById(Long id){
        return postBlogRepository.findById(id);
    }

    public List<PostBlog> getAll(){
        return postBlogRepository.findAll();
    }

    public PostBlog save(PostBlog postBlog){
        if(postBlog.getId() == null){
            postBlog.setCreatedAt(LocalDateTime.now());
        }
        postBlog.setUpdatedAt(LocalDateTime.now());
        return postBlogRepository.save(postBlog);

    }

    public void delete(PostBlog postBlog) {
        postBlogRepository.delete(postBlog);
    }


}
