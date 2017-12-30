package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SuicideCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Suicide";
	private String desc = "Sends 'you know what'";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("suicide", "kill", "selstmord");
	private List<String> usageInstructions = Collections
			.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n" + "!stop \n" + "__Example:__   !stop\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (e.getAuthor().isBot()) {
				return;
			}
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a SUICIDE command!");
				e.getAuthor().openPrivateChannel().queue((channel) -> {
					channel.sendMessage("Hier ist dein Abmeldeformular!").queue();
				});
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
		return usageInstructions;
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
