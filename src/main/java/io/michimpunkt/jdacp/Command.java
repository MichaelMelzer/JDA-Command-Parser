package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;

import java.util.List;

public interface Command<V extends GenericEvent> {

    public String getCommand();

    public String getUsage();

    public List<SubCommand> getSubCommands();

    public CommandConsumer<V> getConsumer();

    public boolean hasPermission(V event);

}
