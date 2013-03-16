/*    */ package me.Restarter;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerLoginEvent;
/*    */ import org.bukkit.event.player.PlayerLoginEvent.Result;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Restarter extends JavaPlugin
/*    */   implements Listener
/*    */ {
/* 20 */   private boolean _in_restart = false;
/*    */ 
/* 22 */   private Logger log = Bukkit.getLogger();
/*    */ 
/*    */   public void onEnabled()
/*    */   {
/* 26 */     getServer().getPluginManager().registerEvents(this, this);
/* 27 */     this.log.info("Restarter initialized.");
/*    */   }
/*    */ 
/*    */   public void onDisabled()
/*    */   {
/* 32 */     this.log.info("Restarter disabled.");
/*    */   }
/*    */ 
/*    */   private void stop_server()
/*    */   {
/* 37 */     this._in_restart = true;
/* 38 */     this.log.info("[RestartMod] server going for shutdown now");
/*    */ 
/* 40 */     for (Player p : getServer().getOnlinePlayers())
/*    */     {
/* 42 */       if (!p.isOnline())
/*    */         continue;
/* 44 */       p.kickPlayer("Server is being restarted.");
/*    */     }
/*    */ 
/* 47 */     for (Plugin p : getServer().getPluginManager().getPlugins())
/*    */     {
/* 49 */       if (!p.isEnabled())
/*    */         continue;
/* 51 */       if (p.getName().equalsIgnoreCase("Guga_SERVER_MOD"))
/*    */         continue;
/* 53 */       if (p.equals(this)) {
/*    */         continue;
/*    */       }
/* 56 */       getServer().getPluginManager().disablePlugin(p);
/*    */     }
/*    */ 
/* 59 */     Bukkit.savePlayers();
/* 60 */     Bukkit.shutdown();
/*    */   }
/*    */ 
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*    */   {
/* 65 */     if (cmd.getName().equalsIgnoreCase("restart"))
/*    */     {
/* 67 */       boolean hasPermission = (sender.isOp()) || (sender.hasPermission("restarter.restart"));
/* 68 */       if (hasPermission)
/*    */       {
/* 70 */         if ((args.length == 1) && (args[0].equals("now")))
/*    */         {
/* 72 */           stop_server();
/*    */         }
/*    */         else
/* 75 */           sender.sendMessage("Bad syntax of restart command.");
/*    */       }
/* 77 */       return true;
/*    */     }
/*    */ 
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onPlayerLogin(PlayerLoginEvent event) {
/* 86 */     if ((this._in_restart) && (!event.getPlayer().isOp()))
/* 87 */       event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Server is being restarted.");
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 93 */     if ((this._in_restart) && (!event.getPlayer().isOp()))
/* 94 */       event.getPlayer().kickPlayer("Server is being restarted.");
/*    */   }
/*    */ }

/* Location:           C:\Users\shaw\Desktop\Restarter.jar
 * Qualified Name:     me.Restarter.Restarter
 * JD-Core Version:    0.6.0
 */