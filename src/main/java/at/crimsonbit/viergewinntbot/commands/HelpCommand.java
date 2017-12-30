package at.crimsonbit.viergewinntbot.commands;

import java.util.Arrays;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.CommandHandler;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends AbstractCommand implements ICommandPlugin {
	
	private boolean isActive = false;
	private String name = "Help";
	private String desc = "Shows the 'CLIL Hilfeseite, Sie brauchens, wir habens!'";
	private boolean isOpCommand = false;
	private List<String> aliases = Arrays.asList("help", "h", "Help");

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (isActive) {
			if (e.getAuthor().isBot()) {
				return;
			}
			if (!isOpCommand || PermissionHandler.getInstance().isOP(e.getAuthor())) {
				Log.log(BotDefs.TYPE_COMMAND, false, e.getAuthor().getName() + " issued a help command!");

				if (args.length < 2) {
					for (AbstractCommand com : CommandHandler.getRegisteredCommands()) {
						EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CLIL Hilfeseite für **"
								+ e.getAuthor().getAsMention() + "** , Sie brauchens, wir habens!**");

						eBuilder.addField((com.getName()), "Command: " + com.getName(), false);
						eBuilder.addField("Description", com.getDescription(), true);
						eBuilder.addField("Aliases", com.getAliases().toString(), true);
						eBuilder.addBlankField(false);
						e.getAuthor().openPrivateChannel().queue((channel) -> {
							channel.sendMessage(eBuilder.build()).queue();
						});
					}
				} else {
					for (AbstractCommand com : CommandHandler.getRegisteredCommands()) {
						if (com.getName().equalsIgnoreCase(args[1])) {
							EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CLIL Hilfeseite für **"
									+ e.getAuthor().getAsMention() + "** , Sie brauchens, wir habens!**");
							eBuilder.addField(com.getName(), "Command: " + com.getName(), false);
							eBuilder.addField("Description", com.getDescription(), true);
							eBuilder.addField("Aliases", com.getAliases().toString(), true);
							eBuilder.addBlankField(false);
							e.getAuthor().openPrivateChannel().queue((channel) -> {
								channel.sendMessage(eBuilder.build()).queue();
							});
							return;
						}
					}
					EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**CLIL Hilfeseite für **"
							+ e.getAuthor().getAsMention() + "** , Sie brauchens, wir habens!**");
					eBuilder.addField("ODER AUCH NICHT!", "Sorry, erm hob i ned! -> " + args[1], false);
					e.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage(eBuilder.build()).queue();
					});
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
