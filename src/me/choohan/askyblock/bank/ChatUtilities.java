package me.choohan.askyblock.bank;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ChatUtilities {
	

	public static void broadcast(String msg){
		for (Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', starter() + msg));
	}
	
	
	public static void message (Player player, String msg){
		player.sendMessage(starter() + msg);
	}
	
	private static String starter(){
		return ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBank &f&l] ");
	}
	


}
