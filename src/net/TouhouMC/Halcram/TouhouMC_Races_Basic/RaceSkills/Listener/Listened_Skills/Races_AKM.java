package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Races_AKM extends JavaPlugin {
	private static String thrpre = TouhouMC_Races_Basic.thrace_Races_pre;
	//移動スキル系
	//吸血鬼カモフラージュ
	public static void kyuuketuki_vamp(final Player pl, final Plugin plugin){
		pl.sendMessage(thrpre + ChatColor.GRAY + "バンプカモフラージュを唱えた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BAT_AMBIENT, 1.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.SMOKE, 1, 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue casted = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
				pl.setMetadata("casting", casted);
				MetadataValue batman = new FixedMetadataValue(plugin, pl.getUniqueId());
				pl.setMetadata("batman", batman);
				pl.setGameMode(GameMode.SPECTATOR);
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 0.0F);
				pl.sendMessage(thrpre + ChatColor.RED + "あなたは蝙蝠になった！！");
				final Entity bat = pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.BAT);
				MetadataValue invincible = new FixedMetadataValue(plugin, pl.getUniqueId());
				bat.setMetadata("invincible", invincible);
				pl.setMetadata("using-magic", usingmagic);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run()
					{
						pl.teleport(bat);
						pl.setGameMode(GameMode.SURVIVAL);
						MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
						pl.setMetadata("using-magic", usingmagic);
						pl.removeMetadata("batman", plugin);
						pl.sendMessage(thrpre + ChatColor.RED + "バンプカモフラージュの効果が切れました");
						bat.removeMetadata("invincible", plugin);
						((Damageable) bat).damage(1000.0D);
					}
				}, 300L);
			}
		}, 20L);
	}

	//攻撃スキル系
	//紅魔法
	public static void akuma_red_magic(final Player pl, final Plugin plugin){
		pl.sendMessage(thrpre + ChatColor.DARK_RED + "紅の魔法を唱えた！");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 2.0F);
		List<Entity> enemys = pl.getNearbyEntities(9.0D, 9.0D, 9.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity)) && (enemy.isOnGround())){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
					((LivingEntity)enemy).damage(15.0D);
					((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 3));
					enemy.getWorld().playEffect(enemy.getLocation(), Effect.TILE_DUST, 12);
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
			}
		}, 400L);
	}

	//鬼の埋め落とし
	public static void oni_kairiki(Player pl, final Plugin plugin, final PlayerInteractEntityEvent event, final LivingEntity entity){
		pl.sendMessage(thrpre + ChatColor.GOLD + "敵を地面に接着させた！！");
		entity.getWorld().playSound(event.getRightClicked().getLocation(), Sound.ENTITY_DONKEY_ANGRY, 1, -1);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		final int task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				entity.setVelocity(entity.getVelocity().setY(-5));
			}
		},0,1L);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				plugin.getServer().getScheduler().cancelTask(task);
				Player pl = event.getPlayer();
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(thrpre + ChatColor.BLUE + "効果と詠唱のクールダウンが終わりました");
			}
		}, 200L);
	}

	//吸血鬼の吸血
	public static void kyuuketuki_drain(final Player pl, final Plugin plugin, PlayerInteractEntityEvent event, LivingEntity entity){
		Entity target = event.getRightClicked();
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		if (pl.getLocation().distanceSquared(target.getLocation()) >= 40.0D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2.0F, 1.0F);
			pl.sendMessage(thrpre + ChatColor.BLUE + "しかし逃げられてしまった！！");
		}else{
			pl.sendMessage(thrpre + ChatColor.DARK_RED + "あなたは吸血した！");
			target.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SPIDER_DEATH, 2.0F, 1.0F);
			target.getWorld().playEffect(pl.getLocation(), Effect.TILE_BREAK, 1, 152);
			if (((LivingEntity)target).getHealth() - 30.0D >= 0.0D) {
				((LivingEntity)target).setHealth(((LivingEntity)target).getHealth() - 30.0D);
			}else{
				((LivingEntity)target).setHealth(0.0D);
			}
			if (pl.getHealth() > pl.getMaxHealth() - 30.0D){
				pl.setHealth(pl.getMaxHealth());
			}else{
				pl.setHealth(30.0D + pl.getHealth());
			}
		}
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(thrpre + ChatColor.BLUE + "効果と詠唱のクールダウンが終わりました");
			}
		}, 200L);
	}
	//TODO 禁忌魔
	//禁忌魔のきゅっとして
	public static void kinnima_kyuttosite(final Player pl, final Plugin plugin, PlayerInteractEntityEvent event, LivingEntity entity){
		Entity target = event.getRightClicked();
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		if (pl.getLocation().distanceSquared(target.getLocation()) >= 80.0D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2.0F, 1.0F);
			pl.sendMessage(thrpre + ChatColor.BLUE + "しかし逃げられてしまった！！");
		}else{
			pl.sendMessage(thrpre + ChatColor.DARK_RED + "きゅっとしてどかーん！");
			target.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 1.0F);
			target.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION, 1, 2);
			if (target instanceof LivingEntity)
			{
				((Player)target).damage(90D,pl);
			}
		}
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(thrpre + ChatColor.BLUE + "効果と詠唱のクールダウンが終わりました");
			}
		}, 200L);
	}
	
	///パッシブ系
	//ダメージ系
	public static void akuma_dark_attack(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (pl.getLocation().getBlock().getLightLevel() < 8){
			event.setDamage(event.getDamage() + 1.0D);
			event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.TILE_BREAK, 152);
		}
	}

	public static void kyuuketuki_shadow_attack(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (pl.getLocation().getBlock().getLightLevel() < 4){
			event.setDamage(event.getDamage() + 1.0D);
			event.getDamager().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_BAT_HURT,1,-1);
		}
	}

	public static void oni_closed_attack(Player pl, Plugin plugin,EntityDamageByEntityEvent event){
		if (pl.getLocation().distanceSquared(event.getEntity().getLocation()) < 12){
			event.setDamage(event.getDamage() + 2.0D);
			event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		}
	}
	//TODO 鬼人
	//反撃
	public static void kizin_revese_attack(Player pl, Plugin plugin,EntityDamageByEntityEvent event){
		double rand = Math.random();
		if (rand >= 0.9 && event.getDamage() > 1){
			Entity reversee = event.getDamager();
			if (reversee instanceof LivingEntity)
			{
				((LivingEntity)reversee).damage(event.getDamage(), event.getDamager());
				if (reversee instanceof Player)
				{
					((Player)reversee).sendMessage(thrpre + ChatColor.DARK_RED + "鬼人の反撃を受けた！！");
				}
			}
		}
	}
	
	//TODO 怪力乱神
	//衝撃波
	public static void kairikirannsin_syougekiha(final Player pl, final Plugin plugin){
		pl.sendMessage(thrpre + ChatColor.DARK_RED + "衝撃波をあたりに放った！");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0F, -1.0F);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity)) && (enemy.isOnGround())){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((LivingEntity)enemy).damage(2.0D);
				((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1));
				enemy.getWorld().playEffect(enemy.getLocation(), Effect.TILE_DUST, 13);
				}}
		}
	}
	
	//防御系
	public static void akuma_antiheat_body(Player pl, Plugin plugin,EntityDamageEvent event){
		if (event.getCause() == DamageCause.FIRE_TICK) event.setCancelled(true);
	}

	//防御系
	public static void kyuuketuki_antiallfire_body(Player pl, Plugin plugin,EntityDamageEvent event){
		if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.LAVA) event.setCancelled(true);
	}
}