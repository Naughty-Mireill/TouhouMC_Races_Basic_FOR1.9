package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule;

import java.io.File;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.nametagedit.plugin.NametagManager;
import com.nametagedit.plugin.api.NametagAPI;

public class Nametag_Schedule implements Listener{
	public static File config = TouhouMC_Races_Basic.configfile;
	public static FileConfiguration conf = TouhouMC_Races_Basic.conf;
	public static NametagAPI nta = new NametagAPI(new NametagManager());
	
	//コンストラクタ リスナー登録
	public Nametag_Schedule(TouhouMC_Races_Basic plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static void runname(final Plugin plugin0, final String thrpre0){
		new BukkitRunnable() {
			public void run() {
				for (final Player player : Bukkit.getOnlinePlayers())
				{
					if(conf.getString("user." + player.getUniqueId()) != null)
					{
						String race = conf.getString("user." + player.getUniqueId() + ".race").toString();
						String realrace = conf.getString("race." + race + ".tag");
						if (realrace == null ) realrace = conf.getString("user." + player.getUniqueId() + ".race");
						nta.setNametag(player.getPlayerListName(), realrace, "");
						player.setPlayerListName(realrace + player.getName() );
					}
				}
			}
		}.runTaskTimer(plugin0, 0, 100L);
		return ;
	}
}