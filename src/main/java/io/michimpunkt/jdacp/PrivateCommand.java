package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class PrivateCommand extends CommandImpl<PrivateMessageReceivedEvent> {

    public PrivateCommand(String name, CommandConsumer<PrivateMessageReceivedEvent> consumer) {
        super(name, consumer);
    }

}
