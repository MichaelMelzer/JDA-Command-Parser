package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;

public class CommandHandler extends ListenerAdapter {

    private String defaultCue;
    private HashMap<Guild, String> guildCues;
    private List<Command> commands;

    public CommandHandler() {
        this("");
    }

    public CommandHandler(String defaultCue) {
        this.defaultCue = defaultCue;
    }

}
