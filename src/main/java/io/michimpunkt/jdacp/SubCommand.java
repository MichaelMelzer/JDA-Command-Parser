package io.michimpunkt.jdacp;

public class SubCommand extends CommandImpl {

    private final Command parent;

    public SubCommand(String name, CommandConsumer consumer, Command parent) {
        super(name, consumer);
        this.parent = parent;
    }

}
