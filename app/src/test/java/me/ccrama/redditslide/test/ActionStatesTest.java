package me.ccrama.redditslide.test;

import net.dean.jraw.models.Comment;
import net.dean.jraw.models.VoteDirection;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import me.ccrama.redditslide.ActionStates;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionStatesTest {

    @Test
    public void testVoteDirectionNotRegistered() {
        Comment comment = mock(Comment.class);
        when(comment.getVote()).thenReturn(VoteDirection.NO_VOTE);
        VoteDirection result = ActionStates.getVoteDirection(comment);
        assertEquals(VoteDirection.NO_VOTE, result);
    }

    @ParameterizedTest
    @MethodSource("voteDirectionProvider")
    public void testGetVoteDirection(ArrayList<String> voteList, VoteDirection voteDirection) {
        voteList.clear();
        Comment comment = mock(Comment.class);
        when(comment.getFullName()).thenReturn("full name");
        voteList.add(comment.getFullName());
        VoteDirection result = ActionStates.getVoteDirection(comment);
        assertEquals(voteDirection, result);
    }

    private static Stream<Arguments> voteDirectionProvider() {
        return Stream.of(
                Arguments.of(ActionStates.unvotedFullnames, VoteDirection.NO_VOTE),
                Arguments.of(ActionStates.downVotedFullnames, VoteDirection.DOWNVOTE),
                Arguments.of(ActionStates.upVotedFullnames, VoteDirection.UPVOTE)
        );
    }

}
