package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;

public interface Command<V extends GenericEvent> {

    public String getCommand();

    public String getUsage();

    public void handleCommand(V event) throws IllegalArgumentException;

    public default Permission getMinimumPermissions() {
        return null;
    }

}
