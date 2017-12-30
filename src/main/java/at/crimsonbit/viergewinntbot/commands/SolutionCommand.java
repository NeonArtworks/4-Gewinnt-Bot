package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.testparser.api.APIQuestion;
import at.crimsonbit.viergewinntbot.answermanager.AnswerManager;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.parser.Parser;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.pointmanager.PointManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SolutionCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Solution";
	private String desc = "Shows the solution of the question.\nNote: For every solution that you request, points will be removed from your score.\nOnce your point count reaches 0 you will not be able to request any solutions.";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("solution", "auflösung");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!solution *</TOKEN>*\n" + "__TOKEN:__\n" + "  /TOKEN_STRING     - The token of the question!.\n" + "\n"
			+ "__Example:__   !solution blablalbablalbablablabla\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (e.getAuthor().isBot()) {
				return;
			}
			APIQuestion p = Parser.getInstance().replicateQuestion(args[1]).getResponse();
			Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a solution command!");
			// if
			// (!AnswerManager.getInstance().getAnswerList().isAnswered(e.getAuthor(),
			// p.getUID())) {

			AnswerManager.getInstance().getAnswerList().addAnswer(e.getAuthor(), p.getUID());
			AnswerManager.getInstance().saveanswerList();
			PointManager.getInstance().getPointList().addPointsToUser(e.getAuthor(), -1);
			PointManager.getInstance().savePointList();

			if (PointManager.getInstance().getPointList().getUserPoints(e.getAuthor()) >= 1) {
				e.getAuthor().openPrivateChannel().queue((channel) -> {
					EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**Question for __" + args[1] + "__**");
					eBuilder.addField("UUID", p.getUID().toString(), false);
					eBuilder.addField("Answer(s)", p.getAnswer(), true);
					eBuilder.addBlankField(false);
					eBuilder.addField("Info", "__You now have **1** point less!__", true);
					eBuilder.addField("Current Points",
							"__You have "
									+ String.valueOf(
											PointManager.getInstance().getPointList().getUserPoints(e.getAuthor()))
									+ " points!__",
							true);

					channel.sendMessage(eBuilder.build()).queue();
				});

			}
		}
		// }
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
