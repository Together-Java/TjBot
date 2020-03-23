package org.togetherjava.discordbot.commands;

import de.ialistannen.commandprocrastination.context.GlobalContext;
import de.ialistannen.commandprocrastination.context.RequestContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/**
 * The context for commands.
 */
public class CommandContext extends GlobalContext {

  /**
   * Creates a new command context.
   *
   * @param requestContext the request context that is specific this this request
   */
  public CommandContext(JdaRequestContext requestContext) {
    super(requestContext);
  }

  @Override
  public JdaRequestContext getRequestContext() {
    return (JdaRequestContext) super.getRequestContext();
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
