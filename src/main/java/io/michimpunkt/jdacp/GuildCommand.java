package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GuildCommand extends CommandImpl<GuildMessageReceivedEvent> {

    public GuildCommand(String name) {
        super(name);
    }

    public GuildCommand(String name, CommandConsumer<GuildMessageReceivedEvent> consumer) {
        super(name, consumer);
    }

}
