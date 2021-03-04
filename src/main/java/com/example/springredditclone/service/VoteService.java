package com.example.springredditclone.service;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        /**
         * Line 28-29: Inside the vote() method of VoteService,
         * we are first retrieving the Post object from the database using the PostRepository.findById() method.
         * If there is no Post with that id we are throwing a PostNotFoundException.
         */
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        /**
         * Line 30: We are retrieving the recent Vote submitted by the currently logged-in user for the given Post.
         * We are doing this using the method – findTopByPostAndUserOrderByVoteIdDesc()
         */
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        /**
         * Line 31-36: We are first checking whether the user has already performed the same Vote action or not.
         * ie. If the user has already upvoted a particular post, he/she is not allowed to upvote that particular post again.
         */
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }

        /**
         * Line 37-43: We are setting the voteCount field according to the VoteType provided by the user,
         * and then we are mapping the VoteDto object to Vote object and saving the Vote as well as Post objects to the database.
         */
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    /**
     * mapToVote()
     * We are using the builder() from Lombok’s @Builder inside the mapToVote method.
     * This builder method is an implementation of the Builder Design Pattern.
     * If you want to learn more about Builder Pattern or any design pattern, I highly recommend you to buy the Head First Design Pattern Book
     * @param voteDto
     * @param post
     * @return
     */
    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
