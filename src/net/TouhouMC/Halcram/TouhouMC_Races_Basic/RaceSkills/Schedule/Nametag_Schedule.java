package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule;

import java.io.File;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.nametagedit.plugin.api.NametagAPI;

public class Nametag_Schedule{
	public static File config = TouhouMC_Races_Basic.configfile;
	public static FileConfiguration conf = TouhouMC_Races_Basic.conf;
	public static NametagAPI nta;
	public void runname(final Plugin plugin0, final String thrpre0){
		new BukkitRunnable() {


			public void run() {
				for (final Player player : Bukkit.getOnlinePlayers())
				{
					if (conf.contains("user." + player.getUniqueId())){
						String race = conf.getString("user." + player.getUniqueId() + ".race").toString();
						String realrace = conf.getString("race." + race + ".tag");
						if (realrace == null ) realrace = conf.getString("user." + player.getUniqueId() + ".race");
						nta.setNametag(player.getPlayerListName(), realrace, "");
					}
				}
			}
		}.runTaskTimer(plugin0, 0, 100L);
		return ;
	}
}