package com.example.springredditclone.mapper;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @Mapper
 * First, we annotated the SubredditMapper with @Mapper(componentModel=’spring’)
 * annotation to specify that this interface is a Mapstruct Mapper and Spring should identify it as a component
 * and should be able to inject it into other components like SubredditService.
 */
@Mapper(componentModel = "spring")
public interface SubredditMapper {

    /**
     * The first method inside SubredditMapper class mapSubredditToDto(),
     * contains only one @Mapping annotation for the target field numberOfPosts,
     * in this case, we are mapping from List<Posts> to an Integer,
     * this kind of mapping is not straight forward and we need to write our logic.
     * We can do that by using the expression field and pass the method definition for mapPosts() which returns an Integer.
     * @param subreddit
     * @return
     */
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    /**
     * We can create reverse mappings from SubredditDto to Subreddit by annotating a method with InheritInverseConfiguration.
     * This annotation reverse’s the mapping which exists to convert from Subreddit to SubredditDto
     * @param subreddit
     * @return
     */
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}
