package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.GenericEvent;

public interface CommandConsumer<V extends GenericEvent> {

    /**
     * Called if all criteria matches. Should be implemented by other developers.
     * Can throw an exception. If this is thrown, the user will receive a usage message if specified
     *
     * @param event The event
     * @throws IllegalArgumentException Consuimers can throw an {@link IllegalArgumentException} if a user supplies faulty input
     */
    void handleCommand(String[] rawArgs, String[] displayArgs, V event) throws IllegalArgumentException;

}
