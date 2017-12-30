package at.crimsonbit.viergewinntbot.main;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.bot.VierGewinntBot;
import at.crimsonbit.viergewinntbot.handler.CommandHandler;
import at.crimsonbit.viergewinntbot.handler.ResourcePathHandler;
import at.crimsonbit.viergewinntbot.handler.SubjectHandler;
import at.crimsonbit.viergewinntbot.parser.Parser;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import at.crimsonbit.viergewinntbot.spreadsheet.SheetHandler;

public class MainClass {

	public static void main(String[] args) {
		ResourcePathHandler.onStartupCheckAndCreateNewFileSystem();
		startBot();
	}

	public static void startBot() {
		Parser.getInstance();
		
		new VierGewinntBot(SettingsFactory.getInstance().getSettings()).loadCommands(new CommandHandler())
				.loadSubjects(new SubjectHandler()).finish();

		
		//SheetQuestion q = handler.getQuestion("KSN", 2);
		//if (q != null)
		//	System.out.println(q.toString());
	}
}