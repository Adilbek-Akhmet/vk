package social_media.vk.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import social_media.vk.dto.PostRequest;
import social_media.vk.model.Post;
import social_media.vk.service.PostService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vk/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public void createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<Post>> getPostByUser(@PathVariable String username) {
        return new ResponseEntity<>(postService.getPostByUsername(username), HttpStatus.OK);
    }


}
