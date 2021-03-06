package net.cubespace.lib.Permission;

import net.cubespace.PluginMessages.PermissionRequest;
import net.cubespace.PluginMessages.PermissionResponse;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Permission.Listener.PlayerJoinListener;
import net.cubespace.lib.Permission.Listener.PlayerQuitListener;
import net.cubespace.lib.Permission.Listener.PluginMessageListener;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 07.01.14 21:05
 */
public class PermissionManager {
    private HashMap<String, PermissionStorage> permissionStorageHashMap = new HashMap<>();
    private String prefix;
    private CubespacePlugin plugin;

    public PermissionManager(CubespacePlugin plugin) {
        this.plugin = plugin;

        plugin.getPluginMessageManager("CubespaceLibrary").addPacketToRegister(null, PermissionRequest.class);
        plugin.getPluginMessageManager("CubespaceLibrary").addPacketToRegister(null, PermissionResponse.class);
        plugin.getPluginMessageManager("CubespaceLibrary").addListenerToRegister(null, new PluginMessageListener(this, plugin));

        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuitListener(plugin, this), plugin);
    }

    public void setup(String prefix) {
        this.prefix = prefix;
    }

    public void create(String player) {
        permissionStorageHashMap.put(player, new PermissionStorage());
    }

    public PermissionStorage get(String player) {
        return permissionStorageHashMap.get(player);
    }

    public boolean has(CommandSender sender, String permission) {
        boolean useStorage = permissionStorageHashMap.containsKey(sender.getName());

        permission = permission.substring(prefix.length());

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for(int i = 0; i < permission.length(); i++) {
            String check = sb.toString() + "*";

            if ((useStorage && permissionStorageHashMap.get(sender.getName()).has(permission)) || sender.hasPermission(check)) {
                return true;
            } else {
                sb.append(permission.charAt(i));
            }
        }

        return (permissionStorageHashMap.containsKey(sender.getName()) && permissionStorageHashMap.get(sender.getName()).has(permission)) || (sender.hasPermission(permission));
    }

    public void remove(String player) {
        permissionStorageHashMap.remove(player);
    }

    public String getPrefix() {
        return prefix;
    }
}
