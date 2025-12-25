package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_IdAndTweet_Id(Long userId, Long tweetId);


}
