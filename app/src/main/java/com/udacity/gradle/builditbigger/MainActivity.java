package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import info.eric.joketeller.JokeTellerActivity;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

  private TellJokeAsyncTask tellJokeAsyncTask;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
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

  private static class TellJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private final WeakReference<Activity> activityRef;

    public TellJokeAsyncTask(Activity activity) {
      activityRef = new WeakReference<>(activity);
    }

    @Override protected String doInBackground(Void... voids) {
      initializeApiService();

      try {
        return myApiService.tellJoke().execute().getData();
      } catch (IOException e) {
        return e.getMessage();
      }
    }

    private void initializeApiService() {
      if (myApiService == null) {  // Only do this once
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
            .setGoogleClientRequestInitializer(
                abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(
                    true));

        myApiService = builder.build();
      }
    }

    @Override
    protected void onPostExecute(String joke) {
      final Activity activity = activityRef.get();
      if (isCancelled() || activity == null) {
        return;
      }

      final Intent intent = JokeTellerActivity.newIntent(activity, joke);
      activity.startActivity(intent);
    }
  }
}
