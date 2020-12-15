package bot.events;

import bot.Main;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Class containing the method for setting up guild objects on the JDA load
 */
public class Startup extends ListenerAdapter {
    private final long swampFeverRoleID = 787788174231470131L;  // This is the ID of the swamp fever role

    public void onGuildReady(@NotNull GuildReadyEvent event) {
        // Send startup message
        System.out.println("Initializing Guild Objects...");

        // Get the bots current guild
        Main.GUILD = event.getGuild();

        // Setup Roles
        Main.SWAMP_FEVER_ROLE = Main.GUILD.getRoleById(swampFeverRoleID);

        /* These are the roles that are immune to the effects of 'Swamp Fever'. To add one, paste
        Main.GUILD.getRoleById([Role ID HERE]L), The 'L' is just because it is a long value
        */
        Main.IMMUNE_ROLES = new Role[] {
                //Main.GUILD.getRoleById(787788681079554068L),
        };

        // Setup Arraylists
        Main.craftingCureIds = new ArrayList<>();

        // Send completion message
        System.out.println("Successfully Initialized Guild Objects...");
    }
}
