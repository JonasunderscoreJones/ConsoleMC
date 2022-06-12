package me.jonasjones.consolemc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.jonasjones.consolemc.ConsoleMC;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.jmx.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static me.jonasjones.consolemc.ConsoleMC.registerCommands;

public class RunCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((((((((((((CommandManager.literal("cmd").requires(source -> source.hasPermissionLevel(4)) //requires OP //TODO: implement config "require-op:[True/False]"
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
                                .executes(context -> toggleAllow(context))))
                ).then(CommandManager.literal("allow-commandBlocks")
                        .then(CommandManager.literal("False")
                                .executes(context -> toggleAllow(context))))

                        //op-permissionLevel Command
                ).then(CommandManager.literal("op-permissionLevel")
                        .then(CommandManager.argument("level", IntegerArgumentType.integer(1, 4))
                                .executes(context -> setLevel(IntegerArgumentType.integer(IntegerArgumentType.getInteger(context, "level")), context))))

                        //enable command
                ).then(CommandManager.literal("enable")
                        .executes(context -> toggle(context)))

                        //disable command
                ).then(CommandManager.literal("disable")
                        .executes(context -> toggle(context)))

                        //help command
                ).then(CommandManager.literal("help")
                        .executes(context -> help()))

                        //whitelist [add <player>/remove <player>]
                ).then(CommandManager.literal("whitelist")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("target", EntityArgumentType.players())
                                        .executes(context -> whitelistAdd(EntityArgumentType.getPlayer(context, "Player"), context)))))
                ).then(CommandManager.literal("whitelist")
                                .then(CommandManager.literal("remove")
                                        .then((CommandManager.argument("target", EntityArgumentType.players())
                                                .executes(context -> whitelistRemove(EntityArgumentType.getPlayer(context, "Player"), context)))))

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
            context.getSource().sendFeedback(Text.of(errorLog), false);
            return -1;
        }
    }
    public static void returnCommandOutput(String cmd, String commandFeedback, CommandContext<ServerCommandSource> context) {
        String consoleLog = "  [" + cmd + "]: " + commandFeedback;
        context.getSource().sendFeedback(Text.of(consoleLog), false);
        ConsoleMC.LOGGER.info(consoleLog);
    }
    public static int toggleAllow(CommandContext<ServerCommandSource> context) {

        ConsoleMC.LOGGER.info("Toggle Allow-CommandBlocks");
        return 1;
    }

    public static int blacklistAdd(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Add " + player.toString() + " to the blacklist");
        return 1;
    }
    public static int blacklistRemove(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Remove " + player.toString() + " from the blacklist");
        return 1;
    }

    public static int reloadCommands(CommandContext<ServerCommandSource> context) {
        registerCommands();
        ConsoleMC.LOGGER.info("Reloaded Commands!");
        return 1;
    }

    public static int setLevel(IntegerArgumentType level, CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Set Command OP Permission Level to: " + level.toString());
        return 1;
    }
    public static int help() {
        ConsoleMC.LOGGER.info("Print help");
        return 1;
    }
    public static int reload(CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Reloading!");
        return 1;
    }

    public static int toggle(CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Toggle Enable");
        return 1;
    }

    public static int whitelistAdd(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Add " + player.toString() + " to whitelist");
        return 1;
    }
    public static int whitelistRemove(ServerPlayerEntity player, CommandContext<ServerCommandSource> context) {
        ConsoleMC.LOGGER.info("Remove " + player.toString() + " from whitelist");
        return 1;
    }
}
