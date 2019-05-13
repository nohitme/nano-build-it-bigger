package com.udacity.gradle.builditbigger;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class TellJokeAsyncTaskTest {

  @Rule
  public final ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class, false, false);

  @Test
  public void tellJoke_returnNonEmptyString() throws ExecutionException, InterruptedException {
    // arrange
    final TellJokeAsyncTask tellJokeAsyncTask =
        new TellJokeAsyncTask(activityTestRule.getActivity());

    // act
    tellJokeAsyncTask.executeOnExecutor(MoreExecutors.directExecutor());

    // assert
    assertThat(tellJokeAsyncTask.get()).isNotEmpty();
  }
}