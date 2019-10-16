package org.togetherjava.discordbot.commands;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.literal;

import de.ialistannen.commandprocrastination.command.execution.CommandExecutor;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import de.ialistannen.commandprocrastination.parsing.SuccessParser;
import org.togetherjava.discordbot.commands.CommandContext.JdaRequestContext;
import org.togetherjava.discordbot.config.TjBotConfig;

/**
 * A command executor for JDA.
 */
class JdaExecutor extends CommandExecutor<CommandContext, JdaRequestContext> {

  private final TjBotConfig config;
  private final CommandFinder<CommandContext> finder;

  JdaExecutor(CommandFinder<CommandContext> finder, TjBotConfig config) {
    super(finder, SuccessParser.wrapping(literal(" ")));
    this.config = config;
    this.finder = finder;
  }

  @Override
  protected CommandContext createContext(JdaRequestContext requestContext) {
    return new CommandContext(requestContext, config, finder);
  }
}
