package org.togetherjava.discordbot.cli;

import static org.togetherjava.discordbot.cli.TerminalColor.BLUE;
import static org.togetherjava.discordbot.cli.TerminalColor.RED;
import static org.togetherjava.discordbot.cli.TerminalColor.RESET;
import static org.togetherjava.discordbot.cli.TerminalColor.UNDERLINE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jbock.CommandLineArguments;
import net.jbock.Parameter;

/**
 * A simple bot to help run a discord server.
 *
 * Made for the Together Java discord server
 */
@CommandLineArguments(programName = "Together Java Bot", missionStatement = "Existing...")
public abstract class CliArgumentSpecification {

  /**
   * The path to the configuration file.
   */
  @Parameter(shortName = 'c', longName = "config", mappedBy = FileMapper.class)
  public abstract Path configFile();

  static class FileMapper implements Supplier<Function<String, Path>> {

    @Override
    public Function<String, Path> get() {
      return pathName -> {
        Path path = Paths.get(pathName);
        if (Files.notExists(path) || !Files.isRegularFile(path)) {
          throw new IllegalArgumentException(
              RED + "The given target path "
                  + BLUE + UNDERLINE + path + RESET + RED
                  + " is no file or does not exist!"
                  + RESET
          );
        }
        return path;
      };
    }
  }
}