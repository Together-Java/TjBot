package org.togetherjava.discordbot.util;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A formatter for {@link Duration}s.
 */
public class DurationFormatter {

  /**
   * Formats a given duration to an english equivalent.
   *
   * @param input the input duration
   * @return the formatted duration
   */
  public String format(Duration input) {
    if (input.toSeconds() < 1) {
      return "Just now";
    }

    List<String> parts = Stream.of(
        formatPartIfNotZero(input.toDaysPart(), "day"),
        formatPartIfNotZero(input.toHoursPart(), "hour"),
        formatPartIfNotZero(input.toMinutesPart(), "minute"),
        formatPartIfNotZero(input.toSecondsPart(), "second")
    )
        .flatMap(Optional::stream)
        .collect(Collectors.toList());

    return joinParts(parts);
  }

  private Optional<String> formatPartIfNotZero(long value, String singular) {
    if (value != 0) {
      return Optional.of(value + " " + pluralize(value, singular));
    }
    return Optional.empty();
  }

  private String pluralize(long value, String singular) {
    return value == 1 ? singular : singular + "s";
  }

  /**
   * Joins parts using a comma or "and", depending on the position. "One", "Two", "Three" will be
   * joined to "One, Two and Three".
   *
   * <p>Empty lists will be joined to an empty String, single-element list will be left
   * untouched.</p>
   *
   * @param parts the parts
   * @return the joined parts
   */
  private String joinParts(List<String> parts) {
    if (parts.size() < 1) {
      return "";
    } else if (parts.size() == 1) {
      return parts.get(0);
    }

    StringBuilder joined = new StringBuilder();

    for (int i = 0; i < parts.size(); i++) {
      if (i == parts.size() - 1) {
        joined.append(" and ");
      } else if (i > 0) {
        joined.append(", ");
      }
      joined.append(parts.get(i));
    }

    return joined.toString();
  }
}
