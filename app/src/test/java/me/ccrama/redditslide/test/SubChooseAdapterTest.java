package me.ccrama.redditslide.test;

import android.content.Context;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import me.ccrama.redditslide.Adapters.SubChooseAdapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SubChooseAdapterTest {

    private Context context;
    private SubChooseAdapter subChooseAdapter;
    private static final ArrayList<String> OBJECTS = new ArrayList<>(Arrays.asList("sub1", "sub2", "sub3"));
    private static final ArrayList<String> ALL_SUBREDDITS = OBJECTS;

    @ParameterizedTest
    @MethodSource("filterProvider")
    public void testFilterListCount(String searchTerm, ArrayList<String> _, int expectedCount) {
        context = mock(Context.class);
        subChooseAdapter = new SubChooseAdapter(context, OBJECTS, ALL_SUBREDDITS);
        SubChooseAdapter.SubFilter subFilter = subChooseAdapter.getSubFilter();
        int resultCount = subFilter.filterAndGetCount(searchTerm) - 1;
        assertEquals(expectedCount, resultCount);
    }

    @ParameterizedTest
    @MethodSource("filterProvider")
    public void testFilterListEquality(String searchTerm, ArrayList<String> expectedFilterList, int _) {
        context = mock(Context.class);
        subChooseAdapter = new SubChooseAdapter(context, OBJECTS, ALL_SUBREDDITS);
        SubChooseAdapter.SubFilter subFilter = subChooseAdapter.getSubFilter();
        ArrayList<String> resultFilterList = (ArrayList) subFilter.filterAndGetValues(searchTerm);
        resultFilterList.remove(resultFilterList.size() - 1); // Remove search prefix from results
        assertEquals(expectedFilterList, resultFilterList);
    }

    private static Stream<Arguments> filterProvider() {
        return Stream.of(
                Arguments.of("sub", ALL_SUBREDDITS, 3),
                Arguments.of("test", new ArrayList<>(), 0)
        );
    }
}
