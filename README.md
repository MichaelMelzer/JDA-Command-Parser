# JDA-Command-Parser
Object oriented way to easily create (classic) Discord bot commands

The command parser lets you easily create bot commands and add sub-command recursively through lambda expressions. The Manager automatically tests each message whether it matches a specified command, and calls an consumer if so. All functionality and examples below.

Uses/Requires [Java Discord API](https://github.com/DV8FromTheWorld/JDA)


# Structure
**Classes**
- CommandHandler: Does all the magic, Implements JDA's ListenerAdapter, and may be passed on to JDABuilder#addEventListener
- GenericCommand (not recommended): A command that listens to private chat messages as well as guild chat messages
- GuildCommand: A command that only listens to chat messages in a guild
- PrivateCommand: A command that only listens to chat messages in PMs
- SubCommand: A command that can be nested underneath other commands, including subcommands

**Why are there three different types of commands?**
Basically, JDA has three different types of commands itself, so this structure was adopted. Also, Generic JDA Commands are kind of sketchy, as they combine two entirely different MessageChannel types. Most Discord bots differentiate between Guild Commands and Private Commands anyways.

**Cues:**
A Command Manager can have an optional cue phrase, so that commands only trigger if a message is predicated by that cue. A cue example might be "!bot", which in turn means that a command might look like "!bot ping", but you only need a "ping" Command

**Args:**
The lambda expressions pass on two String arrays and the MessageReceivedEvent itself
- rawArgs: Raw arguments for easy access, taken from Message#getContentRaw (JDA-CP only processes these)
- displayArgs: Raw arguments for easy access, taken from Message#getContentDisplay
- event: Depending on the type of Command, either the corresponding MessageReceivedEvent, GuildMessageReceivedEvent or PrivateMessageReceivedEvent

**Illegal Arguments:**
JDA-CP supports illegal arguments. You can throw an IllegalArgumentException within a Command Consumer to fetch faulty user input. The user will receive a text reply informing them about the mistake, if a Usage has been specified.

**Permissions:**
You can specify a minimuim permission level a Member has to have in order to execute commands. If they do not have that permission, the bot will reply with an error message. Private Commands can't have any permissions.

# Examples
**Very basic usage:**

	// the handler manages all commands
    CommandHandler pingHandler = new CommandHandler();  
	
	// creates a command that listenes to guild messages and is triggerd by "!ping"	
    pingHandler.addCommand(new GuildCommand("!ping", (rawArgs, displayArgs, event) -> {  
	    // reply with "Pong"
        event.getChannel().sendMessage("Pong!").queue();  
    }));
    
    // the handler has to be added to JDA
    jdaBuilder.addEventListeners(pingHandler);

**A more complicated example:**

	CommandHandler complicatedHandler = new CommandHandler("!bot", "You don't have permission to do that!");  
	
	GuildCommand feedCommand = new GuildCommand("feed");  
	feedCommand.addSubCommand("me", "Usage: !bot feed me <food>", (rawArgs, displayArgs, event) -> {  
		// we need exactly one argument
	    if (rawArgs.length != 1) throw new IllegalArgumentException();  
	    
	    // feed something to the author  
	    event.getChannel().sendMessage(event.getAuthor().getAsMention()+" is feeding on some "+rawArgs[0]).queue();  
	});  
	  
	feedCommand.addSubCommand("you", "Usage: !bot feed you <food>", (rawArgs, displayArgs, event) -> {  
		// we need exactly one argument
	    if (rawArgs.length != 1) throw new IllegalArgumentException(); 
	    
	    // feeds something to the bot   
	    event.getChannel().sendMessage("That's some yummy "+displayArgs[0]+"!").queue();  
	});  
	  
	complicatedHandler.addCommand(feedCommand);  
	jdaBuilder.addEventListeners(complicatedHandler);
This will result in two Guild Commands:
"!bot feed me cheese" -> "@MichiMPunkte is feeding on some cheese"
"!bot feed you bits" -> "That's some yummy bits!"

Where:
!bot = cue words for the handler
feed = the main command
me/you = two seperate SubCommands of feed
rawArgs[] = the arguments of the SubCommand
