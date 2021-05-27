package lootcrate.events.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import net.md_5.bungee.api.ChatColor;

public class PlayerChatListener implements Listener
{

    private LootCrate plugin;

    public PlayerChatListener(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
	Player p = e.getPlayer();

	if (!plugin.chatManager.hasState(p))
	    return;

	ChatState state = plugin.chatManager.getState(p);
	Crate crate = state.getCrate();

	switch (state)
	{
	case CHANGE_CRATE_NAME:
	    crate.setName(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
	    break;
	case CHANGE_CRATE_MESSAGE:
	    crate.setOption(new CrateOption(CrateOptionType.OPEN_MESSAGE,
		    ChatColor.translateAlternateColorCodes('&', e.getMessage())));
	    break;
	case CHANGE_CRATE_SOUND:
	    Sound sound = Sound.valueOf(e.getMessage());
	    if (sound == null)
		return;
	    crate.setOption(new CrateOption(CrateOptionType.OPEN_SOUND, sound.toString()));
	    p.playSound(p.getLocation(), sound, 1, 1);
	    break;
	default:
	    return;
	}

	e.setCancelled(true);
	plugin.cacheManager.update(crate);
	plugin.chatManager.removePlayer(p);

    }

}
