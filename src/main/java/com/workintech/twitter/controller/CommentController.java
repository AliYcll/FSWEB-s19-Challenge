package com.workintech.twitter.controller;

import com.workintech.twitter.dto.requests.CommentRequest;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(@RequestBody @Valid CommentRequest request) {
        return commentService.createComment(
                request.getUserId(),
                request.getTweetId(),
                request.getContent()
        );
    }

    @PutMapping("/{id}")
    public Comment updateComment(
            @PathVariable("id") Long commentId,
            @RequestParam Long userId,
            @RequestBody @Valid CommentRequest request
    ) {
        return commentService.updateComment(commentId, userId, request.getContent());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
    }
}
