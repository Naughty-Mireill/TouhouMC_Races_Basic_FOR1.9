package net.TouhouMC.Halcram.TouhouMC_Races_Basic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Races_EventActionListener2;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Listener.Races_SkillMiscListener;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule.Nametag_Schedule;
import net.TouhouMC.Halcram.TouhouMC_Races_Basic.RaceSkills.Schedule.Races_Schedule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TouhouMC_Races_Basic extends JavaPlugin implements Listener {
	public static Logger logger = Logger.getLogger("Minecraft");
	public static TouhouMC_Races_Basic plugin;
	public static Plugin plugin0;
	public static String thrace_Races_pre = ChatColor.WHITE + "[" + ChatColor.RED + "thrace_Races" + ChatColor.WHITE + "]";
	public static PluginDescriptionFile pdfFile;
	private static File pluginDir = new File("plugins", "TouhouMC_Races_Basic");
	public static File configfile = new File(pluginDir, "config.yml");
	public static FileConfiguration conf = YamlConfiguration.loadConfiguration(configfile);
	public static boolean crackshot_hook = false;
	public static boolean nametagedit_hook = false;
	public static boolean scoreboardapi_hook = false;
	public static boolean barapi_hook;

	public void onDisable(){
		logger.info("[thrace] Plugin Successfully Disabled!");
		SavethraceConfig();
	}

	public void onEnable(){
		//��{�ݒ�
		pdfFile = this.getDescription();
		logger.info("[thrace]" + pdfFile.getVersion() + "�͐������N�����܂���");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		saveDefaultConfig();
		registerEventListener();
		plugin0 = this;
		Races_Schedule schedule = new Races_Schedule();
		String thpre0 = TouhouMC_Races_Basic.thrace_Races_pre;
		schedule.run1(plugin0,thpre0);
		schedule.run2(plugin0,thpre0);
		schedule.run3(plugin0,thpre0);
		//�t�b�N�ݒ�
		logger.info(thrace_Races_pre + pdfFile.getVersion() + "�͐�����NametagEdit�ƘA�g���܂���");
		registerNameTagListener();
	}
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args){
			if(cmd.getName().equalsIgnoreCase("thrace")){

				if(args.length == 0){
					sender.sendMessage(thrace_Races_pre + "Version " + pdfFile.getVersion() + ". Made by:" + pdfFile.getAuthors().toString());
					return true;
				}else {
					if(args[0].equalsIgnoreCase("help")){
						
						if(sender.hasPermission("thrace.help") || sender.hasPermission("thrace.user")){
							sender.sendMessage(thrace_Races_pre + ChatColor.GOLD + "�\�ȃv���O�C���ꗗ");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace : �o�[�W��������");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace stats [playername]: �v���C���[�̏���\������");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace race <racename> : �푰�̏���\������");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace mana check [playername] : ���݃}�i�m�F");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace skill enable/passive/disable [playername]: �X�L���̔������g�O��");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace needpoint check [playername] : �v���C���[�̂̎푰�|�C���g���m�F����");
							sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "thrace evol list [playername]: �v���C���[�̐i���ł���푰�̃��X�g��\������");
							if(sender.hasPermission("thrace.help") || sender.hasPermission("thrace.evol.try")){
								sender.sendMessage(thrace_Races_pre + ChatColor.YELLOW + "thrace evol try <racename> : �푰���i�������w��j�ւ̐i�������݂�����ɉ���");
							}
							if(sender.hasPermission("thrace.help")){
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace setrace <racename> [playername]: �v���C���[�̂̎푰���푰���i�������w��j�ɕύX����");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace mana set <num> [playername]: �}�i��num�ɂ���");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace mana add <num> [playername]: �}�i��num������������");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace needpoint set <num> [playername] : �v���C���[�̂̎푰�|�C���g��ݒ肷��");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace needpoint add <num> [playername] : �v���C���[�̂̎푰�|�C���g��ǉ�����");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace needpoint step <num> <max> [playername] : �v���C���[�̎푰�|�C���g�i�g�����͔C�Ӂj��max������Ƃ���num�㏸����");
//TODO ������								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace needpoint scoreboard <objectives> [playername] : �v���C���[�̂̎푰�|�C���g���X�R�A�{�[�h��̒l����Q�Ƃ���");
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "thrace reload : �����[�h");
							}
							return true;
						}else {
							sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
							return false;
						}
						//�v���C���[�f�[�^������
						}else if(args[0].equalsIgnoreCase("stats")){
							if(args.length == 1)
							{
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								if(p.hasPermission("thrace.user") || p.hasPermission("thrace.stats"))
								{
									 p.sendMessage(ChatColor.GREEN + "���Ȃ��̎푰 �F " + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".race").toString()));
									  return true;
								}
								else 
								{
								p.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
								}
							}
							else if (args.length == 2)
							{
								if(sender.hasPermission("thrace.user") || sender.hasPermission("thrace.stats.other"))
								{
									if (Bukkit.getPlayer(args[1]) != null)
									{
										Player pl = Bukkit.getPlayer(args[1]);
										sender.sendMessage(pl.getName() + ChatColor.GREEN + "�̎푰 �F " + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".race").toString()));
										return true;
									}
									else
									{
										sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
										return false;
									}
								}
								else 
								{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							//����̎푰��������
						}else if(args[0].equalsIgnoreCase("race")){
							if(sender.hasPermission("thrace.race") || sender.hasPermission("thrace.user")){
								boolean existrace = false;
								String inforace = "";
								for (String race : conf.getConfigurationSection("race").getKeys(false)) {
									if (race.equalsIgnoreCase(args[1])){
										existrace = true;
										inforace = race;
										break;
									}
								}
								if (existrace){
									sender.sendMessage(thrace_Races_pre + ChatColor.GOLD + conf.getString(new StringBuilder("race.").append(inforace).append(".real").toString()) + "�F����name��" + inforace + "�i" + conf.getString(new StringBuilder("race.").append(inforace).append(".tag").toString()) + "�j�̏��");
									sender.sendMessage(ChatColor.GREEN + "���푰�F" + conf.getString(new StringBuilder("race.").append(inforace).append(".root").toString()));
									sender.sendMessage(ChatColor.GREEN + "�����N�F" + conf.getString(new StringBuilder("race.").append(inforace).append(".rank").toString()));
									sender.sendMessage(ChatColor.YELLOW + "�i���ɕK�v�Ȏ푰�|�C���g�F" + conf.getString(new StringBuilder("race.").append(inforace).append(".evol.needpoint").toString()));
									sender.sendMessage(ChatColor.YELLOW + "�i���ɕK�v�ȑf�ށF" + conf.getInt(new StringBuilder("race.").append(inforace).append(".evol.costitemamount").toString()) + "��" + Material.getMaterial(conf.getInt(new StringBuilder("race.").append(inforace).append(".evol.costitemid").toString())) + "(���^" + conf.getInt(new StringBuilder("race.").append(inforace).append(".evol.costitemmeta").toString()) + "�j");
									sender.sendMessage(ChatColor.LIGHT_PURPLE + conf.getString("race." + inforace + ".intro.story"));
									sender.sendMessage(ChatColor.GRAY + conf.getString("race." + inforace + ".intro.skills"));
									return true;
								}else	{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "���̎푰����name�͑��݂��܂���B");
									return false;
								}
							}else {
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
							
						//�v���C���[MP������/�Z�b�g����/�A�b�h����
						}else if(args[0].equalsIgnoreCase("mana"))
						{
							if(args.length == 2 && args[1].equalsIgnoreCase("check"))
							{
								if(sender.hasPermission("thrace.user") || sender.hasPermission("thrace.mana.check"))
								{
									if (sender instanceof Player)
									{
										Player p;
										p = (Player)sender;
									  p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "��́F" + conf.getDouble(new StringBuilder("user.").append(p.getUniqueId()).append(".spilit").toString()));
									  return true;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else if (args.length == 3 && args[1].equalsIgnoreCase("check"))
							{
								if(sender.hasPermission("thrace.mana.check"))
								{
									if (Bukkit.getPlayer(args[2]) != null)
									{
										if (sender instanceof Player)
										{
											Player p;
											p = (Player)sender;
										Player pl = Bukkit.getPlayer(args[2]);
										p.sendMessage(thrace_Races_pre + "��́F" + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
										return true;
										}
									}
									else
									{
										sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
										return false;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else if(args.length == 3 && args[1].equalsIgnoreCase("set"))
							{
								if(sender.hasPermission("thrace.mana.set"))
								{
									if (sender instanceof Player)
									{
										Player p;
										p = (Player)sender;
									  conf.set("user." + p.getUniqueId() + ".spilit", Integer.parseInt(args[2]));
									  p.sendMessage(thrace_Races_pre + ChatColor.AQUA +  "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(p.getUniqueId()).append(".spilit").toString()));
									  return true;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else if (args.length == 4 && args[1].equalsIgnoreCase("set"))
							{
								if(sender.hasPermission("thrace.mana.setother"))
								{
									if (Bukkit.getPlayer(args[3]) != null)
									{
										Player pl = Bukkit.getPlayer(args[3]);
										conf.set("user." + pl.getUniqueId() + ".spilit", Integer.parseInt(args[2]));
										sender.sendMessage(thrace_Races_pre + ChatColor.AQUA +  "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
										return true;
									}
									else
									{
										sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
										return false;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else if(args.length == 3 && args[1].equalsIgnoreCase("add"))
							{
								if(sender.hasPermission("thrace.mana.add"))
								{
									if (sender instanceof Player)
									{
										Player p;
										p = (Player)sender;
									  conf.set("user." + p.getUniqueId() + ".spilit", Double.valueOf(conf.getDouble("user." + p.getUniqueId() + ".spilit") + Integer.parseInt(args[2])));
									  p.sendMessage(thrace_Races_pre + ChatColor.AQUA +  "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(p.getUniqueId()).append(".spilit").toString()));
									  return true;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else if (args.length == 4 && args[1].equalsIgnoreCase("add"))
							{
								if(sender.hasPermission("thrace.mana.addother"))
								{
									if (Bukkit.getPlayer(args[3]) != null)
									{
										Player pl = Bukkit.getPlayer(args[3]);
										conf.set("user." + pl.getUniqueId() + ".spilit", Double.valueOf(conf.getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[2])));
										sender.sendMessage(thrace_Races_pre + ChatColor.AQUA +  "��́F" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
										return true;
									}
									else
									{
										sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
										return false;
									}
								}
								else 
								{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
								}
							}
							else
							{
								sender.sendMessage(thrace_Races_pre + "/thrace mana check [playername] ");
								sender.sendMessage(thrace_Races_pre + "/thrace mana set <num> [playername] ");
								sender.sendMessage(thrace_Races_pre + "/thrace mana add <num> [playername] ");
								return false;
							}

					//�v���C���[�푰�|�C���g���m�F/�Z�b�g/�ǉ�/����t���Œǉ�����

					}else if(args[0].equalsIgnoreCase("needpoint")){
						if (args.length == 2 && args[1].equalsIgnoreCase("check"))
						{
							if(sender.hasPermission("thrace.user") || sender.hasPermission("thrace.needpoint.check"))
							{
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "���Ȃ��푰�̃|�C���g��" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) + "�ł�");
								}
								}
							else
							{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
						}
						else if (args.length == 3 && args[1].equalsIgnoreCase("check"))
						{
							if (Bukkit.getPlayer(args[2]) != null)
							{
								if(sender.hasPermission("thrace.needpoint.check"))
								{
									Player pl = Bukkit.getPlayer(args[2]);
									sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�v���C���[�̎푰�|�C���g��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString()) + "�ł�");
								}
								else
								{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
								return false;
							}
						}
						else if (args.length == 3 && args[1].equalsIgnoreCase("set"))
						{
							if(sender.hasPermission("thrace.needpoint.set"))
							{
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								int needpoint = Integer.parseInt(args[2]);
								conf.set("user." + p.getUniqueId() + ".needpoint", needpoint);
								SavethraceConfig();
								p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "���Ȃ��푰�̃|�C���g��" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
							}
							}
							else
							{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
						}
						else if (args.length == 4 && args[1].equalsIgnoreCase("set"))
						{
							if (Bukkit.getPlayer(args[3]) != null)
							{
								if(sender.hasPermission("thrace.needpoint.set"))
								{
									int needpoint = Integer.parseInt(args[2]);
									Player pl = Bukkit.getPlayer(args[3]);
									conf.set("user." + pl.getUniqueId() + ".needpoint",needpoint);
									SavethraceConfig();
									sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�v���C���[�̎푰�|�C���g��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
								}
								else
								{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
								return false;
							}
						}
						else if (args.length == 3 && args[1].equalsIgnoreCase("add"))
						{
							if(sender.hasPermission("thrace.needpoint.add"))
							{
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								int needpoint = Integer.parseInt(args[2]);
								conf.set("user." + p.getUniqueId() + ".needpoint", Integer.valueOf(conf.getInt("user." + p.getUniqueId() + ".needpoint") + needpoint));
								SavethraceConfig();
								p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "���Ȃ��푰�̃|�C���g��" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
							}
							}
							else
							{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
						}
						else if (args.length == 4 && args[1].equalsIgnoreCase("add"))
						{
							if (Bukkit.getPlayer(args[3]) != null)
							{
								if(sender.hasPermission("thrace.needpoint.add"))
								{
									int needpoint = Integer.parseInt(args[2]);
									Player pl = Bukkit.getPlayer(args[3]);
									conf.set("user." + pl.getUniqueId() + ".needpoint", Integer.valueOf(conf.getInt("user." + pl.getUniqueId() + ".needpoint") + needpoint));
									SavethraceConfig();
									sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�v���C���[�̎푰�|�C���g��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
								}
								else
								{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
								return false;
							}
						}
						
						else if (args.length == 4 && args[1].equalsIgnoreCase("step"))
						{
							if(sender.hasPermission("thrace.needpoint.step"))
							{
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								int needpoint = Integer.parseInt(args[2]);
								int max = Integer.parseInt(args[3]);
								if (Integer.valueOf(conf.getInt("user." + p.getUniqueId() + ".needpoint") + needpoint) >= max)
								{
									conf.set("user." + p.getUniqueId() + ".needpoint", max);
									SavethraceConfig();
									p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "���Ȃ��푰�̃|�C���g�͌��E�l�Ƃ���" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
								}
								else
								{	
									conf.set("user." + p.getUniqueId() + ".needpoint", Integer.valueOf(conf.getInt("user." + p.getUniqueId() + ".needpoint") + needpoint));
									SavethraceConfig();
									p.sendMessage(thrace_Races_pre + ChatColor.AQUA + "���Ȃ��푰�̃|�C���g��" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
								}
								}
							}
							else
							{
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
						}
						else if (args.length == 5 && args[1].equalsIgnoreCase("step"))
						{
							if (Bukkit.getPlayer(args[4]) != null)
							{
								if(sender.hasPermission("thrace.needpoint.step"))
								{
									int needpoint = Integer.parseInt(args[2]);
									int max = Integer.parseInt(args[3]);
									Player pl = Bukkit.getPlayer(args[4]);
									if (Integer.valueOf(conf.getInt("user." + pl.getUniqueId() + ".needpoint") + needpoint) >= max)
									{
										conf.set("user." + pl.getUniqueId() + ".needpoint", max);
										SavethraceConfig();
										sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�v���C���[�̎푰�̃|�C���g�͌��E�l�Ƃ���" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
									}
									else
									{
									conf.set("user." + pl.getUniqueId() + ".needpoint", Integer.valueOf(conf.getInt("user." + pl.getUniqueId() + ".needpoint") + needpoint));
									SavethraceConfig();
									sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�v���C���[�̎푰�|�C���g��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString()) + "�ɂȂ�܂���");
									}
								}
								else
								{
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
								return false;
							}
						}
						else
						{
							sender.sendMessage(thrace_Races_pre + "/thrace needpoint check [playername] ");
							sender.sendMessage(thrace_Races_pre + "/thrace needpoint set/add <num> [playername] ");
							sender.sendMessage(thrace_Races_pre + "/thrace needpoint step <num> <max> [playername] ");
							return false;
						}
					//�i���֘A �i����m�F/�i��
					}else if(args[0].equalsIgnoreCase("evol")){
						if (args.length == 2 && args[1].equalsIgnoreCase("list"))
						{
							if(sender.hasPermission("thrace.evol.list") || sender.hasPermission("thrace.user")){
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								p.sendMessage(thrace_Races_pre + ChatColor.BOLD + p.getName() + "�̐i���ł���惊�X�g");
								List<String> evolraces = new ArrayList<String>();
								for (String race : conf.getConfigurationSection("race").getKeys(false)) {
									if (conf.getString("race." + race + ".root").contains(conf.getString("user." + p.getUniqueId() + ".race"))) {
										evolraces.add(race);
									}
								}
								for (String evolrace : evolraces) {
									p.sendMessage(ChatColor.GREEN + conf.getString(new StringBuilder("race.").append(evolrace).append(".real").toString()) + "�F����name��" + evolrace);
								}
								return true;
								}
							}else {
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
							}else if (args.length == 3 && args[1].equalsIgnoreCase("list"))
							{
								if (Bukkit.getPlayer(args[2]) != null)
								{
									if(sender.hasPermission("thrace.evol.list.other")){
										
										Player pl = Bukkit.getPlayer(args[2]);
										sender.sendMessage(thrace_Races_pre + ChatColor.GOLD + pl.getName() + "�̐i���ł���惊�X�g");
										List<String> evolraces = new ArrayList<String>();
										for (String race : conf.getConfigurationSection("race").getKeys(false)) {
											if (conf.getString("race." + race + ".root").contains(conf.getString("user." + pl.getUniqueId() + ".race"))) {
												evolraces.add(race);
											}
										}
										for (String evolrace : evolraces) {
											sender.sendMessage(ChatColor.GREEN + conf.getString(new StringBuilder("race.").append(evolrace).append(".real").toString()) + "�F����name��" + evolrace);
										}
										return true;
									}else {
										sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
										return false;
									}
								}
							}
							else if(args.length == 3 && args[1].equalsIgnoreCase("try")){
							if(sender.hasPermission("thrace.user") || sender.hasPermission("thrace.evol.try")){
								if (sender instanceof Player)
								{
									Player p;
									p = (Player)sender;
								boolean existrace = false;
								String inforace = "";
								for (String race : conf.getConfigurationSection("race").getKeys(false)) {
									if (race.equalsIgnoreCase(args[2])){
										existrace = true;
										inforace = race;
										break;
									}
								}
								if (existrace){
									if (conf.getString("race." + inforace + ".root").contains(conf.getString("user." + p.getUniqueId() + ".race"))){
										PlayerInventory inventory = p.getInventory();
										int ok_raceitem = 0;
										ItemStack raceitem = null;
										if (conf.getInt("race." + inforace + ".evol.costitemamount") != 0){
											raceitem = new ItemStack(Material.getMaterial(conf.getInt("race." + inforace + ".evol.costitemid")), conf.getInt("race." + inforace + ".evol.costitemamount"));
											int raceitemmeta = conf.getInt("race." + inforace + ".evol.costitemmeta");
											raceitem.setDurability((short)raceitemmeta);
										    if (inventory.contains(raceitem)) {
										    	ok_raceitem = 1;
										    }
										    else
										    {
										    	ok_raceitem = 2;
										    }
										}
										int needpoint = conf.getInt("race." + inforace + ".evol.needpoint");
										if ((ok_raceitem == 2)){
											p.sendMessage(thrace_Races_pre + ChatColor.RED + "�i���p�푰�A�C�e��������܂���I");
											return false;
										}else if (needpoint <= conf.getInt(new StringBuilder("user.").append(p.getUniqueId()).append(".needpoint").toString()) ) {
											p.playSound(p.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);
											if (ok_raceitem == 1) {
												inventory.remove(raceitem);
											}
											conf.set("user." + p.getUniqueId() + ".race", inforace);
											SavethraceConfig();
											Bukkit.broadcastMessage(thrace_Races_pre + ChatColor.AQUA + p.getName() + "��" + ChatColor.GRAY + conf.getString(new StringBuilder("race.").append(inforace).append(".root").toString()) + "����" + ChatColor.GOLD + conf.getString(new StringBuilder("race.").append(inforace).append(".real").toString()) + "�ɐi�������I�I");
											ItemStack rewarditem = null;
											if (conf.getInt("race." + inforace + "evol.rewarditemamount") != 0){
												rewarditem = new ItemStack(Material.getMaterial(conf.getInt("race." + inforace + ".evol.rewarditemid")), conf.getInt("race." + inforace + ".evol.rewarditemamount"));
												int rewarditemmeta = conf.getInt("race." + inforace + ".evol.rewarditemmeta");
												rewarditem.setDurability((short)rewarditemmeta);
												p.getInventory().addItem(new ItemStack[] { rewarditem });
											}
											return true;
										}
										else
										{
											p.sendMessage(thrace_Races_pre + ChatColor.RED + "�i�������푰�|�C���g������܂���I");
											return false;
										}
									}else {
										p.sendMessage(thrace_Races_pre + ChatColor.RED + "�i���ł���푰�ł͂���܂���I");
										return false;
									}
								}else {
									p.sendMessage(thrace_Races_pre + ChatColor.RED + "���̎푰����name�͑��݂��܂���B");
									return false;
								}
								}
							}else {
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
							}
							}else if(args.length == 4 && args[1].equalsIgnoreCase("try")){
								if (Bukkit.getPlayer(args[3]) != null)
								{
								if(sender.hasPermission("thrace.evol.try.other")){
									Player pl = Bukkit.getPlayer(args[3]);
									boolean existrace = false;
									String inforace = "";
									for (String race : conf.getConfigurationSection("race").getKeys(false)) {
										if (race.equalsIgnoreCase(args[2])){
											existrace = true;
											inforace = race;
											break;
										}
									}
									if (existrace){
										if (conf.getString("race." + inforace + ".root").contains(conf.getString("user." + pl.getUniqueId() + ".race"))){
											PlayerInventory inventory = pl.getInventory();
											int ok_raceitem = 0;
											ItemStack raceitem = null;
											if (conf.getInt("race." + inforace + ".evol.costitemamount") != 0){
												raceitem = new ItemStack(Material.getMaterial(conf.getInt("race." + inforace + ".evol.costitemid")), conf.getInt("race." + inforace + ".evol.costitemamount"));
												int raceitemmeta = conf.getInt("race." + inforace + ".evol.costitemmeta");
												raceitem.setDurability((short)raceitemmeta);
											    if (inventory.contains(raceitem)) {
											    	ok_raceitem = 1;
											    }
											    else
											    {
											    	ok_raceitem = 2;
											    }
											}
											int needpoint = conf.getInt("race." + inforace + ".evol.needpoint");
											if ((ok_raceitem == 2)){
												sender.sendMessage(thrace_Races_pre + ChatColor.RED + "�i���p�푰�A�C�e��������܂���I");
												return false;
											}else if (needpoint <= conf.getInt(new StringBuilder("user.").append(pl.getUniqueId()).append(".needpoint").toString())) {
												pl.playSound(pl.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);
												if (ok_raceitem == 1) {
													inventory.remove(raceitem);
												}
												conf.set("user." + pl.getUniqueId() + ".race", inforace);
												SavethraceConfig();
												Bukkit.broadcastMessage(thrace_Races_pre + ChatColor.AQUA + pl.getName() + "��" + ChatColor.GRAY + conf.getString(new StringBuilder("race.").append(inforace).append(".root").toString()) + "����" + ChatColor.GOLD + conf.getString(new StringBuilder("race.").append(inforace).append(".real").toString()) + "�ɐi�������I�I");
												ItemStack rewarditem = null;
												if (conf.getInt("race." + inforace + "evol.rewarditemamount") != 0){
													rewarditem = new ItemStack(Material.getMaterial(conf.getInt("race." + inforace + ".evol.rewarditemid")), conf.getInt("race." + inforace + ".evol.rewarditemamount"));
													int rewarditemmeta = conf.getInt("race." + inforace + ".evol.rewarditemmeta");
													rewarditem.setDurability((short)rewarditemmeta);
													pl.getInventory().addItem(new ItemStack[] { rewarditem });
												}
												return true;
											}
											else
											{
												sender.sendMessage(thrace_Races_pre + ChatColor.RED + "�i�������푰�|�C���g������܂���I");
											}
										}else {
											sender.sendMessage(thrace_Races_pre + ChatColor.RED + "�i���ł���푰�ł͂���܂���I");
											return false;
										}
									}else {
										sender.sendMessage(thrace_Races_pre + ChatColor.RED + "���̎푰����name�͑��݂��܂���B");
										return false;
									}
								}else {
									sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.RED + thrace_Races_pre + "�v���C���[�����s���ł��I");
								return false;
							}
						}
							else
							{
								sender.sendMessage(thrace_Races_pre + "/thrace setrace <race> [playername]");
								return false;
							}
						//�v���C���[�푰�𖳏����Őݒ肷��
						}else if(args[0].equalsIgnoreCase("setrace")){
							if(sender.hasPermission("thrace.setrace")){
								if(args.length == 2){
									if (sender instanceof Player)
									{
										Player p;
										p = (Player)sender;
									conf.set("user." + p.getUniqueId() + ".race", args[1].toString());
									SavethraceConfig();
									p.sendMessage(thrace_Races_pre + ChatColor.LIGHT_PURPLE + "���Ȃ��͎푰��" + conf.getString(new StringBuilder("user.").append(p.getUniqueId()).append(".race").toString()) + "�ɂȂ�܂����B");
									return true;
									}
								}else if(args.length == 3){
									if (Bukkit.getPlayer(args[2]) != null){
										Player pl = Bukkit.getPlayer(args[2]);
										conf.set("user." + pl.getUniqueId() + ".race", args[1].toString());
										SavethraceConfig();
										sender.sendMessage(thrace_Races_pre + ChatColor.LIGHT_PURPLE + pl.getName() + "�̎푰��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".race").toString()) + "�ɂ��܂����B");
										pl.sendMessage(thrace_Races_pre + ChatColor.LIGHT_PURPLE + "���Ȃ��͎푰��" + conf.getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".race").toString()) + "�ɂȂ�܂����B");
										return true;
									}
								}else {
									sender.sendMessage(thrace_Races_pre + "/thrace setrace <PlayerName> (race)");
									return false;
								}
							}else {
								sender.sendMessage(thrace_Races_pre + ChatColor.RED + "����������܂���I");
								return false;
						}

					}
						else {
							sender.sendMessage(thrace_Races_pre + ChatColor.RED + "�R�}���h�����݂��܂���I");
					}
				}
			}
			else if (args[0].equalsIgnoreCase("reload"))
			{
				if(sender.hasPermission("thrace.reload"))
				{
					reloadthraceConfig();
					sender.sendMessage(thrace_Races_pre + ChatColor.AQUA + "�����[�h���܂���");
				}
			}
		return false;
	}

	public void registerEventListener(){
		new Races_EventActionListener2(this);
		new Races_SkillMiscListener(this);
	}
	
	public void registerNameTagListener(){
		new Nametag_Schedule(this);
		Nametag_Schedule.runname(plugin0, thrace_Races_pre);
	}
	
	public static void SavethraceConfig(){
		try {
			conf.save(configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reloadthraceConfig(){
		configfile = new File(pluginDir, "config.yml");
		conf = YamlConfiguration.loadConfiguration(configfile);
	}
}