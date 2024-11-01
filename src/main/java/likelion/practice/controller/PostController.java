package likelion.practice.controller;

import likelion.practice.entity.Post;
import likelion.practice.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 쓰기 기능
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam List<MultipartFile> images) {

        if (images.size() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Post post = postService.createPost(title, content, images);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
