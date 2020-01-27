package org.togetherjava.discordbot.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import static org.assertj.core.api.Assertions.*;
class DurationFormatterTest {

  private DurationFormatter durationFormatterUnderTest;

  @BeforeEach
  void setUp() {
    durationFormatterUnderTest = new DurationFormatter();
  }

  @Test
  void testFormat_underOneSecond() {
    // Setup
    final Duration input = Duration.ofMillis(100L);

    // Run the test
    final String result = durationFormatterUnderTest.format(input);

    // Verify the results
    assertThat(result).isEqualTo("Just now");
  }

  @Test
  void testFormat_singleMinute() {
    // Setup
    final Duration input = Duration.ofMinutes(1L);

    // Run the test
    final String result = durationFormatterUnderTest.format(input);

    // Verify the results
    assertThat(result).isEqualTo("1 minute");
  }

  @Test
  void testFormat_pluralize() {
    // Setup
    final Duration input = Duration.ofMinutes(10L);

    // Run the test
    final String result = durationFormatterUnderTest.format(input);

    // Verify the results
    assertThat(result).isEqualTo("10 minutes");
  }

  @Test
  void testFormat_hours() {
    // Setup
    final Duration input = Duration.ofHours(1L);

    // Run the test
    final String result = durationFormatterUnderTest.format(input);

    // Verify the results
    assertThat(result).isEqualTo("1 hour");
  }

  @Test
  void testFormat_days() {
    // Setup
    final Duration input = Duration.ofDays(10L);

    // Run the test
    final String result = durationFormatterUnderTest.format(input);

    // Verify the results
    assertThat(result).isEqualTo("10 days");
  }
}
