package com.workintech.twitter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "retweets",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_retweets_tweet_user",
                columnNames = {"tweet_id", "user_id"}
        )
)
@Data
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
