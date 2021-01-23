package me.ccrama.redditslide.test;

import net.dean.jraw.models.Comment;
import net.dean.jraw.models.VoteDirection;

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

    /**
     * CORRECT Boundary conditions
     *
     * Conformance: Not applicable. A Comment is a type of PublicContribution and a
     * PublicContribution does need to conform to a certain format, namely data must be provided
     * in json format. We circumvent the need to pass this data directly by mocking Comment and
     * mocking the getFullName function of Comment to return a name if invoked.
     *
     * Ordering: The order of adding a comment to a set of lists
     * (upvotedFullnames, unvotedFullnames, downvotedFullnames) does not matter,
     * it is only important to check the boundaries where there is 1 comment in a list.
     *
     * Range: There are no minimum and maximum values to look out for so this point is a not so relevant point
     * for this test.
     *
     * Reference: The (upvotedFullnames, unvotedFullnames, downvotedFullnames are external to the
     * method and are therefore reset on each function call to make sure testing one function
     * scenario doesn't influence another.
     *
     * Existence: The comment can contain three different states for the VoteDirection
     * (NO_VOTE, UPVOTE, DOWNVOTE). One of the lists
     * (upvotedFullnames, unvotedFullnames, downvotedFullnames) contains a value.
     *
     * Cardinality: In this test we check the behavior for each list
     * (upvotedFullnames, unvotedFullnames, downvotedFullnames) when 1 comment is added and we
     * retrieve the vote direction of this 1 comment. We expect the result to be a VoteDirection enum.
     * There must be a list with at least 1 comment name for each test run
     * (number of test runs equals the number of arguments passed to the parameterized test)
     *
     * Time: Time is not an aspect that could influence the result of the function so it's not a relevant
     * aspect to test in this function.
     *
     * @param voteList
     * @param voteDirection
     */
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
