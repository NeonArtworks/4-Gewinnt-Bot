package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.ConfigChangeHandler;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class changeCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Change";
	private String desc = "With this command an operator can change the: gToken, gCGame, gCPrefix, mChannelID, mPLaylistLimit\nmAudioBuffer";
	private boolean isOpCommand = true;
	private ConfigChangeHandler handler = new ConfigChangeHandler();

	private List<String> aliases = Arrays.asList("change", "Change");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!solution *</TOKEN>*\n" + "__TOKEN:__\n" + "  /TOKEN_STRING     - The token of the question!.\n" + "\n"
			+ "__Example:__   !solution blablalbablalbablablabla\n");
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (isActive) {
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_COMMAND, true, e.getMember().getEffectiveName() + " issued a change command!");
				if (args.length < 3) {
					return;
				}
				String arg = args[1];

				switch (arg) {
				case "gToken":
					handler.changeToken(e, args);
					break;
				case "gCGame":
					handler.changeGame(e, args);
					break;
				case "gCPrefix":
					handler.changePrefix(e, args);
					break;
				case "mChannelID":
					handler.changeChannelID(e, args);
					break;
				case "mPLaylistLimit":
					handler.changePlaylistLimit(e, args);
					break;
				case "mAudioBuffer":
					handler.changeAudioBuffer(e, args);
					break;
				default:
					break;
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
		return usageInstruction;
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
