package com.example.springblog.controller;

import com.example.springblog.model.Category;
import com.example.springblog.model.Post;
import com.example.springblog.repository.CategoryRepository;
import com.example.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class MainController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")

    public String main(ModelMap modelMap) {
        List<Post> postList = postRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        modelMap.addAttribute("posts", postList);
        modelMap.addAttribute("categories", categoryList);
        return "index";
    }
}
