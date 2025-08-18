package dev.anvilcraft.addon.jeac.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler {
    public static void init(RegistrateLangProvider provider) {
        provider.add("jei.jeac.fluid.count", "%s mB");
        provider.add("jei.jeac.fluid.fire", "Need to be ignited");
    }
}
