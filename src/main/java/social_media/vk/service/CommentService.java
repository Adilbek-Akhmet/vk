package social_media.vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import social_media.vk.dto.CommentRequest;
import social_media.vk.model.Comment;
import social_media.vk.model.Post;
import social_media.vk.model.User;
import social_media.vk.repository.CommentRepository;
import social_media.vk.repository.PostRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void comment(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new IllegalStateException("Post not found"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());
        comment.setPost(post);
        comment.setUser(authService.getCurrentUser());
        comment.setCreatedAt(Instant.now());

        commentRepository.save(comment);
    }

    public List<Comment> commentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("Post not found"));
        return commentRepository.findAllByPost(post);
    }

    public List<Comment> getAllMyComments(User user) {
        return commentRepository.findByUser(user);
    }
}
