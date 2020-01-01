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
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
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
   * @param jda the jda instance
   */
  public CommandListener(TjBotConfig config, Messages messages, JDA jda) {
    // command finder is null only when called from the constructor
    CommandNode<CommandContext> rootCommand = new CommandDiscovery().findCommands(
        new CommandContext(null, config, messages, null, jda)
    );
    CommandFinder<CommandContext> commandFinder = new CommandFinder<>(rootCommand);

    this.executor = new JdaExecutor(messages, commandFinder, config, jda);
  }

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if (event.getAuthor().isBot() || event.isFromType(ChannelType.PRIVATE)) {
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
      if (e.getCause() != null && e.getCause().getMessage() != null) {
        event.getChannel()
            .sendMessage(
                e.getMessage() + " [The error also whispered ' " + e.getCause().getMessage() + " ']"
            )
            .queue();
      } else {
        event.getChannel().sendMessage(e.getMessage()).queue();
      }
    }
  }
}
