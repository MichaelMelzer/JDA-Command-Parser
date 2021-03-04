package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.GenericEvent;

import java.util.List;

public interface Command<V extends GenericEvent> {

    String getCommand();

    String getUsage();

    List<SubCommand> getSubCommands();

    CommandConsumer<V> getConsumer();

    boolean hasPermission(V event);

}
