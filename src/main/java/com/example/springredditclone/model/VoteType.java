package com.example.springredditclone.model;

/**
 * Class: VoteType Entity
 * An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables).
 *
 * To create an enum, use the enum keyword (instead of class or interface), and separate the constants with a comma.
 * Note that they should be in uppercase letters:
 *
 * You can add fields to a Java enum. Thus, each constant enum value gets these fields.
 * The field values must be supplied to the constructor of the enum when defining the constants.
 */
public enum VoteType {
    UPVOTE(1), //calls constructor with value 1
    DOWNVOTE(-1), //calls constructor with value -1
    ; //semicolon needed when fields / methods follow

    VoteType(int direction) {
    }
}
