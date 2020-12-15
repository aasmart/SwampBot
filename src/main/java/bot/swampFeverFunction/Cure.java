package bot.swampFeverFunction;

import bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class containing the methods for curing individuals with swamp fever
 */
public class Cure extends ListenerAdapter {
    public static void cure(PrivateMessageReceivedEvent event) {
        // Add the user to the list of users who are crafting cures
        Main.craftingCureIds.add(event.getAuthor().getId());

        ScheduledExecutorService crafting = Executors.newScheduledThreadPool(1);

        // Creates a timer for 2 hours, which is how long it takes to craft the antidote
        // If you change the time, make sure to change it in Main.cureCraftEmbed()
        crafting.schedule(() -> {
            try {
               Main.GUILD.removeRoleFromMember(event.getAuthor().getId(), Main.SWAMP_FEVER_ROLE).queue();
               Main.craftingCureIds.remove(event.getAuthor().getId());

               EmbedBuilder embed = new EmbedBuilder().setTitle("You have been cured of swamp fever!").setColor(Main.DARK_GREEN);
               event.getChannel().sendMessage(embed.build()).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("Sorry, I encountered an error curing you...").queue();
            } finally {
                crafting.shutdownNow();
            }
        }, 2, TimeUnit.HOURS);

        // Send the "cure is crafting" embed
        event.getChannel().sendMessage(Main.cureCraftEmbed().build()).queue();
    }
}
