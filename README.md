# P3achB0t
Educational attempt to create a Oldschool Runescape client

This project is to learn:
1. More about how the Oldschool runescape client operates
1. Learning about reflection
1. Learn more about injection
1. Runtime analysis of values to identify fields/classes
1. Learn control flow analysis to trim unused fields and classes when deobfuscating the code

# Discord
If you would like to join the community or have questions feel free to join this discord

https://discord.gg/kcepXFP

# Requirements
Make sure you have Java 11. Its recommended to use the 32-bit to reduce the ram usage. https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=hotspot#x32_win 

# How to setup & build the project
Its recommended that you use InteliJ IDEA for the development environment. Currently, we are using Java 11

## Importing Steps
1. Clone the repo from the commandline, GitHub desktop GUI or even within InteliJ
1. Open up the newly cloned repo in InteliJ 
1. Reimport the maven project by pressing this button in the Maven panel on the right hand side

![](https://puu.sh/F4xnF/248d222f94.png)

*Note* If you get stuck, and the maven project doesnt import correctly, right click the pom.xml and press import as maven project 

At this point you should have all the external Libraries loaded in the project panel  

![](https://puu.sh/F4xpn/64afd8d5b4.png)

## Building & running from Maven
1. Back over to the Maven panel you need to run the compile Lifecycle. You should get [INFO] BUILD SUCCESS
1. Then run the exec:exec plugin to launch the application 

![](https://puu.sh/F4xrN/a21f379a25.png)

## Building & running using native Kotlin template using InteliJ(recommended) 
1. Go to com.p3achb0t.Main or src/com/p3achb0t/main.kt
2. Click on the green play button. This should compile and launch the app as well.
![](https://puu.sh/F4xzk/5b89375591.png)

# Script loading
Currently the scripts live in the follow location: src/com/p3achb0t/scripts
The TutorialIsland.kt script should have an extensive working version on a script. 
As long as you set up the Manifest the scripts should automatically be loaded


## Fast Debugging
If you use the Kotlin template building method you can recompile files and "Hot swap" the class files while the client is working. All you need to do is be sure you are in debug mode and you can do that by pressing the Bug button as long as you have the correct build configurations selected

![](https://puu.sh/F4xJv/c2b19642e8.png)

Within the file if you are editing it you can right click and select "Compile and reload file"

OR

On the Project View on the left hand side you can right click a file and select "Compile

Caveat - Depending on what you change within the file or files its possible you cant just recompile. For example, if you change a class signature or add a new class then you need to do a recompile and relaunch.

# Known issues
When starting the client from a fresh folder we have to download a 2nd copy of the cache into the .cache folder. This is to help to get
varbit info and item names. The Cache reader fails in the original cache. That being said you probably will need to wait a few min before
that loads. Its recommended that you just close the client after about 3-5min and restart.  