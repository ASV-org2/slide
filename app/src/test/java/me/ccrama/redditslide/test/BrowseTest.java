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

    private SubmissionAdapter getAdapter() {
        SettingValues.prefs = prefs;
        return new SubmissionAdapter(context, dataSet, listView, "homepage", displayer);
    }

    /**
     * CORRECT: SubmissionAdapter.getItemCount()
     *
     * Conformance: Not applicable. The item count is a simple Integer value.
     *
     * Ordering: Not applicable. Posts can be added at any time to the adapter.
     *
     * Range: The typical values received from this function are either zero, or equals to
     * the amount of submissions in the data set + 2.
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
     * Time: Not applicable. This function can be called at any time.
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

}