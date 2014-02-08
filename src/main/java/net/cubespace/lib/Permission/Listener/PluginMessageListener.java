package net.cubespace.lib.Permission.Listener;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import net.cubespace.PluginMessages.PermissionRequest;
import net.cubespace.PluginMessages.PermissionResponse;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Permission.Event.PermissionChangedEvent;
import net.cubespace.lib.Permission.Event.PermissionLoadedEvent;
import net.cubespace.lib.Permission.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 07.01.14 21:09
 */
public class PluginMessageListener implements PacketListener {
    private CubespacePlugin plugin;
    private PermissionManager permissionManager;

    public PluginMessageListener(PermissionManager permissionManager, CubespacePlugin plugin) {
        this.plugin = plugin;
        this.permissionManager = permissionManager;
    }

    @PacketHandler
    public void onPermissionResponse(PermissionResponse permissionResponse) {
        if(permissionManager.get(permissionResponse.getSender().getName()) == null) return;

        if(permissionResponse.getMode() == 0) {
            //Clear permissions
            permissionManager.get(permissionResponse.getSender().getName()).clear();
            return;
        }

        if(permissionResponse.getMode() == 1) {
            //Add permission
            permissionManager.get(permissionResponse.getSender().getName()).add(permissionResponse.getPermission());
        }

        if(permissionResponse.getMode() == 2) {
            //Ready
            if(!permissionManager.get(permissionResponse.getSender().getName()).isResolved())
                plugin.getAsyncEventBus().callEvent(new PermissionLoadedEvent(permissionResponse.getSender().getName()));
            else
                plugin.getAsyncEventBus().callEvent(new PermissionChangedEvent(permissionResponse.getSender().getName()));

            permissionManager.get(permissionResponse.getSender().getName()).resolved();
        }
    }

    @PacketHandler
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        if(permissionManager.get(permissionRequest.getSender().getName()) == null) return;

        Player player = permissionRequest.getSender().getBukkitPlayer();

        plugin.getPluginMessageManager("CubespaceLibrary").sendPluginMessage(player, new PermissionResponse("", 0));

        for(PermissionAttachmentInfo permissionAttachment : player.getEffectivePermissions()) {
            String permission = permissionAttachment.getPermission();

            if(permission.startsWith(permissionRequest.getPrefix())) {
                plugin.getPluginMessageManager("CubespaceLibrary").sendPluginMessage(player, new PermissionResponse(permission, 1));
            }
        }

        plugin.getPluginMessageManager("CubespaceLibrary").sendPluginMessage(player, new PermissionResponse("", 2));
    }
}
