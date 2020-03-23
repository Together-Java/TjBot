package org.togetherjava.discordbot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.ialistannen.commandprocrastination.autodiscovery.CommandDiscovery;
import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import org.togetherjava.discordbot.commands.CommandContext;
import org.togetherjava.discordbot.util.Messages;

/**
 * A Guice module for commands.
 */
public class CommandModule extends AbstractModule {

  @Provides
  @Singleton
  public CommandFinder<CommandContext> commandFinder(Injector injector) {
    return new LazyCommandFinder<>(() -> new CommandFinder<>(
        new CommandDiscovery().findCommands(injector::getInstance)
    ));
  }

  @Provides
  @Singleton
  public Messages messages() {
    return new Messages();
  }
}
