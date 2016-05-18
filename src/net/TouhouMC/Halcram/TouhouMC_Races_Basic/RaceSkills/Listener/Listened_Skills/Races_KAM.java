package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Races_KAM extends JavaPlugin {
	//�U���X�L���n
	///�p�b�V�u�n
	//�_���[�W�n
	public static void kami_faith_attack(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 0.0D && event.getDamage() <= 4.0D){
				event.setDamage(event.getDamage() + 1.0D);
			}else if (event.getDamage() > 4.0D && event.getDamage() <= 8.0D){
				event.setDamage(event.getDamage() + 2.0D);
			}else if (event.getDamage() > 8.0D && event.getDamage() <= 12.0D){
				event.setDamage(event.getDamage() + 3.0D);
			}else if (event.getDamage() > 12.0D){
				event.setDamage(event.getDamage() + 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() * 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 4);
		}
	}

	//�h��n
	public static void kami_faith_defence(Player pl, Plugin plugin, EntityDamageByEntityEvent event, int boost, FileConfiguration conf){
		if (boost > 0 && boost < 3){
			if (event.getDamage() > 2.0D && event.getDamage() <= 6.0D){
				event.setDamage(event.getDamage() - 1.0D);
			}else if (event.getDamage() > 6.0D && event.getDamage() <= 10.0D){
				event.setDamage(event.getDamage() - 2.0D);
			}else if (event.getDamage() > 10.0D && event.getDamage() <= 14.0D){
				event.setDamage(event.getDamage() - 3.0D);
			}else if (event.getDamage() > 14.0D){
				event.setDamage(event.getDamage() - 4.0D);
			}
		}else if (boost >= 3){
			event.setDamage(event.getDamage() / 1.5D);
			conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 2);
		}
	}

	public static void houzyousin_feed(Player pl, Plugin plugin, EntityDamageEvent event){
		if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) event.setCancelled(true);
	}

	public static void houzyousin_potato(Player pl, Plugin plugin, EntityDamageByEntityEvent event,int boost){
		if ((Math.random() >= 0.8D) && ((event.getEntity() instanceof Player)) && boost > 0.0D){
			((Player)event.getEntity()).setFoodLevel(((Player)event.getEntity()).getFoodLevel() - 1);
			event.getEntity().sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + pl.getName() + "�͂������������������Ă����I�I");
		}
	}

	public static void yakusin_darkside(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Player && event.getDamage() >= pl.getHealth()){
			Player killplayer = (Player) event.getDamager();
			if (!killplayer.isDead()){
				killplayer.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_RED + "���Ȃ��͐_���M����󂯂��I�I�I");
				killplayer.damage(50.0D);
			}
		}
	}
	
	//TODO �����_
	public static void onnbasira_circlefaith(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "����]������̂͊�炢�邩�I�I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_MINECART_RIDING, 2.0F, 3.0F);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				List<Entity> enemys = pl.getNearbyEntities(16.0D, 16.0D, 16.0D);
				int faither = 1;
				for (Entity enemy : enemys) {
					if (((enemy instanceof Player)) )
					{
						faither ++ ;
					}
				}
				if (faither == 1){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�����̂��I�I�H�H");
					pl.removePotionEffect(PotionEffectType.WEAKNESS);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
				}
				else if (faither <= 3){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "���Ȃ��̂��E�E�E");
					pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
				}
				else if (faither <= 6){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�悩�낤�I�䂪�_�͂����邪�ǂ��I");
					pl.removePotionEffect(PotionEffectType.REGENERATION);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 1));
				}
				else{
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "���������A����قǂ܂ł̐M�҂��I�I");
					pl.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 400, 20));
					pl.removePotionEffect(PotionEffectType.REGENERATION);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 5));
				}
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���͏I���܂���");
			}
		}, 60L);
	}
	
	//TODO �y���_
	public static void kerokero_nativefaith(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�������ɋ߂Â��Ȃ��ŁI");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2.0F, 1.0F);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				List<Entity> enemys = pl.getNearbyEntities(16.0D, 16.0D, 16.0D);
				int faither = 0;
				for (Entity enemy : enemys) {
					if (((enemy instanceof Player)) )
					{
						faither ++ ;
					}
				}
				if (faither > 4){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�������E�E�E�͂܂ꂽ��");
					pl.removePotionEffect(PotionEffectType.SLOW);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
				}
				else if (faither > 2){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�Ȃ�ŗ�����ł����I");
					pl.removePotionEffect(PotionEffectType.HUNGER);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0));
				}
				else if (faither > 0){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�����Ȃ�΂������̐_�͂𔭓��ł����I");
					pl.removePotionEffect(PotionEffectType.SPEED);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3));
				}
				else{
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "��������[�I�P���P���f�B�X�e�B�j�[�I�I");
					pl.removePotionEffect(PotionEffectType.HEAL);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 99));
					pl.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
					pl.removePotionEffect(PotionEffectType.WATER_BREATHING);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1));
					pl.removePotionEffect(PotionEffectType.SATURATION);
					pl.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1));
				}
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���͏I���܂���");
			}
		}, 60L);
	}
	
	//TODO �t�r�_
	public static void tukumogami_sadezumu(Player pl, Plugin plugin, EntityDamageByEntityEvent event, MemorySection conf){
		if (event.getDamager() instanceof Player){
			Player attackplayer = (Player) event.getDamager();
			double mpoint = event.getDamage();
			if (pl.hasMetadata("sadezumu"))
			{
				mpoint += pl.getMetadata("sadezumu").get(0).asDouble();
				pl.removeMetadata("sadezumu", plugin);
			}
			if (mpoint >= 300 + (0.5 - Math.random()) * 100){
				mpoint = 0;
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_AQUA + "���ł��ށ[�[�[�I�I�I�I");
				attackplayer.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_AQUA + "���ł��ށ[�[�[�I�I�I�I");
				pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 3.0F, 0.0F);
				
				conf.set("user." + pl.getUniqueId() + ".spilit", 200);
				pl.setHealth(pl.getMaxHealth());
			}
			MetadataValue sadezumu = new FixedMetadataValue(plugin, mpoint) ;
			pl.setMetadata("sadezumu", sadezumu);
		}
	}
	
	//TODO ���_
	public static void sinigami_deathhall(Player pl, Plugin plugin, EntityDamageByEntityEvent event, MemorySection conf){
		if (event.getEntity() instanceof Player){
			Player killedplayer = (Player) event.getEntity();
			if (killedplayer.getHealth() <= event.getDamage()){
				List<Entity> enemys = event.getEntity().getNearbyEntities(12.0D, 12.0D, 12.0D);
				for (Entity enemy : enemys) {
					boolean no_damage = false;
					if (enemy instanceof Player)
					{
						no_damage = enemys == pl;
					}
					if (!no_damage)
					{
						if (!enemy.equals(pl))
						{
							((Player)enemy).sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_RED + "���O�����Ɏ��ˁI�I");
							((Player)enemy).damage(20.0D);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 5);
						}
					}
				}
			}
		}
	}
}