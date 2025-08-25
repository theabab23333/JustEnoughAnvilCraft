package dev.anvilcraft.addon.jeac.util;

import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.recipe.component.HasCauldronSimple;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.CauldronFluidContent;

public class CauldronFluidUtil {
    public static CauldronFluidContent getCauldronFluidContent(HasCauldronSimple cauldronSimple) {
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(cauldronSimple.getTransformCauldron());
        assert cauldronFluidContent != null;
        return cauldronFluidContent;
    }

    public static CauldronFluidContent getCauldronFluidContent(Block block) {
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) {
            throw new IllegalArgumentException("No CauldronFluidContent found for block: " + block);
        }
        return cauldronFluidContent;
    }

    public static Fluid getFluidInHasCauldronSimple(HasCauldronSimple cauldronSimple) {
        CauldronFluidContent cauldronFluidContent = getCauldronFluidContent(cauldronSimple);
        return cauldronFluidContent.fluid;
    }

    public static Fluid getFluidInBlock(Block block) {
        if (!isFluid(block)) return Fluids.EMPTY;
        CauldronFluidContent cauldronFluidContent = getCauldronFluidContent(block);
        return cauldronFluidContent.fluid;
    }

    public static int getLevelInNowBlock(Block block) {
        return getCauldronFluidContent(block).currentLevel(block.defaultBlockState());
    }

    public static int getLevelInMaxBlock(Block block) {
        return getCauldronFluidContent(block).maxLevel;
    }

    public static boolean isFluid(Block block) {
        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON)) return false;
        if (block.defaultBlockState().is(ModBlocks.HONEY_CAULDRON)) return false;
        return true;
    }
}
