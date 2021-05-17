package social_media.vk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social_media.vk.model.Post;
import social_media.vk.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByUser(User user);
}
