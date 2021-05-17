package social_media.vk.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_media.vk.dto.CommentRequest;
import social_media.vk.model.Comment;
import social_media.vk.model.User;
import social_media.vk.service.AuthService;
import social_media.vk.service.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("vk/comments")
public class CommentController {

    private final AuthService authService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> comment(@RequestBody CommentRequest commentRequest) {
        commentService.comment(commentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentByPost(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.commentsByPost(postId), HttpStatus.OK);
    }

    @GetMapping("me")
    public ResponseEntity<List<Comment>> getAllPostByUsername() {
        User currentUser = authService.getCurrentUser();
        return new ResponseEntity<>(commentService.getAllMyComments(currentUser), HttpStatus.OK);
    }


}
