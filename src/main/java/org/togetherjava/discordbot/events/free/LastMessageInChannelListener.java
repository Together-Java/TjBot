package org.togetherjava.discordbot.events.free;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listens for messages and keeps track of the last message.
 */
public class LastMessageInChannelListener extends ListenerAdapter {

  private Map<Long, LastSentMessage> lastMessageMap;

  public LastMessageInChannelListener(JDA jda) {
    this.lastMessageMap = new ConcurrentHashMap<>();

    // Prime cache
    jda.getGuilds()
        .stream()
        // Only return channels we are allowed to monitor (exclude moderator channels and so on)
        .flatMap(guild -> guild.getTextChannels()
            .stream()
            .filter(chan -> guild.getSelfMember().hasPermission(chan, Permission.MESSAGE_HISTORY))
        )
        .forEach(it -> it.getIterableHistory().limit(1).queue(messages -> {
          if (!messages.isEmpty()) {
            Message message = messages.get(0);

            // do not overwrite existing ones
            lastMessageMap.computeIfAbsent(
                message.getChannel().getIdLong(),
                key -> new LastSentMessage(
                    message,
                    Instant.from(message.getTimeCreated().toInstant())
                )
            );
          }
        }));
  }

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    lastMessageMap.compute(
        event.getChannel().getIdLong(),
        (key, old) -> new LastSentMessage(event.getMessage(), Instant.now())
    );
  }

  /**
   * Returns a map with the last times a message was sent in a given channel.
   *
   * @return the map containing the last sent messages in a channel
   */
  public Collection<LastSentMessage> getLastMessages() {
    return Collections.unmodifiableCollection(lastMessageMap.values());
  }

  /**
   * Represents the last message sent in a channel.
   */
  public static class LastSentMessage {

    private Message message;
    private Instant time;

    public LastSentMessage(Message message, Instant time) {
      this.message = message;
      this.time = time;
    }

    /**
     * Returns the sent message.
     *
     * @return the sent message
     */
    public Message getMessage() {
      return message;
    }

    /**
     * Returns the time the message was sent at.
     *
     * @return the time the message was sent at
     */
    public Instant getTime() {
      return time;
    }

    /**
     * Returns the duration that has passed since this message was sent.
     *
     * @return the duration that has passed since this message was sent
     */
    public Duration getDurationToNow() {
      return Duration.between(time, Instant.now());
    }

  }
}
