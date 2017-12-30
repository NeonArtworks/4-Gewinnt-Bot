package at.crimsonbit.viergewinntbot.handler;

import java.util.HashSet;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.log.Log;

public class CommandHandler {
	private static HashSet<AbstractCommand> commands;

	public CommandHandler() {
		commands = new HashSet<AbstractCommand>();
	}

	public static void deleteRegisteredCommands() {
		commands.clear();
	}

	public static AbstractCommand registerCommand(AbstractCommand command) {
		if (command == null) {
			Log.log(BotDefs.TYPE_REGISTRY, true, "ERROR while register: Command was null!");
			return command;

		} else {
			if (command.isActive()) {
				Log.log(BotDefs.TYPE_REGISTRY, false, "Command Name: " + command.getName());
				Log.log(BotDefs.TYPE_REGISTRY, false, "Command Aliases: " + command.getAliases());
				try {
					commands.add(command);
				} catch (NullPointerException e) {
					Log.log(BotDefs.TYPE_REGISTRY, true, "ERROR while register: Command was null!");
				}

				Log.log(BotDefs.TYPE_REGISTRY, false,
						"Command registry for " + command.getName() + " was successfull!");
			}
		}
		return command;
	}

	public static int getNumberOfRegisteredCommands() {
		return commands.size();
	}

	public static HashSet<AbstractCommand> getRegisteredCommands() {
		return commands;
	}
}
