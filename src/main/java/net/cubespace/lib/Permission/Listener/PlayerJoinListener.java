package net.cubespace.lib.Permission.Listener;

import net.cubespace.PluginMessages.PermissionRequest;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Permission.PermissionManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 26.11.13 23:38
 */
public class PlayerJoinListener implements Listener {
    private final CubespacePlugin plugin;
    private final PermissionManager permissionManager;

    public PlayerJoinListener(CubespacePlugin plugin, PermissionManager permissionManager) {
        this.plugin = plugin;
        this.permissionManager = permissionManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerConnected(final PlayerJoinEvent event) {
        if(permissionManager.get(event.getPlayer().getName()) == null) {
            permissionManager.create(event.getPlayer().getName());
        }

        //Better wait some time since the player need to be in the Playable State
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getPluginMessageManager("CubespaceLibrary").sendPluginMessage(event.getPlayer(), new PermissionRequest(permissionManager.getPrefix()));
            }
        });
    }
}
