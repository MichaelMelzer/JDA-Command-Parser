package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GenericCommand extends CommandImpl<MessageReceivedEvent> {

    public GenericCommand(String name) {
        super(name);
    }

    public GenericCommand(String name, CommandConsumer<MessageReceivedEvent> consumer) {
        super(name, consumer);
    }

}
