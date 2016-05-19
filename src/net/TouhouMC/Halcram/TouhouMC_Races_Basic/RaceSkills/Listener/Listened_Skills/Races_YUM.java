package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Races_YUM extends JavaPlugin {
	////�A�N�e�B�u�X�L���n
	///�ړ��X�L���n
	public static void tenngu_kamikaze(Player pl, int boost){
		if (boost > 0 && boost <= 2){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 0.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(5.0D));
			//pl.setFallDistance(-30F);
			//�C�x���g���X�i�[�ֈڍs (EntityDamageEvent)
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 0.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, -1.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(10.0D));
			//pl.setFallDistance(-10F);
			//�C�x���g���X�i�[�ֈڍs (EntityDamageEvent)
		}
	}

	///�U���X�L���n
	public static void youma_golden_shockwave(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "���̕��őS�Ă𐁂���΂��I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1.0F, 0.0F);
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double(enemy.getLocation().getX() - pl.getLocation().getX()).doubleValue(), 0.0D, new Double(enemy.getLocation().getZ() - pl.getLocation().getZ()).doubleValue())));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
			}}
		}
	}

	public static void kappa_stone_tnt(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�΂̕���TNT�𓊂����I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_LINGERINGPOTION_THROW, 1.0F, 0.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        TNTPrimed tnt = (TNTPrimed)pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getName());
        tnt.setMetadata("kappa-tnt", shooter);
        tnt.setVelocity(velocity);
        tnt.setIsIncendiary(true);
        tnt.setFireTicks(20);
        tnt.setFuseTicks(20);
	}

	public static void youma_wooden_upper(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "���Œn�ʂ�@���グ���I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				boolean no_damage = false;
				if (enemy instanceof Player)
				{
					no_damage = enemys == pl;
				}
				if (!no_damage)
				{
				enemy.setVelocity(enemy.getVelocity().add(new Vector(0.0D, 1.5D, 0.0D)));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0F, 0.0F);
				}
			}
		}
	}
	
	//TODO �V��̗����ϐ�
	public static void tenngu_toramporin(Player pl, final Plugin plugin, EntityDamageEvent e){
		if(pl.isSneaking()){
			double rnd = Math.random();
			if (rnd > 0.5D){
				if (rnd > 0.4D){
					e.setCancelled(true);
				}else {
					e.setDamage(e.getDamage() / 10.0D);
				}
			}else {
				e.setDamage(e.getDamage() / 6.0D);
			}
		}else {
			e.setDamage(e.getDamage() / 4.0D);
		}
	}
	
	//TODO �X�L�}�d
	public static void sukima_sukima_warp(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "�X�L�}���J�����g�̎E�l�ɏo����I");
		if (pl.getKiller() != null)
		{
		pl.teleport(pl.getKiller());
		pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1.0F, 2.0F);
		if (pl.getKiller() instanceof Player)
		{
			((Player)pl).getKiller().sendMessage("�n���[�L���[��");
			((Player)pl).getKiller().addPotionEffect(new PotionEffect (PotionEffectType.BLINDNESS , 100 , 1));
		}
		}
		else
		{
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "���Ȃ������I");
		}
	}
	
	//TODO �R�͓�
	public static void yamakappa_thown_creeper(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GREEN + "�����I�N���[�p�[�I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_CREEPER_DEATH, 1.0F, 1.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        Creeper creeper = (Creeper)pl.getWorld().spawnEntity(location, EntityType.CREEPER);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getUniqueId().toString());
        creeper.setMetadata("yamagappa_creeper", shooter);
        creeper.setVelocity(velocity);
        creeper.setNoDamageTicks(1000);
        creeper.setFireTicks(20);
        creeper.setPowered(true);
	}
	
	//TODO ��V��
	@SuppressWarnings("deprecation")
	public static void karasutenngu_hopping(Player pl, final Plugin plugin, int boost){
		///�ړ��X�L���n
		if (!pl.isOnGround()){
			if (boost > 0 && boost <= 2){
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(0.5D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.5D).getZ()));
			}else{
				pl.setVelocity(new Vector(pl.getLocation().getDirection().multiply(0.9D).getX(),pl.getVelocity().getY(),pl.getLocation().getDirection().multiply(0.9D).getZ()));
			}
		}
	}
	
	//TODO �����V��
	public static void syoukaitenngu_scent(Player pl, final Plugin plugin, int boost){
			double scentarea;
			if (boost == 1){
				scentarea = 40D;
			}else{
				scentarea = 20D;
			}
			List<Entity> enemys=pl.getNearbyEntities(scentarea, scentarea, scentarea);
			for (Entity enemy : enemys){
				if (enemy instanceof Player && enemy.isDead() == false){
					((Player) enemy).getWorld().playEffect(((Player) enemy).getLocation().add(0D, 6D,0D), Effect.NOTE, 1);
				}
			}
	}
}