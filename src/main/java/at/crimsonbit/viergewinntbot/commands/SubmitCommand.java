package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.util.BrowserUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SubmitCommand extends AbstractCommand implements ICommandPlugin {

	private boolean isActive = false;
	private String name = "Submit";
	private String desc = "Opens the 4-Gewinnt Bot Question Forms.\nYou can submit your own questions!";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("submit", "sub", "qAdd");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {

		if (isActive)
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_COMMAND, false, e.getMember().getEffectiveName() + " issued a submit command!");
				BrowserUtil.Open();
				Log.log(BotDefs.TYPE_COMMAND, false, "Opened the Submit form!");
			}
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getUsageInstructions() {
		return null;
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
