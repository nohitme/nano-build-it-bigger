package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private TellJokeAsyncTask tellJokeAsyncTask;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (tellJokeAsyncTask != null) {
      tellJokeAsyncTask.cancel(false);
    }
  }

  public void tellJoke(View view) {
    if (tellJokeAsyncTask != null) {
      tellJokeAsyncTask.cancel(false);
    }

    tellJokeAsyncTask = new TellJokeAsyncTask(this);
    tellJokeAsyncTask.execute();
  }
}
