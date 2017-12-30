package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.pointmanager.PointList;
import at.crimsonbit.viergewinntbot.pointmanager.PointManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class pAddUserCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "pAddUser";
	private String desc = "Adds a user to the point list";
	private boolean isOpCommand = true;

	private List<String> aliases = Arrays.asList("pAddUser", "padduser");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!question *</SUBJECT> </DIFFICULTY>*\n" + "__SUBJECT:__\n" + "  /TBD     - TO BE DONE!.\n" + "\n"
			+ "__DIFFICULTY:__\n" + "  /0      - a rather simple question that everyone should be able to solve\n"
			+ "  /1     - a more difficult question. More test oriented\n" + "  /2     - TO BE DONE\n" + "\n"
			+ "__Example:__   !challenge hwe 1\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (isActive) {
			if (e.getAuthor().isBot()) {
				return;
			}
			Log.log(BotDefs.TYPE_POINTMANAGER, true, e.getAuthor() + "issued a PointManager command!");
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				PointManager pManager = PointManager.getInstance();
				PointList pList = pManager.getPointList();
				pList.addNewUser(e.getAuthor());
				pManager.setPointList(pList);
				pManager.savePointList();
				Log.log(BotDefs.TYPE_POINTMANAGER, true, "PointList updated!");
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
