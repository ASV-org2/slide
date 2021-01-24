package me.ccrama.redditslide.test;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import net.dean.jraw.models.Submission;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import me.ccrama.redditslide.Adapters.SubmissionAdapter;
import me.ccrama.redditslide.Adapters.SubmissionDisplay;
import me.ccrama.redditslide.Adapters.SubredditPosts;
import me.ccrama.redditslide.SettingValues;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BrowseTest {

    @Mock
    Activity context;
    @Mock
    SubredditPosts dataSet;
    @Mock
    RecyclerView listView;
    @Mock
    SubmissionDisplay displayer;
    @Mock
    SharedPreferences prefs;
    @Mock
    Submission submission;
    @Mock
    Date date;

    private SubmissionAdapter getAdapter() {
        SettingValues.prefs = prefs;
        return new SubmissionAdapter(context, dataSet, listView, "homepage", displayer);
    }

    /**
     * CORRECT: SubmissionAdapter.getItemCount()
     *
     * Conformance: Not applicable. The item count is a simple Integer value.
     *
     * Ordering: Make sure that we create a new array list before adding posts. Other values
     * in the data set like booleans can be set whenever.
     *
     * Range: The minimum range of the item count is zero. It doesn't have a maximum range.
     * A RecyclerView is made just for that purpose. It can have as many values as you
     * want, because it recycles the views.
     *
     * Reference: This function makes use of the amount of posts in the data set whenever this
     * amount is higher than zero. Whenever this amount of posts changes, notifyDataSetChanged()
     * is called to notify the Adapter that the amount of data has changed. The adapter will then
     * call getItemCount() to find out how many rows it should display.
     *
     * Existence: For this test, only positive numbers can exist during runtime, because
     * a list can only be empty or contain some values. Extremely high numbers aren't supposed to
     * be checked either. So a low value is good enough.
     *
     * Cardinality: The item count can only be zero or more than zero.
     *
     * Time: Not applicable. This function is called whenever data has been added. This timing
     * doesn't matter for our test.
     */

    @Test
    public void getItemCountZero() {
        SubmissionAdapter adapter = getAdapter();

        int result = adapter.getItemCount();

        assertEquals(0, result);
    }

    @Test
    public void getItemCountNotZero() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);

        int result = adapter.getItemCount();

        assertEquals(3, result);
    }

    /**
     * CORRECT: SubmissionAdapter.getItemViewType()
     *
     * Conformance: Not applicable. The view type id is a simple Integer value.
     *
     * Ordering: Make sure that we create a new array list before adding posts. Other values
     * in the data set like booleans can be set whenever.
     *
     * Range: Not applicable. The view type id doesn't have specific minimum or maximum value.
     *
     * Reference: These tests reference the amount of posts and some boolean values like error,
     * offline and nomore.
     *
     * Existence: During runtime all of the view types returned can exist.
     *
     * Cardinality: The amount of view types return must match the amount of view types we handle
     * in onCreateViewHolder.
     *
     * Time: Not applicable. During runtime this function is called by the adapter to figure
     * out which view to show. This doesn't matter for the tests.
     */

    private final int SPACER = 6;
    private final int ERROR = 7;
    private final int LOADING_SPINNER = 5;
    private final int NO_MORE = 3;

    @Test
    public void getItemViewTypeSpacer() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);

        long result = adapter.getItemViewType(0);

        assertEquals(SPACER, result);
    }

    @Test
    public void getItemViewTypeError() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.error = true;

        long result = adapter.getItemViewType(0);

        assertEquals(ERROR, result);
    }

    @Test
    public void getItemViewTypeLoader() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);

        long result = adapter.getItemViewType(dataSet.posts.size() + 1);

        assertEquals(LOADING_SPINNER, result);
    }

    @Test
    public void getItemViewTypeOffline() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.offline = true;

        long result = adapter.getItemViewType(0);

        assertEquals(NO_MORE, result);
    }

    @Test
    public void getItemViewTypeNoMore() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);
        dataSet.nomore = true;

        long result = adapter.getItemViewType(dataSet.posts.size() + 1);

        assertEquals(NO_MORE, result);
    }

    @Test
    public void getItemViewTypeCardView() {
        SubmissionAdapter adapter = getAdapter();
        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);
        dataSet.posts.add(submission);
        dataSet.posts.add(submission);

        long result = adapter.getItemViewType(dataSet.posts.size());

        assertEquals(1, result);
    }

}