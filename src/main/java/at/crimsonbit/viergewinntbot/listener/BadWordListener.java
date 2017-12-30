package at.crimsonbit.viergewinntbot.listener;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.wordcheck.BlackListHelper;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BadWordListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) {
			return;
		}
		String c = "";
		if (!e.getTextChannel().getName().contains("4g_log"))
			for (String s : BlackListHelper.getInstance().getBlackList().getBlackList()) {
				if (e.getMessage().getContent().toLowerCase().contains(s)) {
					Log.log(BotDefs.TYPE_GENERAL, false, "Censored " + e.getAuthor().getName() + "'s message ts ts ts");
					c = e.getMessage().getContent().replaceAll(s, BlackListHelper.replaceRandom(s));
				}
			}
		e.getTextChannel().deleteMessageById(e.getMessageId()).queue();

		e.getTextChannel()
				.sendMessage(e.getAuthor().getAsMention() + " - Did you mean: " + c + "\nPlease watch your tongue!")
				.queue();

	}

}
