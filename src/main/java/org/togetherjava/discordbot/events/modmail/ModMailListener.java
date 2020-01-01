package org.togetherjava.discordbot.events.modmail;

import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.togetherjava.discordbot.config.TjBotConfig;
import javax.annotation.Nonnull;
import java.util.List;



public class ModMailListener extends ListenerAdapter {
    private TextChannel moderation;

    public ModMailListener(JDA jda, TjBotConfig config) {
        List<TextChannel> channels = jda.getTextChannelsByName(config.getModerationChannel(), false);
        if(channels.isEmpty()){
            throw new IllegalArgumentException("Moderation channel is invalid, does not exist");
        }
        moderation = channels.get(0);

    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            String message = "<@" + event.getAuthor().getId() + ">: " + event.getMessage().getContentRaw();
            moderation.sendMessage(message).queue();
        }
    }

    @Override
    public void onGuildMessageReceived(@NonNull GuildMessageReceivedEvent event) {
        List<Member> mentioned = event.getMessage().getMentionedMembers();
        if (!event.getAuthor().isBot() && !mentioned.isEmpty()) {
            mentioned.get(0).getUser().openPrivateChannel().queue((channel) ->
                    channel.sendMessage(event.getMessage()).queue());
        }
    }

}
