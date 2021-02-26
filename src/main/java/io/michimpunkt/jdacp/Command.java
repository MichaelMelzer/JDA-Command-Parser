package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;

public interface Command<V extends GenericEvent> {

    public String getCommand();

    public String getUsage();

    /**
     * Called if all criteria matches. Should be implemented by other developers.
     * Can throw an exception. If this is thrown, the user will receive a usage message if specified
     *
     * @param event The event
     * @throws IllegalArgumentException
     */
    public void handleCommand(V event) throws IllegalArgumentException;

    /**
     * The JDA command parser will only allow a user to execute this command if they have at least this permission.
     * This can also be null (default)
     *
     * @return The minimum required permissions to execute the command
     */
    public default Permission getMinimumPermissions() {
        return null;
    }

}
