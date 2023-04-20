# ConsoleMC

<a href="https://github.com/J-onasJones/ConsoleMC/blob/master/LICENSE"><img src="https://img.shields.io/github/license/J-onasJones/ConsoleMC?style=flat&color=900c3f" alt="License"></a>
<a href="https://discord.gg/V2EsuUVmWh"><img src="https://img.shields.io/discord/702180921234817135?color=5865f2&label=Discord&style=flat" alt="Discord"></a>
<a href="https://www.curseforge.com/minecraft/mc-mods/consolemc"><img src="https://cf.way2muchnoise.eu/full_650244.svg" alt="CF"></a>
<a href="https://modrinth.com/mod/consolemc"><img src="https://img.shields.io/modrinth/dt/consolemc?logo=modrinth&label=&style=flat&color=242629&labelColor=00AF5C&logoColor=white" alt="Modrinth"></a>
<a href="https://modrinth.com/mod/consolemc"><img src="https://img.shields.io/modrinth/game-versions/consolemc?logo=modrinth&color=242629&labelColor=00AF5C&logoColor=white"></a>
![GitHub all releases](https://img.shields.io/github/downloads/J-onasJones/ConsoleMC/total?label=GitHub%20downloads)

<a align="center"><img src="http://cdn.jonasjones.me/uploads/mod-badges/fabric-api.png" width="250px">
<img src="http://cdn.jonasjones.me/uploads/mod-badges/no-support-forge.png" width="250px">
<img src="http://cdn.jonasjones.me/uploads/mod-badges/available-modrinth.png" width="250px"><img src="http://cdn.jonasjones.me/uploads/mod-badges/support-fabric.png"  width="250px"><img src="http://cdn.jonasjones.me/uploads/mod-badges/support-quilt.png" width="250px"></a>

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
