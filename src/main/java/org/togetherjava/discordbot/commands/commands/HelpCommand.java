package org.togetherjava.discordbot.commands.commands;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.greedyPhrase;

import de.ialistannen.commandprocrastination.autodiscovery.ActiveCommand;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder.FindResult;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import de.ialistannen.commandprocrastination.command.tree.data.DefaultDataKey;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import de.ialistannen.commandprocrastination.util.StringReader;
import java.util.Optional;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.togetherjava.discordbot.commands.CommandContext;

/**
 * A basic help command.
 */
@ActiveCommand(name = "help", parentClass = BasePrefixCommand.class)
public class HelpCommand extends CommandNode<CommandContext> {

  public HelpCommand() {
    super("help");
    setCommand(this::execute);
  }

  private void execute(CommandContext context) throws ParseException {
    String path = context.shift(greedyPhrase());

    FindResult<CommandContext> foundCommands = context.getCommandFinder()
        // Remove the need to specify the prefix
        .find(getParent().orElseThrow(), new StringReader(path));

    CommandNode<CommandContext> finalNode = foundCommands.getChain().getFinalNode();

    EmbedBuilder embedBuilder = new EmbedBuilder();

    finalNode.getOptionalData(DefaultDataKey.IDENTIFIER).ifPresent(name ->
        embedBuilder.setTitle(name.toString())
    );

    finalNode.getHeadParser().getName().ifPresent(name ->
        embedBuilder.addField("Keyword", '`' + name + '`', true)
    );

    finalNode.getOptionalData(DefaultDataKey.USAGE)
        .map(usage -> "`" + usage + "`")
        .or(() -> fetchFromMessages("usage", context, finalNode))
        .or(() -> Optional.of("*(approx)* `" + foundCommands.getChain().buildUsage() + "`"))
        .ifPresent(usage ->
            embedBuilder.addField("Usage", usage, true)
        );

    finalNode.getOptionalData(DefaultDataKey.SHORT_DESCRIPTION)
        .or(() -> fetchFromMessages("short-description", context, finalNode))
        .ifPresent(desc ->
            embedBuilder.addField("Short description", desc.toString(), true)
        );
    finalNode.getOptionalData(DefaultDataKey.LONG_DESCRIPTION)
        .or(() -> fetchFromMessages("long-description", context, finalNode))
        .ifPresent(desc ->
            embedBuilder.setDescription(desc.toString())
        );

    finalNode.getOptionalData(DefaultDataKey.PERMISSION).ifPresent(perm ->
        embedBuilder.addField("Permission", "`" + perm + "`", true)
    );

    context.getRequestContext().getChannel().sendMessage(
        new MessageBuilder().setEmbed(embedBuilder.build()).build()
    ).queue();
  }

  private Optional<String> fetchFromMessages(String restPath, CommandContext commandContext,
      CommandNode<CommandContext> node) {
    if (!node.hasOptionalData(DefaultDataKey.IDENTIFIER)) {
      return Optional.empty();
    }
    String identifier = node.<String>getOptionalData(DefaultDataKey.IDENTIFIER).orElseThrow();
    String lookupPath = "commands." + identifier + "." + restPath;

    return commandContext.getMessages().trOptional(lookupPath);
  }
}
