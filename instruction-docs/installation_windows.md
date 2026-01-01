# Setup Instructions

## Installing IntelliJ IDEA

Click on [this link](https://www.jetbrains.com/idea/download/) to install IntelliJ. 

Download the appropriate installer for your device

  - Windows: exe installer

  - MacOS: dmg installer

and run the installer and follow the directions to install IntelliJ IDEA

When installing on Windows, you will need to make sure to select both **Update Context Menu** and **Update PATH Variable**

<p align="left">
  <img src="assets/windows/win_intellij_install.png" width="400"><br>
</p>


## Creating a NeoForge Mod

Use the [NeoForge Mod Generator](https://neoforged.net/mod-generator/) to create a template mod that you will import into IntelliJ.  

We will be using **Minecraft/NeoForge version 1.21.7** and the **ModDevGradle Plugin**

If you wish to follow the same project and directory naming conventions as Jon, then:
  - set Mod Name: **Example Mod**
  - enable **Generate mod id from name**
  - set Package Name: **com.example.examplemod**

Under Mod Metadata, you can put your name and a description if you wish. 

<br>


All in all, if you are using the "default" settings, it should look like this:

<p align="left">
  <img src="assets/neoforge_example.png" width="700"><br>
</p>

Then click *Download Mod Project* to download a zip of the NeoForge Mod. Unzip the generated mod file, and place it *somewhere*. For example, on MacOS, I renamed the folder to `cpsc298-minecraft-examplemod` and store it within `/Users/spencerau/Documents/GitHub`.


## Importing the Gradle Project

Open up IntelliJ, and open up the unzipped mod project you have generated from NeoForge. Following the earlier example, the generated mod folder is at `/Users/spencerau/Documents/GitHub/cpsc298-minecraft-examplemod`, but obviously your own mod folder name and location will differ.

<p align="left">
  <img src="assets/intellij_welcome.png" width="500"><br>
</p>

InteliJ will prompt you to *Trust and Open* the Project. Click *Trust Project* and it will open up the project.

<p align="left">
  <img src="assets/intellij_trust.png" width="450"><br>
</p>


## Installing Java

You may be prompted to install Java by IntelliJ in order to run Gradle. NeoForge will want **JDK 21**, so make sure to select that.

<p align="left">
  <img src="assets/windows/win_intellij_jdk21.png" width="350"><br>
</p>

In addition, you may need to install extensions to support .bat files in IntelliJ. Click on the `gradlew.bat` file, and you should get this prompt. Click on **Install Batch Scripts Support plugin**

<p align="left">
  <img src="assets/windows/bat_plugin1.png" height="130"><br>
</p>

Which should pull up the install for the Batch Scripts Support Plugin. Install that in order to execute .bat files in IntelliJ.

<p align="left">
  <img src="assets/windows/bat_plugin2.png" width="400"><br>
</p>

Just to confirm that Gradle and IntelliJ are using the correct versions of JDK, we want to navigate to both **Settings** and **Project Structure**.

### IntelliJ Settings

Navigate to the file menu by clicking on the triple bars on the top left, and select **Settings**

<p align="left">
  <img src="assets/windows/win_intellij_settings1.png" width="350"><br>
</p>

Navigate to **Build, Execution, Deployment**, then to **Build Tools**, and then to **Gradle**. From here, make sure that **Gradle JVM** has the correct selected version of Java for your mod project.

<p align="left">
  <img src="assets/windows/win_intellij_settings2.png" width="500"><br>
</p>

### IntelliJ Project Structure

Navigate to the file menu by clicking on the triple bars on the top left, and select **Project Structure**

<p align="left">
  <img src="assets/windows/win_intellij_projstructure1.png" width="300"><br>
</p>

We want to check that both the **SDK** and **Language level** settings are pointing to the correct version of Java. 

<p align="left">
  <img src="assets/windows/win_intellij_projstructure2.png" width="550"><br>
</p>


## Building and Running

To build and run, navigate to the top middle of the screen, and find the menu options that say **Current File**. From here, we want to open the menu, navigate to **Client**, and click on the corresponding green *play* button in order to build and run the Gradle project. IntelliJ makes this fairly simple as you just need to click the button to do all this.

<p align="left">
  <img src="assets/windows/win_build_run.png" height="300"><br>
</p>

If it builds properly, and launches Minecraft and lets you play, then congratulations, you have set it up correctly!