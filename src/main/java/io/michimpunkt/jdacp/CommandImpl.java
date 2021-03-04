package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandImpl<V extends GenericEvent> implements Command<V>  {

    private final String name;
    @Nullable
    private String usage;
    private List<SubCommand> subCommands = new ArrayList<>();
    @Nullable
    private CommandConsumer<V> consumer;
    @Nullable
    private Permission minimumPermission;

    public CommandImpl(String name) {
        this.name = name;
    }

    public CommandImpl(String name, CommandConsumer<V> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public CommandImpl(String name, @Nullable String usage, CommandConsumer<V> consumer) {
        this.name = name;
        this.usage = usage;
        this.consumer = consumer;
    }

    public CommandImpl(String name, @Nullable String usage, CommandConsumer<V> consumer, Permission minimumPermission) {
        this.name = name;
        this.usage = usage;
        this.consumer = consumer;
        this.minimumPermission = minimumPermission;
    }

    @Override
    public String getCommand() {
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
     * User input is faulty, if {@link CommandConsumer#handleCommand} throws an {@link IllegalArgumentException}
     */
    public void setUsage(@Nullable String usage) {
        this.usage = usage;
    }

    @Override
    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    /**
     * @see #CommandImpl(String, CommandConsumer)
     *
     * @param command The command to add
     * @return The handler
     */
    protected CommandImpl addSubCommand(SubCommand command) {
        subCommands.add(command);
        return this;
    }

    /**
     * Adds the sub command to the handler.
     * Returns the handler (can be utilized as builder pattern).
     *
     * @param name The name of the sub command
     * @param usage Correct usage of the command
     * @param consumer The parent command (builder pattern)
     * @return
     */
    public CommandImpl addSubCommand(String name, String usage, CommandConsumer<V> consumer) {
        SubCommand add = new SubCommand(name, consumer, this);
        add.setUsage(usage);
        return addSubCommand(add);
    }

    /**
     * @see #addSubCommand(String, String, CommandConsumer)
     */
    public CommandImpl addSubCommand(String name, CommandConsumer<V> consumer) {
        return addSubCommand(name, null, consumer);
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

    /**
     *
     * @return The consumer of this command
     */
    @Override
    public CommandConsumer<V> getConsumer() {
        return consumer;
    }

    /**
     *
     * @param consumer The consumer to be set
     */
    public void setConsumer(CommandConsumer<V> consumer) {
        this.consumer = consumer;
    }

    /**
     * The JDA command parser will only allow a user to execute this command if they have at least this permission.
     * This can also be null (default)
     *
     * @return The minimum required permissions to execute the command
     */
    public Permission getMinimumPermissions() {
        return minimumPermission;
    }

    /**
     * @see #getMinimumPermissions()
     * @param minimumPermission The new minimum permission
     */
    public void setMinimumPermission(Permission minimumPermission) {
        this.minimumPermission = minimumPermission;
    }

    public String getName() {
        return name;
    }

    public Permission getMinimumPermission() {
        return minimumPermission;
    }

    /**
     * @param event The event
     * @return Whether or not a event can be handled, based on permissions
     */
    @Override
    public boolean hasPermission(V event) {
        if (minimumPermission == null) {
            // of no permission is set, none is required
            return true;
        }

        if (event instanceof GuildMessageReceivedEvent) {
            // check for guild permissions
            return ((GuildMessageReceivedEvent) event).getMember().hasPermission(getMinimumPermissions());
        }

        if (event instanceof PrivateMessageReceivedEvent) {
            // private channels don't have permissions
            return true;
        }

        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent msrEvent = (MessageReceivedEvent) event;
            if ((msrEvent.getChannelType() == ChannelType.TEXT)) {
                // if it in a guild text channel?
                return msrEvent.getMember().hasPermission(getMinimumPermissions());
            } else if ((msrEvent.getChannelType() == ChannelType.PRIVATE)) {
                // it is a private message
                return true;
            }
        }

        // unsupported event type. returning false as a fallback
        return false;
    }

}
