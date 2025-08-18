package dev.anvilcraft.jeac.data;

import com.tterrag.registrate.providers.ProviderType;
import dev.anvilcraft.jeac.JustEnoughAnvilCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static dev.anvilcraft.jeac.JustEnoughAnvilCraft.REGISTRATE;

@EventBusSubscriber(modid = JustEnoughAnvilCraft.MOD_ID)
public class ModDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

    }

    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
