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

class SubChooseAdapterTest {

    private Context context;
    private SubChooseAdapter subChooseAdapter;
    private static final ArrayList<String> OBJECTS = new ArrayList<>(Arrays.asList("sub1", "sub2", "sub3"));
    private static final ArrayList<String> ALL_SUBREDDITS = OBJECTS;

    @ParameterizedTest
    @MethodSource("filterProvider")
    void testFilterListCount(String searchTerm, ArrayList<String> expectedFilterList, int expectedCount) {
        context = mock(Context.class);
        subChooseAdapter = new SubChooseAdapter(context, OBJECTS, ALL_SUBREDDITS);
        SubChooseAdapter.SubFilter subFilter = subChooseAdapter.getSubFilter();
        int resultCount = subFilter.filterAndGetCount(searchTerm) - 1;
        assertEquals(expectedCount, resultCount);
    }

    /**
     * CORRECT Boundary conditions
     *
     * Conformance: For instantiating the SubChooseAdapter we need a Context, which we map and two lists
     * that identify post text, comment text, etc...
     *
     * Ordering: Ordering does not matter in the context of the function being tested. What matters
     * is that text is filtered based on search terms. Only text matching the search terms should be
     * present in the result list.
     *
     * Range: There are no minimum and maximum values to look out for so this point is a not so relevant point
     * for this test.
     *
     * Reference: There is no external code that is not under the control of this function. Code that
     * would be, like Context, is mocked as a way of bypassing this lack of control.
     *
     * Existence: The result of performFiltering should always be an list. If there are no matches
     * an list containing the search term is returned. This makes the tests less predictable so
     * we remove the search term from the array to test more consistently.
     *
     * Cardinality: The size of the list returned from getDrafts needs to be equal with the expected
     * size for the test to pass. For this function we do need to account for the fact that if no
     * search terms are matched the search term itself is included in the result list. For this reason
     * we remove this element from the list, so the size of the result list is as you would expect.
     *
     * Time: Normally the filter would be called to filter the items displayed by an adapter, but
     * since this is an asynchronous process using queues we make sure performFiltering is called
     * instead so the results of filtering are returned immediately.
     *
     */
    @ParameterizedTest
    @MethodSource("filterProvider")
    void testFilterListEquality(String searchTerm, ArrayList<String> expectedFilterList, int expectedCount) {
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
