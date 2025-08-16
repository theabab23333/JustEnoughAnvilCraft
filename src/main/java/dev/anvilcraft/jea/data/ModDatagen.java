package dev.anvilcraft.jea.data;

import com.tterrag.registrate.providers.ProviderType;
import dev.anvilcraft.jea.JustEnoughAnvilCraft;
import dev.anvilcraft.jea.data.lang.LangHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static dev.anvilcraft.jea.JustEnoughAnvilCraft.REGISTRATE;

@EventBusSubscriber(modid = JustEnoughAnvilCraft.MOD_ID)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {}

    /**
     * 初始化生成器
     */
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
