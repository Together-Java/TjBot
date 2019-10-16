package org.togetherjava.discordbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togetherjava.discordbot.cli.CliArgumentSpecification;
import org.togetherjava.discordbot.cli.CliArgumentSpecification_Parser;
import org.togetherjava.discordbot.config.TjBotConfig;

public class BotMain {

  private static final Logger logger = LoggerFactory.getLogger(BotMain.class);

  public static void main(String[] args) throws IOException {
    logger.info("Starting up");

    CliArgumentSpecification spec = CliArgumentSpecification_Parser.create().parseOrExit(args);

    logger.info("Loading config from {}", spec.configFile().toAbsolutePath());
    TjBotConfig config = loadConfig(spec.configFile());

    logger.info("Config loaded");
  }

  private static TjBotConfig loadConfig(Path path) throws IOException {
    return TjBotConfig.loadFromStream(Files.newInputStream(path));
  }
}
