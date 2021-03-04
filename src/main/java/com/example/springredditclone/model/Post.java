package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Class: Post Entity
 */

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    /**
     * @lob Specifies that a persistent property or field should be
     * persisted as a large object to a database-supported large object type.
     */
    @Lob
    private String description;
    /**
     *  From a youtube comment on postman 500 message is empty error
     *  "Check your db table "Post" it's likely that column "vote_count" is empty.
     *  Manually add "0" there. And it will work (Postman create vote test). That happens because you try to decrement a non-existing value. Hope it helps"
     *  TODO how do initialize voteCount to 0 instead of null?
     */
    @ColumnDefault("0")
    private Integer voteCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;
}
