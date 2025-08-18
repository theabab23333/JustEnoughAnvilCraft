package dev.anvilcraft.addon.jeac;

import com.tterrag.registrate.Registrate;
import dev.anvilcraft.addon.jeac.data.ModDataGen;
import net.neoforged.fml.common.Mod;

@Mod(JustEnoughAnvilCraft.MOD_ID)
public class JustEnoughAnvilCraft {
    public static final String MOD_ID = "jeac";
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

    public JustEnoughAnvilCraft() {
        ModDataGen.init();
    }
}
