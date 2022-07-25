package me.jonasjones.consolemc.config;

import com.mojang.datafixers.util.Pair;
import me.jonasjones.consolemc.ConsoleMC;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean IS_ENABLED;
    public static boolean REUQIRE_OP;
    public static boolean ALLOW_CMD_BLOCKS;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(ConsoleMC.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("cmd.enable", true), "whether or not to allow chat command execution.");
        configs.addKeyValuePair(new Pair<>("cmd.requireOp", true), "whether or not operator level is required in order to run commands.");
        configs.addKeyValuePair(new Pair<>("cmd.allowCmdBlocks", false), "whether or not commands can be un through command blocks");
    }

    private static void assignConfigs() {
        IS_ENABLED = CONFIG.getOrDefault("cmd.enable", true);
        REUQIRE_OP = CONFIG.getOrDefault("cmd.requireOp", true);
        ALLOW_CMD_BLOCKS = CONFIG.getOrDefault("cmd.allowCmdBlocks", false);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
