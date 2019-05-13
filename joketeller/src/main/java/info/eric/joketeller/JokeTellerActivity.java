package info.eric.joketeller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JokeTellerActivity extends AppCompatActivity {

  private static final String EXTRA_JOKE = "EXTRA_JOKE";

  @NonNull
  public static Intent newIntent(@NonNull Context context, @NonNull String joke) {
    final Intent intent = new Intent(context, JokeTellerActivity.class);
    intent.putExtra(EXTRA_JOKE, joke);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_joke_teller);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final String joke = getIntent().getStringExtra(EXTRA_JOKE);
    final TextView jokeText = findViewById(R.id.joke_teller_text);
    jokeText.setText(joke);
  }
}
