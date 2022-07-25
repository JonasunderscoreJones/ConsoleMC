package me.jonasjones.consolemc.system;

import com.mojang.brigadier.context.CommandContext;
import me.jonasjones.consolemc.ConsoleMC;
import me.jonasjones.consolemc.command.RunCommand;
import net.minecraft.server.command.ServerCommandSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommand {
    public static int execute(String command, CommandContext<ServerCommandSource> context) {
        new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (ConsoleMC.ISWINDOWS) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                processBuilder.command("bash", "-c", command);
            }

            try {

                Process process = processBuilder.start();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    RunCommand.returnCommandOutput(command, line, context);
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    ConsoleMC.LOGGER.info("Exited with error code : " + exitCode);
                } else {
                    ConsoleMC.LOGGER.error("Exited with error code : " + exitCode);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return 1;
    }
}
