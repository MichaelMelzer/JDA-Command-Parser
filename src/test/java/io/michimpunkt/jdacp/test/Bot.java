package io.michimpunkt.jdacp.test;

import io.michimpunkt.jdacp.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {

    private JDA jda;

    public Bot() throws LoginException {
        // use first argument as Discord API token
        jda = JDABuilder.createDefault(Main.args[0])
                .addEventListeners(new CommandHandler())
                .build();
    }

}
