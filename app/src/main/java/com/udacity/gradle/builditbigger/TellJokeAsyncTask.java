package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import info.eric.joketeller.JokeTellerActivity;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class TellJokeAsyncTask extends AsyncTask<Void, Void, String> {

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
      return "";
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
