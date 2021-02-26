package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;

public class CommandHandler extends ListenerAdapter {

    private String defaultCue;
    private HashMap<Guild, String> guildCues;
    private List<Command> commands;

    /**
     * @see CommandHandler(String)
     */
    public CommandHandler() {
        this("");
    }

    /**
     * A command handler can be setup so that it will only listen to a certain cue phrase. This is optional tho.
     * If the handler does not have a cue, commands will always be triggered, if they match all other requirements.
     *
     * @param defaultCue The default cue which the command will listen to
     */
    public CommandHandler(String defaultCue) {
        this.defaultCue = defaultCue;
    }

    /**
     *
     * @param command The command to be added to the handler
     * @return The same handler (builder pattern)
     */
    public CommandHandler addCommand(Command command) {
        commands.add(command);
        return this;
    }

}
