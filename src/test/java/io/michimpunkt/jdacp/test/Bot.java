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

        // just a ping handler
        CommandHandler pingHandler = new CommandHandler();
        pingHandler.addCommand(
            new GuildCommand("!ping", (rawArgs, displayArgs, event) -> {
                event.getChannel().sendMessage("Pong!").queue();
            })
        );
        builder.addEventListeners(pingHandler);

        // something more complicated
        CommandHandler complicatedHandler = new CommandHandler("!bot", "You can't do that!");
        GuildCommand feedCommand = new GuildCommand("feed");

        feedCommand.addSubCommand("me", "Usage: !bot feed me <food>", (rawArgs, displayArgs, event) -> {
            // feed something to the author
            if (rawArgs.length != 1) throw new IllegalArgumentException();
            event.getChannel().sendMessage(event.getAuthor().getAsMention()+" is feeding on some "+rawArgs[0]).queue();
        });

        feedCommand.addSubCommand("you", "Usage: !bot feed you <food>", (rawArgs, displayArgs, event) -> {
            // feeds something to the bot
            if (rawArgs.length != 1) throw new IllegalArgumentException();
            event.getChannel().sendMessage("That's some yummy "+displayArgs[0]+"!").queue();
        });

        complicatedHandler.addCommand(feedCommand);
        builder.addEventListeners(complicatedHandler);

        jda = builder.build();
    }

}
