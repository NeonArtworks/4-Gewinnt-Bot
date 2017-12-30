package at.crimsonbit.viergewinntbot.plugins;

import java.io.File;

public interface ICommandPluginManager {

	void onExternalPluginLoad();

	void onExternalPluginLoad(String name);

	void onExternalPluginStop();

	void onExternalPluginStop(String name);

	void loadExternalPlugin(File file);

	void loadExternalPlugin(File file, String name);

	// void onInternalPluginLoad();
	// void onInternalPluginStop();

}
