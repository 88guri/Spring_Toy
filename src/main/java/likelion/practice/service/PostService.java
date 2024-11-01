package likelion.practice.service;

import likelion.practice.entity.Post;
import likelion.practice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String title, String content, List<MultipartFile> imageFiles) throws IOException {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                String filePath = uploadDir + File.separator + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                imagePaths.add(filePath);
            }
        }

        Post post = new Post();
        post.setTitle(title);
        post.setText(content);
        post.setImages(imagePaths);
        post.setCreateDay(LocalDateTime.now());
        post.setUpdateDay(LocalDateTime.now());

        return postRepository.save(post);
    }
}
