package org.togetherjava.discordbot.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.regex.Pattern;

/**
 * Stores the config for commands.
 */
public class CommandConfig {

  private FreeCommandConfig free;

  @JsonCreator
  public CommandConfig(FreeCommandConfig free) {
    this.free = free;
  }

  /**
   * Returns the config for the free command.
   *
   * @return the config for the free command
   */
  public FreeCommandConfig getFree() {
    return free;
  }

  /**
   * Stores the config of the "free" command.
   */
  public static class FreeCommandConfig {

    private final String helpChannelRegex;

    @JsonCreator
    public FreeCommandConfig(String helpChannelRegex) {
      this.helpChannelRegex = helpChannelRegex;
    }

    /**
     * Returns the regex to detect help channels.
     *
     * @return the help channel regex
     */
    public Pattern getHelpChannelRegex() {
      return Pattern.compile(helpChannelRegex);
    }
  }
}
