package info.eric.joker;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Tells random jokes. It provides jokes in string format.
 *
 * <p>source of jokes: http://www.laughfactory.com/jokes/word-play-jokes
 */
public class Joker {

  private static final List<String> JOKES = Arrays.asList(
      "What happens to a frog's car when it breaks down? It gets toad away.",
      "A teacher asked her students to use the word \"beans\" in a sentence. \"My father grows "
          + "beans,\" said one girl. \"My mother cooks beans,\" said a boy. A third student spoke"
          + " up, \"We are all human beans.\"",
      "Q: Why was six scared of seven? \n"
          + "A: Because seven \"ate\" nine.",
      "Q: Can a kangaroo jump higher than the Empire State Building? \n"
          + "A: Of course. The Empire State Building can't jump.",
      "Instead of \"the John,\" I call my toilet \"the Jim.\" That way it sounds better when I"
          + " say I go to the Jim first thing every morning."
  );

  private static final Random random = new SecureRandom();

  @NotNull
  public String getJoke() {
    return JOKES.get(random.nextInt(JOKES.size()));
  }
}
