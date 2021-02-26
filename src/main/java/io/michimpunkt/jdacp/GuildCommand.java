package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class GuildCommand extends CommandImpl<GuildMessageReceivedEvent> {

    public GuildCommand(String name) {
        super(name);
    }

}
