package at.crimsonbit.viergewinntbot.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.log.LogWriter;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StopCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Stop";
	private String desc = "Stops the bot.\nThis command can only be issued by on operator.";
	private boolean isOpCommand = true;

	private List<String> aliases = Arrays.asList("stop", "exit");
	private List<String> usageInstructions = Collections
			.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n" + "!stop \n" + "__Example:__   !stop\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (e.getAuthor().isBot())
				return;
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_GENERAL, false, e.getAuthor().getName() + " stopped the server!");
				Log.log(BotDefs.TYPE_GENERAL, false, "Stopping Bot....");
				Log.log(BotDefs.TYPE_GENERAL, false, "Stopped server with exitcode: " + BotDefs.EXIT_ON_USER);
				try {
					LogWriter.writeLogToFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(BotDefs.EXIT_ON_USER);
			}
		}
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return usageInstructions;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setDescription(String desc) {
		this.desc = desc;

	}

	public boolean isOpCommand() {
		return isOpCommand;
	}

	public void setOpCommand(boolean isOpCommand) {
		this.isOpCommand = isOpCommand;
	}

	@Override
	public void onStart() {
		isActive = true;

	}

	@Override
	public void onStop() {
		isActive = false;

	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public AbstractCommand getCommand() {
		return this;
	}

}
