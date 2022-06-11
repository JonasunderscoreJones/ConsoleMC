package me.jonasjones.consolemc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.jonasjones.consolemc.ConsoleMC;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("cmd")
                .requires(source -> source.hasPermissionLevel(4)) //requires OP
                .then(CommandManager.argument("Console Command", MessageArgumentType.message())
                        .executes((ctx -> {

                            Text broadcastText = Text.of("Ran Console Command \"" + MessageArgumentType.getMessage(ctx, "Console Command").getString() + "\"");
                            ctx.getSource().sendFeedback(broadcastText, true);

                            if (MessageArgumentType.getMessage(ctx, "Console Command").getString() != "") {

                                run(MessageArgumentType.getMessage(ctx, "Console Command").getString(), ctx);
                                return 1;
                            } else {
                                return -1;
                            }
                }))));
    }
    public static void run(String command, CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        runCommand(command, ctx);
    }
    public static void runCommand(String cmd, CommandContext<ServerCommandSource> ctx) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {returnCommandOutput(cmd, s, ctx);
            }
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = stdError.readLine()) != null) {returnCommandOutput(cmd, s, ctx);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            String errorLog = "Error: \"" + cmd + "\", No such file or directory!";
            ConsoleMC.LOGGER.info(errorLog);
            ctx.getSource().sendFeedback(Text.of(errorLog), false);
        }
    }
    public static void returnCommandOutput(String cmd, String commandFeedback, CommandContext<ServerCommandSource> ctx) {
        String consoleLog = "  [" + cmd + "]: " + commandFeedback;
        ctx.getSource().sendFeedback(Text.of(consoleLog), false);
        ConsoleMC.LOGGER.info(consoleLog);
    }
}
