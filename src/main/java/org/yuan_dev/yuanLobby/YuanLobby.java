package org.yuan_dev.yuanLobby;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class YuanLobby extends JavaPlugin {

    private static final String BUNGEE_CHANNEL = "BungeeCord";
    private static final String LOBBY_SERVER = "lobby";

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, BUNGEE_CHANNEL);
        getLogger().info("YuanLobby đã được kích hoạt");
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, BUNGEE_CHANNEL);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName().toLowerCase();
        if (!cmd.equals("hub") && !cmd.equals("lobby")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Chi nguoi choi moi co the dung lenh nay.");
            return true;
        }

        try {
            sendPlayerToLobby(player);
            player.sendMessage(ChatColor.GREEN + "Dang chuyen ban ve cum lobby...");
        } catch (IOException ex) {
            player.sendMessage(ChatColor.RED + "Khong the ket noi cum lobby. Thu lai sau.");
            getLogger().warning("Khong the dua nguoi choi ve" + player.getName() + " cum lobby: " + ex.getMessage());
        }

        return true;
    }

    private void sendPlayerToLobby(Player player) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArray);

        out.writeUTF("Connect");
        out.writeUTF(LOBBY_SERVER);

        player.sendPluginMessage(this, BUNGEE_CHANNEL, byteArray.toByteArray());
    }
}
