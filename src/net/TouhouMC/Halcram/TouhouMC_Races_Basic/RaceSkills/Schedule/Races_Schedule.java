/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Schedule.Races_Schedule$1
 *  net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Schedule.Races_Schedule$2
 *  net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Schedule.Races_Schedule$3
 *  net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule;

import java.io.File;
import java.util.List;
import java.util.UUID;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule.Races_Schedule;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Races_Schedule {
    public static File config = TouhouMC_Races_Basic.configfile;
    public static FileConfiguration conf = TouhouMC_Races_Basic.conf;

    public void run1(final Plugin plugin0, final String thrpre0) {
    	new BukkitRunnable() {
			public void run() {
				for (final Player player : Bukkit.getOnlinePlayers()){
					if (player.hasMetadata("anti_chain"))
					{
						plugin0.getServer().getScheduler().scheduleSyncDelayedTask(plugin0, new Runnable()
						{
							public void run()
							{
								player.removeMetadata("anti_chain", plugin0);
							}
					
						}, 10L);
					}
					if ((conf.getDouble("user." + player.getUniqueId() + ".spilit") < 500.0D) && ((player.getMetadata("spilituse").get(0)).asDouble() == 0.0D)){
						conf.set("user." + player.getUniqueId() + ".spilit", Double.valueOf(conf.getDouble("user." + player.getUniqueId() + ".spilit") + 1.0D));
						if (player.isSneaking() && conf.getDouble("user." + player.getUniqueId() + ".spilit") % 5 == 0) player.sendMessage(thrpre0 + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
						if (conf.getDouble("user." + player.getUniqueId() + ".spilit") >= 500.0D) player.sendMessage(thrpre0 + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
					}else if ((conf.getDouble("user." + player.getUniqueId() + ".spilit") < 500.0D) && ((player.getMetadata("spilituse").get(0)).asDouble() < 0.0D)){
						conf.set("user." + player.getUniqueId() + ".spilit", Double.valueOf(conf.getDouble("user." + player.getUniqueId() + ".spilit") - (player.getMetadata("spilituse").get(0)).asDouble()));
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0F, -1.0F);
						if (player.isSneaking() && conf.getDouble("user." + player.getUniqueId() + ".spilit") % 5 == 0) player.sendMessage(thrpre0 + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
					}else if ((conf.getDouble("user." + player.getUniqueId() + ".spilit") > 0.0D) && ((player.getMetadata("spilituse").get(0)).asDouble() > 0.0D)){
						conf.set("user." + player.getUniqueId() + ".spilit", Double.valueOf(conf.getDouble("user." + player.getUniqueId() + ".spilit") - (player.getMetadata("spilituse").get(0)).asDouble()));
						if (player.isSneaking() && conf.getDouble("user." + player.getUniqueId() + ".spilit") % 5 == 0) player.sendMessage(thrpre0 + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
					}
					if (player.hasPermission("thr.skill")){
						if (!player.hasMetadata("ignoreskill")) {
							if ((player.hasMetadata("satorin0")) && (player.isSneaking())){
								Player dpl = Bukkit.getPlayer((player.getMetadata("satorin0").get(0)).asString());
								if (dpl != null){
									player.sendMessage("���O:" + (player.getMetadata("satorin0").get(0)).asString());
									player.sendMessage("�̗�:" + dpl.getHealth());
									player.sendMessage("���W:" + dpl.getLocation().getBlockX() + "," + dpl.getLocation().getBlockY() + "," + dpl.getLocation().getBlockZ());
								}
							}
						}

					}
				}
			}
		}.runTaskTimer(plugin0, 0, 10L);
    }

    public void run2(Plugin plugin0, final String thrpre0) {
    	new BukkitRunnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()){
					String race = conf.getString("user." + player.getUniqueId() + ".race").toString();
					if (race.contains("sukimayou") || (race.contains("yamakappa")) || (race.contains("karasutenngu")) || (race.contains("syoukaitenngu")) || (race.contains("youma")) || (race.contains("kappa")) || (race.contains("tenngu")) || (race.contains("kennyou"))){
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 0));
			            if(race.contains("kappa")){
			                player.removePotionEffect(PotionEffectType.WATER_BREATHING);
			                player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000, 0));
			            }
					}
					if (race.contains("egosatori") ||  (race.contains("satori")) ||race.contains("kinnima") || race.contains("kairikirannsin") || (race.contains("akuma")) || (race.contains("kyuuketuki")) || (race.contains("oni"))){
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000, 0));
					}
					if (race.contains("daiyousei") || race.contains("hyouketuyousei") || race.contains("zigokuyousei") || race.contains("kyozin") || race.contains("sikiyou") || (race.contains("yousei")) || (race.contains("kobito")) || (race.contains("kibito"))){
						player.removePotionEffect(PotionEffectType.JUMP);
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000, 1));
					}
					if (race.contains("gyokuto") || race.contains("sinnzyuu") || race.contains("kyuubi") || race.contains("ryuugyo") || (race.contains("youzyuu")) || (race.contains("siki")) || (race.contains("zyuuzin")) || (race.contains("ninngyo"))) {
						if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 0));
						}
						if(race.contains("ryuugyo") || race.contains("ninngyo")){
							player.removePotionEffect(PotionEffectType.WATER_BREATHING);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000, 0));
						}
					}
					if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ||race.equalsIgnoreCase("sinnrei")||race.equalsIgnoreCase("saigyouyou")||race.equalsIgnoreCase("zibakurei")||race.equalsIgnoreCase("kyourei")) {
						player.removePotionEffect(PotionEffectType.LUCK);
						player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 2000, 0));
					}
					if ((race.contains("gyokuto"))  || (race.contains("sinnzyuu"))  || (race.contains("zyuuzin")) ){
						if ((player.getWorld().getTime() >= 16000L))
						{
							if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
							}
							player.removePotionEffect(PotionEffectType.JUMP);
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2000, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2000, 0));
						}
					}
					if ((race.contains("gyokuto"))  || (race.contains("sinnzyuu"))  || (race.contains("zyuuzin")) ){
						if((player.getWorld().getTime() >= 16000L) && (player.getWorld().getTime() < 16040L))
						{
							player.sendMessage(thrpre0 + ChatColor.RED + "���Ȃ��͏b�̌����Ăъo�܂����I�I");
							player.playSound(player.getLocation(), Sound.ENTITY_WOLF_DEATH, 1.0F, -1.0F);
						}
					}
					if ((race.contains("kinnima")) || (race.contains("kyuuketuki"))){
						if ( (player.getWorld().getTime() >= 14000L))
						{
							player.removePotionEffect(PotionEffectType.NIGHT_VISION);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
							}
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
						
					}else{
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000, 0));
					}
					}
				}
			}
		}.runTaskTimer(plugin0, 0, 40L);
    }

    public void run3(final Plugin plugin0, String thrpre0) {
		new BukkitRunnable() {
			public void run() {
				List<World> worlds = Bukkit.getWorlds();
				for (World world : worlds){
					for (Bat bat : Bukkit.getWorld(world.getName()).getEntitiesByClass(Bat.class)) {
						if (bat.hasMetadata("invincible")){
							List<Entity> entityforsyugorei = bat.getNearbyEntities(20.0D, 20.0D, 20.0D);
							for (Entity entity : entityforsyugorei) {
								if ((entity instanceof Player)){
									bat.setVelocity(bat.getVelocity().add(new Vector(new Double(20.0D - (bat.getLocation().getX() - entity.getLocation().getX())).doubleValue() / 16.0D, 0.0D, new Double(20.0D - (bat.getLocation().getZ() - entity.getLocation().getZ())).doubleValue() / 16.0D)));
									break;
								}
							}
						}
					}
					for (final Snowman snowman : Bukkit.getWorld(world.getName()).getEntitiesByClass(Snowman.class)) {
						if (snowman.hasMetadata("syugoreisnow")){
							if (snowman.hasMetadata("syugoreitarget")){
								List<Entity> entityforsyugorei = snowman.getNearbyEntities(20.0D, 20.0D, 20.0D);
								for (Entity entity : entityforsyugorei) {
									if ((entity instanceof Player)) {
										if (!((Player)entity).getName().toString().contains((snowman.getMetadata("syugoreitarget").get(0)).asString())){
											snowman.setTarget((LivingEntity)entity);
											break;
										}
									}
								}
							}
							plugin0.getServer().getScheduler().scheduleSyncDelayedTask(plugin0, new Runnable(){
								public void run(){
									snowman.damage(1000.0D);
								}
							}, 300L);
						}
					}
					for (final IronGolem irongolem : Bukkit.getWorld(world.getName()).getEntitiesByClass(IronGolem.class)) {
						if (irongolem.hasMetadata("syugoreiiron")){
							if (irongolem.hasMetadata("syugoreitarget")) {
								if (irongolem.getMetadata("syugoreitarget").get(0) != null){
									List<Entity> entityforsyugorei = irongolem.getNearbyEntities(20.0D, 20.0D, 20.0D);
									for (Entity entity : entityforsyugorei) {
										if ((entity instanceof Player)) {
											if (!((Player)entity).getName().toString().contains((irongolem.getMetadata("syugoreitarget").get(0)).asString())){
												irongolem.setTarget((LivingEntity)entity);
												break;
											}
										}
									}
								}
							}
							plugin0.getServer().getScheduler().scheduleSyncDelayedTask(plugin0, new Runnable(){
								public void run(){
									irongolem.damage(1000.0D);
								}
							}, 300L);
						}
					}
					for (Wolf wolf : Bukkit.getWorld(world.getName()).getEntitiesByClass(Wolf.class)) {
						if (wolf.hasMetadata("tamedwolf")) {
							if (wolf.hasMetadata("wolfowner")){
								String owner = (wolf.getMetadata("wolfowner").get(0)).asString();
								for (Entity enemy : wolf.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
									if ((enemy instanceof LivingEntity)) {
										if ((enemy instanceof Player)){
											if (!((Player)enemy).getUniqueId().toString().contains(owner)){
												wolf.setTarget((LivingEntity)enemy);
												break;
											}
										}else if (!(enemy instanceof Wolf)){
											wolf.setTarget((LivingEntity)enemy);
											break;
										}
									}
								}
							}
						}
					}
					for (Ocelot cat : Bukkit.getWorld(world.getName()).getEntitiesByClass(Ocelot.class)) {
						if (cat.hasMetadata("tamedcat")) {
							if (cat.hasMetadata("catowner")){
								String owner = (cat.getMetadata("catowner").get(0)).asString();
								for (Entity enemy : cat.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
									if ((enemy instanceof LivingEntity)) {
										if ((enemy instanceof Player)){
											if (!((Player)enemy).getUniqueId().toString().contains(owner)){
												cat.setTarget((LivingEntity)enemy);
												cat.teleport((LivingEntity)enemy);
												break;
											}
										}else if (!(enemy instanceof Ocelot)){
											cat.setTarget((LivingEntity)enemy);
											cat.teleport((LivingEntity)enemy);
											break;
										}
									}
								}
							}
						}
					}
					for (Creeper creeper : Bukkit.getWorld(world.getName()).getEntitiesByClass(Creeper.class)) {
						if (creeper.hasMetadata("yamagappa_creeper")) {
							UUID owner = UUID.fromString((creeper.getMetadata("yamagappa_creeper").get(0)).asString());
							for (Entity enemy : creeper.getNearbyEntities(20.0D, 20.0D, 20.0D)) {
								if ((enemy instanceof LivingEntity)) {
									if ((enemy instanceof Player)){
										if (!((Player)enemy).getUniqueId().toString().equals(owner)){
											creeper.setTarget((LivingEntity)enemy);
											creeper.setVelocity(creeper.getVelocity().setY(0.5D));
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(plugin0, 0, 100L);
    }
}