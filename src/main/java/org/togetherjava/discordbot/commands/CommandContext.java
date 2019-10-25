package org.togetherjava.discordbot.commands;

import de.ialistannen.commandprocrastination.command.tree.CommandFinder;
import de.ialistannen.commandprocrastination.context.GlobalContext;
import de.ialistannen.commandprocrastination.context.RequestContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.togetherjava.discordbot.config.TjBotConfig;
import org.togetherjava.discordbot.util.Messages;

/**
 * The context for commands.
 */
public class CommandContext extends GlobalContext {

  private TjBotConfig config;
  private Messages messages;
  private JdaRequestContext requestContext;
  private CommandFinder<CommandContext> commandFinder;
  private JDA jda;

  /**
   * Creates a new command context.
   *
   * @param requestContext the request context that is specific this this request
   * @param config the config
   * @param messages the messages class to use
   * @param commandFinder the command finder
   * @param jda the jda instance
   */
  public CommandContext(JdaRequestContext requestContext, TjBotConfig config,
      Messages messages,
      CommandFinder<CommandContext> commandFinder, JDA jda) {
    super(requestContext);
    this.config = config;
    this.requestContext = requestContext;
    this.messages = messages;
    this.commandFinder = commandFinder;
    this.jda = jda;
  }

  /**
   * Returns the config.
   *
   * @return the config used by the bot
   */
  public TjBotConfig getConfig() {
    return config;
  }

  /**
   * Returns the request context.
   *
   * @return the request context
   */
  @Override
  public JdaRequestContext getRequestContext() {
    return requestContext;
  }

  /**
   * Returns the command finder.
   *
   * @return the command finder
   */
  public CommandFinder<CommandContext> getCommandFinder() {
    return commandFinder;
  }

  /**
   * Returns the {@link Messages}.
   *
   * @return the messages
   */
  public Messages getMessages() {
    return messages;
  }

  /**
   * Returns the JDA instance used by this bot.
   *
   * @return the JDA instance used by this bot
   */
  public JDA getJda() {
    return jda;
  }

  /**
   * The context for a single request.
   */
  public static class JdaRequestContext extends RequestContext {

    private Message message;
    private User user;
    private Guild guild;
    private MessageChannel channel;

    JdaRequestContext(Message message, User user, Guild guild) {
      this.message = message;
      this.user = user;
      this.guild = guild;
      this.channel = message.getChannel();
    }

    /**
     * Returns the message that triggered the command.
     *
     * @return the message that triggered the command
     */
    public Message getMessage() {
      return message;
    }

    /**
     * Returns the channel the message was in.
     *
     * @return the channel the message was in
     */
    public MessageChannel getChannel() {
      return channel;
    }

    /**
     * Returns the user that sent the message.
     *
     * @return the user that sent the message
     */
    public User getUser() {
      return user;
    }

    /**
     * Returns the guild the message was sent in.
     *
     * @return the guild the message was sent in
     */
    public Guild getGuild() {
      return guild;
    }
  }

}
