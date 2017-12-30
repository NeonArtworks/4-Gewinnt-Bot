package at.crimsonbit.viergewinntbot.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.log.LogWriter;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class WriteLogCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Log";
	private String desc = "Writes the current log to a log-file.";
	private boolean isOpCommand = true;

	private List<String> aliases = Arrays.asList("log", "writelog");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (isActive)
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_COMMAND, false, e.getMember().getEffectiveName() + " issued a write-log command!");
				try {
					LogWriter.writeLogToFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public void setDescription(String desc) {
		// TODO Auto-generated method stub
		this.desc = desc;
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
