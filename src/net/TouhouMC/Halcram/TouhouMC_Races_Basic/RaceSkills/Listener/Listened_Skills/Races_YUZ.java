package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityMountEvent;

public class Races_YUZ extends JavaPlugin {
	//�����X�L���n
	//�T����(�d�b�n)
	public static void yuz_summon_wolf(final Player pl, final Plugin plugin, PlayerInteractEvent event){
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�T�̌Q����Ăяo���E�I�I�I�[��");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_WOLF_WHINE, 4.0F, -1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.BLAZE_SHOOT, 1, 1);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				int n = 0;
				while (n < 3){
					Entity wolf = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.WOLF);
					MetadataValue tamedwolf = new FixedMetadataValue(plugin, Boolean.valueOf(true));
					wolf.setMetadata("tamedwolf", tamedwolf);
					MetadataValue wolfowner = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
					wolf.setMetadata("wolfowner", wolfowner);
					n++;
				}
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_WOLF_GROWL, 1.0F, 1.0F);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�u�E�I���v�u�E�I���v�u�E�I���v");
			}
		}, 40L);
	}

	//�L����(��)
	public static void siki_summon_oc(final Player pl, final Plugin plugin, PlayerInteractEvent event){
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�����邵���L�B���Ăяo���j���A");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_AMBIENT, 4.0F, -1.0F);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				int n = 0;
				while (n < 3){
					Entity cat = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.OCELOT);
					MetadataValue tamedcat = new FixedMetadataValue(plugin, Boolean.valueOf(true));
					cat.setMetadata("tamedcat", tamedcat);
					MetadataValue catowner = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
					cat.setMetadata("catowner", catowner);
					n++;
				}
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0F, 1.0F);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�u�j���A�v�u�j���A�v�u�j���A�v");
			}
		}, 40L);
	}
	//TODO ���
	//�n����(��)
	public static void kyuubi_summon_horse(final Player pl, final Plugin plugin, PlayerInteractEvent event){
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "���V�������n���Ăяo���q�q�[��");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_HORSE_GALLOP, 4.0F, 0.0F);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				Entity horse = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.HORSE);
				MetadataValue tamedhorse = new FixedMetadataValue(plugin, Boolean.valueOf(true));
				horse.setMetadata("tamedhorse", tamedhorse);
				MetadataValue horseowner = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
				horse.setMetadata("horseowner", horseowner);
				horse.setCustomName(pl.getName() + "'s horse");
				pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0F, -1.0F);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�u�Ђア�I�H�i��j�v");
			}
		}, 100L);
	}
	
	//�n�̓���h�~
	public static void no_owner_horse(final Player pl, final Plugin plugin, EntityMountEvent event)
	{
		if (event.getMount() instanceof Horse && event.getEntity() instanceof Player)
		{
			if (event.getMount().hasMetadata("tamedhorse"))
			{
				if (!event.getMount().getMetadata("horseowner").get(0).asString().equalsIgnoreCase(event.getEntity().getUniqueId().toString()))
				{
					((Player)event.getEntity()).sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�n�͂��Ȃ������L�҂ł��邱�Ƃ�ے肵���I");
					event.setCancelled(true);
				}
			}
		}
	}
	
	//�U���X�L���n
	//��͏���ŋ���
	public static void youzyu_gainenergy(Player pl, final Plugin plugin, final PlayerInteractEvent event){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "��͂Ŏ��g�̔\�͑�����}�����I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_BURN, 1.0F, 1.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.MOBSPAWNER_FLAMES, 1, 1);
		double ram = Math.random();
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + ram);
		if (ram < 0.1D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "���s�I");
		}else if (ram < 0.2D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "�ړ��̑��x������ɏオ�����I");
			pl.removePotionEffect(PotionEffectType.SPEED);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
		}else if (ram < 0.3D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "�����͂��オ�����I");
			pl.removePotionEffect(PotionEffectType.JUMP);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 0));
		}else if (ram < 0.4D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�U��̑������オ�����I");
			pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 250, 0));
		}else if (ram < 0.5D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.LIGHT_PURPLE + "�y���Đ��\�͂𓾂��I");
			pl.removePotionEffect(PotionEffectType.REGENERATION);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 0));
		}else if (ram < 0.6D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "�}�]�Ȑ��_�𓾂�?��?");
			pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 250, 1));
		}else if (ram < 0.7D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "�T�]�Ȑ��_�𓾂��I");
			pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 0));
		}else if (ram < 0.8D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.AQUA + "�V��̗͂𓾂��I");
			if (pl.getWorld().isThundering()){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "�V�͗����̔@���͂������ĉ��������I");
				pl.removePotionEffect(PotionEffectType.SPEED);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
				pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
				pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
			}else if (pl.getWorld().hasStorm()){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.DARK_BLUE + "�V�͉J�̑����b�݂������ĉ��������I");
				pl.removePotionEffect(PotionEffectType.JUMP);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
				pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
				pl.removePotionEffect(PotionEffectType.REGENERATION);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
			}else{
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�V�͐��V�̋P�������������ĉ��������I");
				pl.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0));
				pl.removePotionEffect(PotionEffectType.HEAL);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 0, 10));
				pl.removePotionEffect(PotionEffectType.NIGHT_VISION);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 0));
			}
		}else if (ram < 0.9D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.UNDERLINE + "���̗͂𓾂��I");
			if (pl.getWorld().getTime() < 14000L){
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "���z�̃G�i�W�[�͋M���ɂ����܂����Đ��͂�^����I");
				pl.removePotionEffect(PotionEffectType.REGENERATION);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
			}else{
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "���������͂��Ȃ��̐S�����킷�ł��낤");
				pl.removePotionEffect(PotionEffectType.CONFUSION);
				pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 1));
			}
		}else if (ram < 1.0D){
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "���s�I");
		}else{
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "���s�I");
		}
		Object casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
		pl.setMetadata("casting", (MetadataValue)casting);
		MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
		pl.setMetadata("using-magic", usingmagic);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				Player pl = event.getPlayer();
				MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
				pl.setMetadata("using-magic", usingmagic);
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "�r���̃N�[���_�E���������܂���");
			}
		}, 300L);
	}

	////�A�N�u�X�L���n
	public static void ninngyo_swimming(Player pl, final Plugin plugin, int boost){
		///�ړ��X�L���n
		if (pl.getLocation().getBlock().getType() == Material.WATER || pl.getLocation().getBlock().getType() == Material.STATIONARY_WATER){
			if (boost == 1){
				pl.setVelocity(pl.getLocation().getDirection().multiply(2.0D));
			}else{
				pl.setVelocity(pl.getLocation().getDirection().multiply(0.4D));
			}
		}
	}
	
	//TODO �ʓe
	public static void gyokuto_ranged_attack(Player pl, Plugin plugin,EntityDamageByEntityEvent event){
		if (pl.getLocation().distanceSquared(event.getEntity().getLocation()) >= 12){
			event.setDamage(event.getDamage() + 2.0D);
			event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.SPLASH, 1);
		}
	}
	
	//TODO �_�b
	public static void sinnzyuu_voice(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "���Ȃ���K�I�I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_WOLF_GROWL, 5.0F, -1.0F);
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENTITY_WOLF_GROWL, 5.0F, 0.0F);
		List<Entity> enemys = pl.getNearbyEntities(50.0D, 50.0D, 50.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity))){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				((LivingEntity)enemy).setVelocity(new Vector (0,0,0));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_DONKEY_HURT, 1.0F, 1.0F);
				}
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
		}, 120L);
	}
	
	//TODO ����
	public static void ryuugyo_volt(final Player pl,final Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�d���𐅏�ɗ������I");
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 2.0F, 2.0F);
		pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 2.0F, 1.0F);
		List<Entity> enemys = pl.getNearbyEntities(50.0D, 50.0D, 50.0D);
		for (Entity enemy : enemys) {
			if (((enemy instanceof LivingEntity)) ){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				if (enemy.getLocation().getBlock().getType() == Material.WATER || enemy.getLocation().getBlock().getType() == Material.STATIONARY_WATER)
				{
					((LivingEntity)enemy).damage(10D);
					if (enemy instanceof Player ) ((Player)enemy).sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "�r���r���I�I");
					enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_ZOMBIE_HORSE_HURT, 1.0F, 2.0F);
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
				pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
			}
		}, 40L);
	}
	
}