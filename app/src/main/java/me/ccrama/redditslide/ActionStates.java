package me.ccrama.redditslide;

import net.dean.jraw.models.Comment;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.VoteDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlo_000 on 2/26/2016.
 */
public class ActionStates {

    private ActionStates() {}

    private static final List<String> upVotedFullnames = new ArrayList<>();
    private static final List<String> downVotedFullnames = new ArrayList<>();

    private static final List<String> unvotedFullnames = new ArrayList<>();
    private static final List<String> savedFullnames = new ArrayList<>();
    private static final List<String> unSavedFullnames = new ArrayList<>();

    public static List<String> getUpVotedFullnames() {
        return upVotedFullnames;
    }

    public static List<String> getDownVotedFullnames() {
        return downVotedFullnames;
    }

    public static List<String> getUnvotedFullnames() {
        return unvotedFullnames;
    }

    public static List<String> getSavedFullnames() {
        return savedFullnames;
    }

    public static List<String> getUnSavedFullnames() {
        return unSavedFullnames;
    }

    public static VoteDirection getVoteDirection(PublicContribution s) {
        if (upVotedFullnames.contains(s.getFullName())) {
            return VoteDirection.UPVOTE;
        } else if (downVotedFullnames.contains(s.getFullName())) {
            return VoteDirection.DOWNVOTE;
        } else if (unvotedFullnames.contains(s.getFullName())) {
            return VoteDirection.NO_VOTE;
        } else {
            return s.getVote();
        }
    }

    public static void setVoteDirection(PublicContribution s, VoteDirection direction) {
        String fullname = s.getFullName();
        upVotedFullnames.remove(fullname);
        downVotedFullnames.remove(fullname);
        unvotedFullnames.remove(fullname);
        switch (direction) {

            case UPVOTE:
                upVotedFullnames.add(fullname);
                break;
            case DOWNVOTE:
                downVotedFullnames.add(fullname);
                break;
            case NO_VOTE:
                unvotedFullnames.add(fullname);
                break;
        }
    }

    public static boolean isSaved(Submission s) {
        if (savedFullnames.contains(s.getFullName())) {
            return true;
        } else if (unSavedFullnames.contains(s.getFullName())) {
            return false;
        } else {
            return s.isSaved();
        }
    }

    public static boolean isSaved(Comment s) {
        if (savedFullnames.contains(s.getFullName())) {
            return true;
        } else if (unSavedFullnames.contains(s.getFullName())) {
            return false;
        } else {
            return s.isSaved();
        }
    }

    public static void setSaved(Submission s, boolean b) {
        String fullname = s.getFullName();
        savedFullnames.remove(fullname);
        if (b) {
            savedFullnames.add(fullname);
        } else {
            unSavedFullnames.add(fullname);
        }
    }

    public static void setSaved(Comment s, boolean b) {
        String fullname = s.getFullName();
        savedFullnames.remove(fullname);
        if (b) {
            savedFullnames.add(fullname);
        } else {
            unSavedFullnames.add(fullname);
        }
    }

}
