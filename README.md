# ConsoleMC
A mod that allows console commands to be executed over the ingame Minecraft chat.

If the images above aren't visible it's because curseforge is down again (I'm just gonna leave this here)

## //TODO
- Readme
- proper terminal input chat
- config file
    - running            -> allow /cmd
    - allow-commandblock -> allow the use of /cmd in command blocks
    - require-op         -> require op to run /cmd
    - blacklist          -> players that are not allowed to run /cmd even though match all criteria
    - whitelist          -> players that are allowed to run /cmd even though don't match the criteria
- command
  - /cmd [command] -> run command
    - /cmd reload -> reload config
    - /cmd disable -> toggle cmd off
    - /cmd enable -> toggle cmd on
    - /cmd help -> print help
    - /cmd whitelist
      - /cmd whitelist add [player]
      - /cmd whitelist remove [player]
    - /cmd blacklist
      - /cmd blacklist add [player]
      - /cmd blacklist remove [player]
    - /cmd allow-commandBlock [True/False]
    - /cmd require-op [True/False]
