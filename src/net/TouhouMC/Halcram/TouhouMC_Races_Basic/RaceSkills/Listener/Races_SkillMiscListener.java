package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener;

import java.io.File;
import java.util.Random;
import java.util.UUID;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_SkillMiscListener implements Listener {

	static File file = TouhouMC_Races_Basic.configfile;
	static FileConfiguration conf = TouhouMC_Races_Basic.conf;

	public Races_SkillMiscListener(TouhouMC_Races_Basic thrplugin){
		thrplugin.getServer().getPluginManager().registerEvents(this, thrplugin);
	}
	//メテオ弾
	//爆発地点7D以内のシューター以外の敵に３５ダメージ
	@SuppressWarnings({ "unused", "deprecation" })
	@EventHandler
	public void firevall_expplosion(EntityExplodeEvent e){
		if(e.getEntity().hasMetadata("meteoreffect"))
		{
			for (Entity victim : e.getEntity().getNearbyEntities(7D, 7D, 7D))
			{
				if (victim instanceof LivingEntity)
				{
					if (Bukkit.getPlayer(UUID.fromString(e.getEntity().getMetadata("makizin-meteor").get(0).asString()) ) != null)
						{
							if (!victim.getUniqueId().equals(UUID.fromString(e.getEntity().getMetadata("makizin-meteor").get(0).asString()))){
								EntityDamageByEntityEvent firevalldamage = new EntityDamageByEntityEvent(Bukkit.getPlayer(UUID.fromString(e.getEntity().getMetadata("makizin-meteor").get(0).asString())), victim, DamageCause.ENTITY_EXPLOSION, 35D);
							}
						}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onSkillDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(e.getDamager() instanceof Snowball){
				Entity damagerentity = e.getDamager();
				Snowball snowball = (Snowball)damagerentity;
				if (snowball.hasMetadata("seirei-lightball")) {
					//精霊弾
					Player shooter = Bukkit.getServer().getPlayer(UUID.fromString(snowball.getMetadata("seirei-lightball").get(0).asString()));
					boolean no_damage = false;
					if (shooter instanceof Player)
					{
						no_damage = shooter == p;
					}
					if (!no_damage)
					{
						e.setDamage(6.0D);
					}
				}else if(snowball.hasMetadata("kappa-yukidama")){
					Player shooter = Bukkit.getServer().getPlayer(UUID.fromString(snowball.getMetadata("seirei-lightball").get(0).asString()));
					boolean no_damage = false;
					if (shooter instanceof Player)
					{
						no_damage = shooter == p;
					}
					if (!no_damage)
					{
						e.setDamage(20);
						p.sendMessage(((Player)snowball.getShooter()).getName() + "からの攻撃を受けた！");
					}
				}else if(snowball.hasMetadata("fireeffect")){
					Player shooter = Bukkit.getServer().getPlayer(UUID.fromString(snowball.getMetadata("seirei-lightball").get(0).asString()));
					boolean no_damage = false;
					if (shooter instanceof Player)
					{
						no_damage = shooter == p;
					}
					if (!no_damage)
					{
						e.setDamage(15);
						p.setFireTicks(160);
					}
				}else if(snowball.hasMetadata("saigyouyou-deathball")){
					Player shooter = Bukkit.getServer().getPlayer(UUID.fromString(snowball.getMetadata("seirei-lightball").get(0).asString()));
					boolean no_damage = false;
					if (shooter instanceof Player)
					{
						no_damage = shooter == p;
					}
					if (!no_damage)
					{
						p.sendMessage(((Player)snowball.getShooter()).getName() + "に死へ誘われる！！");
						p.setNoDamageTicks(0);
						e.setDamage(999);
					}
				}else if (snowball.hasMetadata("hannrei-curseball")) {
					Player shooter = Bukkit.getServer().getPlayer(UUID.fromString(snowball.getMetadata("seirei-lightball").get(0).asString()));
					boolean no_damage = false;
					if (shooter instanceof Player)
					{
						no_damage = shooter == p;
					}
					if (!no_damage)
					{
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 3));
						p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 150, 3));
						if (((e.getEntity() instanceof Player)) && (Bukkit.getPlayer(UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString())) != null)){
							if (conf.getInt("user." + p.getUniqueId() + ".spilit") >= 30){
								conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + 30.0D));
								conf.set("user." + p.getUniqueId() + ".spilit", Double.valueOf(conf.getInt("user." + p.getUniqueId() + ".spilit") - 30.0D));
								if (conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
									conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
									TouhouMC_Races_Basic.SavethraceConfig();
								}
							}else{
								conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Integer.valueOf(conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + conf.getInt("user." + p.getUniqueId() + ".spilit")));
								conf.set("user." + p.getUniqueId() + ".spilit", Integer.valueOf(0));
								if (conf.getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
									conf.set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
									TouhouMC_Races_Basic.SavethraceConfig();
								}
							}
							p.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_PURPLE + "霊力を吸い取られた！！！");
						}
					}
				}else if((Player)e.getEntity() == snowball.getShooter()){
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e){
		Entity ent = e.getEntity();
		if(ent.getType().equals(EntityType.PRIMED_TNT)){
			if(ent.hasMetadata("kappa-tnt")){
				final World world = e.getLocation().getWorld();
				final Location loc = e.getLocation();
				e.setCancelled(true);
				world.createExplosion(loc, 0.0F);
				final Player shooter = Bukkit.getServer().getPlayer(ent.getMetadata("kappa-tnt").get(0).asString());
				for (int shot = 200; shot > 0; shot--){
					int x = new Random().nextInt(90) - 45;
					int y = new Random().nextInt(70) - 45;
					int z = new Random().nextInt(90) - 45;
					Snowball snowball = world.spawn(loc, Snowball.class);
					snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, shooter.getUniqueId().toString()));
					snowball.setShooter(shooter);
					Vector vectory = new Vector(x, y, z);
					snowball.setVelocity(vectory);
				}
				Bukkit.getScheduler().runTaskLater(TouhouMC_Races_Basic.plugin0, new Runnable() {
					public void run() {
						world.createExplosion(loc, 0.0F);
						for (int shot = 200; shot > 0; shot--){
							int x = new Random().nextInt(90) - 45;
							int y = new Random().nextInt(70) - 45;
							int z = new Random().nextInt(90) - 45;
							Snowball snowball = world.spawn(loc, Snowball.class);
							snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, shooter.getUniqueId().toString()));
							snowball.setShooter(shooter);
							Vector vectory = new Vector(x, y, z);
							snowball.setVelocity(vectory);
						}
					}
				}, 5);
				Bukkit.getScheduler().runTaskLater(TouhouMC_Races_Basic.plugin0, new Runnable() {
					public void run() {
						world.createExplosion(loc, 0.0F);
						for (int shot = 170; shot > 0; shot--){
							int x = new Random().nextInt(90) - 45;
							int y = new Random().nextInt(70) - 45;
							int z = new Random().nextInt(90) - 45;
							Snowball snowball = world.spawn(loc, Snowball.class);
							snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, shooter.getUniqueId().toString()));
							snowball.setShooter(shooter);
							Vector vectory = new Vector(x, y, z);
							snowball.setVelocity(vectory);
						}
					}
				},10);
			}
		}
	}
}