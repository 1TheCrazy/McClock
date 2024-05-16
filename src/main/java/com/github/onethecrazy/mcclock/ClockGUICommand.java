package com.github.onethecrazy.mcclock;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class ClockGUICommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "clockgui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        McClocks.commandOpenGui = new ClockConfigGUI();
        sender.addChatMessage(new ChatComponentText("Opening Clock-Config-GUI..."));
    }

    @Override
    public List<String> getCommandAliases(){
        return Arrays.asList("clockconfig", "cconfig");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
