package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.GenericEvent;

public class SubCommand<V extends GenericEvent> extends CommandImpl<V> {

    private final Command parent;

    public SubCommand(String name, CommandConsumer consumer, Command parent) {
        super(name, consumer);
        this.parent = parent;
    }

}
