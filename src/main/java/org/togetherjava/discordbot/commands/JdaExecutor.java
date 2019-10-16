package org.togetherjava.discordbot.commands;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.literal;

import de.ialistannen.commandprocrastination.command.execution.CommandExecutor;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import de.ialistannen.commandprocrastination.parsing.SuccessParser;
import org.togetherjava.discordbot.commands.CommandContext.JdaRequestContext;
import org.togetherjava.discordbot.config.TjBotConfig;
import org.togetherjava.discordbot.util.Messages;

/**
 * A command executor for JDA.
 */
class JdaExecutor extends CommandExecutor<CommandContext, JdaRequestContext> {

  private final Messages messages;
  private final TjBotConfig config;
  private final CommandFinder<CommandContext> finder;

  JdaExecutor(Messages messages, CommandFinder<CommandContext> finder, TjBotConfig config) {
    super(finder, SuccessParser.wrapping(literal(" ")));
    this.messages = messages;
    this.config = config;
    this.finder = finder;
  }

  @Override
  protected CommandContext createContext(JdaRequestContext requestContext) {
    return new CommandContext(requestContext, config, messages, finder);
  }
}
