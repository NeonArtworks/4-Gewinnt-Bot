package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.util.ChallengeManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AnswerChallenge extends AbstractCommand implements ICommandPlugin {

	private boolean isActive = false;
	private String name = "AnswerChallenge";
	private String desc = "With this command you will be able to answer to a challenge.";
	private boolean isOpCommand = false;
	private List<String> aliases = Arrays.asList("answerChallenge", "answerchallenge", "answerC");
	private ChallengeManager manager = ChallengeManager.getInstance();

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {

		if (isActive) {
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				if (manager.isActive()) {
					
					// TBD
					// ANSWER CHECKING
					//
					
					boolean correct = true;
					if (correct) {
						if (!manager.isFilled()) {
							if (manager.getMemberList().contains(e.getMember())) {
								EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**HOOOOLD ON!**");
								eBuilder.addField("YOU CANT!", "You already have answered the challenge correctly!",
										false);
								e.getAuthor().openPrivateChannel().queue((channel) -> {
									channel.sendMessage(eBuilder.build()).queue();
								});
								return;
							}
							EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CHALLENGE**");
							eBuilder.addField("**__WOOOW__**",
									e.getMember().getEffectiveName() + " deine Antwort war richtig!", true);
							e.getTextChannel().sendMessage(eBuilder.build()).queue();
							manager.addUser(e.getAuthor(), e.getMember());

							eBuilder = new EmbedBuilder().setDescription("**__SO SCHAUTS AUS!__**");
							if (manager.getWinnerList().size() == 1) {
								eBuilder.addField("**__1.__**", e.getMember().getEffectiveName(), false);
								eBuilder.addField("**__2.__**", "_________", false);
								eBuilder.addField("**__3.__**", "_________", false);
							} else if (manager.getWinnerList().size() == 2) {
								eBuilder.addField("**__1.__**", manager.getMemberList().get(0).getEffectiveName(),
										false);
								eBuilder.addField("**__2.__**", manager.getMemberList().get(1).getEffectiveName(),
										false);
								eBuilder.addField("**__3.__**", "_________", false);

							}
							e.getTextChannel().sendMessage(eBuilder.build()).queue();
						} else {
							ueueMessage(e);
						}
					} else {
						EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CHALLENGE**");
						eBuilder.addField("**__NOOOO__**",
								e.getMember().getEffectiveName() + " deine Antwort war leider falsch!", true);
						e.getTextChannel().sendMessage(eBuilder.build()).queue();
					}
					if (manager.isFilled()) {
						ueueMessage(e);
					}
				}
			}

		}

	}

	private void ueueMessage(MessageReceivedEvent e) {
		EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CHALLENGE**");
		eBuilder.addField("**__First__**",
				manager.getMemberList().get(0).getEffectiveName() + " +" + BotDefs.CHALLENGE_POINTS_FIRST, true);
		eBuilder.addField("**__Second__**",
				manager.getMemberList().get(1).getEffectiveName() + " +" + BotDefs.CHALLENGE_POINTS_SECOND, true);
		eBuilder.addField("**__Third__**",
				manager.getMemberList().get(2).getEffectiveName() + " +" + BotDefs.CHALLENGE_POINTS_THIRD, true);
		e.getTextChannel().sendMessage(eBuilder.build()).queue();
		manager.addPointsToWinners();
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
