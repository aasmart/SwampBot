package bot.swampFeverFunction;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * Class containing the methods for various 'Swamp Fever' symptoms
 */
public class Symptoms {
    public static final int MESSAGE_SIZE = 1000;    // Message size limit for an infected individual

    /**
     * A symptom of 'Swamp Fever' is shorter messages...
     * @param event The event
     */
    public static void messageLength(GuildMessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().length() > MESSAGE_SIZE)
            event.getMessage().delete().queue();
    }

    /**
     * This method causes the :sick: emoji to be added to a message. This has a 20% change of occurring
     * @param event The event
     */
    public static void sickReaction(GuildMessageReceivedEvent event) {
        if(Math.random() >= .8)
            event.getMessage().addReaction("\uD83E\uDD22").queue();
    }


}
