package bot.events;

import bot.Main;
import bot.swampFeverFunction.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Class containing the methods for interacting with messages sent in the Discord server
 */
public class MessageReceived extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        // Make sure the message is not from a bot
        if(event.getAuthor().isBot())
            return;

        Member member = event.getMember();
        if(member == null)
            return;

        List<Member> members = event.getMessage().getMentionedMembers();

        // If the user is infected and not immune, give them symptoms
        if(Main.isInfected(member) && !Main.isImmune(member)) {
            Symptoms.messageLength(event);
            Symptoms.sickReaction(event);
        // If the member mentions users, attempt to infect them
        } else if(members.size() > 0)
            Transmit.transmit(event, members);

    }
}
