package me.choohan.askyblock.bank;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;

public class Method {
	
	public static UUID owneruuid;
	// Get Member that have access in modifying money
	public static ArrayList<String> MembersWithAccess = (ArrayList<String>) main.plugin.getIslandConfig().getStringList(owneruuid + ".memberaccess");
	
	// Add member 
	public static void AddMember (Player owner ,Player player){
		if (Method.isOwner(owner)){
			UUID owneruuid = Method.getIslandOwner(player);
		if (Method.MembersWithAccess.contains(player.getUniqueId().toString())){
			ChatUtilities.message(owner, ChatColor.translateAlternateColorCodes('&', "Target already granted access before!"));
		}else{
			MembersWithAccess.add(player.getUniqueId().toString());
			main.plugin.getIslandConfig().set(owneruuid + ".memberaccess", MembersWithAccess);
			ChatUtilities.message(owner, ChatColor.translateAlternateColorCodes('&', "&fAdded!"));
	        main.plugin.saveDefaultConfig();
	        
	        try {
	           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
	        } catch (IOException fpe) {
	           //Whatever you want to print/etc for error purposes
	        }
		}
		
		}else{
			ChatUtilities.message(owner, ChatColor.translateAlternateColorCodes('&', "&4You are not owner!"));
		}
	}
	public static void RemMember (Player owner ,Player player){

		if (Method.isOwner(owner)){
			UUID owneruuid = Method.getIslandOwner(player);
		if (Method.MembersWithAccess.contains(player.getUniqueId().toString())){
			MembersWithAccess.remove(player.getUniqueId().toString());
			main.plugin.getIslandConfig().set(owneruuid + ".memberaccess", MembersWithAccess);
			ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&fRemove!"));
	        main.plugin.saveDefaultConfig();
	        
	        try {
	           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
	        } catch (IOException fpe) {
	           //Whatever you want to print/etc for error purposes
	        }
		}else{
			ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "The player didnt granted access before!"));
		}
		}else{

			ChatUtilities.message(owner, ChatColor.translateAlternateColorCodes('&', "&4You are not owner!"));
		}
	}
	
	//Get log in log.yml
	public static ArrayList<String> Log = (ArrayList<String>) main.plugin.getLogConfig().getStringList("Logs." + owneruuid);
	
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
	public static Date date = new Date();
	public static String dt = dateFormat.format(date);
	
	//Add log to log.yml
	public static void addLog (Player player, String action, double amount){
		if(Method.getIslandOwner(player) != null && Method.checkDoPlayerGotIsland(player) != false){
			String owneruuid = Method.getIslandOwner(player).toString();
			Method.Log.add(player.getName() + " : " + action + " " + amount + " to island bank");
			main.plugin.getLogConfig().set("Logs." + owneruuid, Method.Log);
	        main.plugin.saveDefaultConfig();
	        
	        try {
	           main.plugin.getLogConfig().save(main.plugin.getLogConfigFile());
	        } catch (IOException fpe) {
	           //Whatever you want to print/etc for error purposes
	        }
		}else{
			ChatUtilities.message(player, "Errored");
		}
	}
	
	//Get balance 
	public static double getBalance (Player player){
		if(Method.checkDoPlayerGotIsland(player) == true){
			if (main.plugin.getIslandConfig().getString(Method.getIslandOwner(player).toString()) != null ){

				UUID owner = Method.getIslandOwner(player);
				double balance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
				return balance;
			}else{
				Method.registerIsland(player);
				UUID owner = Method.getIslandOwner(player);
				double balance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
				return 0;
			}
		}else{
			return 0;
		}
		
	}
	
	//Add Balance
	public static void addBalance(Player player, double balance){
		if(Method.checkDoPlayerGotIsland(player) == true){

			if (main.plugin.getIslandConfig().getString(Method.getIslandOwner(player).toString()) != null ){

				UUID owner = Method.getIslandOwner(player);
				double islandbalance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
				main.plugin.getIslandConfig().set(owner + ".balance", balance + islandbalance);
		        main.plugin.saveDefaultConfig();
		        
		        try {
		           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
			}else{
				Method.registerIsland(player);

				UUID owner = Method.getIslandOwner(player);
				double islandbalance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
				main.plugin.getIslandConfig().set(owner + ".balance", balance + islandbalance);
		        main.plugin.saveDefaultConfig();
		        
		        try {
		           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
			}
		}else{
			return;
		}
	}
	//Take Balance
		public static void takeBalance(Player player, double balance){
			if(Method.checkDoPlayerGotIsland(player) == true){

				if (main.plugin.getIslandConfig().getString(Method.getIslandOwner(player).toString()) != null ){
				UUID owner = Method.getIslandOwner(player);
				double islandbalance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
				main.plugin.getIslandConfig().set(owner + ".balance", islandbalance - balance);
		        main.plugin.saveDefaultConfig();
		        
		        try {
		           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
				}else{
					Method.registerIsland(player);
					UUID owner = Method.getIslandOwner(player);
					double islandbalance = main.plugin.getIslandConfig().getDouble(owner + ".balance");
					main.plugin.getIslandConfig().set(owner + ".balance", islandbalance - balance);
			        main.plugin.saveDefaultConfig();
			        
			        try {
			           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
				}
			}else{
				return;
			}
		}
		
	// Check if island registered in Island.yml
	public static boolean checkIslandRegistered (Player player){
		if(Method.checkDoPlayerGotIsland(player) == true){
			String owner = Method.getIslandOwner(player).toString();
			if(main.plugin.getIslandConfig().getString(owner) != null){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	// Register island
	public static void registerIsland (Player player){
		if(Method.checkDoPlayerGotIsland(player) == true){
			String owner = Method.getIslandOwner(player).toString();
			main.plugin.getIslandConfig().set(owner + ".balance", 0);
	        main.plugin.saveDefaultConfig();
	        
	        try {
	           main.plugin.getIslandConfig().save(main.plugin.getIslandConfigFile());
	        } catch (IOException fpe) {
	           //Whatever you want to print/etc for error purposes
	        }
		}else{
			return;
		}
	}
	// Player Deposit Money to bank
	public static void playerDepositMoneyToBank (Player player, double amount){
		if(Method.checkDoPlayerGotIsland(player) == true){
			if (Method.isOwner(player)){
				
				double islandbalance = Method.getBalance(player);
				double playerbalance = main.economy.getBalance(player);
				if (amount <= playerbalance){
					main.economy.withdrawPlayer(player, amount);
					Method.addBalance(player, amount);
					Method.addLog(player, "deposit", amount);
					double amt = islandbalance + amount;
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&aDeposit &6$" + amount + " *&ato your island bank. Balance: &6$" + amt));
					
				}else{
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4You do not have enought money"));
				}
				
			}else{
				UUID owneruuid = Method.getIslandOwner(player);
				if(Method.MembersWithAccess.contains(player.getUniqueId().toString())){
					double balance = Method.getBalance(player);
					double playerbalance = main.economy.getBalance(player);
					if (amount <= playerbalance){
						main.economy.withdrawPlayer(player, amount);
						Method.addBalance(player, amount);
						Method.addLog(player, "deposit", amount);

						double amt = balance + amount;
						ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&',  "&aDeposit &6$" + amount + " *&ato your island bank. Balance: &6$" + amt));
					}else{
						ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4You do not have enought money"));
					}
					
				}else{
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "Your owner do not allowed you to modify money!"));
				}
			}
		}else{
			ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4You do not have an island"));
		}
	}
	

	// Player Withdraw Money from bank
	public static void playerWithdrawtMoneyFromBank (Player player, double amount){
		if(Method.checkDoPlayerGotIsland(player) == true){
			if (Method.isOwner(player)){
				double islandbalance = Method.getBalance(player);
				double playerbalance = main.economy.getBalance(player);
				if (amount <= islandbalance){
					main.economy.withdrawPlayer(player, amount);
					Method.takeBalance(player, amount);
					Method.addLog(player, "withdraw", amount);
					double amt = islandbalance - amount;
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&aWithdraw &6$" + amount + " *&afrom your island bank. Balance: &6$" + amt));
				}else{
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4Your bank do not have enought money"));
				}
				
			}else{
				UUID owneruuid = Method.getIslandOwner(player);
				if(Method.MembersWithAccess.contains(player.getUniqueId().toString())){
					double balance = Method.getBalance(player);
					double playerbalance = main.economy.getBalance(player);
					if (amount <= balance){
						main.economy.withdrawPlayer(player, amount);
						Method.takeBalance(player, amount);
						Method.addLog(player, "withdraw", amount);
						double amt = balance - amount;
						ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&aWithdraw &6$" + amount + " *&afrom your island bank. Balance: &6$" + amt));
					}else{
						ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4Your bank do not have enought money"));
					}
					
				}else{
					ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "Your owner do not allowed you to modify money!"));
				}
			}
		}else{
			ChatUtilities.message(player, ChatColor.translateAlternateColorCodes('&', "&4You do not have an island"));
		}
	}
	
	// Admin add money to player's island bank

	public static void adminAddMoneyToBank (Player admin, Player targetplayer, double amount){
		if(Method.checkDoPlayerGotIsland(targetplayer) == true){
					Method.addBalance(targetplayer, amount);
					ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&aAdded &6$" + amount + " to " + targetplayer.getName() + "'s island bank"));
		}else{
			ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&4Target player do not have an island"));
		}
	}
	// Admin take money to player's island bank

	public static void adminTakeMoneyFromoBank (Player admin, Player targetplayer, double amount){
		if(Method.checkDoPlayerGotIsland(targetplayer) == true){
					Method.takeBalance(targetplayer, amount);
					ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&aTake &6$" + amount + " to " + targetplayer.getName() + "'s island bank"));
		}else{
			ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&4Target player do not have an island"));
		}
	}
	
	// Admin reset player's island bank

	public static void adminResetPlayerBank (Player admin, Player targetplayer){
		if(Method.checkDoPlayerGotIsland(targetplayer) == true){
					Method.registerIsland(targetplayer);
					ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&aReset " +   targetplayer.getName() + "'s island bank"));
		}else{
			ChatUtilities.message(admin, ChatColor.translateAlternateColorCodes('&', "&4Target player do not have an island"));
		}
	}
	
	
	// Check did target is owner of an island
	public static boolean isOwner (Player player){
		if(ASkyBlockAPI.getInstance().getOwnedIslands() == null){
			return false;
		}else{
			UUID puuid = player.getUniqueId();
			if(ASkyBlockAPI.getInstance().getIslandOwnedBy(puuid).getOwner().equals(puuid)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	// Get Island owner (Owner / Member)
	public static UUID getIslandOwner (Player player){
		if(ASkyBlockAPI.getInstance().getOwnedIslands() == null){
			return null;
		}else{
			if(Method.checkDoPlayerGotIsland(player)){
			UUID puuid = player.getUniqueId();
				UUID owner = ASkyBlockAPI.getInstance().getIslandOwnedBy(puuid).getOwner();
				return owner;
			}else{
				return null;
			}
		}
	}
	
	
	// True when target have island(Owner / Member)
	public static boolean checkDoPlayerGotIsland(Player player){
		UUID puuid = player.getUniqueId();
		if(ASkyBlockAPI.getInstance().getOwnedIslands() == null){
			return false;
		}else{
			if (ASkyBlockAPI.getInstance().getIslandOwnedBy(puuid) == null){

				return false;
			}else{
				return true;
			}
		}
	}
}
