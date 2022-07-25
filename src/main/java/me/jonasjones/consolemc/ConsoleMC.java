package me.jonasjones.consolemc;

import me.jonasjones.consolemc.command.RunCommand;
import me.jonasjones.consolemc.config.ModConfigs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleMC implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("consolemc");
	public static String MOD_ID = "consolemc";

	public static void registerCommands() {
		CommandRegistrationCallback.EVENT.register(RunCommand::register);
	}

	//find out if operating system is windows
	public static boolean ISWINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
	public static String OS = System.getProperty("os.name");

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();
		registerCommands();
		LOGGER.info("ConsoleMC initialized!");
		LOGGER.info("Server running on " + OS);
	}
}
