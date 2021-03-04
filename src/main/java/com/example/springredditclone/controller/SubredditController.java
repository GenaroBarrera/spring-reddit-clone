package com.example.springredditclone.controller;

/**
 * Class: SubredditController controller
 * Now to test the above JWT validation, we need a secured API,
 * so let’s start by implementing the API to Create and Read Subreddits inside SubredditController.java
 */
import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    /**
     * getAllSubreddits()
     * Get All Subreddits	GET	/api/subreddit
     * For getAllSubreddits() and getSubreddit() call, it is straight forward.
     * We are reading all/required the subreddits from DB, mapping them to SubredditDTO and returning them back to the client.
     * As we just read the data here, we marked the getAll() and getSubreddit() methods inside the SubredditService.java as @Transaction(readonly=true)
     * @return
     */
    @GetMapping
    public List<SubredditDto> getAllSubreddits() {
        return subredditService.getAll();
    }

    /**
     * getSubreddit()
     * For getAllSubreddits() and getSubreddit() call, it is straight forward.
     * We are reading all/required the subreddits from DB, mapping them to SubredditDTO and returning them back to the client.
     * As we just read the data here, we marked the getAll() and getSubreddit() methods inside the SubredditService.java as @Transaction(readonly=true)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public SubredditDto getSubreddit(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }

    /**
     * crate() REST endpoint to create a subreddit
     * Create Subreddit	POST	/api/subreddit
     * For create() call, we are mapping the data from SubredditDto to Subreddit and saving it in SubredditRepository.
     * @param subredditDto
     * @return
     */
    @PostMapping
    public SubredditDto create(@RequestBody @Valid SubredditDto subredditDto) {
        return subredditService.save(subredditDto);
    }
}

