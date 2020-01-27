package org.togetherjava.discordbot.commands.parsers;

import de.ialistannen.commandprocrastination.parsing.AtomicParser;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import de.ialistannen.commandprocrastination.util.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

/**
 * Contains parsers for JDA {@link User}s.
 */
public class UserParsers {

  private UserParsers() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Parses a user by their id.
   *
   * @param jda the jda instance to use
   * @return the parser
   */
  public static AtomicParser<User> userById(JDA jda) {
    Pattern pattern = Pattern.compile("\\d+");
    return input -> {
      String id = input.readRegex(pattern);
      return toUserFromId(jda, input, id);
    };
  }

  /**
   * Parses a user by their id.
   *
   * @param jda the jda instance to use
   * @return the parser
   */
  public static AtomicParser<User> userByMention(JDA jda) {
    Pattern pattern = Pattern.compile("<@(\\d+)>");

    return input -> {
      String nextWord = input.readRegex(pattern);

      Matcher matcher = pattern.matcher(nextWord);

      if (!matcher.find()) {
        throw new ParseException(input, "Expected an id of the format <@id>");
      }

      return toUserFromId(jda, input, matcher.group(1));
    };
  }

  private static User toUserFromId(JDA jda, StringReader input, String id) throws ParseException {
    if (id.isBlank()) {
      throw new ParseException(input, "Id may not be empty");
    }

    User user;
    try {
      user = jda.getUserById(id);
    } catch (IllegalArgumentException e) {
      throw new ParseException(input, "Invalid id: " + e.getMessage());
    }

    if (user == null) {
      throw new ParseException(input, "User not found!");
    }
    return user;
  }
}
