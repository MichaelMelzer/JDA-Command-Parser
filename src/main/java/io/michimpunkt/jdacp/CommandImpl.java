package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.GenericEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandImpl<V extends GenericEvent> implements Command<V>  {

    private final String name;
    @Nullable
    private String usage;
    private List<SubCommand> subCommands = new ArrayList<>();

    public CommandImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * @see #setUsage
     * @return The usage of this command
     */
    @Override
    @Nullable
    public String getUsage() {
        return usage;
    }

    /**
     * Commands can have an optional Usage parameter. This will be sent to the user, if their input is faulty.
     * User input is faulty, if {@link Command#handleCommand} throws an {@link IllegalArgumentException}
     *
     * @return The usage of this command
     */
    public void setUsage(@Nullable String usage) {
        this.usage = usage;
    }

    /**
     * Adds the sub command to the handler.
     * Returns the handler (can be utilized as builder pattern).
     *
     * @param command The command to add
     * @return The handler
     */
    public CommandImpl addSubCommand(SubCommand command) {
        subCommands.add(command);
        return this;
    }

    /**
     * Removes a sub command from the handler.
     *
     * @param command The command to be removed
     * @return Boolean, taken from {@link java.util.List#remove}
     */
    public boolean removeSubCommand(SubCommand command) {
        return subCommands.remove(command);
    }

}
