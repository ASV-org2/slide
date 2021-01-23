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

public class DraftsTest {

    public static Context context;

    @BeforeAll
    public static void init() {
        Authentication.authentication = mock(SharedPreferences.class);
        context = mock(Context.class);
    }

    @ParameterizedTest
    @MethodSource("draftsSizeProvider")
    public void testGetDraftsListEquality(String drafts, int _, ArrayList<String> expectedDrafts) {
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(Authentication.authentication);
        when(Authentication.authentication.getString(anyString(), anyString())).thenReturn(drafts);

        ArrayList<String> resultDrafts = Drafts.getDrafts();
        assertEquals(expectedDrafts, resultDrafts );
    }

    @ParameterizedTest
    @MethodSource("draftsSizeProvider")
    public void testGetDraftsSize(String drafts, int expectedNumberOfDrafts, ArrayList<String> _) {
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(Authentication.authentication);
        when(Authentication.authentication.getString(anyString(), anyString())).thenReturn(drafts);

        int resultNumberOfDrafts = Drafts.getDrafts().size();
        assertEquals(expectedNumberOfDrafts, resultNumberOfDrafts);
    }

    private static Stream<Arguments> draftsSizeProvider() {
        return Stream.of(
                Arguments.of("some more more words </newdraft>", 1, new ArrayList<>(Collections.singletonList("some more more words "))),
                Arguments.of("</newdraft>", 0, new ArrayList<>()),
                Arguments.of("</newdraft> some more more words", 1, new ArrayList<>(Collections.singletonList(" some more more words"))),
                Arguments.of("some words </newdraft> some more more words", 2, new ArrayList<>(Arrays.asList("some words ", " some more more words")))
        );
    }

}
