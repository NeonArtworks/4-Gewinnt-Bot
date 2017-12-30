package at.crimsonbit.viergewinntbot.plugins;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import at.crimsonbit.viergewinntbot.annotation.InjectJDA;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.commands.MusicCommand;
import at.crimsonbit.viergewinntbot.handler.CommandHandler;
import at.crimsonbit.viergewinntbot.injector.JDAInjector;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.JDA;

public class CommandPluginManagerImpl implements ICommandPluginManager {

	private List<AbstractCommand> loaded;
	private static CommandPluginManagerImpl instance;

	@InjectJDA
	private static JDA jda;
	static {
		JDAInjector.registerTarget(CommandPluginManagerImpl.class);
	}

	public static CommandPluginManagerImpl getInstance() {
		if (instance == null) {
			instance = new CommandPluginManagerImpl();
		}
		return instance;
	}

	public CommandPluginManagerImpl() {
		loaded = new ArrayList<>();
	}

	@Override
	public void onExternalPluginLoad() {
		loaded.clear();
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading external plugins...");
		File[] files = new File(BotDefs.PLUGINS_FOLDER).listFiles();
		for (File f : files) {
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading plugin file: " + f.getName());
			loadExternalPlugin(f);
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Plugins successfully loaded!");
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Plugins successfully enabled!");
		}
		registerCommands();
	}

	@Override
	public void onExternalPluginStop() {
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Disabling all plugins...");
		for (AbstractCommand ac : loaded) {
			ac.onStop();
		}
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "All plugins disabled!");

	}

	@Override
	public void onExternalPluginStop(String name) {
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Disabling plugin: " + name);
		for (AbstractCommand ac : loaded) {
			if (ac.getName().equalsIgnoreCase(name))
				ac.onStop();
		}
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Plugin: " + name + " disabled!");

	}

	public void registerCommands() {
		if (jda != null) {
			for (AbstractCommand com : CommandHandler.getRegisteredCommands()) {
				jda.removeEventListener(com);
			}
			CommandHandler.deleteRegisteredCommands();
			for (AbstractCommand com : getLoadedCommandPlugins()) {
				if (com.isActive())
					jda.addEventListener(CommandHandler.registerCommand(com));
			}
			jda.addEventListener(CommandHandler.registerCommand(new MusicCommand()));
		}
		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Writing plugins to settings factory...");
		SettingsFactory.getInstance().getSettings().setCommands(loaded);
		SettingsFactory.getInstance().saveSettings();
		SettingsFactory.getInstance().loadSettings();
	}

	@Override
	public void loadExternalPlugin(File jar) {

		try {
			JarFile jf = new JarFile(jar);
			/*
			 * List<URL> urls = new ArrayList<URL>();
			 * urls.add(jar.toURI().toURL()); Manifest mf = jf.getManifest(); if
			 * (mf != null) { String cp =
			 * mf.getMainAttributes().getValue("class-path"); if (cp != null) {
			 * for (String cpe : cp.split("\\s+")) { File lib = new
			 * File(jar.getParentFile(), cpe); urls.add(lib.toURI().toURL()); }
			 * } }
			 */
			for (Enumeration<JarEntry> entries = jf.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();

				String name = entry.getName();
				if (!name.endsWith(".class")) {
					continue;
				}
				String className = name.replaceAll("/", ".");
				className = className.substring(0, className.length() - ".class".length());
				URLClassLoader loader = URLClassLoader.newInstance(new URL[] { jar.toURI().toURL() });
				Class<?> clazz = loader.loadClass(className);
				for (Class<?> ci : clazz.getInterfaces()) {
					if (ci == ICommandPlugin.class) {
						ICommandPlugin instance = (ICommandPlugin) clazz.newInstance();
						Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading: " + instance.getCommand().getName());
						AbstractCommand c = instance.getCommand();
						c.onStart();
						loaded.add(c);
						break;
					}
				}

				loader.close();
			}
			jf.close();

		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<AbstractCommand> getLoadedCommandPlugins() {
		return loaded;
	}

	@Override
	public void onExternalPluginLoad(String name) {
		for (int i = 0; i < loaded.size(); i++) {
			if (loaded.get(i).getName().equalsIgnoreCase(name)) {
				loaded.remove(i);
			}
		}

		Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading external " + name);
		File[] files = new File(BotDefs.PLUGINS_FOLDER).listFiles();
		for (File f : files) {
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading plugin file: " + f.getName());
			loadExternalPlugin(f, name);
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Plugins successfully loaded!");
			Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Plugins successfully enabled!");

		}
		registerCommands();
	}

	@Override
	public void loadExternalPlugin(File jar, String pName) {
		try {
			JarFile jf = new JarFile(jar);
			/*
			 * List<URL> urls = new ArrayList<URL>();
			 * urls.add(jar.toURI().toURL()); Manifest mf = jf.getManifest(); if
			 * (mf != null) { String cp =
			 * mf.getMainAttributes().getValue("class-path"); if (cp != null) {
			 * for (String cpe : cp.split("\\s+")) { File lib = new
			 * File(jar.getParentFile(), cpe); urls.add(lib.toURI().toURL()); }
			 * } }
			 */

			for (Enumeration<JarEntry> entries = jf.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				if (!name.endsWith(".class")) {
					continue;
				}
				String className = name.replaceAll("/", ".");
				className = className.substring(0, className.length() - ".class".length());
				URLClassLoader loader = URLClassLoader.newInstance(new URL[] { jar.toURI().toURL() });
				Class<?> clazz = loader.loadClass(className);
				for (Class<?> ci : clazz.getInterfaces()) {
					if (ci == ICommandPlugin.class) {
						ICommandPlugin instance = (ICommandPlugin) clazz.newInstance();
						if (pName.equalsIgnoreCase(instance.getCommand().getName())) {
							Log.log(BotDefs.TYPE_PLUGINMANAGER, false, "Loading: " + instance.getCommand().getName());
							AbstractCommand c = instance.getCommand();
							c.onStart();
							loaded.add(c);
						}
						break;
					}
				}
				loader.close();
			}
			jf.close();
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
