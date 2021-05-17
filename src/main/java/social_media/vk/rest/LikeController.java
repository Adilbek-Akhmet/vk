package social_media.vk.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import social_media.vk.dto.LikeRequest;
import social_media.vk.model.Post;
import social_media.vk.service.LikeService;

@RestController
@AllArgsConstructor
@RequestMapping("/vk/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> like(@RequestBody LikeRequest likeRequest) {
        likeService.like(likeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
