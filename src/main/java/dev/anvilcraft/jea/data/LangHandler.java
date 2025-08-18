package dev.anvilcraft.jea.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler {
    public static void init(RegistrateLangProvider provider) {
        provider.add("jei.jeac.fluid.count", "%s mB");
    }
}
