package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

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
import org.bukkit.util.Vector;

public class Races_NNG extends JavaPlugin {
	///�ړ��X�L���n
	//��l�̕ǔ���
	public static void sennnin_passthough(Player pl, Plugin plugin){
		float pitch = pl.getLocation().getPitch();
		float yaw = pl.getLocation().getYaw();
		Location warploc = new Location (pl.getWorld(),pl.getLocation().getX() + pl.getLocation().getDirection().getX() * 2,pl.getLocation().getY() + pl.getLocation().getDirection().getY() * 2,pl.getLocation().getZ() + pl.getLocation().getDirection().getZ() * 2);
		if (pl.getWorld().getBlockAt(warploc).getType() != Material.AIR){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_HURT, 2, 0);
		}else{
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 1);
			pl.getWorld().playEffect(pl.getLocation(), Effect.COLOURED_DUST, 1, 5);
			warploc.setPitch(pitch);
			warploc.setYaw(yaw);
			pl.teleport(warploc);
			plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
			plugin.saveConfig();
		}
	}

	//�U���X�L���n
	//���@
	//�y���@
	public static void magic_dirt(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.YELLOW + "�y�̖��@���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0F, -1.0F);
		List<Entity> enemys = pl.getNearbyEntities(12.0D, 12.0D, 12.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity)) && (enemy.isOnGround())){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((LivingEntity)enemy).damage(25.0D);
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_HORSE_HURT, 1.0F, 0.0F);
			}}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 60L);
	}

	//�����@
	public static void magic_wind(final Player pl, final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GREEN + "���̖��@���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
		pl.setVelocity(pl.getVelocity().add(new Vector(0.5D, 3.0D, 0.5D)));
		pl.setFallDistance(-40.0F);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 60L);
	}

	//�Ζ��@
	public static void magic_fire(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�΂̖��@���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1, 0);
		Location location =pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw=location.getYaw() / 180.0F * 3.1415927F ;
		double motX=-Math.sin(yaw) * Math.cos(pitch);
		double motZ=Math.cos(yaw) * Math.cos(pitch);
		double motY=-Math.sin(pitch);
		Vector velocity=new Vector(motX,motY,motZ).multiply(2D);
		Snowball snowball= (Snowball) pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.SNOWBALL);
		MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString()) ;
		MetadataValue fireeffect = new FixedMetadataValue(plugin, 30D) ;
		snowball.setMetadata("fireeffect", fireeffect);
		snowball.setMetadata("mazyo-fireball", shooter);
		snowball.setVelocity(velocity);
		snowball.setFireTicks(300);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		},80L);
	}

	//�����@
	public static void magic_water(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GREEN + "���̖��@���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_MAGMACUBE_JUMP, 1, 0);
		List<Entity> enemys=pl.getNearbyEntities(8D, 8D, 8D);
		enemys.add(pl);
		for (Entity enemy : enemys){
			if (enemy instanceof LivingEntity && enemy.isDead() == false){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (no_damage)
				{
				if (((LivingEntity) enemy).getHealth() + 12D > ((LivingEntity) enemy).getMaxHealth()){
					((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getMaxHealth());
					enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
				}else{
					((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getHealth() + 12D);
					enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
				}
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}
		, 180L);
	}

	//�����@
	public static void magic_thunder(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.DARK_PURPLE + "���̖��@���������I");
		pl.setNoDamageTicks(20);
		pl.getWorld().spawnEntity(pl.getLocation(), EntityType.LIGHTNING);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		},180L);
	}

	//�������@
	public static void magic_heal(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�_���\�����I");
		pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.YELLOW + "���Ȏ������g�����I");
				pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				if (pl.getHealth() + 8D > pl.getMaxHealth()){
					pl.setHealth(pl.getMaxHealth());
				}else{
					pl.setHealth(pl.getHealth() + 8D);
				}
				pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
				MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
				pl.setMetadata("using-magic", usingmagic);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
					  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
					  pl.setMetadata("using-magic", usingmagic);
					  pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
					}
				}, 20L);
				MetadataValue casted = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("casting", casted);
			}
		});
	}

	//TODO ���E�l
	
	//�s�b�P�������e�I���@
	public static void magic_meteor(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.YELLOW + "���e�I���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.0F);
		List<Entity> enemys = pl.getNearbyEntities(32.0D, 32.0D, 32.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity)) && (enemy.isOnGround())){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				Location location =enemy.getLocation();
				Vector velocity=new Vector(0D,-3D,0D);
				Entity firevall =pl.getWorld().spawnEntity(location.add(0D, 32D, 0D), EntityType.FIREBALL);
				MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString()) ;
				MetadataValue fireeffect = new FixedMetadataValue(plugin, 30D) ;
				firevall.setMetadata("meteoreffect", fireeffect);
				firevall.setMetadata("makizin-meteor", shooter);
				firevall.setVelocity(velocity);
				firevall.setFireTicks(300);
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 180L);
	}

	//�V���x�����t�F�U�[�t���C���@
	public static void magic_flyfeather(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.YELLOW + "�t�F�U�[�t���C���������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0F, 0.0F);
		pl.setAllowFlight(true);
		pl.setFlying(true);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.setFlying(false);
				pl.setAllowFlight(false);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̌��ʂ��I���܂���");
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
						MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
						pl.setMetadata("using-magic", usingmagic);
						pl.setFlying(false);
						pl.setAllowFlight(false);
						pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
					}
				}, 200L);
			}
		}, 160L);
	}
	
	//�����^�C�t�[��
	public static void magic_tyhoon(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GREEN + "�^�C�t�[�����������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 3, 1);
		List<Entity> enemys=pl.getNearbyEntities(20D, 20D, 20D);
		for (Entity enemy : enemys){
			if (enemy instanceof LivingEntity && enemy.isDead() == false){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(Math.random() * 3,Math.random() * 3,Math.random() * 3)));
				}
			}
		}
		MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}
		, 150L);
	}
	
	//�L���C���r���V�u�����@
	public static void magic_invincible(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�C���r���V�u�����������I");
		pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 0);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.YELLOW + "���G�ɂȂ����I�I");
				pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_STARE, 1, 0);
				pl.setNoDamageTicks(200);
				MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
				pl.setMetadata("using-magic", usingmagic);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
					public void run(){
					  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
					  pl.setMetadata("using-magic", usingmagic);
					  pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̌��ʋy�уN�[���_�E�����I���܂���");
					}
				}, 200L);
				MetadataValue casted = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("casting", casted);
			}
		}, 60L);
	}
	//TODO �����l
	//�g���̂ă��[�v�Q�[�g�ݒu
	public static void mugen_one_gate_put(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "���ݒl�ɖ����̋��Ԃ�������I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_AMBIENT, 1, 2);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		
		MetadataValue mugen_locw = new FixedMetadataValue(plugin, pl.getWorld().getName());
		pl.setMetadata("mugen_locw", mugen_locw);
		MetadataValue mugen_locx = new FixedMetadataValue(plugin, pl.getLocation().getBlockX());
		pl.setMetadata("mugen_locx", mugen_locx);
		MetadataValue mugen_locy = new FixedMetadataValue(plugin, pl.getLocation().getBlockY());
		pl.setMetadata("mugen_locy", mugen_locy);
		MetadataValue mugen_locz = new FixedMetadataValue(plugin, pl.getLocation().getBlockZ());
		pl.setMetadata("mugen_locz", mugen_locz);
		
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 180L);
	}
	//�g���̂ă��[�v�Q�[�g����
	public static void mugen_one_gate_use(final Player pl, final Plugin plugin) {
		MetadataValue casting = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("casting", casting);
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�����̋��Ԃ��g�����I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_HURT, 1, 2);
		pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
				if (pl.hasMetadata("mugen_locw"))
				{
					if (pl.getMetadata("mugen_locw").get(0).asString().equals(pl.getWorld().getName())){
						pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 2);
						pl.teleport(new Location(pl.getWorld(), pl.getMetadata("mugen_locx").get(0).asDouble(),pl.getMetadata("mugen_locy").get(0).asDouble(),pl.getMetadata("mugen_locz").get(0).asDouble()));
					}
					else
					{
						pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1, 2);
						pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�������Ⴄ���󐢊E�ɂ͂Ȃ���Ȃ������I�I");
					}
					pl.removeMetadata("mugen_locw", plugin);
					pl.removeMetadata("mugen_locx", plugin);
					pl.removeMetadata("mugen_locy", plugin);
					pl.removeMetadata("mugen_locz", plugin);
					pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GOLD + "�����̋��Ԃ��������I");
				}
				else
				{
					pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1, 2);
					pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "���������Ԃ�����Ă��Ȃ������I");
				}
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̌��ʂƃN�[���_�E�����I���܂���");
			}
		}, 100L);
	}
	
	//TODO ���l
	//�h�����A�b�v
	public static void tukibito_regen_chance_boost(final Player pl, final Plugin plugin, PlayerInteractEvent e) {
		pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.GREEN + "���g�̑h���m����啝�ɏグ���I�I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0);
		MetadataValue regen_chance_boost = new FixedMetadataValue(plugin, true) ;
		pl.setMetadata("regen_chance_boost", regen_chance_boost);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}
		, 60L);
	}
	
	////�p�b�V�u�X�L���n
	///�G���e�B�e�B��p
	public static void sibito_deadattack(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (pl.getHealth() <= 20D){
			event.setDamage(event.getDamage() + 3D);
			event.getDamager().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_PIG_HURT, 1, 1);
			event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.TILE_BREAK, 49);
		}
	}

	public static void gennzinnsin_luckyattack(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (Math.random() > 0.7 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 35D){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
			event.setDamage(event.getDamage() + 5D);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
		}
	}
	//TODO ���l
	public static void seizin_luckydefence(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamage() > 1 && Math.random() > 0.7 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 35D){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
			event.setDamage(event.getDamage() - 3D);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 2);
		}
	}
	
	//TODO �V�l
	public static void tennzin_mazo(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamage() > 4 && Math.random() > 0.8 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 30D){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 10D);
			event.setDamage(event.getDamage() * -1);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 2);
		}
	}
	
	//TODO �Ñ�l
	public static void kodaizin_anti_chain(Player pl, final Plugin plugin, EntityDamageByEntityEvent event){
		if (event.getDamage() > 0 && plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 40D){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 1D);
			if (pl.hasMetadata("anti_chain"))
			{
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1, 1);
				event.setDamage(event.getDamage() / 2);
			}
			else
			{
				MetadataValue anti_chain = new FixedMetadataValue(plugin, true) ;
				pl.setMetadata("anti_chain", anti_chain);
			}
		}
	}
	
	//TODO 腖�
	public static void ennma_judgementattack(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		if (pl.getHealth() <= 5D){
			event.setDamage(event.getDamage() + 12D);
			event.getDamager().getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_PIG_ANGRY, 1, 2);
		}
	}
	///�G���e�B�e�B�E�u���b�N���p
	public static void houraizin_reverselife_Entity(Player pl, Plugin plugin, EntityDamageByEntityEvent event){
		double reverse = Math.random();
		double chance = 0.6;
		if (pl.hasMetadata("regen_chance_boost"))
		{
			chance = 0.2;
			pl.removeMetadata("regen_chance_boost", plugin);
		}
		if (event.getDamage() >= pl.getHealth() && reverse > chance){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
			pl.setHealth(pl.getMaxHealth());
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.AQUA + "�M���͕s���̗͂��g���h�����I�I");
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, -1);
			event.setDamage(0D);
		}
	}

	public static void houraizin_reverselife_block(Player pl, Plugin plugin, EntityDamageEvent event){
		double reverse = Math.random();
		if (event.getDamage() >= pl.getHealth() && reverse > 0.6){
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
			pl.setHealth(pl.getMaxHealth());
			pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.AQUA + "�M���͕s���̗͂��g���h�����I�I");
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, -1);
			event.setDamage(0D);
		}
	}
}