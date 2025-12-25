package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            TweetRepository tweetRepository
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Comment createComment(Long userId, Long tweetId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found."));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not allowed to update this comment.");
        }

        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not allowed to delete this comment.");
        }

        commentRepository.delete(comment);
    }
}
