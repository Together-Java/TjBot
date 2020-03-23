package org.togetherjava.discordbot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.dv8tion.jda.api.JDA;
import org.togetherjava.discordbot.config.TjBotConfig;

/**
 * A GUice module for the core classes.
 */
public class CoreConfigModule extends AbstractModule {

  private TjBotConfig config;
  private JDA jda;

  public CoreConfigModule(TjBotConfig config, JDA jda) {
    this.config = config;
    this.jda = jda;
  }

  @Provides
  public JDA jda() {
    return jda;
  }

  @Provides
  public TjBotConfig config() {
    return config;
  }

}
