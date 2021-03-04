package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Class: Comment Entity
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Project Lombok's @Builder is a useful mechanism for using the Builder pattern without writing boilerplate code.
 * We can apply this annotation to a Class or a method.
 * When to Use Builder Pattern
 * When the process involved in creating an object is extremely complex, with lots of mandatory and optional parameters
 * When an increase in the number of constructor parameters leads to a large list of constructors
 * When client expects different representations for the object that's constructed
 */
@Builder
/**
 * A class which should be persisted in a database it must be annotated with javax.persistence.Entity.
 * Such a class is called Entity. JPA uses a database table for every entity.
 * Persisted instances of the class will be represented as one row in the table.
 */
@Entity
//do we not need the @table annotation?
public class Comment {
    @Id //denotes this column as the primary key
    /**
     * @GeneratedValue specifies the generation strategies for the values of primary keys.
     * SEQUENCE allows using a database sequence object to generate identifier values.
     * This is the best generation strategy when using JPA and Hibernate.
     */
    @GeneratedValue(strategy = SEQUENCE)
    //what about the @column annotation? is it not required?
    private Long id;

    @NotEmpty //constraint to ensure that this column isn't empty
    private String text;

    @ManyToOne(fetch = LAZY)
    /**
     * NOTE: This was to get rid of the annoying red error highlights under the @JoinColumn annotations
     * For those who just want to disable this check,
     * go to Intellij IDEA -> Preferences -> Search
     * "Unresolved database references in annotations" and uncheck it.
     */
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    private Instant createdDate;

    /**
     * @ManyToOneMany employees can share the same status.
     * So, employee to employeeStatus is a many to one relation.
     * @ManyToOne annotation can be used for the same.
     *
     * (ftech = Lazy)
     * Eager Loading is a design pattern in which data initialization occurs on the spot
     * Lazy Loading is a design pattern which is used to defer initialization of an object as long as it's possible
     * Example One User can have multiple OrderDetails.
     * In eager loading strategy, if we load the User data, it will also load up all orders associated with it and will store it in a memory.
     * But, when lazy loading is enabled, if we pull up a UserLazy, OrderDetail data won't be initialized and loaded into a memory
     * until an explicit call is made to it.
     */
    @ManyToOne(fetch = LAZY) //When working with an ORM, data fetching/loading can be classified into two types: eager and lazy.
    /**
     * @JoinColumn annotation is used for one-to-one or many-to-one associations
     * when foreign key is held by one of the entities.
     */
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
