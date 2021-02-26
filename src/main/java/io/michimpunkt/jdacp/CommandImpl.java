package io.michimpunkt.jdacp;

import net.dv8tion.jda.api.events.GenericEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandImpl<V extends GenericEvent>  {

    private final String name;
    @Nullable
    private String usage;
    private List<SubCommand> subCommands = new ArrayList<>();

    public CommandImpl(String name) {
        this.name = name;
    }

}
