package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Races_YUS extends JavaPlugin {
	///�A�N�e�B�u�X�L���n
	//�ړ��X�L���n
	public static void yousei_feather(Player pl, Plugin plugin){
		pl.setVelocity(pl.getLocation().getDirection().multiply(0.8D));
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
	}

	//�U���X�L���n
	public static void yousei_illusion(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.LIGHT_PURPLE + "���̃V���x���̋P�����������f�킷�I�I");
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
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 60L);
	}

	public static void kibito_venom(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GREEN + "���l�͓ł��΂�܂����I");
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
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 100L);
	}
	
	///�p�b�V�u�X�L���n
	public static void yousei_glaze(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double ran = Math.random();
		if (ran >= 0.9D){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_HISS, 1.0F, 1.0F);
			pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
			event.setCancelled(true);
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
			pl.sendMessage(event.getDamager().getName() + ":�̗�:" + ((Player)event.getDamager()).getHealth());
			pl.sendMessage(event.getDamager().getName() + ":���W:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
			pl.sendMessage(ChatColor.DARK_PURPLE + "�o��܂����E�E�E�o���ĂȂ����E�E�E");
			String satorin0 = event.getDamager().getName();
			MetadataValue satorin00 = new FixedMetadataValue(plugin, satorin0);
			pl.setMetadata("satorin0", satorin00);
		}
	}
	
	//TODO �G�S�T�g��
	//���S����
	public static void egosatori_vanished(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_AQUA + "���ׂĂ𖳈ӎ��ɂ���I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GRAY + "���S�ɏ��ł����悤�Ɍ�����E�E�E");
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
						pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̌��ʋy�уN�[���_�E�����I���܂���");
					}
				}, 400L);
				MetadataValue casted = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("casting", casted);
			}
		}, 60L);
	}
	
	//TODO ���l
	public static void kyozin_mightydefence(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamage() > 1 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 5D){
			event.setDamage(event.getDamage() - 2D);
		}
	}
	
	//TODO �n���d��
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
	
	
    //TODO �l�G�d
	
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
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_GREEN + "�l�G�d�͎�������̓ł��΂�܂����I�I");
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
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 240L);
	}
}