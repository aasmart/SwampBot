package bot.events;

import bot.Main;
import bot.swampFeverFunction.Cure;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Class containing the method for interacting with private messages
 */
public class PrivateMessage extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        if(event.getAuthor().isBot())
            return;

        try {
            // Check and make sure the user has swamp fever
            if (Main.isInfected(Objects.requireNonNull(Main.GUILD.getMember(event.getAuthor())))) {
                // If the users message doesn't contain 'lesser antidote' (case insensitive) anywhere, it wont craft the antidote
                if(!event.getMessage().getContentRaw().toLowerCase().contains("lesser antidote"))
                    return;

                // Make sure the user isn't already crafting an antidote
                if (!Main.craftingCureIds.contains(event.getAuthor().getId()))
                    Cure.cure(event);
                else
                    event.getChannel().sendMessage("You're already crafting the cure!").queue();

            } else
                event.getChannel().sendMessage("You can't craft a cure because you're not infected!").queue();
        } catch (Exception ignored) { }
    }
}
