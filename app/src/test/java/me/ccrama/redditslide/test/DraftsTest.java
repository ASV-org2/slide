package me.ccrama.redditslide.test;

import android.content.Context;
import android.content.SharedPreferences;

import net.dean.jraw.models.VoteDirection;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import me.ccrama.redditslide.ActionStates;
import me.ccrama.redditslide.Authentication;
import me.ccrama.redditslide.Drafts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DraftsTest {

    public static Context context;

    @BeforeAll
    public static void init() {
        Authentication.authentication = mock(SharedPreferences.class);
        context = mock(Context.class);
    }

    /**
     * CORRECT Boundary conditions
     *
     * Conformance: The text identifying all the drafts for a user are retrieved from SharedPreferences
     * using a String key. The text itself also can contain this special character sequence </newdraft>,
     * which indicates where one draft ends and another begins.
     *
     * Ordering: Ordering matters for this test in the sense that the expected result
     * is an array of a certain order. The string retrieved from SharedPreferences representing
     * the drafts of a user, need to be split in the right order. The test validates this order.
     *
     * Range: There are no minimum and maximum values to look out for so this point is a not so relevant point
     * for this test.
     *
     * Reference: Authentication.authentication is external to the method and not easily instantiable and
     * is therefore mocked along with a getString function call on Authentication.authentication.
     *
     * Existence: The result of getDrafts should always return a list. There should be no null values.
     * If there are no drafts we expect an empty list back. This scenario is also tested in this function
     * using one of the set of arguments provided by the draftsProvider function.
     *
     * Cardinality: The size of the list returned from getDrafts needs to be equal with the expected
     * size for the test to pass.
     *
     * Time: Time is not an aspect that could influence the result of the function so it's not a relevant
     * aspect to test in this function.
     *
     */
    @ParameterizedTest
    @MethodSource("draftsProvider")
    void testGetDraftsListEquality(String drafts, int _, ArrayList<String> expectedDrafts) {
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(Authentication.authentication);
        when(Authentication.authentication.getString(anyString(), anyString())).thenReturn(drafts);

        ArrayList<String> resultDrafts = Drafts.getDrafts();
        assertEquals(expectedDrafts, resultDrafts );
    }

    @ParameterizedTest
    @MethodSource("draftsProvider")
    void testGetDraftsSize(String drafts, int expectedNumberOfDrafts, ArrayList<String> _) {
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(Authentication.authentication);
        when(Authentication.authentication.getString(anyString(), anyString())).thenReturn(drafts);

        int resultNumberOfDrafts = Drafts.getDrafts().size();
        assertEquals(expectedNumberOfDrafts, resultNumberOfDrafts);
    }

    private static Stream<Arguments> draftsProvider() {
        return Stream.of(
                Arguments.of("some more more words </newdraft>", 1, new ArrayList<>(Collections.singletonList("some more more words "))),
                Arguments.of("</newdraft>", 0, new ArrayList<>()),
                Arguments.of("</newdraft> some more more words", 1, new ArrayList<>(Collections.singletonList(" some more more words"))),
                Arguments.of("some words </newdraft> some more more words", 2, new ArrayList<>(Arrays.asList("some words ", " some more more words")))
        );
    }

}
