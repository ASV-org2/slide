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

    @Test
    public void getItemCountZero() {
        SettingValues.prefs = prefs;
        SubmissionAdapter adapter = new SubmissionAdapter(context, dataSet, listView, "homepage", displayer);

        int result = adapter.getItemCount();

        assertEquals(0, result);
    }

    @Test
    public void getItemCountNotZero() {
        SettingValues.prefs = prefs;
        SubmissionAdapter adapter = new SubmissionAdapter(context, dataSet, listView, "homepage", displayer);

        dataSet.posts = new ArrayList<>();
        dataSet.posts.add(submission);
        int result2 = adapter.getItemCount();

        assertEquals(3, result2);
    }

}
