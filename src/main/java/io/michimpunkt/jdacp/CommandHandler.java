package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandHandler extends ListenerAdapter {

    @Nullable
    private String defaultCue;
    private final List<Command<? extends GenericEvent>> commands = new ArrayList<>();
    private String noPermissions;

    /**
     * A command handler can be setup so that it will only listen to a certain cue phrase. This is optional tho.
     * If the handler does not have a cue, commands will always be triggered, if they match all other requirements.
     */
    public CommandHandler() {
        this(null, "You don't have enough permissions!");
    }

    public CommandHandler(@Nullable String defaultCue) {
        this(defaultCue, "You don't have enough permissions!");
    }

    /**
     * @see CommandHandler
     *
     * @param defaultCue The default cue which the command will listen to
     * @param noPermissions The message to be displayed if a member doesn't have enough permissions
     */
    public CommandHandler(@Nullable  String defaultCue, String noPermissions) {
        this.defaultCue = defaultCue;
        this.noPermissions = noPermissions;
    }

    /**
     *
     * @param command The command to be added to the handler
     * @return The same handler (builder pattern)
     */
    public CommandHandler addCommand(Command<? extends GenericEvent> command) {
        commands.add(command);
        return this;
    }

    @Nullable
    public String getDefaultCue() {
        return defaultCue;
    }

    public CommandHandler setDefaultCue(@Nullable String defaultCue) {
        this.defaultCue = defaultCue;
        return this;
    }

    public String getNoPermissions() {
        return noPermissions;
    }

    public CommandHandler setNoPermissions(String noPermissions) {
        this.noPermissions = noPermissions;
        return this;
    }

    @Override
    public String toString() {
        return "CommandHandler{" +
                "defaultCue='" + defaultCue + '\'' +
                ", commands=" + commands +
                ", noPermissions='" + noPermissions + '\'' +
                '}';
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        commands.stream().filter(command -> command instanceof GuildCommand)
                .forEach(command -> handleCommand(command, event, event.getMessage()));
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        commands.stream().filter(command -> command instanceof  PrivateCommand)
                .forEach(command -> handleCommand(command, event, event.getMessage()));
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        commands.stream().filter(command -> command instanceof  GenericCommand)
                .forEach(command -> handleCommand(command, event, event.getMessage()));
    }

    private void handleCommand(Command<? extends GenericEvent> command, @NotNull GenericEvent event, Message message) {
        String[] rawArgs = message.getContentRaw().split(" ");
        String[] displayArgs = message.getContentDisplay().split(" ");

        // message needs to have content
        if (rawArgs.length == 0) {
            return;
        }

        // handle cue
        if (defaultCue != null && !defaultCue.equalsIgnoreCase("")) {
            if (!rawArgs[0].equalsIgnoreCase(defaultCue)) {
                return;
            }

            rawArgs = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
            displayArgs = Arrays.copyOfRange(displayArgs, 1, displayArgs.length);
        }

        // find correct sub command recursively
        Command subCommand = command;
        while (!subCommand.getSubCommands().isEmpty() && rawArgs.length > 1) {
            for (Object o : subCommand.getSubCommands()) {
                // not sure why this is necessary
                SubCommand<? extends GenericEvent> subSubCommand = (SubCommand<? extends GenericEvent>) o;

                if (subSubCommand.getCommand().equalsIgnoreCase(rawArgs[1])) {
                    subCommand = subSubCommand;
                    break;
                }
            }

            rawArgs = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
            displayArgs = Arrays.copyOfRange(displayArgs, 1, displayArgs.length);
        }


        // process command
        if (subCommand.getConsumer() == null || !subCommand.getCommand().equalsIgnoreCase(rawArgs[0])) {
            // no command matches
            return;
        }
        rawArgs = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
        displayArgs = Arrays.copyOfRange(displayArgs, 1, displayArgs.length);
        if (subCommand.hasPermission(event)) {
            try {
                subCommand.getConsumer().handleCommand(rawArgs, displayArgs, event);
            } catch (IllegalArgumentException e) {
                // easily handle illegal arguments
                if (e.getMessage() != null && !e.getMessage().equals("")) {
                    message.getChannel().sendMessage(e.getMessage()).queue();
                } else if (subCommand.getUsage() != null) {
                    message.getChannel().sendMessage(subCommand.getUsage()).queue();
                }
            } catch (Exception e) {
                message.getChannel().sendMessage("Error executing command!").queue();
                e.printStackTrace();
            }
        } else {
            // member does not have permissions
            message.getChannel().sendMessage(noPermissions).queue();
        }
    }

}
