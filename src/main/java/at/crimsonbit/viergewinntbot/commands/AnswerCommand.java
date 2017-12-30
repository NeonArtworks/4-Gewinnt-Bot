package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.testparser.api.APIQuestion;
import at.crimsonbit.testparser.api.DataQuestionParser;
import at.crimsonbit.viergewinntbot.answermanager.AnswerManager;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.parser.Parser;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.pointmanager.PointManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AnswerCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Answer";
	private String desc = "Checks wether your answer is true or false. \nIf your answer is true, points will be added to your point list!";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("answer", "antwort");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!solution *</TOKEN>*\n" + "__TOKEN:__\n" + "  /TOKEN_STRING     - The token of the question!.\n" + "\n"
			+ "__Example:__   !solution blablalbablalbablablabla\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (args.length >= 2)
				if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
					String[] arg = Arrays.copyOfRange(args, 2, args.length);
					// for(String s : arg) System.out.println(s);
					Log.log(BotDefs.TYPE_COMMAND, false,
							e.getMember().getEffectiveName() + " issued a answer command!");
					DataQuestionParser parser = Parser.getInstance();
					if (args[1] == " " || args[1] == "" || args[1] == null || args[1].length() != 24) {
						Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor() + " incorrect answer!");
						e.getChannel().sendMessage("Invalid answer command! The ID was incorrect").queue();
					} else {
						APIQuestion p = parser.replicateQuestion(args[1]).getResponse();
						EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**ANSWER**");
						eBuilder.addField("UUID", p.getUID().toString(), false);
						if (!AnswerManager.getInstance().getAnswerList().isAnswered(e.getAuthor(), p.getUID())) {
							boolean[] rets = p.areSolutions(arg);
							e.getAuthor().openPrivateChannel().queue((channel) -> {
								int c = 0, c2 = 0;
								for (boolean b : rets) {
									if (b) {
										eBuilder.addField("Answer: " + (c + 1), " was correct!", true);

										Log.log(BotDefs.TYPE_COMMAND, false, e.getMember().getEffectiveName()
												+ " had a correct answer (" + c + ") to question " + p.getUID());
										c2++;
									} else {
										eBuilder.addField("Answer: " + (c + 1), " was incorrect!", true);

										Log.log(BotDefs.TYPE_COMMAND, false, e.getMember().getEffectiveName()
												+ " had the wrong answer (" + c + ") to question " + p.getUID());
									}
									c++;
								}
								if (c2 == arg.length) {
									AnswerManager.getInstance().getAnswerList().addAnswer(e.getAuthor(), p.getUID());
									AnswerManager.getInstance().saveanswerList();
									PointManager.getInstance().getPointList().addPointsToUser(e.getAuthor(), c2);
									PointManager.getInstance().savePointList();
								}
								eBuilder.addField("Info", "You had a total of " + c2 + " answers correct!", true);
								eBuilder.addField("Your Points",
										"You now have "
												+ PointManager.getInstance().getPointList().getUserPoints(e.getAuthor())
												+ " points",
										true);
								channel.sendMessage(eBuilder.build()).queue();
							});
						} else {
							e.getAuthor().openPrivateChannel().queue((channel) -> {
								eBuilder.addField("Information", "You already have answered this question!", false);
								channel.sendMessage(eBuilder.build()).queue();
							});
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
