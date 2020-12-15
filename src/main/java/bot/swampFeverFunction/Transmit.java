package bot.swampFeverFunction;

import bot.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

/**
 * This class provides the transmission function for 'Swamp Fever'. To transmit 'Swamp Fever', a player must mention a member
 * who has 'Swamp Fever'.
 */
public class Transmit {
    /**
     * Transmits 'Swamp Fever' by checking if a list of mentioned members contains a member who has swamp fever. If they do, the
     * sender is given swamp fever as well
     *
     * @param event   The event
     * @param members The List of members to check for 'Swamp Fever'
     */
    public static void transmit(GuildMessageReceivedEvent event, List<Member> members) {
        Member sender = event.getMember();

        // Make sure the sender is not null
        if (sender == null) {
            System.out.println("Member null");
            return;
            // Make sure the sender doesn't already have 'Swamp Fever'
        } else if (Main.isInfected(sender))
            return;


        // Loop through the list of mentioned members and check to see if any of the members have 'Swamp Fever'
        for (Member m : members) {
            if (Main.isInfected(m)) {
                // If one of the members has 'Swamp Fever', give the sender 'Swamp Fever'
                Main.GUILD.addRoleToMember(sender, Main.SWAMP_FEVER_ROLE).queue(
                        success -> {
                            // Send the user the message telling them they were infected
                            try {
                                sender.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(Main.transmitEmbed().build()).queue(throwable -> {
                                }));
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Couldn't send DM");
                            }
                        },
                        throwable -> System.out.println(
                                "Couldn't modify role of user " + sender.getUser().getName() +
                                        "#" + sender.getUser().getDiscriminator() + ": " + throwable.getMessage())
                );

                // DEBUG MESSAGE
                //System.out.println("Gave " + sender.getUser().getName() + "#" + sender.getUser().getDiscriminator() + " swamp fever");
                return;
            }
        }
    }
}
