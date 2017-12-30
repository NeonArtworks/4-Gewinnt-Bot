package at.crimsonbit.viergewinntbot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import com.google.api.client.repackaged.com.google.common.base.Supplier;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import at.crimsonbit.viergewinntbot.subject.Subject;
import at.crimsonbit.viergewinntbot.util.ChallengeManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ChallengeCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = false;
	private String name = "Challenge";
	private String desc = "Starts a challenge.\nThis command can only be issued by an operator!";
	private boolean isOpCommand = true;

	private List<String> aliases = Arrays.asList("challenge", "startChallenge", "Challenge");
	private List<String> usageInstruction = Collections.singletonList("CLIL Hilfeseite. Sie brauchens, wir habens!\n"
			+ "!challenge *</SUBJECT> </DIFFICULTY>*\n" + "__SUBJECT:__\n" + "  /TBD     - TO BE DONE!.\n" + "\n"
			+ "__DIFFICULTY:__\n" + "  /0      - a rather simple question that everyone should be able to solve\n"
			+ "  /1     - a more difficult question. More test oriented\n" + "  /2     - TO BE DONE\n" + "\n"
			+ "__Example:__   !challenge hwe 1\n");

	public ChallengeCommand() {
		super();
	}

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			if (e.getAuthor().isBot())
				return;
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				if (!e.isFromType(ChannelType.PRIVATE)) {
					if (ChallengeManager.getInstance().isActive()) {
						Log.log(BotDefs.TYPE_CHALLENGE, true, e.getMember().getEffectiveName()
								+ " tried to start a challenge while one was still ongoing!");
						return;
					}
					String subject = args[1];
					int dif = 1;
					try {
						dif = Integer.parseInt(args[2]);
					} catch (Exception ex) {
						Log.log(BotDefs.TYPE_CHALLENGE, true,
								e.getMember().getEffectiveName() + " issued a wrong challenge command!");
					}
					Subject s = SettingsFactory.getInstance().getSettings().searchSubject(args[1]);
					if (s != null) {
						// e.getTextChannel().sendMessage("@everyone").queue();
						EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CHALLENGE**");
						eBuilder.addField("HEY 4C", "CHALLENGE HERE!", false);
						eBuilder.addField("Subject", args[1], true);
						eBuilder.addField("Difficulty", args[2], true);
						eBuilder.addField("How to:",
								"Everyone can enter by using the **__challengeAnswer__** command!\nThe first three with the correct answer will be given 10 points!",
								false);
						eBuilder.addBlankField(false);
						e.getTextChannel().sendMessage(eBuilder.build()).queue();
						ChallengeManager.getInstance().setActive(true);
					} else {
						Log.log(BotDefs.TYPE_CHALLENGE, true, e.getMember().getEffectiveName()
								+ " issued a wrong challenge command! -> no subject named " + args[1] + " found!");
					}
				}
			}
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
