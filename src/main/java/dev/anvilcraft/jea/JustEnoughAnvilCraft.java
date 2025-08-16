package dev.anvilcraft.jea;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import dev.anvilcraft.jea.data.ModDatagen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(JustEnoughAnvilCraft.MOD_ID)
public class JustEnoughAnvilCraft {
    public static final String MOD_ID = "just_enough_anvilcraft";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

    public JustEnoughAnvilCraft(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {
        ModDatagen.init();
    }

    public static @NotNull ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
