package org.togetherjava.discordbot;

import java.nio.file.Files;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togetherjava.discordbot.cli.CliArgumentSpecification;
import org.togetherjava.discordbot.cli.CliArgumentSpecification_Parser;
import org.togetherjava.discordbot.commands.CommandListener;
import org.togetherjava.discordbot.config.TjBotConfig;
import org.togetherjava.discordbot.events.modmail.ModMailListener;
import org.togetherjava.discordbot.util.Messages;

public class BotMain {

  private static final Logger logger = LoggerFactory.getLogger(BotMain.class);

  public static void main(String[] args) throws Exception {
    logger.info("Starting up");

    CliArgumentSpecification spec = CliArgumentSpecification_Parser.create().parseOrExit(args);

    logger.info("Loading config from {}", spec.configFile().toAbsolutePath());
    TjBotConfig config = TjBotConfig.loadFromStream(Files.newInputStream(spec.configFile()));
    logger.info("Loaded config");

    Messages messages = new Messages();

    logger.info("Starting JDA");
    JDA jda = new JDABuilder(AccountType.BOT)
        .setToken(config.getAndDeleteBotToken())
        .build()
        .awaitReady();
    jda.addEventListener(new ModMailListener(jda, config));
    jda.addEventListener(new CommandListener(config, messages, jda));

    logger.info("Started JDA");
  }
}
