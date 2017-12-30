package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.CommandHandler;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.CommandPluginManagerImpl;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PluginCommand extends AbstractCommand implements ICommandPlugin {

	private boolean isActive = false;
	private String name = "Plugin";
	private String desc = "This command can be used by an operator to activate/deactivate plugins\nreload them and more.";
	private boolean isOpCommand = true;
	private List<String> aliases = Arrays.asList("plugin");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			return;
		}

		if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
			Log.log(BotDefs.TYPE_COMMAND, true, e.getMember().getEffectiveName() + " has issued a plugin command!");

			switch (args[1]) {
			case "toggle":
				for (AbstractCommand ac : CommandHandler.getRegisteredCommands()) {
					if (ac.getName().equalsIgnoreCase(args[2])) {
						Log.log(BotDefs.TYPE_COMMAND, false, ac.getName() + " is beeing toggled!");
						if (ac.isActive()) {
							ac.onStop();
							Log.log(BotDefs.TYPE_COMMAND, false, ac.getName() + " changed to " + ac.isActive());
						} else {
							ac.onStart();
							Log.log(BotDefs.TYPE_COMMAND, false, ac.getName() + " changed to " + ac.isActive());
						}
					}
				}
				break;
			case "reload":
				CommandPluginManagerImpl cpmi = CommandPluginManagerImpl.getInstance();
				if (args.length >= 3) {
					for (String s : Arrays.copyOfRange(args, 2, args.length)) {
						cpmi.onExternalPluginLoad(s);
					}
				} else {
					Log.log(BotDefs.TYPE_COMMAND, true, "Reloading plugins by command!");

					cpmi.onExternalPluginLoad();
					SettingsFactory.getInstance().getSettings().setCommands(cpmi.getLoadedCommandPlugins());
					SettingsFactory.getInstance().saveSettings();
					SettingsFactory.getInstance().loadSettings();
				}
				break;
			default:
				break;
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
