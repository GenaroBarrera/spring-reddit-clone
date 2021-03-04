package com.example.springredditclone.controller;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    /**
     * createComment() Post endpoint
     * The createComment takes an object of type CommentsDtos as input,
     * the creation logic is handled inside the CommentService class
     * Once the comment is created we are returning a 201 response back to the client (CREATED).
     * @param commentsDto
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    /**
     * getAllCommentsForPost() Get endpoint
     * After that we have the getAllCommentsForPost() and getAllCommentsByUser() methods
     * where we are retrieving the relevant comments for given user/post from the CommentService.
     * @param postId
     * @return
     */
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    /**
     * getAllCommentsByUser() Get endpoint
     * After that we have the getAllCommentsForPost() and getAllCommentsByUser() methods
     * where we are retrieving the relevant comments for given user/post from the CommentService.
     * @param userName
     * @return
     */
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
}
