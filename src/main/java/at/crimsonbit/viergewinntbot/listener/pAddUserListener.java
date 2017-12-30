package at.crimsonbit.viergewinntbot.listener;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.pointmanager.PointList;
import at.crimsonbit.viergewinntbot.pointmanager.PointManager;
import at.crimsonbit.viergewinntbot.pointmanager.PointUser;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class pAddUserListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!e.getAuthor().isBot()) {
			if (!e.isFromType(ChannelType.PRIVATE))
				if (!e.getChannel().getName().contains("4g_log")) {
					PointManager pManager = PointManager.getInstance();
					PointList pList = pManager.getPointList();
					boolean isMemeber = pList.getUserList()
							.contains(new PointUser(e.getAuthor().getId(), e.getAuthor().getName(), 5));
					if (!isMemeber) {
						pList.addNewUser(e.getAuthor());
						pManager.setPointList(pList);
						pManager.savePointList();
						Log.log(BotDefs.TYPE_POINTMANAGER, true,
								e.getAuthor().getName() + " was added to the point system!");
					}
				}
		}
	}
}
