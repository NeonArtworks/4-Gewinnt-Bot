package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HintCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Hint";
	private String desc = "Gives you (a) hint(s) to the question(s).";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("hint", "hinweis");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (isActive) {
			if (e.getAuthor().isBot()) {
				return;
			}
			Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a hint command!");
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
