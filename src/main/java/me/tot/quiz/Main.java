package me.tot.quiz;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    private static Economy econ;

    HashMap<String, String> questions = new HashMap<>();

    HashMap<UUID, Integer> leaderboard = new HashMap<>();

    String currentAnswer = "";

    boolean active = false;

    @Override
    public void onEnable() {

        if (!setupEconomy()) {
            getLogger().warning("Vault tidak ditemukan!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        loadQuestions();

        Bukkit.getPluginManager().registerEvents(this, this);

        // Quiz tiap 5 menit
        Bukkit.getScheduler().runTaskTimer(this, () -> {

            if (active) return;

            Object[] q = questions.keySet().toArray();

            Random random = new Random();

            String question = (String) q[random.nextInt(q.length)];

            currentAnswer = questions.get(question);

            active = true;

            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("§b§l[Quest-SMP");
            Bukkit.broadcastMessage("§6Round off to 2 decimal places:");
            Bukkit.broadcastMessage("§e" + question);
            Bukkit.broadcastMessage("§aHadiah: §6100 Coins");
            Bukkit.broadcastMessage("§7Jawab di chat dalam §f1 menit");
            Bukkit.broadcastMessage(" ");

            // Sound pas quiz muncul
            for (Player all : Bukkit.getOnlinePlayers()) {

                all.playSound(
                        all.getLocation(),
                        Sound.BLOCK_NOTE_BLOCK_PLING,
                        1f,
                        1f
                );
            }

            // Kesempatan jawab 1 menit
            Bukkit.getScheduler().runTaskLater(this, () -> {

                if (active) {

                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage("§c✖ Tidak ada yang menjawab!");
                    Bukkit.broadcastMessage("§7Jawaban: §f" + currentAnswer);
                    Bukkit.broadcastMessage(" ");

                    // Sound gagal
                    for (Player all : Bukkit.getOnlinePlayers()) {

                        all.playSound(
                                all.getLocation(),
                                Sound.ENTITY_VILLAGER_NO,
                                1f,
                                1f
                        );
                    }

                    active = false;
                }

            }, 20 * 60);

        }, 20 * 10, 20 * 300);

        getLogger().info("Quiz aktif!");
    }

    public void loadQuestions() {

        questions.clear();

        if (getConfig().getConfigurationSection("questions") == null) {
            getLogger().warning("Section questions tidak ditemukan!");
            return;
        }

        for (String question : getConfig()
                .getConfigurationSection("questions")
                .getKeys(false)) {

            String answer = getConfig()
                    .getString("questions." + question);

            questions.put(question, answer.toLowerCase());
        }

        getLogger().info("Loaded " + questions.size() + " questions!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!active) return;

        if (e.getMessage().equalsIgnoreCase(currentAnswer)) {

            active = false;

            econ.depositPlayer(p, 100);

            leaderboard.put(
                    p.getUniqueId(),
                    leaderboard.getOrDefault(p.getUniqueId(), 0) + 1
            );

            int score = leaderboard.get(p.getUniqueId());

            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("§b§l[Quest-SMP]");
            Bukkit.broadcastMessage("§a✔ §f" + p.getName() + " menjawab dengan benar!");
            Bukkit.broadcastMessage("§6+100 Coins");
            Bukkit.broadcastMessage("§bScore Quiz: §f" + score);
            Bukkit.broadcastMessage(" ");

            // Sound berhasil
            for (Player all : Bukkit.getOnlinePlayers()) {

                all.playSound(
                        all.getLocation(),
                        Sound.ENTITY_PLAYER_LEVELUP,
                        1f,
                        1f
                );
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {

        // /quiztop
        if (command.getName().equalsIgnoreCase("quiztop")) {

            sender.sendMessage("§6=== Quiz Leaderboard ===");

            leaderboard.forEach((uuid, score) -> {

                String name = Bukkit.getOfflinePlayer(uuid).getName();

                sender.sendMessage("§e" + name + " §7- §f" + score);
            });
        }

        // /quizreload
        if (command.getName().equalsIgnoreCase("quizreload")) {

            reloadConfig();

            loadQuestions();

            sender.sendMessage("§aQuiz config berhasil direload!");
        }

        return true;
    }

    private boolean setupEconomy() {

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager()
                        .getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();

        return econ != null;
    }
}