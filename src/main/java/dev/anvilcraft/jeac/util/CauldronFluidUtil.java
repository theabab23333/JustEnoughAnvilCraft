package dev.anvilcraft.jeac.util;

import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.CauldronFluidContent;

public class CauldronFluidUtil {
    public static CauldronFluidContent getCauldronFluidContent(HasCauldronSimple cauldronSimple) {
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(cauldronSimple.getTransformCauldron());
        assert cauldronFluidContent != null;
        return cauldronFluidContent;
    }

    public static Fluid getFluidInCauldronFluidContent(HasCauldronSimple cauldronSimple) {
        CauldronFluidContent cauldronFluidContent = getCauldronFluidContent(cauldronSimple);
        return cauldronFluidContent.fluid;
    }
}
