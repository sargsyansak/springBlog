package com.example.springblog.controller;

import com.example.springblog.model.Category;
import com.example.springblog.model.Post;
import com.example.springblog.repository.CategoryRepository;
import com.example.springblog.repository.PostRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller

public class PostController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/post/add")
    public String addPostView(ModelMap map) {
        map.addAttribute("categories", categoryRepository.findAll());
        return "addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@ModelAttribute Post post, @RequestParam("picture") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        file.transferTo(picture);
        post.setPicUrl(fileName);
        post.setDate(new Date());
        postRepository.save(post);
        return "redirect:/";

    }

    @GetMapping("/post/getImage")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/post/detailPost")
    public String currentPost(ModelMap modelMap, @RequestParam("id") int id) {
        Post post = postRepository.getOne(id);
        modelMap.addAttribute("post", post);
        return "detailPost";

    }

    @GetMapping("/category/detailCategory")
    public String currentCategory(ModelMap modelMap, @RequestParam("id") int id) {
        Category category = categoryRepository.getOne(id);
        List<Post> posts = postRepository.findAll();
        List<Post> getPosts = new ArrayList<>();
        for (Post post : posts) {
            for (Category cat : post.getCategories()) {
                if (cat == category) {
                    getPosts.add(post);
                }

            }

        }
        if (getPosts != null) {
            modelMap.addAttribute("posts", getPosts);

            return "detailCategory";
        }
        return "redirect:/";
    }

    @GetMapping("/post/delete")
    public String deleteUser(@RequestParam("id") int id) {
        postRepository.deleteById(id);
        return "redirect:/";
    }
}
