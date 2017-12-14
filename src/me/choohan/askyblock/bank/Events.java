package me.choohan.askyblock.bank;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.wasteofplastic.askyblock.events.IslandDeleteEvent;
import com.wasteofplastic.askyblock.events.IslandJoinEvent;
import com.wasteofplastic.askyblock.events.IslandLeaveEvent;
import com.wasteofplastic.askyblock.events.IslandNewEvent;

public class Events implements Listener {

	public Events(Main main) {
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onIslandCreate (IslandNewEvent event){
		Player owner = event.getPlayer();
		String owneruuid = owner.getUniqueId().toString();
		Main.plugin.getIslandConfig().set(owneruuid, 0);

        Main.plugin.saveDefaultConfig();
        
        try {
           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
        } catch (IOException fpe) {
           //Whatever you want to print/etc for error purposes
        }
	}
	
	@EventHandler
	public void onPlayerJoinIsland (IslandJoinEvent event){
		String player = event.getPlayer().toString();
		String owner = event.getIslandOwner().toString();
		Main.plugin.getPlayerConfig().set(player, owner);


        Main.plugin.saveDefaultConfig();
        
        try {
           Main.plugin.getPlayerConfig().save(Main.plugin.getPlayerConfigFile());
        } catch (IOException fpe) {
           //Whatever you want to print/etc for error purposes
        }
	}
	
	@EventHandler
	public void onPlayerLeaveIsland (IslandLeaveEvent event){
		String player = event.getPlayer().toString();
		String owner = event.getIslandOwner().toString();
		Main.plugin.getPlayerConfig().set(player, null);

        Main.plugin.saveDefaultConfig();
        
        try {
           Main.plugin.getPlayerConfig().save(Main.plugin.getPlayerConfigFile());
        } catch (IOException fpe) {
           //Whatever you want to print/etc for error purposes
        }
	}
	
	@EventHandler
	public void onIslandDelete (IslandDeleteEvent event){
		String member = event.getPlayerUUID().toString();
		Main.plugin.getPlayerConfig().set(member, null);
		Main.plugin.getIslandConfig().set(member, null);

        Main.plugin.saveDefaultConfig();
        
        try {
           Main.plugin.getPlayerConfig().save(Main.plugin.getPlayerConfigFile());
        } catch (IOException fpe) {
           //Whatever you want to print/etc for error purposes
        }
        

        try {
           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
        } catch (IOException fpe) {
           //Whatever you want to print/etc for error purposes
        }
	}
}
