package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.ConfigChangeHandler;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.util.ChallengeManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ChallengeClearCommand extends AbstractCommand implements ICommandPlugin {

	private boolean isActive = false;
	private String name = "ChallengeClear";
	private String desc = "Clears all challenges";
	private boolean isOpCommand = true;
	private List<String> aliases = Arrays.asList("cClear", "challengeClear");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub

		if (isActive) {
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_CHALLENGE, false,
						e.getMember().getEffectiveName() + " issued a challenge clear commadn!");
				ChallengeManager manager = ChallengeManager.getInstance();
				if (manager.isActive()) {
					manager.setActive(false);
					Log.log(BotDefs.TYPE_CHALLENGE, false, "Current challenge canceled!");
					e.getTextChannel().sendMessage(" ");
				} else {
					Log.log(BotDefs.TYPE_CHALLENGE, false, "No challenge to cancel!");
				}
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
