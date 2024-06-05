package com.github.onethecrazy.mcclock.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import com.github.onethecrazy.mcclock.McClocks;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;


public class StateSaver {
    public static String configPath = "./config/mcclock.cfg";

    public static void Init(){
        File config = new File(configPath);

        try{
            if(!config.exists()){
                SendMessage("There was no mcclock.cfg file found. Trying to create one...");

                McClocks.state = new ClockState();

                if(!config.createNewFile()){
                    SendMessage("Failed to create the mcclock.cfg file. This is bad.");
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cThere was an Error trying to create the config file for McClock. As a result, your configurations (e.g. clock-position...) cannot be saved!"));
                }
                else{
                    Save();
                    SendMessage("Success creating the mcclock.cfg file!");
                }
            }
            else{
                SendMessage("Trying to read the mcclock.cfg file...");

                Gson gson = new Gson();
                byte[] bytes = {};

                try{
                    bytes = Files.readAllBytes(Paths.get(configPath));

                    String jsonString = new String(bytes, StandardCharsets.UTF_8);

                    McClocks.state = gson.fromJson(jsonString, ClockState.class);

                    SendMessage("Success reading the mcclock.cfg file!");
                }
                catch(IOException ex){
                    SendMessage("Failed to read the mcclock.cfg file. This is bad.");
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cThere was an Error trying to load the config file for McClock. As a result, your configurations (e.g. clock-position...) couldn't be loaded!"));
                }
            }
        }
        catch(IOException ex){
            //What in tf am I supposed to do here? Make a log file and log the error? Naaaahhhh...
            SendMessage("There was an error trying to access the mcclock.cfg file-path.\n" + ex);
        }

    }

    public static void Save(){
        SendMessage("Trying to save the mcclock.cfg file...");

        Gson gson = new Gson();
        ClockState state = McClocks.state;

        String jsonString = gson.toJson(state);

        try{
            Files.write(Paths.get(configPath), jsonString.getBytes(StandardCharsets.UTF_8));
            SendMessage("Success saving the mcclock.cfg file.");
        }
        catch(IOException ex){
            SendMessage("There was an error trying to save the mcclock.cfg file.\n" + ex);
        }
    }

    private static void SendMessage(String msg){
        LogManager.getLogger("McClock").info(msg);
    }
}
