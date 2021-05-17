package social_media.vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import social_media.vk.dto.LikeRequest;
import social_media.vk.model.Like;
import social_media.vk.model.Post;
import social_media.vk.repository.LikeRepository;
import social_media.vk.repository.PostRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final LikeRepository likeRepository;

    public void like(LikeRequest likeRequest) {
        Post post = postRepository.findById(likeRequest.getPostId()).orElseThrow(() -> new IllegalStateException("Post not found"));
        Like byUserAndPost = likeRepository.findByUserAndPost(authService.getCurrentUser(), post)
                .orElseGet(() -> {
                    Like like = new Like();
                    like.setIsLiked(false);
                    like.setPost(post);
                    like.setUser(authService.getCurrentUser());
                    return like;
                });
        if (byUserAndPost.getIsLiked().equals(true) && !likeRequest.getIsLike()) {
            byUserAndPost.setIsLiked(false);
            decreaseNumberOfLikes(post);
        } else {
            byUserAndPost.setIsLiked(true);
            increaseNumberOfLikes(post);
        }
        likeRepository.save(byUserAndPost);
        postRepository.save(post);
    }

    public void increaseNumberOfLikes(Post post) {
        post.setNumberOfLikes(post.getNumberOfLikes() + 1);

    }

    public void decreaseNumberOfLikes(Post post) {
        post.setNumberOfLikes(post.getNumberOfLikes() - 1);

    }
}
