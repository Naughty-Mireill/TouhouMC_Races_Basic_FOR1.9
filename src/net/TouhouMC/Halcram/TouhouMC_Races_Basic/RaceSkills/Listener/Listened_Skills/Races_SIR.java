package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.io.File;
import java.util.Collection;
import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_SIR extends JavaPlugin {

	static File file = TouhouMC_Races_Basic.configfile;
	static FileConfiguration conf = TouhouMC_Races_Basic.conf;
	//移動スキル系
	//召喚スキル系
	public static void seirei_summon(final Player pl, final Plugin plugin){
		double type = Math.random();
		if (type <= 8.0D){
			int n = 0;
			while (n < 3){
				Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.SNOWMAN);
				MetadataValue syugoreisnow = new FixedMetadataValue(plugin, Boolean.valueOf(true));
				snowman.setMetadata("syugoreisnow", syugoreisnow);
				MetadataValue syugoreitarget = new FixedMetadataValue(plugin, pl.getName());
				snowman.setMetadata("syugoreitarget", syugoreitarget);
				n++;
			}
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SNOWMAN_HURT, 2.0F, 1.0F);
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.AQUA + "雪の霊を召喚した！");
		}else{
			int n = 0;
			while (n < 1){
				Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.IRON_GOLEM);
				MetadataValue syugoreiiron = new FixedMetadataValue(plugin, Boolean.valueOf(true));
				snowman.setMetadata("syugoreiiron", syugoreiiron);
				MetadataValue syugoreitarget = new FixedMetadataValue(plugin, pl.getName());
				snowman.setMetadata("syugoreitarget", syugoreitarget);
				n++;
			}
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 2.0F, -1.0F);
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GOLD + "岩の霊を召喚した");
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 800L);
	}

	//攻撃スキル系
	public static void hannrei_hannrei_ball(final Player pl, final Plugin plugin){
		pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_SAND_BREAK, 2.0F, 2.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 1);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(2.0D);
		Snowball snowball= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
		snowball.setMetadata("hannrei-curseball", shooter);
		snowball.setVelocity(velocity);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 200L);
	}

	public static void sourei_music(final Player pl, final Plugin plugin){
		List<Entity> enemys = pl.getNearbyEntities(16.0D, 16.0D, 16.0D);
		double rand = Math.random();
		if (rand >= 0.8D){
			pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_HAT, 10.0F, -2.0F);
			for (Entity enemy : enemys) {
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
					((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 5));
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_BLUE + "鬱だ・・");
				}
			}
		}else if (rand >= 0.4D){
			pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 10.0F, 1.0F);
			for (Entity enemy : enemys) {
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
					((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 2));
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_RED + "躁だ☆");
				}
			}
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_HARP, 10.0F, 0.0F);
			for (Entity enemy : enemys) {
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GREEN + "騒音だ！！");
					if (((Player)enemy).getHealth() - 15.0D >= 0.0D) {
						((Player)enemy).setHealth(((Player)enemy).getHealth() - 15.0D);
					}else {
						((Player)enemy).setHealth(0.0D);
					}
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 3200L);
	}
	//光弾
	public static void seirei_lightball(final Player pl, final Plugin plugin){
		pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_SNOW_BREAK, 2.0F, 2.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 1);
		Location location = pl.getEyeLocation();
		int n = 0;
		while (n < 8){
			float pitch = location.getPitch() / 180.0F * 3.1415927F;
			float yaw = location.getYaw() / 180.0F * 3.1415927F + n * 45;
			double motX = -Math.sin(yaw) * Math.cos(pitch);
			double motZ = Math.cos(yaw) * Math.cos(pitch);
			double motY = -Math.sin(pitch);
			Vector velocity = new Vector(motX, motY, motZ).multiply(2.0D);
			Snowball snowball= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
			MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
			snowball.setMetadata("seirei-lightball", shooter);
			snowball.setShooter(pl);
			snowball.setVelocity(velocity);
			n++;
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 100L);
	}

	public static void onnryou_never_vanish(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost){
		double rand = Math.random();
		if (boost > 0) rand += 0.1;
		if (rand > 0.7D){
			pl.setHealth(50.0D);
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_RED + "消えたくない・・・っ");
			if ((event.getDamager() instanceof Player)){
				Player dpl = (Player)event.getDamager();
				dpl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_RED + "消えたくない・・・っ");
				dpl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 4));
			}
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GHAST_WARN, 2.0F, 2.0F);
			event.setCancelled(true);
		}
	}

	public static void seirei_mighty_guard(Player pl, Plugin plugin, EntityDamageEvent event, int boost){
		if (boost >= 3){
			if (pl.isSneaking() && event.getDamage() > 5.0D){
				event.setDamage(event.getDamage() - 5.0D);
			}else if (event.getDamage() > 2.0D){
				event.setDamage(event.getDamage() - 2.0D);
			}
		}else if (boost > 0 && boost < 3){
			if (pl.isSneaking() && event.getDamage() > 3.0D){
				event.setDamage(event.getDamage() - 3.0D);
			}
		}else{
			if (pl.isSneaking() && event.getDamage() > 1.0D){
				event.setDamage(event.getDamage() - 1.0D);
			}
		}
	}

	public static void seirei_mighty_guard(Player pl, Plugin plugin, EntityDamageByBlockEvent event, int boost){
		if (boost >= 3){
			if (pl.isSneaking() && event.getDamage() > 5.0D){
				event.setDamage(event.getDamage() - 5.0D);
			}else if (event.getDamage() > 2.0D){
				event.setDamage(event.getDamage() - 2.0D);
			}
		}else if (boost > 0 && boost < 3){
			if (pl.isSneaking() && event.getDamage() > 3.0D){
				event.setDamage(event.getDamage() - 3.0D);
			}
		}else{
			if (pl.isSneaking() && event.getDamage() > 1.0D){
				event.setDamage(event.getDamage() - 1.0D);
			}
		}
	}
	//TODO 神霊
	public static void sinnrei_godsoul(final Player pl, final Plugin plugin){
		Collection<? extends Player> enemys = Bukkit.getServer().getOnlinePlayers();
		double rand = Math.random();
		if (rand >= 0.6D){
			for (Entity enemy : enemys) {
				if ((enemy instanceof Player)){
					((Player)enemy).playSound(pl.getLocation(), Sound.ENTITY_CAT_PURR, 1.0F, 1.0F);
					((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1));
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GRAY + "透明な世界になった！");
				}
			}
		}else if (rand >= 0.2D){
			for (Entity enemy : enemys) {
				if ((enemy instanceof Player)){
					((Player)enemy).playSound(pl.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 2.0F);
					((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 3));
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GRAY + "神速の世界になった！");
				}
			}
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_HARP, 10.0F, 0.0F);
			for (Entity enemy : enemys) {
				if ((enemy instanceof Player)){
					((Player)enemy).playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0F, -1.0F);
					((Player)enemy).setNoDamageTicks(100);
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GRAY + "無敵の世界になった！");
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 6000L);
	}
	//TODO 夢喰
	public static void yumekui_dreamy(final Player pl, final Plugin plugin){
		List<Entity> enemys = pl.getNearbyEntities(20.0D, 20.0D, 20.0D);
		for (Entity enemy : enemys) 
		{
			if ((enemy instanceof Player))
			{
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.LIGHT_PURPLE + "夢喰の精神攻撃！");
				if (conf.getInt("user." + enemy.getUniqueId() + ".spilit") >= 20)
				{
					conf.set("user." + pl.getUniqueId() + ".spilit",conf.getInt("user." + pl.getUniqueId() + ".spilit") + 20 );
					conf.set("user." + enemy.getUniqueId() + ".spilit",conf.getInt("user." + enemy.getUniqueId() + ".spilit") - 20 );
				}
				else
				{
					int mana = conf.getInt("user." + enemy.getUniqueId() + ".spilit");
					conf.set("user." + pl.getUniqueId() + ".spilit",conf.getInt("user." + pl.getUniqueId() + ".spilit") + mana );
					conf.set("user." + enemy.getUniqueId() + ".spilit",conf.getInt("user." + enemy.getUniqueId() + ".spilit") - mana );
					int damage = 20 - mana ;
					((Player)enemy).sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_PURPLE + "あなたは霊力が無かったため夢に喰われた！！");
					((Player)enemy).damage(damage,pl);
				}
				}
			}
		}
		if (conf.getInt("user." + pl.getUniqueId() + ".spilit") >= 201)
		{
			conf.set("user." + pl.getUniqueId() + ".spilit",200 );
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.LIGHT_PURPLE + "夢でお腹いっぱい。");
		}
		TouhouMC_Races_Basic.SavethraceConfig();
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 2400L);
	}
	
	//TODO 西行妖
	public static void saigyou_kotyouran(Player pl, Plugin plugin){
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 3.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(2.0D);
		Snowball snowball= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
		snowball.setMetadata("saigyouyou-deathball", shooter);
		snowball.setVelocity(velocity);
	}
	
	//TODO 地縛霊
	public static void zibakurei_always_unvanish(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost){
		double rand = Math.random();
		if (event.getDamage() >= 10) rand += 0.1;
		if (rand > 0.8D){
			pl.setHealth(pl.getHealth() + 10.0D);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0F, 2.0F);
			if ((event.getDamager() instanceof Player)){
				Player dpl = (Player)event.getDamager();
				dpl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0F, 2.0F);
				dpl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1));
			}
		}
	}
	
	//TODO 狂霊
	public static void kyourei_berserker(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost){
		double adddamager = pl.getHealth() / (11 - boost);
		event.setDamage((adddamager + event.getDamage()) - 3);
	}
}