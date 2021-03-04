package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

public class GenericCommand extends CommandImpl<MessageReceivedEvent> {

    public GenericCommand(String name) {
        super(name);
    }

    public GenericCommand(String name, CommandConsumer<MessageReceivedEvent> consumer) {
        super(name, consumer);
    }

    public GenericCommand(String name, @Nullable String usage, CommandConsumer<MessageReceivedEvent> consumer) {
        super(name, usage, consumer);
    }

    public GenericCommand(String name, @Nullable String usage, CommandConsumer<MessageReceivedEvent> consumer, Permission minimumPermission) {
        super(name, usage, consumer, minimumPermission);
    }

}
