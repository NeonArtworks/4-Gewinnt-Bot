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
import at.crimsonbit.viergewinntbot.pointmanager.PointUser;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class statCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Status";
	private String desc = "This command shows you your current status.";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("stat", "Stat", "rank");
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
			if (args.length == 1)
				if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
					Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a stat command!");

					PointManager pManager = PointManager.getInstance();
					PointList pList = pManager.getPointList();
					PointUser u = pList.findUser(e.getAuthor().getId());
					EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**STATUS**");
					if (u != null) {
						int pos = pList.getPosition(u.getPoints()) + 1;
						eBuilder.addField("Name", e.getMember().getAsMention(), false);
						eBuilder.addField("Your Points", "You currently have " + u.getPoints() + " points", false);
						eBuilder.addField("Place", "You are currently at " + pos + " place!", false);
						e.getChannel().sendMessage(eBuilder.build()).queue();
					} else {
						eBuilder.addField("Name", e.getMember().getAsMention(), false);
						eBuilder.addField("Info", "No one with this name does exist in my database!", false);
						e.getChannel().sendMessage(eBuilder.build()).queue();
					}
				}
			if (args.length == 2) {
				if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
					Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a stat command!");
					EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**STATUS**");
					PointManager pManager = PointManager.getInstance();
					PointList pList = pManager.getPointList();
					PointUser u = pList.findUserByName(args[1]);
					if (u != null) {
						int pos = pList.getPosition(u.getPoints()) + 1;

						eBuilder.addField("Name", args[1], false);
						eBuilder.addField("Points", "They currently have " + u.getPoints() + " points", false);
						eBuilder.addField("Place", "Placing them at " + pos + " place!", false);

						e.getChannel().sendMessage(eBuilder.build()).queue();
					} else {
						eBuilder.addField("Name", e.getMember().getAsMention(), false);
						eBuilder.addField("Info", "No one with this name does exist in my database!", false);
						e.getChannel().sendMessage(eBuilder.build()).queue();
					}

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
