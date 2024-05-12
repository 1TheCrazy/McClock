package com.github.onethecrazy.mcclock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;


public class StateSaver {
    public static String configPath = "./config/mcclock.cfg";

    public static void Init(){
        File config = new File(configPath);
        try{
            if(!config.exists()){

                McClocks.Save = new SaveClass();

                if(!config.createNewFile()){
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cThere was an Error trying to create the config file for McClock. As a result, your configurations (e.g. clock-position...) cannot be saved!"));
                }
            }
            else{
                Gson gson = new Gson();
                byte[] bytes = {};

                try{
                    bytes = Files.readAllBytes(Paths.get(configPath));
                }
                catch(IOException ex){
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cThere was an Error trying to load the config file for McClock. As a result, your configurations (e.g. clock-position...) couldn't be loaded!"));
                }

                String jsonString = new String(bytes, StandardCharsets.UTF_8);

                McClocks.Save = gson.fromJson(jsonString, SaveClass.class);
            }
        }
        catch(IOException ex){
            //What in tf am I supposed to do here? Make a log file and log the error? Naaaahhhh...
        }

    }

    public static void Save(){
        Gson gson = new Gson();
        SaveClass save = McClocks.Save;

        String jsonString = gson.toJson(save);

        try{
            Files.write(Paths.get(configPath), jsonString.getBytes(StandardCharsets.UTF_8));
        }
        catch(IOException ex){}
    }

}
