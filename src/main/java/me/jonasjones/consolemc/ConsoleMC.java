package me.jonasjones.consolemc;

import me.jonasjones.consolemc.command.RunCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleMC implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("consolemc");

	private static void registerCommands() {
		CommandRegistrationCallback.EVENT.register(RunCommand::register);
	}

	@Override
	public void onInitialize() {
		registerCommands();
	}
}
