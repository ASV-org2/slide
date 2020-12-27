package me.ccrama.redditslide.test;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import me.ccrama.redditslide.PostMatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostMatchTest {

    @Test
    public void testContainsTotalMatchNegative() {
        Set<String> strings = new HashSet<>();
        strings.add("spor");
        strings.add("sport");
        boolean result = PostMatch.contains("sports", strings, true);
        assertFalse(result);
    }

    @Test
    public void testContainsTotalMatchPositive() {
        Set<String> strings = new HashSet<>();
        strings.add("sports");
        boolean result = PostMatch.contains("sports", strings, true);
        assertTrue(result);
    }

    @Test
    public void testContainsPartialMatchPositive() {
        Set<String> strings = new HashSet<>();
        strings.add("sport");
        boolean result = PostMatch.contains("sports", strings, false);
        assertTrue(result);
    }

    @Test
    public void testContainsPartialMatchNegative() {
        Set<String> strings = new HashSet<>();
        strings.add("sprots");
        boolean result = PostMatch.contains("sports", strings, false);
        assertFalse(result);
    }

}
