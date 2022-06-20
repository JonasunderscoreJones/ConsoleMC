package me.jonasjones.consolemc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.jonasjones.consolemc.ConsoleMC;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static me.jonasjones.consolemc.ConsoleMC.registerCommands;

public class RunCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((((((((((CommandManager.literal("cmd").requires(source -> source.hasPermissionLevel(4))
                        .then(CommandManager.literal("run")
                                .then(CommandManager.argument("Console Command", MessageArgumentType.message())
                                        .executes((context -> run(MessageArgumentType.getMessage(context, "Console Command").getString(), context)))))
                        //Debug Command
                ).then(CommandManager.literal("debug")
                        .then(CommandManager.literal("reloadCommand")
                                .executes(context -> reloadCommands(context))))

                        //reload Command
                ).then(CommandManager.literal("reload")
                        .executes(context -> reload(context)))

                        //allow-commandBlocks Command
                ).then(CommandManager.literal("allow-commandBlocks")
                        .then(CommandManager.literal("True")
                                .executes(context -> enableCmdBlocks(context))))
                ).then(CommandManager.literal("allow-commandBlocks")
                        .then(CommandManager.literal("False")
                                .executes(context -> disableCmdBlocks(context))))

                        //enable command
                ).then(CommandManager.literal("enable")
                        .executes(context -> enable(context)))

                        //disable command
                ).then(CommandManager.literal("disable")
                        .executes(context -> disable(context)))

                        //help command
                ).then(CommandManager.literal("help")
                        .executes(context -> help()))
                        //blacklist [add/remove]
                ).then(CommandManager.literal("blacklist")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("target", EntityArgumentType.players())
                                        .executes(context -> blacklistAdd(EntityArgumentType.getPlayer(context, "Player"), context)))))
                ).then(CommandManager.literal("blacklist")
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> blacklistRemove(EntityArgumentType.getPlayer(context, "Player"), context)))))
        );
    }
    public static void broadcastToOP(String message, CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(Text.of(message), true);
    }

    public static int run(String command, CommandContext<ServerCommandSource> context) {
        broadcastToOP("Ran Console Command \"" + command + "\"", context);
        if (command != "") {
            return runCommand(command, context);
        } else {
            return -1;
        }
    }
    public static int runCommand(String cmd, CommandContext<ServerCommandSource> context) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                returnCommandOutput(cmd, s, context);
            }
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = stdError.readLine()) != null) {
                returnCommandOutput(cmd, s, context);
            }
            return 1;
        } catch (IOException e) {
            //e.printStackTrace();
            String errorLog = "Error: \"" + cmd + "\", No such file or directory!";
            ConsoleMC.LOGGER.info(errorLog);
            broadcastToOP(errorLog, context);
            return -1;
        }
    }
    public static void returnCommandOutput(String cmd, String commandFeedback, CommandContext<ServerCommandSource> context) {
        String consoleLog = "  [" + cmd + "]: " + commandFeedback;
        broadcastToOP(consoleLog, context);
        ConsoleMC.LOGGER.info(consoleLog);
    }
    public static int enableCmdBlocks(CommandContext<ServerCommandSource> context) {

        broadcastToOP("Enabled ConsoleMC in Commandblocks", context);
        return 1;
    }
    public static int disableCmdBlocks(CommandContext<ServerCommandSource> context) {

        broadcastToOP("Disabled ConsoleMC in Commandblocks", context);
        return 1;
    }

    public static int blacklistAdd(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        broadcastToOP("Added " + player.toString() + " to the ConsoleMC blacklist", context);
        return 1;
    }
    public static int blacklistRemove(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        broadcastToOP("Removed " + player.toString() + " from the ConsoleMC blacklist", context);
        return 1;
    }

    public static int reloadCommands(CommandContext<ServerCommandSource> context) {
        registerCommands();
        broadcastToOP("DEBUG Reregistered /cmd command", context);
        return 1;
    }

    public static int help() {
        ConsoleMC.LOGGER.info("Print help");
        return 1;
    }
    public static int reload(CommandContext<ServerCommandSource> context) {
        broadcastToOP("Reloaded ConsoleMC Config", context);
        return 1;
    }

    public static int enable(CommandContext<ServerCommandSource> context) {
        broadcastToOP("Enabled ConsoleMC commands", context);
        return 1;
    }
    public static int disable(CommandContext<ServerCommandSource> context) {
        broadcastToOP("Disabled ConsoleMC commands", context);
        return 1;
    }
}
