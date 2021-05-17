package social_media.vk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_media.vk.model.Comment;
import social_media.vk.model.Post;
import social_media.vk.model.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);


    List<Comment> findByUser(User user);

}
