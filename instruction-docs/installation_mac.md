# Setup Instructions

## Installing Java

TODO (prob brew or whatever the windows non cli equivalent is)

## Installing IntelliJ

- Click on [this link](https://www.jetbrains.com/idea/download/) to install IntelliJ. The JetBrains site will autodetect which OS your computer is running:
    - Windows (x86-64)
	- macOS (Apple silicon)
	- macOS (Intel)

- Run the installer and follow the directions to install IntelliJ IDEA

## Creating a NeoForge Mod

- Use the [NeoForge Mod Generator](https://neoforged.net/mod-generator/) to create a template mod that you will import into IntelliJ.  
<br>

- We will be using Minecraft/NeoForge version 1.21.7 and the ModDevGradle Plugin
<br>

- If you wish to follow the same project and directory naming conventions as Jon, then:
    - set Mod Name: **Example Mod**
    - enable **Generate mod id from name**
    - set Package Name: **com.example.examplemod**
<br>

- Under Mod Metadata, you can put your name and a description if you wish
<br>

- All in all, if you are using the "default" settings, it should look like this:

![](assets/neoforge_example.png)

- Then click *Download Mod Project* to download a zip of the NeoForge Mod. Unzip the generated mod file, and place it *somewhere*. For example, on MacOS, I renamed the folder to `cpsc298-minecraft-examplemod` and store it within `/Users/spencerau/Documents/GitHub`.

## Importing the Gradle Project

- Open up IntelliJ, and open up the unzipped mod project you have generated from NeoForge. Following the earlier example, the generated mod folder is at `/Users/spencerau/Documents/GitHub/cpsc298-minecraft-examplemod`, but obviously your own mod folder name and location will differ.
<br>

- InteliJ will prompt you to *Trust and Open* the Project. Click *Trust Project* and it will open up the project.
<br>

- Open up a command line interface by clicking on this icon on the bottom left of IntelliJ:
<p align="center">
  <img src="assets/intellij_cli.png" width="30"><br>
</p>


- Set Gradle permissions with `chmod +x gradlew`.
<br>

- Run `./gradlew build` to build the project, and then run `./gradlew runClient` to actually start up Minecraft and test your mod.
<br>

- If you can run and play Minecraft, congrats! Everything should be working for you to start modding now.