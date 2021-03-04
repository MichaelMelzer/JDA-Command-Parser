package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

public class PrivateCommand extends CommandImpl<PrivateMessageReceivedEvent> {

    public PrivateCommand(String name, CommandConsumer<PrivateMessageReceivedEvent> consumer) {
        super(name, consumer);
    }

    public PrivateCommand(String name, @Nullable String usage, CommandConsumer<PrivateMessageReceivedEvent> consumer) {
        super(name, usage, consumer);
    }

    public PrivateCommand(String name, @Nullable String usage, CommandConsumer<PrivateMessageReceivedEvent> consumer, Permission minimumPermission) {
        super(name, usage, consumer, minimumPermission);
    }

    public PrivateCommand(String name) {
        super(name);
    }
}
