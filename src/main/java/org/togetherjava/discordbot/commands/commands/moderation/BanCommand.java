package org.togetherjava.discordbot.commands.commands.moderation;

import static de.ialistannen.commandprocrastination.parsing.defaults.IntegerParsers.intGreaterThan;
import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.greedyPhrase;
import static org.togetherjava.discordbot.commands.parsers.UserParsers.userById;
import static org.togetherjava.discordbot.commands.parsers.UserParsers.userByMention;

import de.ialistannen.commandprocrastination.autodiscovery.ActiveCommand;
import de.ialistannen.commandprocrastination.command.execution.CommandException;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.togetherjava.discordbot.commands.CommandContext;
import org.togetherjava.discordbot.commands.commands.BasePrefixCommand;

/**
 * A simple ban command.
 */
@ActiveCommand(name = "ban", parentClass = BasePrefixCommand.class)
public class BanCommand extends CommandNode<CommandContext> {

  public BanCommand() {
    // The keyword for this command
    super("ban");

    // The command to execute
    setCommand(this::execute);
  }

  private void execute(CommandContext context) throws ParseException {
    JDA jda = context.getRequestContext().getChannel().getJDA();

    // Uses the parser that matches, fails if none do
    User user = context.shiftAny(List.of(
        userById(jda),
        userByMention(jda)
    ));

    // We do not allow 0 days
    int durationInDays = context.shift(intGreaterThan(1));

    // The reason is optional
    String reason = context.shiftOptionally(greedyPhrase())
        .orElse("");

    // Ban and handle success/error
    try {
      context.getRequestContext().getGuild().ban(
          user, durationInDays, reason
      ).queue(
          success ->
              context.getRequestContext().getChannel().sendMessage(
                  "Banned user " + user.getAsMention() + " for " + durationInDays + " days due to '"
                      + reason + "'!"
              ).queue(),
          error ->
              context.getRequestContext().getChannel().sendMessage(
                  "Failed to ban user " + user.getAsMention() + " due to " + error.getMessage()
              ).queue()
      );
    } catch (HierarchyException | InsufficientPermissionException e) {
      throw new CommandException("I do not have the necessary rights: " + e.getMessage());
    }
  }
}
