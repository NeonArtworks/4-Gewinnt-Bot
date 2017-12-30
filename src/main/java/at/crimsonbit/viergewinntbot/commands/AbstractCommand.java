package at.crimsonbit.viergewinntbot.commands;

import java.util.List;

import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public abstract class AbstractCommand extends ListenerAdapter {

	public abstract void onCommand(MessageReceivedEvent e, String[] args);

	public abstract List<String> getAliases();

	public abstract String getDescription();

	public abstract String getName();

	public abstract List<String> getUsageInstructions();

	public abstract void setName(String name);

	public abstract void setDescription(String desc);

	public abstract void onStart();

	public abstract void onStop();

	public abstract boolean isActive();

	public AbstractCommand() {
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (e.getAuthor().isBot())
			return;

		if (containsCommand(e.getMessage()))
			onCommand(e, commandArgs(e.getMessage()));
	}

	protected boolean containsCommand(Message message) {
		return (getAliases().contains(commandArgs(message)[0].substring(1)))
				&& (commandArgs(message)[0].startsWith(SettingsFactory.getInstance().getSettings().getCommandPrefix()));
	}

	protected String[] commandArgs(Message message) {
		return commandArgs(message.getContent());
	}

	protected String[] commandArgs(String string) {
		return string.replaceAll(" +", " ").split(" ");
	}
}
