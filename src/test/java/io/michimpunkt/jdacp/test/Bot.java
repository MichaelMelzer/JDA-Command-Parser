package io.michimpunkt.jdacp.test;

import io.michimpunkt.jdacp.CommandHandler;
import io.michimpunkt.jdacp.GuildCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class Bot {

    private JDA jda;

    public Bot() throws LoginException {
        // use first argument as Discord API token
        JDABuilder builder = JDABuilder.createDefault(Main.args[0]);

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.addCommand(new GuildCommand("!ping", (event) -> {
            event.getChannel().sendMessage("Pong!").queue();
        }));

        builder.addEventListeners(commandHandler);

        jda = builder.build();
    }

}
