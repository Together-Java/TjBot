package org.togetherjava.discordbot.commands.commands;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.literal;

import de.ialistannen.commandprocrastination.autodiscovery.ActiveCommand;
import de.ialistannen.commandprocrastination.command.Command;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import de.ialistannen.commandprocrastination.command.tree.data.DefaultDataKey;
import de.ialistannen.commandprocrastination.parsing.SuccessParser;
import de.ialistannen.commandprocrastination.parsing.defaults.OptionParser;
import java.util.List;
import javax.inject.Inject;
import org.togetherjava.discordbot.commands.CommandContext;
import org.togetherjava.discordbot.config.TjBotConfig;

/**
 * A command that enforces a given prefix for commands.
 */
@ActiveCommand(name = "prefixed-base")
public class BasePrefixCommand extends CommandNode<CommandContext> {

  /**
   * Creates a new command that just enforces a base prefix.
   *
   * @param config the config
   */
  @Inject
  public BasePrefixCommand(TjBotConfig config) {
    super(Command.nop(), getParserForPrefix(config));

    // needed because a space after a prefix is annoying
    setData(DefaultDataKey.NO_ARGUMENT_SEPARATOR, true);
  }

  private static SuccessParser getParserForPrefix(TjBotConfig config) {
    List<String> prefixes = config.getPrefixes();
    OptionParser<Void> parser = new OptionParser<>();

    for (String prefix : prefixes) {
      parser = parser.or(literal(prefix));
    }

    return SuccessParser.wrapping(parser);
  }


}
