package me.jonasjones.consolemc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.jonasjones.consolemc.ConsoleMC;
import me.jonasjones.consolemc.system.ShellCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RunCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register((CommandManager.literal("cmd").requires(source -> source.hasPermissionLevel(4))
                        .then(CommandManager.argument("Console Command", MessageArgumentType.message())
                                        .executes((context -> run(MessageArgumentType.getMessage(context, "Console Command").getString(), context)))))
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
        new Thread(() -> {ShellCommand.execute(cmd, context);}).start();
        return 1;
    }
    public static void returnCommandOutput(String cmd, String commandFeedback, CommandContext<ServerCommandSource> context) {
        String consoleLog = "  [" + cmd + "]: " + commandFeedback;
        broadcastToOP(consoleLog, context);
        ConsoleMC.LOGGER.info(consoleLog);
    }
}
