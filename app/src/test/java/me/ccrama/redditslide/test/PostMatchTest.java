package me.ccrama.redditslide.test;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import me.ccrama.redditslide.PostMatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostMatchTest {

    /**
     * CORRECT Boundary conditions
     *
     * Conformance: Not applicable.
     *
     * Ordering: The order does not matter, only whether at least one element in a set matches at least
     * one search term.
     *
     * Range: There are no minimum and maximum values to look out for so this point is a not so relevant point
     * for this test.
     *
     * Reference: The code does not reference something external to itself.
     *
     * Existence: If there is an element that matches one or more search terms a boolean value is
     * returned. Depending on whether totalMatch is true or false this match behavior changes.
     *
     * Cardinality: For this test it does not matter how many elements match the search terms
     * as long as one matches the contains method will return true.
     *
     * Time: Time is not an aspect that could influence the result of the function so it's not a relevant
     * aspect to test in this function.
     *
     */
    @ParameterizedTest
    @MethodSource("containsProvider")
    public void testContainsTotalMatchNegative(String searchTerm, Set<String> searchPossibilities, boolean totalMatch, boolean expectedDoesContain) {
        boolean result = PostMatch.contains(searchTerm, searchPossibilities, totalMatch);
        assertEquals(expectedDoesContain, result);
    }

    private static Stream<Arguments> containsProvider() {
        return Stream.of(
                Arguments.of("sports", new HashSet<>(Arrays.asList("spor", "sport")), true, false),
                Arguments.of("sports", new HashSet<>(Collections.singletonList("sports")), true, true),
                Arguments.of("sports", new HashSet<>(Collections.singletonList("sport")), false, true),
                Arguments.of("sports", new HashSet<>(Collections.singletonList("sprots")), false, false)
        );
    }

}
