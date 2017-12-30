package at.crimsonbit.viergewinntbot.bot;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;

import at.crimsonbit.viergewinntbot.answermanager.AnswerList;
import at.crimsonbit.viergewinntbot.answermanager.AnswerManager;
import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.commands.MusicCommand;
import at.crimsonbit.viergewinntbot.handler.CommandHandler;
import at.crimsonbit.viergewinntbot.handler.PermissionHandler;
import at.crimsonbit.viergewinntbot.handler.SubjectHandler;
import at.crimsonbit.viergewinntbot.injector.JDAInjector;
import at.crimsonbit.viergewinntbot.listener.pAddUserListener;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.log.ScheduledLog;
import at.crimsonbit.viergewinntbot.plugins.CommandPluginManagerImpl;
import at.crimsonbit.viergewinntbot.settings.Settings;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import at.crimsonbit.viergewinntbot.subject.Subject;
import at.crimsonbit.viergewinntbot.wordcheck.BlackList;
import at.crimsonbit.viergewinntbot.wordcheck.BlackListHelper;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/**
 * <h>VierGewinntBot</h>
 * <p>
 * This is the main bot.
 * </p>
 * 
 * @author Florian W.
 *
 */
public class VierGewinntBot {
	private JDA jda;
	private Settings settings;
	@SuppressWarnings("unused")
	private CommandHandler cHelper;
	private SubjectHandler sHelper;
	private ScheduledLog sLog;

	/**
	 * 
	 * @param token
	 */
	@SuppressWarnings("deprecation")
	public VierGewinntBot(Settings settings) {
		this.settings = settings;
		String token = settings.getToken();
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setGame(Game.of(SettingsFactory.getInstance().getSettings().getCurrentGame())).setToken(token)
					.buildBlocking();
		} catch (LoginException e) {
			Log.log("JDA", true, "ERROR while logigng in: LoginException");
			System.exit(BotDefs.EXIT_ON_INVALIDLOGIN);

		} catch (IllegalArgumentException e) {
			Log.log("JDA", true, "ERROR while logigng in: IllegalArgumentException");
			System.exit(BotDefs.EXIT_ON_ILLEGALARGUMENT);

		} catch (InterruptedException e) {
			Log.log("JDA", true, "ERROR while logigng in: InterruptedException");
			System.exit(BotDefs.EXIT_ON_INTERRUPTED);

		} catch (RateLimitedException e) {
			Log.log("JDA", true, "ERROR while logigng in: RateLimitException");
			System.exit(BotDefs.EXIT_ON_RATELIMIT);

		}
		jda.setAutoReconnect(true);

	}

	public VierGewinntBot loadSettings(Settings settings) {
		this.settings = settings;
		return this;
	}

	public VierGewinntBot loadSubjects(SubjectHandler helper) {
		Log.log("Registry", false, "Start Subject register!");
		this.sHelper = helper;
		for (Subject s : settings.getSubjects()) {
			helper.registerSubject(s);
		}
		return this;
	}

	public VierGewinntBot finish() {

		PermissionHandler.getInstance().loadOPS(settings);

		AnswerList aList = AnswerManager.getInstance().getAnswerList();
		//BlackList bList = BlackListHelper.getInstance().getBlackList();

		Log.log(BotDefs.TYPE_REGISTRY, false, "Registry finished!");
		Log.log(BotDefs.TYPE_REGISTRY, false,
				"Total Commands loaded: " + String.valueOf(CommandHandler.getNumberOfRegisteredCommands()));
		Log.log(BotDefs.TYPE_REGISTRY, false,
				"Total subjects loaded: " + String.valueOf(sHelper.getNumberOfRegisteredSubjects()));
		Log.log(BotDefs.TYPE_REGISTRY, false,
				"\"" + settings.getCommandPrefix() + "\" was registered as command prefix!");
		Log.log(BotDefs.TYPE_GENERAL, false, "Running on idle....");

		SettingsFactory.getInstance().saveSettings();

		EmbedBuilder eBuilder = new EmbedBuilder().setDescription("**4-Gewinnt Bot is __UP AND RUNNING__**");
		eBuilder.addField("Created by ", "Alexander Daum", true);
		eBuilder.addField("And", "Florian Wagenr", true);
		eBuilder.addBlankField(false);
		eBuilder.addField("Version", SettingsFactory.getInstance().getSettings().getVersion(), true);
		eBuilder.addField("Started at", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()), false);

		if (BotDefs.PrintStartUpMessage)
			jda.getGuildById(SettingsFactory.getInstance().getSettings().getCurrentGuild()).getDefaultChannel()
					.sendMessage(eBuilder.build()).queue();

		sLog = ScheduledLog.getInstance();
		sLog.startScheduledLog();
		JDAInjector.InjectJDA(jda);
		return this;
	}

	public VierGewinntBot loadCommands(CommandHandler helper) {
		CommandPluginManagerImpl cpmi = CommandPluginManagerImpl.getInstance();
		cpmi.onExternalPluginLoad();

		Log.log(BotDefs.TYPE_REGISTRY, false, "Start command register!");
		this.cHelper = helper;
		// for (AbstractCommand command : settings.getCommands()) {
		// if (command.isActive())
		// jda.addEventListener(CommandHandler.registerCommand(command));

		// }
		for (AbstractCommand com : CommandHandler.getRegisteredCommands()) {
			jda.removeEventListener(com);
		}
		for (AbstractCommand com : cpmi.getLoadedCommandPlugins()) {
			if (com.isActive())
				jda.addEventListener(CommandHandler.registerCommand(com));
		}
		jda.addEventListener(new pAddUserListener());
		jda.addEventListener(CommandHandler.registerCommand(new MusicCommand()));
		// jda.addEventListener(new BadWordListener());

		return this;
	}

	public void addCommand(AbstractCommand command) {
		jda.addEventListener(command);
	}

}
