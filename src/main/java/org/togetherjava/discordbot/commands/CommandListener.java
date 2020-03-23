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

/**
 * A command listener for JDA. It is responsible for listening to messages an invoking commands.
 */
@Singleton
public class CommandListener extends ListenerAdapter {

  private JdaExecutor executor;

  /**
   * Creates a new command listener.
   *
   * @param executor the jda executor
   */
  @Inject
  public CommandListener(JdaExecutor executor) {
    this.executor = executor;
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
