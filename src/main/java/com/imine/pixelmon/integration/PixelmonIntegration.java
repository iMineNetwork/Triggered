package com.imine.pixelmon.integration;

import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.UUID;

public class PixelmonIntegration {

    private static final Logger logger = LoggerFactory.getLogger(PixelmonIntegration.class);

    public static boolean hasPlayerRegisteredPixelmonAsCaughtInPokedex(Player player, int id) {
        Optional<PlayerStorage> oPlayerStorage = PixelmonStorage.pokeBallManager.getPlayerStorage(getEntityMPPlayer(player.getUniqueId()));
        return oPlayerStorage.map(playerStorage -> playerStorage.pokedex.hasCaught(id)).orElse(false);
    }

    public static int getPlayerPixelmonCount(Player player) {
        Optional<PlayerStorage> oPlayerStorage = PixelmonStorage.pokeBallManager.getPlayerStorage(getEntityMPPlayer(player.getUniqueId()));
        return oPlayerStorage.map(PlayerStorage::countTeam).orElse(0);
    }

    public static EntityPlayerMP getEntityMPPlayer(UUID uuid) {
        MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        return PixelmonStorage.pokeBallManager.getPlayerFromUUID(minecraftServer, uuid);
    }
}
