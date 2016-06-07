package net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener;

import java.io.File;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Races_Global;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_AKM;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_NNG;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_SIR;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_KAM;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUM;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUS;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUZ;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/*PVP�ǉ��n���X�i�[*/
public class Races_EventActionListener2 implements Listener {
	static String pluginpre = TouhouMC_Races_Basic.thrace_Races_pre;
	public static File config = TouhouMC_Races_Basic.configfile;
	//config�̌Ăяo���͂���𐄏�
	static File file = TouhouMC_Races_Basic.configfile;
	static FileConfiguration conf = TouhouMC_Races_Basic.conf;

	//�R���X�g���N�^ ���X�i�[�o�^
	public Races_EventActionListener2(TouhouMC_Races_Basic plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/*�C�x���g������������*/
	//�`���b�g����
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onAsyncChat(final AsyncPlayerChatEvent e){
		//�O�u���Ɏ푰����������
		Player pl = e.getPlayer();
		String format = e.getFormat();
		if(conf.getString("user." + pl.getUniqueId()) != null)
		{
			boolean existrace = false;
			String inforace = "";
			for (String race : conf.getConfigurationSection("race").getKeys(false)) {
				if (race.toLowerCase().contains(conf.getString("user." + pl.getUniqueId() + ".race"))){
					existrace = true;
					inforace = race;
					break;
				}
			}
			String race;
			if (existrace){
				race = conf.getString("race." + inforace + ".tag");
			}else {
				race = conf.getString("user." + pl.getUniqueId() + ".race");
			}
			e.setFormat(race +  ChatColor.WHITE+ format);
		}
	}

	//�v���C���[�Q�����̏���
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player pl = e.getPlayer();
		//�V�K�o�^
		if (!conf.contains("user." + pl.getUniqueId())){
			conf.set("user." + pl.getUniqueId() + ".name" , pl.getName());
			conf.set("user." + pl.getUniqueId() + ".needpoint" , 0);
			conf.set("user." + pl.getUniqueId() + ".race" , "kedama");
			conf.set("user." + pl.getUniqueId() + ".spilit", 0);
			TouhouMC_Races_Basic.SavethraceConfig();
		}
		conf.set("user." + pl.getUniqueId() + ".spilit", 0);
		TouhouMC_Races_Basic.SavethraceConfig();
		//���^�����t�^
		MetadataValue casted = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, false) ;
		pl.setMetadata("casting", casted);
		MetadataValue usingmagic = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, false) ;
		pl.setMetadata("using-magic", usingmagic);
		MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 0) ;
		pl.setMetadata("spilituse", spilituse);
	}

	//�v���C���[�ޏo���̏���
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player pl = e.getPlayer();
		for (LivingEntity bat : pl.getWorld().getEntitiesByClass(Bat.class)) {
			if (bat.hasMetadata("invincible")) {
				if (pl.hasMetadata("batman")) {
					if (((MetadataValue)pl.getMetadata("batman").get(0)).asString().toString().contains(((MetadataValue)bat.getMetadata("invincible").get(0)).asString().toString())){
						bat.removeMetadata("invincible", TouhouMC_Races_Basic.plugin0);
						bat.damage(1000.0D);
					}
				}
			}
		}
		pl.setFlying(false);
		pl.setAllowFlight(false);
		if (pl.hasMetadata("batman")) pl.removeMetadata("batman", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("casting")) pl.removeMetadata("casting", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("using-magic")) pl.removeMetadata("using-magic", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("satorin0")) pl.removeMetadata("satorin0", TouhouMC_Races_Basic.plugin0);
		if (pl.getGameMode() == GameMode.SPECTATOR) pl.setGameMode(GameMode.SURVIVAL);
		if (pl.hasMetadata("freeze")) pl.removeMetadata("freeze", TouhouMC_Races_Basic.plugin0);
	}

	//�N���b�N�֘A�̏���(�ʏ�N���b�N)
	@EventHandler(ignoreCancelled = false)
	public void onPlayerInteract(final PlayerInteractEvent e){
		final Player pl = e.getPlayer();
		if (!pl.hasMetadata("vanished"))
		{
		Material handitem = pl.getInventory().getItemInMainHand().getType();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
			///�E�N��
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				///�O���[�o��
				Material dust_is_ok = pl.getInventory().getItemInMainHand().getType();
				if (dust_is_ok == Material.SUGAR || dust_is_ok == Material.SULPHUR || dust_is_ok == Material.GLOWSTONE_DUST){
					Races_Global.global_charge_mana(pl, TouhouMC_Races_Basic.plugin0, pluginpre, e);
				}
				//�l�Ԗ����̉񕜖��@�i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("mugennzin")||race.equalsIgnoreCase("makaizin")||race.equalsIgnoreCase("mazyo")||race.equalsIgnoreCase("ninngen") ) {
					mana = 50;
					if(handitem == Material.STICK && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_NNG.magic_heal(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
					if (race.equalsIgnoreCase("mugennzin")||race.equalsIgnoreCase("makaizin")||race.equalsIgnoreCase("mazyo"))
					{
						//�����̕����@�i�������ݗL�j�i�O�u���L(�r���L)
						mana = 70;
						if(handitem == Material.WOOD_SWORD && (pl.isSneaking())) {
							if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
								pl.setMetadata("casting", casting);
								conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
								TouhouMC_Races_Basic.SavethraceConfig();
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
								pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
								TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
									public void run() {
										Races_NNG.magic_wind(pl, TouhouMC_Races_Basic.plugin0);
										MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
										pl.setMetadata("casting", casting);
									}
								}, 10L);
							}
						}
						//�����̓y���@�i�������ݗL�j�i�O�u���L(�r���L)
						mana = 100;
						if (handitem == Material.STONE_SWORD && (pl.isSneaking())) {
							if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
								pl.setMetadata("casting", casting);
								conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
								TouhouMC_Races_Basic.SavethraceConfig();
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
								pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
								TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
									public void run() {
										Races_NNG.magic_dirt(pl, TouhouMC_Races_Basic.plugin0);
										MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
										pl.setMetadata("casting", casting);
									}
								}, 60L);
							}
						}
						//�����̉Ζ��@�i�������ݗL�j�i�O�u���L(�r���L)
						mana = 50;
						if (handitem == Material.IRON_SWORD && (pl.isSneaking())) {
							if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
								pl.setMetadata("casting", casting);
								conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
								TouhouMC_Races_Basic.SavethraceConfig();
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
								pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
								TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
									public void run() {
										Races_NNG.magic_fire(pl, TouhouMC_Races_Basic.plugin0, e);
										MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
										pl.setMetadata("casting", casting);
									}
								}, 20L);
							}
						}
						//�����̐����@�i�������ݗL�j�i�O�u���L(�r���L)
						mana = 150;
						if (handitem == Material.DIAMOND_SWORD && (pl.isSneaking())) {
							if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
								pl.setMetadata("casting", casting);
								conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
								TouhouMC_Races_Basic.SavethraceConfig();
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
								pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
								TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
									public void run() {
										Races_NNG.magic_water(pl, TouhouMC_Races_Basic.plugin0,e);
										MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
										pl.setMetadata("casting", casting);
									}
								}, 50L);
							}
						}
						//�����̗����@�i�������ݗL�j�i�O�u���L(�r���L)
						mana = 250;
						if (handitem == Material.GOLD_SWORD && (pl.isSneaking())) {
							if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
								pl.setMetadata("casting", casting);
								conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
								TouhouMC_Races_Basic.SavethraceConfig();
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
								pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
								TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
									public void run() {
										Races_NNG.magic_thunder(pl, TouhouMC_Races_Basic.plugin0, e);
										MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
										pl.setMetadata("casting", casting);
									}
								}, 70L);
							}
						}
						//TODO ���E�l
						if (race.equalsIgnoreCase("makaizin"))
						{
							//���E�l�̃��e�I���@�i�������ݗL�j�i�O�u���L(�r���L)
							mana = 400;
							if (handitem == Material.DIAMOND_PICKAXE && (pl.isSneaking())) {
								if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
									pl.setMetadata("casting", casting);
									conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
									TouhouMC_Races_Basic.SavethraceConfig();
									pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
									pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
									TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
										public void run() {
											Races_NNG.magic_meteor(pl, TouhouMC_Races_Basic.plugin0);
											MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
											pl.setMetadata("casting", casting);
										}
									}, 140L);
								}
							}
							//���E�l�̔�s���@�i�������ݗL�j�i�O�u���L(�r���L)
							mana = 250;
							if (handitem == Material.DIAMOND_SPADE && (pl.isSneaking())) {
								if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
									pl.setMetadata("casting", casting);
									conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
									TouhouMC_Races_Basic.SavethraceConfig();
									pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
									pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
									TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
										public void run() {
											Races_NNG.magic_flyfeather(pl, TouhouMC_Races_Basic.plugin0);
											MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
											pl.setMetadata("casting", casting);
										}
									}, 100L);
								}
							}
							//���E�l�̑䕗���@�i�������ݗL�j�i�O�u���L(�r���L)
							mana = 350;
							if (handitem == Material.DIAMOND_AXE && (pl.isSneaking())) {
								if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
									pl.setMetadata("casting", casting);
									conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
									TouhouMC_Races_Basic.SavethraceConfig();
									pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
									pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
									TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
										public void run() {
											Races_NNG.magic_tyhoon(pl, TouhouMC_Races_Basic.plugin0, e);
											MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
											pl.setMetadata("casting", casting);
										}
									}, 20L);
								}
							}
							//���E�l�̖��G���@�i�������ݗL�j�i�O�u���L(�r���L)
							mana = 400;
							if (handitem == Material.DIAMOND_HOE && (pl.isSneaking())) {
								if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
									pl.setMetadata("casting", casting);
									conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
									TouhouMC_Races_Basic.SavethraceConfig();
									pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
									pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
									TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
										public void run() {
											Races_NNG.magic_invincible(pl, TouhouMC_Races_Basic.plugin0);
											MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
											pl.setMetadata("casting", casting);
										}
									}, 40L);
								}
							}
						}
					}
				}
				//TODO ���l
				//���l�̑h���̓A�b�v�i�������ݗL�j�i�O�u���L(�r���L)
				mana = 100;
				if (race.equalsIgnoreCase("tukibito")) {
					if(handitem == Material.WATCH && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����I�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_NNG.tukibito_regen_chance_boost(pl, TouhouMC_Races_Basic.plugin0, e);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 200L);
						}
					}
				}
				///�V��_���������ݗL�j�i�O�u���L�i�u�[�X�^�[���L
				mana = 100;
				if (race.equalsIgnoreCase("tenngu") || race.equalsIgnoreCase("karasutenngu") || race.equalsIgnoreCase("syoukaitenngu") ) {
					if(conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana)
					{
						int boost = 0;
						if (pl.getMetadata("spilituse").get(0).asInt() > 0 && handitem == Material.FEATHER && (pl.isSneaking())){
							boost = pl.getMetadata("spilituse").get(0).asInt();
							Races_YUM.tenngu_kamikaze(pl, boost);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						}
					}
				}
				//�d�� �����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("youma") || race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("tenngu") || race.equalsIgnoreCase("kennyou") || race.equalsIgnoreCase("karasutenngu") || race.equalsIgnoreCase("syoukaitenngu") || race.equalsIgnoreCase("sukimayou") || race.equalsIgnoreCase("yamakappa")){
					mana = 40;
					if(handitem == Material.GOLD_AXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 1.0F);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
								public void run(){
									Races_YUM.youma_golden_shockwave(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
					mana = 40;
					if(handitem == Material.WOOD_AXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 1.0F);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
								public void run(){
									Races_YUM.youma_wooden_upper(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
				}
				if (race.equalsIgnoreCase("kappa"))
				{
					mana = 70;
					if(handitem == Material.STONE_AXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"TNT���\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 1.0F);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
								public void run(){
									Races_YUM.kappa_stone_tnt(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 20L);
						}
					}
				}
				//TODO �R�͓�
				else if (race.equalsIgnoreCase("yamakappa"))
				{
					mana = 120;
					if(handitem == Material.IRON_AXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�N���[�p�[���\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1.0F, 1.0F);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
								public void run(){
									Races_YUM.yamakappa_thown_creeper(pl,TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
				}
				//TODO �X�L�}�d
				if (race.equalsIgnoreCase("sukimayou"))
				{
					mana = 300;
					if(handitem == Material.DIAMOND_AXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�X�L�}���Ăяo���Ă���I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 1.0F, 1.0F);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
								public void run(){
									Races_YUM.sukima_sukima_warp(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 200L);
						}
					}
				}
				//TDO �����V��
				///�����V��@�m�������ݗL�j�i�O�u���L�i�u�[�X�^�[���L
				mana = 5;
				if (race.equalsIgnoreCase("syoukaitenngu") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
					int boost = 0;
					if (pl.getMetadata("spilituse").get(0).asInt() > 0 && handitem == Material.COMPASS && (pl.isSneaking())){
						boost = pl.getMetadata("spilituse").get(0).asInt();
						Races_YUM.syoukaitenngu_scent(pl,TouhouMC_Races_Basic.plugin0, boost);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SavethraceConfig();
						pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
					}
				}
				///��d���̃u���C���_�[�i�������ݗL�j�i�O�u���L(�r���L)
				mana = 50;
				if (race.equalsIgnoreCase("daiyousei") || race.equalsIgnoreCase("hyouketuyousei")){
					if(handitem == Material.STONE_SPADE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana, "�΂̑����f�����I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUS.daiyousei_illusion(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 10L);
						}
					}
				}
				mana = 70;
				///�d���̃C�����[�W�����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("kobito") || race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("kyozin") || race.equalsIgnoreCase("zigokuyousei") || race.equalsIgnoreCase("sikiyou")){
					if(handitem == Material.WOOD_SPADE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana, "�؂̑����f�����I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUS.yousei_illusion(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 30L);
						}
					}
				}
				///���l�̓ŎU�z�i�������ݗL�j�i�O�u���L(�r���L)
				mana = 100;
				if (race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("sikiyou")){
					if(handitem == Material.STONE_SPADE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�Ԃ͊J������I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, -1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUS.kibito_venom(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 45L);
						}
					}
				}
				//TODO �G�S�T�g��
				mana = 450;
				///�G�S�T�g���̊��S���Łi�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("egosatori")){
					if(handitem == Material.IRON_SPADE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana, "�S�̑����f�����I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUS.egosatori_vanished(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 30L);
						}
					}
				}
				//TODO �l�G�d
				///�l�G�d�̎��ŎU�z�i�������ݗL�j�i�O�u���L(�r���L)
				mana = 300;
				if (race.equalsIgnoreCase("sikiyou")){
					if(handitem == Material.GOLD_SPADE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�Ԃ͊J������I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, -1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUS.sikiyou_deadly_poison(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 90L);
						}
					}
				}
				//�X���d���̕X���@�i�������ݗL�j�i�O�u���L(�r���L)
				mana = 99;
				if (handitem == Material.DIAMOND_SPADE && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SavethraceConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUS.hyouketuyousei_cold(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 5L);
					}
				}
				///�z���S�̃J���t���[�W���i�������ݗL�j�i�O�u���L(�r���L)
				mana = 120;
				if (race.equalsIgnoreCase("kyuuketuki") || race.equalsIgnoreCase("kinnima")){
					if(handitem == Material.WOOD_PICKAXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�p��ς�����I")){
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BAT_HURT, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_AKM.kyuuketuki_vamp(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 5L);
						}
					}
				}
				mana = 60;
				//�g���@�i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("akuma")||race.equalsIgnoreCase("oni")||race.equalsIgnoreCase("kyuuketuki")  || race.equalsIgnoreCase("kinnima") || race.equalsIgnoreCase("kairikirannsin") || race.equalsIgnoreCase("kizin")|| race.equalsIgnoreCase("egosatori") || race.equalsIgnoreCase("satori") ) {
					if(handitem == Material.STONE_PICKAXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_AKM.akuma_red_magic(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 80L);
						}
					}
				}
				mana = 30;
				//TODO ���͗��_
				//�Ռ��g�i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("kairikirannsin") ) {
					if(handitem == Material.GOLD_PICKAXE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�Б����\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_IRONGOLEM_STEP, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_AKM.kairikirannsin_syougekiha(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 10L);
						}
					}
				}
				mana = 110;
				//����̏����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ||race.equalsIgnoreCase("sinnrei")||race.equalsIgnoreCase("saigyouyou")||race.equalsIgnoreCase("zibakurei")||race.equalsIgnoreCase("kyourei")) {
					if(handitem == Material.STONE_HOE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.seirei_summon(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 100L);
						}
					}
				}
				mana = 30;
				//����̌��e�i�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ||race.equalsIgnoreCase("sinnrei")||race.equalsIgnoreCase("saigyouyou")||race.equalsIgnoreCase("zibakurei")||race.equalsIgnoreCase("kyourei")) {
					if(handitem == Material.WOOD_HOE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�r���I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.seirei_lightball(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 30L);
						}
					}
				}
				mana = 150;
				//����̏����i�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("hannrei") || race.equalsIgnoreCase("saigyouyou")) {
					if(handitem == Material.GOLD_HOE && (pl.isSneaking()))
						{
						if(Races_Global.magic_iscastable(pl , mana,"�s���I����I") ){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_GHAST_WARN, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.hannrei_hannrei_ball(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 5L);
						}
					}
				}
				mana = 200;
				//����̃I�[�P�X�g���i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("sourei")) {
					if(handitem == Material.IRON_HOE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"���b�c�I�[�P�X�g���I�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.hannrei_hannrei_ball(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 120L);
						}
					}
				}
				//TODO �_��
				//�_��̐��E�����i�������ݗL�j�i�O�u���L(�r���L)
				else if (race.equalsIgnoreCase("sinnrei") ) {
					if(handitem == Material.IRON_HOE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.sinnrei_godsoul(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 200L);
						}
					}
				}
				mana = 0;
				//TODO ����
				//����̖��̎x�z�i�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("yumekui") ) {
					if(handitem == Material.DIAMOND_HOE && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"���̐��E�����[�h���Ă���B")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_SIR.yumekui_dreamy(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 100L);
						}
					}
				}
				mana = 50;
				//TODO �_�b
				//�_�b�̙��K(�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("sinnzyuu") ) {
					if(handitem == Material.SHEARS && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"���𐮂�")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_WOLF_WHINE, 1, 0);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.sinnzyuu_voice(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 5L);
						}
					}
				}
				mana = 120;
				//TODO �����_
				//�����_�̐_�y���W(�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("tyuuousin") ) {
					if(handitem == Material.SHEARS && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����_�̂��ȁ[��["))
						{
	
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							Races_KAM.onnbasira_circlefaith(pl, TouhouMC_Races_Basic.plugin0);
						}
					}
				}
				//TODO �y���_
				//�y���_�̐_�y�l��(�������ݗL�j(�r���L)
				else if (race.equalsIgnoreCase("dotyakusin") ) {
					if(handitem == Material.SHEARS && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�y���_�̂��ȁ[��["))
						{
	
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							Races_KAM.kerokero_nativefaith(pl, TouhouMC_Races_Basic.plugin0);
						}
					}
				}
			}
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				//TODO �����l
				//�����l�̃��[�v�ݒu�i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("mugennzin") ) {
					mana = 400;
					if(handitem == Material.BOW && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_NNG.mugen_one_gate_put(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 60L);
						}
					}
					//�����l�̃��[�v�g�p�i�������ݗL�j�i�O�u���L(�r���L)
					else if(handitem == Material.FISHING_ROD && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_NNG.mugen_one_gate_use(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 60L);
						}
					}
				}
				///�d�b�l���b�l
				if (race.equalsIgnoreCase("youzyuu") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("zyuuzin")) {
					//�d�b�̘T�����i�������ݗL�j�i�O�u���L(�r���L)
					mana = 70;
					if (handitem == Material.FISHING_ROD && (pl.isSneaking())) {
						if(Races_Global.magic_iscastable(pl , mana,"�����I�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.yuz_summon_wolf(pl, TouhouMC_Races_Basic.plugin0, e);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 30L);
						}
					}
				}
				//���̃l�R�����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("siki")) {
					mana = 80;
					if (handitem == Material.FISHING_ROD && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana ,"�����I�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.siki_summon_oc(pl, TouhouMC_Races_Basic.plugin0, e);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
				}
				//����̔n�����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("kyuubi")) {
					mana = 100;
					if (handitem == Material.FISHING_ROD && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana ,"�����I�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1, 2);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.kyuubi_summon_horse(pl, TouhouMC_Races_Basic.plugin0, e);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 40L);
						}
					}
				}
				//�d�b�̋����i�������ݗL�j�i�O�u���L(�r���L)
				if (race.equalsIgnoreCase("youzyuu") || race.equalsIgnoreCase("zyuuzin") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("siki")|| race.equalsIgnoreCase("kyuubi")|| race.equalsIgnoreCase("sinnzyuu")|| race.equalsIgnoreCase("gyokuto")|| race.equalsIgnoreCase("ryuugyo")) {
					mana = 150;
					if (handitem == Material.BOW && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana ,"�������@�I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_DONKEY_ANGRY, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.youzyu_gainenergy(pl, TouhouMC_Races_Basic.plugin0, e);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 15L);
						}
					}
				}
				mana = 80;
				//TODO ����
				//�����̓d��(�������ݗL�j(�r���L)
				if (race.equalsIgnoreCase("ryuugyo") ) {
					if(handitem == Material.FLINT_AND_STEEL && (pl.isSneaking())){
						if(Races_Global.magic_iscastable(pl , mana,"�|�[�Y���\�����I")){
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
							pl.setMetadata("casting", casting);
							conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
							TouhouMC_Races_Basic.SavethraceConfig();
							pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
							TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
								public void run() {
									Races_YUZ.ryuugyo_volt(pl, TouhouMC_Races_Basic.plugin0);
									MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
									pl.setMetadata("casting", casting);
								}
							}, 20L);
						}
					}
				}
			}
		}
	}

	
	//�N���b�N�֘A�̏���(Entity)
	@EventHandler
	public void onPlayerInteractEntity(final PlayerInteractEntityEvent e){
			//��l�ԑ��l�K���O�u���L
			int mana = 0;
			final Player pl = e.getPlayer();
			if (!pl.hasMetadata("vanished"))
			{
			Material handitem = pl.getInventory().getItemInMainHand().getType();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//�S�̖��ߗ��Ƃ��i�������ݗL�j�i�O�u���L(�r���L)
			mana = 100;
			if (race.equalsIgnoreCase("oni") || race.equalsIgnoreCase("kairikirannsin")){
				if(handitem == Material.IRON_PICKAXE && e.getRightClicked() instanceof LivingEntity && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"�����\�����I")){
						final LivingEntity entity = (LivingEntity) e.getRightClicked();
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SavethraceConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 3);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_AKM.oni_kairiki(pl, TouhouMC_Races_Basic.plugin0, e, entity);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 20L);
					}
				}
			}
			//�z���S�̋z���i�������ݗL�j�i�O�u���L(�r���L)
			mana = 80;
			if (race.equalsIgnoreCase("kyuuketuki") || race.equalsIgnoreCase("kinnima")){
				if(handitem == Material.IRON_PICKAXE && e.getRightClicked() instanceof LivingEntity && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"����\�����I")){
						final LivingEntity entity = (LivingEntity) e.getRightClicked();
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SavethraceConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 1, 1);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_AKM.kyuuketuki_drain(pl, TouhouMC_Races_Basic.plugin0, e, entity);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 60L);
					}
				}
			}
			//TODO �֊���
			//�֊����̂�����Ƃ��āi�������ݗL�j�i�O�u���L(�r���L)
			mana = 210;
			if (race.equalsIgnoreCase("kinnima")){
				if(handitem == Material.DIAMOND_PICKAXE && e.getRightClicked() instanceof LivingEntity && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"��̕����\�����I")){
						final LivingEntity entity = (LivingEntity) e.getRightClicked();
						if (entity instanceof Player) ((Player)entity).sendMessage(pluginpre + ChatColor.DARK_RED + "���ӂӁE�E�E");
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SavethraceConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_AKM.kinnima_kyuttosite(pl, TouhouMC_Races_Basic.plugin0, e, entity);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 200L);
					}
				}
			}
			}
	}
	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
		int mana = 50;
		if (event.getDamager() instanceof Player ) {
			if (!event.getDamager().hasMetadata("vanished"))
			{
			Player pl = (Player) event.getDamager();
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//���l
			if (race.equalsIgnoreCase("sibito") || race.equalsIgnoreCase("ennma")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.sibito_deadattack(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO 腖�
			if (race.equalsIgnoreCase("ennma") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.ennma_judgementattack(pl, TouhouMC_Races_Basic.plugin0, event);
			//���l�_
			if (race.equalsIgnoreCase("gennzinnsin") || race.equalsIgnoreCase("seizin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.gennzinnsin_luckyattack(pl, TouhouMC_Races_Basic.plugin0, event);
			//����
			if (race.equalsIgnoreCase("akuma") || race.equalsIgnoreCase("kyuuketuki") || race.equalsIgnoreCase("kinnima")|| race.equalsIgnoreCase("egosatori") || race.equalsIgnoreCase("satori") )
				if (conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_AKM.akuma_dark_attack(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			//�S
			if (race.equalsIgnoreCase("oni") || race.equalsIgnoreCase("kairikirannsin"))
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_AKM.oni_closed_attack(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			//�z���S
			if (race.equalsIgnoreCase("kyuuketuki") || race.equalsIgnoreCase("kinnima"))
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_AKM.kyuuketuki_shadow_attack(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			//TODO ����
			if (race.equalsIgnoreCase("kyourei") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_SIR.kyourei_berserker(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//TODO �ʓe
			if (race.equalsIgnoreCase("gyokuto") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_YUZ.gyokuto_ranged_attack(pl, TouhouMC_Races_Basic.plugin0, event);
			//�_
			if (race.equalsIgnoreCase("kami") || race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("yakusin") || race.equalsIgnoreCase("tyuuousin") || race.equalsIgnoreCase("dotyakusin") || race.equalsIgnoreCase("tukumogami") || race.equalsIgnoreCase("sinigami"))
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_KAM.kami_faith_attack(pl, TouhouMC_Races_Basic.plugin0, event, boost, conf);
				}
			//�L���_
			if (race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("tyuuousin") || race.equalsIgnoreCase("dotyakusin"))
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_KAM.houzyousin_potato(pl, TouhouMC_Races_Basic.plugin0, event, boost);
				}
			//TODO ���_
			if (race.equalsIgnoreCase("sinigami") && conf.getInt("user." + pl.getUniqueId() + ".split") >= 50)
				Races_KAM.sinigami_deathhall(pl, TouhouMC_Races_Basic.plugin0, event, conf);
			//�O���[�o��
			if (pl.getMetadata("spilituse").get(0).asDouble() < 0){
				event.setDamage(event.getDamage() / 2D);
				if (pl.isSneaking()){
					pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�M���͗�͍Đ����[�h�̈ז{�C���o���܂���I");
				}
			}
		}
		}
		mana = 40;
		if (event.getEntity() instanceof Player) {
			Player pl = (Player) event.getEntity();
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//�H���l
			if (race.equalsIgnoreCase("houraizin") || race.equalsIgnoreCase("tukibito"))
				if (conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_NNG.houraizin_reverselife_Entity(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			//TODO ���l
			if (race.equalsIgnoreCase("seizin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.kodaizin_anti_chain(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO �V�l
			if (race.equalsIgnoreCase("tennzin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.tennzin_mazo(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO �Ñ�l
			if (race.equalsIgnoreCase("kodaizin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.seizin_luckydefence(pl, TouhouMC_Races_Basic.plugin0, event);
			//�d�� (���l����)
			if (race.equalsIgnoreCase("yousei")|| race.equalsIgnoreCase("kibito"))
			{
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_YUS.yousei_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			}
			//�d�� (���l����)
			if (race.equalsIgnoreCase("daiyousei") || race.equalsIgnoreCase("hyouketuyousei"))
			{
				if(conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_YUS.daiyousei_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			}
			//���l
			else if (race.equalsIgnoreCase("kobito")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= mana) Races_YUS.kobito_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO �n���d��
			else if (race.equalsIgnoreCase("zigokuyousei") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana)Races_YUS.crownpeace_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
			//�T�g��
			if (race.equalsIgnoreCase("satori")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 50) Races_YUS.satori_satori(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO ���l
			if (race.equalsIgnoreCase("satori")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 50) Races_YUS.satori_satori(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO �l�G�d
			if (race.equalsIgnoreCase("sikiyou")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 50) Races_YUS.sikiyou_rose_venom(pl, TouhouMC_Races_Basic.plugin0, event);
			//TODO �S�l
			if (race.equalsIgnoreCase("kizin") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= 50) Races_AKM.kizin_revese_attack(pl, TouhouMC_Races_Basic.plugin0, event);
			//����
			if (race.equalsIgnoreCase("onnryou")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 20) Races_SIR.onnryou_never_vanish(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//�n����
			if (race.equalsIgnoreCase("zibakurei")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 50) Races_SIR.zibakurei_always_unvanish(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//�_
			if (race.equalsIgnoreCase("kami") || race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("yakusin")|| race.equalsIgnoreCase("tyuuousin") || race.equalsIgnoreCase("dotyakusin") || race.equalsIgnoreCase("tukumogami") || race.equalsIgnoreCase("sinigami"))
				if (conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_KAM.kami_faith_defence(pl, TouhouMC_Races_Basic.plugin0, event, boost, conf);
				}
			//��_
			if (race.equalsIgnoreCase("yakusin")  || race.equalsIgnoreCase("tukumogami") || race.equalsIgnoreCase("sinigami"))
			{
				if (conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				{
					Races_KAM.yakusin_darkside(pl, TouhouMC_Races_Basic.plugin0, event);
				}
			}
			//�t�r�_
			if (race.equalsIgnoreCase("tukumogami") && conf.getInt("user." + pl.getUniqueId() + ".split") >= 0)
				Races_KAM.tukumogami_sadezumu(pl, TouhouMC_Races_Basic.plugin0, event, conf);
			//�O���[�o��
			if (pl.getMetadata("spilituse").get(0).asDouble() < 0){
				event.setDamage(event.getDamage() * 1.5D);
				if (pl.isSneaking()){
					pl.sendMessage(TouhouMC_Races_Basic.thrace_Races_pre + ChatColor.RED + "�M���͗�͍Đ����[�h�̈ה��ɏ_���ł��I");
				}
			}
		}
	}

	//�_���[�W�֘A�̏���(�U���ȊO�܂�)
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		int mana = 30;
		Entity ent = e.getEntity();
		if(ent instanceof Player){
			Player pl = (Player) ent;
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//�͓�
			if (race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("yamakappa")){
				if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) e.setCancelled(true);
			}
			//�V��
			if(race.equalsIgnoreCase("tenngu") || race.equalsIgnoreCase("karasutenngu") || race.equalsIgnoreCase("syoukaitenngu")){
				if (e.getCause() == EntityDamageEvent.DamageCause.FALL && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_YUM.tenngu_toramporin(pl, TouhouMC_Races_Basic.plugin0, e);
			}
			//�d��
			if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("kobito") || race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("kyozin")  || race.equalsIgnoreCase("zigokuyousei") || race.equalsIgnoreCase("sikiyou"))
				{
					if (e.getCause() == EntityDamageEvent.DamageCause.FALL && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_YUS.yousei_fall_protection(pl, TouhouMC_Races_Basic.plugin0, e);
				}
			//����
			if (race.equalsIgnoreCase("akuma")|| race.equalsIgnoreCase("oni")|| race.equalsIgnoreCase("kyuuketuki")|| race.equalsIgnoreCase("kinnima")|| race.equalsIgnoreCase("kairikirannsin")|| race.equalsIgnoreCase("egosatori") || race.equalsIgnoreCase("satori") )
				{
				 if (conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_AKM.akuma_antiheat_body(pl, TouhouMC_Races_Basic.plugin0, e);
				}
			//�z���S
			if (race.equalsIgnoreCase("kyuuketuki")||race.equalsIgnoreCase("kinnima") ){
				if(conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_AKM.kyuuketuki_antiallfire_body(pl, TouhouMC_Races_Basic.plugin0, e);
			}
			//����
			if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ||race.equalsIgnoreCase("sinnrei")||race.equalsIgnoreCase("saigyouyou")||race.equalsIgnoreCase("zibakurei")||race.equalsIgnoreCase("kyourei") )
			{
				if (conf.getInt("user." + pl.getUniqueId() + ".split") >= mana && (pl.isSneaking())) Races_SIR.seirei_mighty_guard(pl, TouhouMC_Races_Basic.plugin0, e, boost);
			}
			//TODO ���s�d
			if(race.equalsIgnoreCase("saigyouyou") && conf.getInt("user." + pl.getUniqueId() + ".spilit") <= 40){
				if (e.getDamage() >= pl.getHealth()) Races_SIR.saigyou_kotyouran(pl, TouhouMC_Races_Basic.plugin0);
			}
			//�L���_
			if (race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("tyuuousin") || race.equalsIgnoreCase("dotyakusin")) {
				if (conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_KAM.houzyousin_feed(pl, TouhouMC_Races_Basic.plugin0, e);
			}
		}
		else if (ent instanceof Snowman && ent.hasMetadata("syugoreisnow")) e.setCancelled(true);
		else if (ent instanceof IronGolem && ent.hasMetadata("syugoreiiron")) e.setCancelled(true);
		else if (ent instanceof Wolf && ent.hasMetadata("tamedwolf")) e.setDamage(1D);
		else if (ent instanceof Ocelot && ent.hasMetadata("tamedcat")) e.setDamage(1D);
		else if (ent instanceof Horse && ent.hasMetadata("tamedhorse")) e.setDamage(1D);
	}
		@EventHandler
		public void onHorseDied(EntityDeathEvent event) {
			if (event.getEntity() instanceof Horse && event.getEntity().hasMetadata("tamedhorse")) event.getDrops().clear();
		}
		
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player pl = event.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
		//�l�������j�������ݗL�j�i�u�[�X�^�[���L
		mana = 3;
		if (race.equalsIgnoreCase("ninngyo")|| race.equalsIgnoreCase("ryuugyo")) {
			if (conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana)
			{
				int boost = 0;
				if (pl.getMetadata("spilituse").get(0).asInt() > 0) boost = 1;
				Races_YUZ.ninngyo_swimming(pl, TouhouMC_Races_Basic.plugin0, boost);
				if (boost == 1){
					conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
					TouhouMC_Races_Basic.SavethraceConfig();
			}
			}
		}
		//�l�������j�������ݗL�j�i�u�[�X�^�[���L
		mana = 3;
		if (race.equalsIgnoreCase("hyouketuyousei")) {
			if (conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana)
			{
				int boost = 0;
				if (pl.getMetadata("spilituse").get(0).asInt() > 0) boost = 1;
				Races_YUS.hyouketuyousei_skate(pl, TouhouMC_Races_Basic.plugin0, boost);
				if (boost == 1){
					conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
					TouhouMC_Races_Basic.SavethraceConfig();
			}
			}
		}
		//TODO ��V��
		mana = 2;
		if (pl.getMetadata("spilituse").get(0).asInt() > 0 && race.equalsIgnoreCase("karasutenngu") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
			int boost = 0;
			if (pl.getMetadata("spilituse").get(0).asInt() > 0) boost = 1;
			Races_YUM.karasutenngu_hopping(pl, TouhouMC_Races_Basic.plugin0, boost);
			if (boost == 1){
				conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
				TouhouMC_Races_Basic.SavethraceConfig();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e){
		Player pl = e.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
		//�d���H�΂���
		mana = 10;
		if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("kobito") || race.equalsIgnoreCase("kibito")  || race.equalsIgnoreCase("kyozin") || race.equalsIgnoreCase("zigokuyousei") || race.equalsIgnoreCase("sikiyou")){
			if(conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana)
			{
				if (!pl.isOnGround() && pl.isSneaking() && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana ){
					Races_YUS.yousei_feather(pl, TouhouMC_Races_Basic.plugin0);
					conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
					TouhouMC_Races_Basic.SavethraceConfig();
				}
			}
		}
		//��l�̕ǔ�
		mana = 60;
		if (race.equalsIgnoreCase("sennnin") || race.equalsIgnoreCase("kodaizin")) {
			if ((!pl.isOnGround()) && (pl.isSneaking()) && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
				Races_NNG.sennnin_passthough(pl, TouhouMC_Races_Basic.plugin0);
				conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
				TouhouMC_Races_Basic.SavethraceConfig();
				pl.sendMessage(pluginpre + ChatColor.GREEN + "���" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		//���X�|�����g���K�[�Ƃ��đ�̗͒���
		Player pl = event.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		if (race.equalsIgnoreCase("youma") || race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("tenngu") || race.equalsIgnoreCase("yamakappa") || race.equalsIgnoreCase("karasutenngu") || race.equalsIgnoreCase("syoukaitenngu")){
			pl.setMaxHealth(120.0D);
		}else if(race.equalsIgnoreCase("kennyou") || race.equalsIgnoreCase("sukimayou")){
			pl.setMaxHealth(150.0D);
		}else{
			pl.setMaxHealth(100D);
		}
		conf.set("user." + pl.getUniqueId() + ".spilit", 250D);
		MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 0) ;
		pl.setMetadata("spilituse", spilituse);
		
	}

	//���������
}