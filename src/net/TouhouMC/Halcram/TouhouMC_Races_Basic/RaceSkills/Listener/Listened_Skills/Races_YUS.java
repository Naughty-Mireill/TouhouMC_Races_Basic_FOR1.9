package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_YUS extends JavaPlugin {
	///アクティブスキル系
	//移動スキル系
	public static void yousei_feather(Player pl, Plugin plugin){
		pl.setVelocity(pl.getLocation().getDirection().multiply(0.8D));
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
	}

	//防御スキル系
	public static void daiyousei_illusion(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.LIGHT_PURPLE + "３秒間避けの体制を構えた！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_PURREOW, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.SPELL, 1, 1);
		pl.setNoDamageTicks(60);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 300L);
	}
	
	//攻撃スキル系
	public static void yousei_illusion(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.LIGHT_PURPLE + "怪しげな輝きがあたりを惑わす！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_PURR, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.HAPPY_VILLAGER, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
		for (Entity enemy : enemys){
			boolean no_damage = false;
			if (enemy instanceof Player)
			{
				no_damage = enemys == pl;
			}
			if (!no_damage)
			{
				((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 3));
				((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 3));
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
		}, 600L);
	}

	public static void kibito_venom(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GREEN + "樹人は毒をばらまいた！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_PIG_DEATH, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.VOID_FOG, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)) {
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2));
			}}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 400L);
	}
	
	///パッシブスキル系

	public static void hyouketuyousei_skate(Player pl, final Plugin plugin, int boost){
		///移動スキル系
		if (pl.getWorld().getBlockAt(pl.getLocation().getBlockX(), pl.getLocation().getBlockY() - 1, pl.getLocation().getBlockZ()).getType() == Material.PACKED_ICE || pl.getWorld().getBlockAt(pl.getLocation().getBlockX(), pl.getLocation().getBlockY() - 1, pl.getLocation().getBlockZ()).getType() == Material.ICE){
			if (boost > 1 && boost <= 3){
				pl.setVelocity(new Vector (pl.getLocation().getDirection().multiply(2.0D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(2.0D).getZ()));
			}else if (boost == 1){
				pl.setVelocity(new Vector (pl.getLocation().getDirection().multiply(1.0D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(1.0D).getZ()));
			}
		}
	}
	
	public static void yousei_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.9D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
		}
    }

	public static void daiyousei_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.5D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, 1, 2);
			event.setDamage(event.getDamage() / 2);
		}
    }
	
	public static void kobito_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.8D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 2.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
		}else if ((event.getDamage() > 1.0D) && (plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 20)){
			event.setDamage(event.getDamage() + 1.0D);
		}else if (event.getDamage() > 1.0D){
			event.setDamage(event.getDamage() + 3.0D);
		}
	}
	
	public static void yousei_fall_protection(Player pl, Plugin plugin, EntityDamageEvent event){
		event.setDamage(event.getDamage() / 2.0D);
	}

	public static void satori_satori(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if(event.getDamage() >= pl.getHealth() && event.isCancelled() == false){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"), Double.valueOf(plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 50.0D));
			pl.sendMessage(event.getDamager().getName() + ":体力:" + ((Player)event.getDamager()).getHealth());
			pl.sendMessage(event.getDamager().getName() + ":座標:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
			pl.sendMessage(ChatColor.DARK_PURPLE + "覚りました・・・覚えてなさい・・・");
			String satorin0 = event.getDamager().getName();
			MetadataValue satorin00 = new FixedMetadataValue(plugin, satorin0);
			pl.setMetadata("satorin0", satorin00);
		}
	}
	
	//TODO エゴサトリ
	//完全消滅
	public static void egosatori_vanished(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_AQUA + "すべてを無意識にする！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GRAY + "完全に消滅したように見える・・・");
				pl.setNoDamageTicks(400);
				for (Player vpl : Bukkit.getServer().getOnlinePlayers())
				{
					vpl.hidePlayer(pl);
				}
				MetadataValue vanished = new FixedMetadataValue(plugin, true) ;
				pl.setMetadata("vanished", vanished);
				MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
				pl.setMetadata("using-magic", usingmagic);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						pl.removeMetadata("vanished", plugin);
						for (Player vpl : Bukkit.getServer().getOnlinePlayers())
						{
							vpl.showPlayer(pl);
						}
						MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
						pl.setMetadata("using-magic", usingmagic);
						pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱の効果及びクールダウンが終わりました");
					}
				}, 400L);
				MetadataValue casted = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("casting", casted);
			}
		}, 3000L);
	}
	
	//TODO 巨人
	public static void kyozin_mightydefence(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamage() > 1 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 5D){
			event.setDamage(event.getDamage() - 2D);
		}
	}
	
	//TODO 地獄妖精
	public static void crownpeace_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.5D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 2.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 3.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
		}else if ((event.getDamage() > 1.0D) && (plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 20)){
			event.setDamage(event.getDamage() + 4.0D);
		}else if (event.getDamage() > 1.0D){
			event.setDamage(event.getDamage() + 6.0D);
		}
	}
	
	
    //TODO 四季妖
	
	public static void sikiyou_rose_venom(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.9D)
		{
			if (event.getDamager() instanceof LivingEntity)
			{
				((LivingEntity)event.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 3));
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_PIG_DEATH, 3.0F, 2.0F);
			}
		}
	}
	
	public static void sikiyou_deadly_poison(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_GREEN + "四季妖は死せる樹の毒をばらまいた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SKELETON_DEATH, 3.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.VOID_FOG, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(5.0D, 5.0D, 5.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)) {
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 3));
			}}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 1000L);
	}
	

	//氷魔法
	public static void hyouketuyousei_cold(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "氷結の弾を撃った！");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 0);
		Location location =pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw=location.getYaw() / 180.0F * 3.1415927F ;
		double motX=-Math.sin(yaw) * Math.cos(pitch);
		double motZ=Math.cos(yaw) * Math.cos(pitch);
		double motY=-Math.sin(pitch);
		Vector velocity1=new Vector(motX,motY,motZ).multiply(0.7D);
		Vector velocity2=new Vector(motX,motY,motZ).multiply(1.2D);
		Vector velocity3=new Vector(motX,motY,motZ).multiply(1.7D);
		
		Snowball snowball1= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		Snowball snowball2= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		Snowball snowball3= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString()) ;
		MetadataValue fireeffect = new FixedMetadataValue(plugin, 30D) ;
		snowball1.setMetadata("coldeffect", fireeffect);
		snowball1.setMetadata("hyouketuyousei-coldball", shooter);
		snowball1.setVelocity(velocity1);
		snowball1.setFireTicks(300);
		snowball2.setMetadata("coldeffect", fireeffect);
		snowball2.setMetadata("hyouketuyousei-coldball", shooter);
		snowball2.setVelocity(velocity2);
		snowball2.setFireTicks(300);
		snowball3.setMetadata("coldeffect", fireeffect);
		snowball3.setMetadata("hyouketuyousei-coldball", shooter);
		snowball3.setVelocity(velocity3);
		snowball3.setFireTicks(300);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		},200L);
	}
}