package org.togetherjava.discordbot.commands;

import de.ialistannen.commandprocrastination.autodiscovery.CommandDiscovery;
import de.ialistannen.commandprocrastination.command.execution.AbnormalCommandResultException;
import de.ialistannen.commandprocrastination.command.execution.CommandException;
import de.ialistannen.commandprocrastination.command.execution.CommandNotFoundException;
import de.ialistannen.commandprocrastination.command.execution.NoSeparatorException;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.togetherjava.discordbot.commands.CommandContext.JdaRequestContext;
import org.togetherjava.discordbot.config.TjBotConfig;
import org.togetherjava.discordbot.util.Messages;

/**
 * A command listener for JDA. It is responsible for listening to messages an invoking commands.
 */
public class CommandListener extends ListenerAdapter {

  private JdaExecutor executor;

  /**
   * Creates a new command listener.
   *
   * @param config the config to use
   * @param messages the messages to use
   */
  public CommandListener(TjBotConfig config, Messages messages) {
    // command finder is null only when called from the constructor
    CommandNode<CommandContext> rootCommand = new CommandDiscovery().findCommands(
        new CommandContext(null, config, messages, null)
    );
    CommandFinder<CommandContext> commandFinder = new CommandFinder<>(rootCommand);

    this.executor = new JdaExecutor(messages, commandFinder, config);
  }

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    String content = event.getMessage().getContentRaw();
    try {
      JdaRequestContext context = new JdaRequestContext(
          event.getMessage(),
          event.getAuthor(),
          event.getGuild()
      );
      executor.execute(content, context);
    } catch (CommandNotFoundException | NoSeparatorException ignored) {
    } catch (ParseException | AbnormalCommandResultException | CommandException e) {
      event.getChannel().sendMessage(e.getMessage()).queue();
    }
  }
}
