package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

public class GuildCommand extends CommandImpl<GuildMessageReceivedEvent> {

    public GuildCommand(String name) {
        super(name);
    }

    public GuildCommand(String name, CommandConsumer<GuildMessageReceivedEvent> consumer) {
        super(name, consumer);
    }

    public GuildCommand(String name, @Nullable String usage, CommandConsumer<GuildMessageReceivedEvent> consumer) {
        super(name, usage, consumer);
    }

    public GuildCommand(String name, @Nullable String usage, CommandConsumer<GuildMessageReceivedEvent> consumer, Permission minimumPermission) {
        super(name, usage, consumer, minimumPermission);
    }
}
