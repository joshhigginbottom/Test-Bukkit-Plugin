package me.joshhigginbottom;

import java.util.Arrays;
import java.util.Map;

import com.onarandombox.MultiverseCore.MultiverseCore;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.material.Dye;

public final class testbukkitplugin extends JavaPlugin implements Listener
{

    public static void main( String[] args )
    {
        
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable has been invoked!");
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("whereami").setExecutor(new PluginCommandExecutor(this));
        //MultiverseCore core = (MultiverseCore)Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        //getLogger().info(event.getPlayer().getName() + " moved from: " + event.getFrom() + " to: " + event.getTo());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamaged(EntityDamageByEntityEvent event) {
        if(!event.isCancelled()){
            if (event.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Sheep) {
                    Sheep sheep = (Sheep)event.getEntity();
                    Player player = (Player)event.getDamager();
    
                    sheep.setColor(DyeColor.PINK);
                    player.sendMessage("You just pinked that sheep!");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDispenserEvent(BlockDispenseEvent event){
        if(event.getBlock().getState() instanceof Dispenser){
            Dispenser dispenser = (Dispenser)event.getBlock().getState();
            Directional directional = (Directional)event.getBlock().getBlockData();
            BlockFace blockFace = directional.getFacing();
            Location dispenserLocation = dispenser.getLocation();
            dispenserLocation.add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());

            Sheep sheep = (Sheep)getEntityAtLocation(dispenserLocation, EntityType.SHEEP);

            Map<String, Object> map = event.getItem().serialize();
            String color = map.get("type").toString().replace("_DYE","").replace("LEGACY_", "");
            getServer().broadcastMessage(color);
            DyeColor dyeColor = DyeColor.valueOf(color);

            if (sheep != null){
                getServer().broadcastMessage("Sheep is not null");
                sheep.setColor(dyeColor);
                getServer().broadcastMessage(map.get("type").toString().replace("LEGACY_", ""));
                Material dyeMaterial = event.getItem().getType();
                getServer().broadcastMessage(dyeMaterial.name());
                getServer().broadcastMessage(dispenser.getInventory().getItem(1).getType().name());
                dispenser.getInventory().remove(new ItemStack(dyeMaterial,1));
                event.setCancelled(true);
            }
        }
    }

    public Entity getEntityAtLocation(Location location, EntityType entityType){
        for (Entity entity : location.getChunk().getEntities()){
            if (entity.getType() == entityType){
                /*getServer().broadcastMessage(entity.getType() + " ||| " + entityType.toString() + " ||| " 
                                            + Math.floor(entity.getLocation().getX()) + " ||| " 
                                            + Math.floor(entity.getLocation().getY()) + " ||| " 
                                            + Math.floor(entity.getLocation().getZ()));
                getServer().broadcastMessage(location.getX() + " ||| " + location.getY() + " ||| " + location.getZ());*/
                if (Math.floor(entity.getLocation().getX()) == location.getX() && 
                    Math.floor(entity.getLocation().getY()) == location.getY() &&
                    Math.floor(entity.getLocation().getZ()) == location.getZ()){
                    return entity;
                }
            }
        }
        return null;
    }
}
