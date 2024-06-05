package com.github.onethecrazy.mcclock.commands;

import com.github.onethecrazy.mcclock.McClocks;
import com.github.onethecrazy.mcclock.objects.StateSaver;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class ClockCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "clock";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0){
            sender.addChatMessage(new ChatComponentText("§o/clock enable§r ---> Enables the clock\n§o/clock disable§r ---> Disables the clock\n§o/clockgui§r ---> Opens the configuration of the Clock-GUI"));
        } else if(args[0].equals("enable")){
            McClocks.state.isEnabled = true;
            StateSaver.Save();
            sender.addChatMessage(new ChatComponentText("§aEnabled the in-game Clock"));
        }else if(args[0].equals("disable")){
            McClocks.state.isEnabled = false;
            StateSaver.Save();
            sender.addChatMessage(new ChatComponentText("§cDisabled the in-game Clock"));
        }
    }

    @Override
    public List<String> getCommandAliases(){
        return Arrays.asList("mcclock", "mclock");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public boolean canCommandSenderUseCommand(){
        return true;
    }
}
