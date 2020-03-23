package org.togetherjava.discordbot.commands;

import de.ialistannen.commandprocrastination.command.execution.AbnormalCommandResultException;
import de.ialistannen.commandprocrastination.command.execution.CommandException;
import de.ialistannen.commandprocrastination.command.execution.CommandNotFoundException;
import de.ialistannen.commandprocrastination.command.execution.NoSeparatorException;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.togetherjava.discordbot.commands.CommandContext.JdaRequestContext;
import org.togetherjava.discordbot.util.Messages;

/**
 * A command listener for JDA. It is responsible for listening to messages an invoking commands.
 */
@Singleton
public class CommandListener extends ListenerAdapter {

  private final JdaExecutor executor;
  private final Messages messages;

  /**
   * Creates a new command listener.
   *
   * @param executor the jda executor
   * @param messages the messages container
   */
  @Inject
  public CommandListener(JdaExecutor executor, Messages messages) {
    this.executor = executor;
    this.messages = messages;
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
      if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) {
        event.getChannel().sendMessage(messages.tr("commands.on-mention.response")).queue();
      }
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
