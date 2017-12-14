package me.choohan.askyblock.bank;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.wasteofplastic.askyblock.ASkyBlock;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin{


	public Permission adminPermission = new Permission("bank.admin");
    public static Economy economy = null;

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	private FileConfiguration island;
	private File islandf;
	private FileConfiguration log;
	private File logf;
	
	private FileConfiguration player;
	private File playerf;
	
	public static Main plugin;
	
	public void onEnable(){

	      // Plugin Manager
	      PluginManager pm = getServer().getPluginManager();
	      pm.addPermission(this.adminPermission);
		setupEconomy();
		
		if(!setupEconomy()){
			getServer().getPluginManager().disablePlugin(this);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4This plugin required VAULT!"));
		}
		// Define the plugin
		  plugin = this;
		
		//Create file
		createFiles();
		
		//Register Listeners
		registerListener();
		
		//Register Command Executor
		registerCommand();

		// Check did config available 
	    if (!new File(getDataFolder(), "config.yml").exists()) {
	      getConfig().options().copyDefaults(true);
	    }
	    
	    // Save the config!
	    saveDefaultConfig();
	}
	
	public void onDisable(){
		
		//Save the config
		saveDefaultConfig();
		

		// Prevent memmory from leeking
		plugin = null;
	}
	
	// Register Listener
	public void registerListener(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Events(this), this);
	}
	
	// Register Command Executor
	public void registerCommand(){
	    getCommand("sbbank").setExecutor(new Cmd(this));
	}
	
	public FileConfiguration getIslandConfig() {
	      return this.island;
	  }
	public FileConfiguration getLogConfig() {
	      return this.log;
	  }

	public FileConfiguration getPlayerConfig() {
	      return this.player;
	  }
	 private void createFiles(){
	     
		  // Define island.yml
	      islandf = new File(getDataFolder(), "island.yml");
		  // Define log.yml
	      logf = new File(getDataFolder(), "log.yml");
	      

		  // Define player.yml
	      playerf = new File(getDataFolder(), "player.yml");
	      

	      // Create island.yml
	      if(!islandf.exists()) {
	          islandf.getParentFile().mkdirs();
	          saveResource("island.yml", false);
	      }


	      // Create log.yml
	      if(!logf.exists()) {
	          logf.getParentFile().mkdirs();
	          saveResource("log.yml", false);
	      }
	      

	      
	      // Create player.yml
	      if(!playerf.exists()) {
	          playerf.getParentFile().mkdirs();
	          saveResource("player.yml", false);
	      }
	      

	      
	      // Load player.yml
	      player = new YamlConfiguration();
	      try{
	          player.load(playerf);
	      } catch (IOException e){
	          e.printStackTrace();
	      } catch (InvalidConfigurationException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	      
	      // Load log.yml
	      log = new YamlConfiguration();
	      try{
	          log.load(logf);
	      } catch (IOException e){
	          e.printStackTrace();
	      } catch (InvalidConfigurationException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	      

	      // Load island.yml
	      island = new YamlConfiguration();
	      try{
	          island.load(islandf);
	      } catch (IOException e){
	          e.printStackTrace();
	      } catch (InvalidConfigurationException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	 }

	  public File getIslandConfigFile() {
	      return this.islandf;
	  }
	  public File getPlayerConfigFile() {
	      return this.playerf;
	  }
	  public File getLogConfigFile() {
	      return this.logf;
	  }
	 
	 
	  
	  
}
