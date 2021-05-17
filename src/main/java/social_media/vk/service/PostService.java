package social_media.vk.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_media.vk.dto.PostRequest;
import social_media.vk.exception.PostNotFoundException;
import social_media.vk.model.Post;
import social_media.vk.model.User;
import social_media.vk.repository.PostRepository;
import social_media.vk.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();

        Post post = Post.builder()
                .id(postRequest.getId())
                .title(postRequest.getTitle())
                .imageUrl(postRequest.getImageUrl())
                .description(postRequest.getDescription())
                .createdDate(Instant.now())
                .user(currentUser)
                .numberOfLikes(0)
                .build();

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id).
                orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> getPostByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        return postRepository.findPostByUser(user);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
