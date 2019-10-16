package org.togetherjava.discordbot.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The pojo for the main config file for this bot.
 */
public class TjBotConfig {

  private List<String> prefixes;

  @JsonCreator
  public TjBotConfig(List<String> prefixes) {
    this.prefixes = prefixes;
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
