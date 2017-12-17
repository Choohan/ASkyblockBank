package me.choohan.askyblock.bank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin{

	public Permission adminPermission = new Permission("asbbank.admin");
    public static Economy economy = null;

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	private FileConfiguration log;
	private File logf;
	
	private FileConfiguration Island;
	private File islandf;
	
	public static main plugin;
	
	private static ArrayList<String> loggy;
	
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
	
	
	// Register Command Executor
	public void registerCommand(){
	    getCommand("sbbank").setExecutor(new Cmd(this));
	}
	
	public FileConfiguration getLogConfig() {
	      return this.log;
	  }
	public FileConfiguration getIslandConfig() {
	      return this.Island;
	  }

	  
	 private void createFiles(){
	     
		  // Define log.yml
	      logf = new File(getDataFolder(), "log.yml");
		  // Define island.yml
	      islandf = new File(getDataFolder(), "island.yml");
	      


	      // Create log.yml
	      if(!logf.exists()) {
	          logf.getParentFile().mkdirs();
	          saveResource("log.yml", false);
	      }
	      // Create island.yml
	      if(!islandf.exists()) {
	          islandf.getParentFile().mkdirs();
	          saveResource("island.yml", false);
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
	      Island = new YamlConfiguration();
	      try{
	          Island.load(islandf);
	      } catch (IOException e){
	          e.printStackTrace();
	      } catch (InvalidConfigurationException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	      
	 }

	  public File getLogConfigFile() {
	      return this.logf;
	  }
	  public File getIslandConfigFile() {
	      return this.islandf;
	  }
	 
	 
	  

}
