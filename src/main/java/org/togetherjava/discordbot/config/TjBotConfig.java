package org.togetherjava.discordbot.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * The pojo for the main config file for this bot.
 */
public class TjBotConfig {

  private List<String> prefixes;
  private String botToken;
  private CommandConfig commands;
  private String moderationChannel;

  @JsonCreator
  public TjBotConfig(List<String> prefixes, String botToken, String moderationChannel, CommandConfig commands) {
    this.prefixes = Objects.requireNonNull(prefixes, "prefixes can not be null!");
    this.botToken = Objects.requireNonNull(botToken, "botToken can not be null!");
    this.commands = Objects.requireNonNull(commands, "commands can not be null!");
    this.moderationChannel = Objects.requireNonNull(moderationChannel, "channel can not be null!");
  }

  /**
   * Returns all bot command prefixes.
   *
   * @return the prefixes the bot listens to
   */
  public List<String> getPrefixes() {
    return prefixes;
  }

  /**
   * Returns the configuration section for commands.
   *
   * @return the configuration section for commands
   */
  public CommandConfig getCommands() {
    return commands;
  }

  /**
   * Returns the moderation channel for modmail.
   *
   * @return the moderation channel for modmail.
   */
  public String getModerationChannel() { return moderationChannel; }

  /**
   * Returns the token and deletes it from the config.
   *
   * @return the bot token
   */
  public String getAndDeleteBotToken() {
    String token = botToken;
    botToken = null;
    return token;
  }

  /**
   * Loads the config from the given input stream.
   *
   * @param inputStream the input stream
   * @return the loaded config
   * @throws IOException if an error occurs when reading
   */
  public static TjBotConfig loadFromStream(InputStream inputStream) throws IOException {
    try (inputStream) {
      return new ObjectMapper(new YAMLFactory())
          .registerModule(new ParameterNamesModule())
          .readValue(inputStream, TjBotConfig.class);
    }
  }
}
