package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.crimsonbit.testparser.api.APIQuestion;
import at.crimsonbit.testparser.api.APIResponse;
import at.crimsonbit.testparser.api.DataQuestionParser;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.parser.Parser;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import at.crimsonbit.viergewinntbot.subject.Subject;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class QuestionCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Question";
	private String desc = "Generates a random question correspongind to your entered subject.\nYou can answer a question qith the ID provided.";
	private boolean isOpCommand = false;

	private List<String> aliases = Arrays.asList("frage", "question", "Question");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!question *</SUBJECT> </DIFFICULTY>*\n" + "__SUBJECT:__\n" + "  /TBD     - TO BE DONE!.\n" + "\n"
			+ "__DIFFICULTY:__\n" + "  /0      - a rather simple question that everyone should be able to solve\n"
			+ "  /1     - a more difficult question. More test oriented\n" + "  /2     - TO BE DONE\n" + "\n"
			+ "__Example:__   !challenge hwe 1\n");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (e.getAuthor().isBot())
				return;
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				if (args.length < 3) {
					Log.log(BotDefs.TYPE_COMMAND, true,
							"Received an invalid Question request by: " + e.getAuthor().getName());
					e.getChannel().sendMessage(getUsageInstructions().get(0)).queue();
					EmbedBuilder ebuilder = new EmbedBuilder();
					ebuilder.setDescription("**CLIL Hilfeseite. Sie brauchens, wir habens!**");
					for (Subject s : SettingsFactory.getInstance().getSettings().getSubjects()) {
						ebuilder.addField(s.getSubject(), "__", true);
					}
					e.getChannel().sendMessage(ebuilder.build()).queue();

				} else {

					DataQuestionParser parser = Parser.getInstance();
					int i = 1;
					try {
						i = Integer.parseInt(args[2]);
						Subject s = SettingsFactory.getInstance().getSettings().searchSubject(args[1].toUpperCase());
						if (s == null) {
							Log.log(BotDefs.TYPE_COMMAND, true, "Received an invalid Question request by: "
									+ e.getAuthor().getName() + " Subject NULL!");
							e.getChannel().sendMessage("Invalid Subject!").queue();
							return;
						}
						if (i < 0 || i > s.getDifficultyRange()) {
							Log.log(BotDefs.TYPE_COMMAND, true, "Received an invalid Question request by: "
									+ e.getAuthor().getName() + " Difficulty out of range!");
							e.getChannel().sendMessage("Difficulty out of range!").queue();
							return;
						}
					} catch (Exception a) {
						Log.log(BotDefs.TYPE_COMMAND, true, "Received an invalid Question request by: "
								+ e.getAuthor().getName() + " no INT was received for arg. 2");
						e.getChannel().sendMessage("Difficulty has to be an integer!").queue();
						return;
					}
					Log.log(BotDefs.TYPE_COMMAND, false, "Received a question request from " + e.getAuthor().getName());
					Log.log(BotDefs.TYPE_COMMAND, false, "Question type: " + args[1]);
					Log.log(BotDefs.TYPE_COMMAND, false, "Question difficulty: " + i);

					APIResponse<APIQuestion> r = parser.getRandomQuestion(args[1], i);
					APIQuestion q = r.getResponse();
					if (q == null) {
						Log.log(BotDefs.TYPE_COMMAND, true, r.getMessage());
						return;
					}
					String t = q.getQ().getTasksAsString().replace("*", "\\*");

					EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**QUESTION**");
					eBuilder.addField("Subject", args[1], true);
					eBuilder.addField("Difficulty", args[2], true);
					eBuilder.addField("UUID", q.getUID().toString(), false);
					eBuilder.addField("Question", t, false);
					eBuilder.addBlankField(false);

					e.getChannel().sendMessage(eBuilder.build()).queue();
					Log.log(BotDefs.TYPE_COMMAND, false, "Question UID: " + q.getUID());
					Log.log(BotDefs.TYPE_COMMAND, false, "Question: " + q.getQ());

				}
			}
			// sender.sendPublicMessage(e.getChannel(), "Hier könnte Ihre
			// Werbung stehen!");
		}
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getName() {
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
