package org.togetherjava.discordbot.commands;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.literal;

import de.ialistannen.commandprocrastination.command.execution.CommandExecutor;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import de.ialistannen.commandprocrastination.parsing.SuccessParser;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.togetherjava.discordbot.commands.CommandContext.JdaRequestContext;

/**
 * A command executor for JDA.
 */
@Singleton
public class JdaExecutor extends CommandExecutor<CommandContext, JdaRequestContext> {

  @Inject
  JdaExecutor(CommandFinder<CommandContext> finder) {
    super(finder, SuccessParser.wrapping(literal(" ")));
  }

  @Override
  protected CommandContext createContext(JdaRequestContext requestContext) {
    return new CommandContext(requestContext);
  }
}
