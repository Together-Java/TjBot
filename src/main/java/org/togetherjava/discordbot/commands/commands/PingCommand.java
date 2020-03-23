package org.togetherjava.discordbot.commands.commands;

import de.ialistannen.commandprocrastination.autodiscovery.ActiveCommand;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import javax.inject.Inject;
import org.togetherjava.discordbot.commands.CommandContext;

/**
 * A simple ping command.
 */
@ActiveCommand(name = "ping", parentClass = BasePrefixCommand.class)
public class PingCommand extends CommandNode<CommandContext> {

  @Inject
  public PingCommand() {
    super("ping");

    setCommand(context -> context.getRequestContext().getChannel().sendMessage("Pong!").queue());
  }
}
