package net.cubespace.lib.Permission.Listener;

import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Permission.PermissionManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 28.11.13 11:24
 */
public class PlayerQuitListener implements Listener {
    private final CubespacePlugin plugin;
    private final PermissionManager permissionManager;

    public PlayerQuitListener(CubespacePlugin plugin, PermissionManager permissionManager) {
        this.plugin = plugin;
        this.permissionManager = permissionManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(final PlayerQuitEvent event) {
        permissionManager.remove(event.getPlayer().getName());
    }
}
