package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public abstract class PrivateCommand extends CommandImpl<PrivateMessageReceivedEvent> {

    public PrivateCommand(String name) {
        super(name);
    }

}
