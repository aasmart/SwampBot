package bot;

import bot.events.MessageReceived;
import bot.events.PrivateMessage;
import bot.events.Startup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Just the main class for the bot. Run this to load the bot. It also contains global variables and basic methods
 */
public class Main extends ListenerAdapter implements EventListener {
    /**
     * Run this to load the bot
     * @param args No need for arguments
     */
    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        // Create the JDA object for the bot
        loadBot();
    }

    // Guild
    public static Guild GUILD;

    // Roles
    public static Role SWAMP_FEVER_ROLE;
    public static Role[] IMMUNE_ROLES;  // Add immune roles in bot.events.Startup

    // ArrayLists
    public static ArrayList<String> craftingCureIds; // Contains the IDs of users who are crafting cures

    // Colors
    public static Color DARK_GREEN = new Color(0x047601);

    /**
     * Creates the JDA object for the bot
     * @throws IOException If the 'creds.secret' file doesn't exist
     * @throws LoginException If the bots token is invalid
     * @throws InterruptedException If the JDA object is interrupted during setup
     */
    public static void loadBot() throws IOException, LoginException, InterruptedException {
        // Load credentials from creds.secret file
        File file = new File(Main.class.getResource("/creds.secret").getFile().substring(1));
        System.out.println(file);
        byte[] bytes = Files.readAllBytes(file.toPath());

        // Discord
        JDA jda = JDABuilder.createDefault(
                new String(bytes))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Startup())
                .addEventListeners(new MessageReceived())
                .addEventListeners(new PrivateMessage())
                .setActivity(Activity.playing("with leeches"))
                .build();
        jda.awaitReady();

        System.out.println("Bot Loaded — Ready to start infecting");
    }

    /**
     * Checks if a member has swamp fever
     * @param member The member to check for swamp fever
     * @return True if the member has swamp fever, false if not
     */
    public static boolean isInfected(Member member) {
        return member.getRoles().contains(SWAMP_FEVER_ROLE);
    }

    /**
     * Checks if a member is immune to the effects of 'Swamp Fever' depending on if they have a role
     * in 'IMMUNE ROLES'
     * @param member The member to check for immunity
     * @return True if the member is immune, false if not
     */
    public static boolean isImmune(Member member) {
        for(Role r : member.getRoles()) {
            for(Role immuneRole : IMMUNE_ROLES) {
                if(r.getId().equals(immuneRole.getId()))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method creates the embed sent to users if they contract 'Swamp Fever'
     * @return The embed
     */
    public static EmbedBuilder transmitEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("You've Caught Swamp Fever");
        embed.setDescription("Yup, it sucks. Don't worry however, there's a cure! If you happen to figure it out, message me " +
                "the cure...");
        embed.setColor(DARK_GREEN);
        embed.addField("Symptoms of Swamp Fever may Include:",
                "\n - Shorter Messages (1000 character limit)" +
                "\n - Mild sickness" +
                "\n - Many may experience no reactions", false);

        return embed;
    }

    /**
     * This method creates the embed sent to user if they craft "Lesser Antidote"
     * @return The embed
     */
    public static EmbedBuilder cureCraftEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Crafting Lesser Antidote");
        embed.setDescription("Your **lesser antidote** is crafting! It will be done in two hours, and then you will be cured! For " +
                "the sake of the 'game', don't tell anyone the cure!");
        embed.setColor(DARK_GREEN);

        return embed;
    }
}
