package org.togetherjava.discordbot.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Optional;

/**
 * A collection of messages, read from a file and maybe translated.
 */
public class Messages {
  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private Map<String, Object> map;

  /**
   * Creates a new messages instance.
   */
  public Messages() {
    try (InputStream inputStream = getClass().getResourceAsStream("/messages.yml")) {
      map = new ObjectMapper(new YAMLFactory())
          .readValue(inputStream, new TypeReference<>() {
          });
    } catch (IOException e) {
      LOG.error("An error occurred while reading messages ",e);
    }
  }

  /**
   * Translates a string.
   *
   * @param key the key
   * @param formatArgs the format arguments
   * @return tbe translated string
   * @throws IllegalArgumentException if the key was not found
   */
  public String tr(String key, Object... formatArgs) {
    String storedValue = implGet(key, map);
    if (storedValue == null) {
      throw new IllegalArgumentException("Path not found: '" + key + "'");
    }
    return String.format(storedValue, formatArgs);
  }

  /**
   * Translates a string.
   *
   * @param key the key
   * @param formatArgs the format arguments
   * @return tbe translated string
   */
  public Optional<String> trOptional(String key, Object... formatArgs) {
    String storedValue = implGet(key, map);
    if (storedValue == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(String.format(storedValue, formatArgs));
  }

  /**
   * Checks if the messages has a given key.
   *
   * @param key the key
   * @return true if the kex exists.
   */
  public boolean hasKey(String key) {
    return implGet(key, map) != null;
  }

  private String implGet(String path, Map<String, Object> root) {
    String firstPart = path.split("\\.")[0];

    if (!root.containsKey(firstPart)) {
      return null;
    }
    Object fetched = root.get(firstPart);

    if (path.length() == firstPart.length()) {
      return fetched.toString();
    }

    String restPath = path.substring(path.indexOf('.') + 1);

    @SuppressWarnings("unchecked")
    Map<String, Object> nestedMap = (Map<String, Object>) fetched;
    return implGet(restPath, nestedMap);
  }
}
