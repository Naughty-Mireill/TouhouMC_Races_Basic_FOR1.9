/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  com.gmail.louis1234567890987654321.teams.Member
 *  com.gmail.louis1234567890987654321.teams.Team
 *  com.gmail.louis1234567890987654321.teams.TeamManager
 *  net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Races_Global$1
 *  net.TouhouMC.Halcram.TouhouMC_Races_Basic.TeamManager_tmcr
 *  net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.Team
 */
package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Races_Global
extends JavaPlugin
implements Listener {
    public static boolean magic_iscastable(Player pl, int mana, String string) {
        if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean()) {
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.RED + "\u4ed6\u306e\u9b54\u6cd5\u3092\u8a60\u5531\u4e2d\u3067\u3059");
            return false;
        }
        if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean()) {
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.RED + "\u4ed6\u306e\u9b54\u6cd5\u3092\u4f7f\u7528\u4e2d\u3067\u3059");
            return false;
        }
        if (TouhouMC_Races_Basic.conf.getDouble("user." + pl.getUniqueId() + ".spilit") > (double)mana) {
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.LIGHT_PURPLE + string);
            return true;
        }
        pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.RED + "\u970a\u529b\u304c\u4e0d\u8db3\u3057\u3066\u3044\u307e\u3059");
        return false;
    }

    public static void global_respawnhealth(Player pl, Plugin plugin, PlayerRespawnEvent event) {
        pl.setMaxHealth(100.0);
    }

    public static void global_charge_mana(Player pl, Plugin plugin, String pluginpre, PlayerInteractEvent event) {
        Material dust_is_ok = pl.getInventory().getItemInMainHand().getType();
        if (((MetadataValue)pl.getMetadata("spilituse").get(0)).asDouble() != 0.0) {
            FixedMetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, plugin);
            pl.setMetadata("spilituse", (MetadataValue)spilituse);
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.WHITE + "\u970a\u529b\u30ce\u30fc\u30de\u30eb");
        } else if (dust_is_ok == Material.SUGAR) {
            FixedMetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, plugin);
            pl.setMetadata("spilituse", (MetadataValue)spilituse);
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.AQUA + "\u970a\u529b\u6d88\u8cbb\u5c0f");
        } else if (dust_is_ok == Material.SULPHUR) {
            FixedMetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, plugin);
            pl.setMetadata("spilituse", (MetadataValue)spilituse);
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.DARK_GRAY + "\u970a\u529b\u6d88\u8cbb\u5927");
        } else if (dust_is_ok == Material.GLOWSTONE_DUST) {
            FixedMetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, plugin);
            pl.setMetadata("spilituse", (MetadataValue)spilituse);
            pl.sendMessage(String.valueOf(TouhouMC_Races_Basic.tmc_Races_pre) + (Object)ChatColor.YELLOW + "\u970a\u529b\u56de\u5fa9\u4e2d");
        }
    }
}