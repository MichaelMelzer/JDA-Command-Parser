package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;
import org.jetbrains.annotations.Nullable;

public class SubCommand<V extends GenericEvent> extends CommandImpl<V> {

    private final Command parent;

    public SubCommand(String name, Command parent) {
        super(name);
        this.parent = parent;
    }

    public SubCommand(String name, CommandConsumer<V> consumer, Command parent) {
        super(name, consumer);
        this.parent = parent;
    }

    public SubCommand(String name, @Nullable String usage, CommandConsumer<V> consumer, Command parent) {
        super(name, usage, consumer);
        this.parent = parent;
    }

    public SubCommand(String name, @Nullable String usage, CommandConsumer<V> consumer, Permission minimumPermission, Command parent) {
        super(name, usage, consumer, minimumPermission);
        this.parent = parent;
    }
}
