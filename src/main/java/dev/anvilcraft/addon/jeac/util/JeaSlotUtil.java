package dev.anvilcraft.addon.jeac.util;

import com.google.common.collect.ImmutableList;
import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import static dev.anvilcraft.addon.jeac.util.CauldronFluidUtil.*;

public class JeaSlotUtil {
    // 部分(大多数)代码来自本体JeiSlotUtil

    public static void addFluidAmountTooltips(IRecipeSlotBuilder slot, int count) {
        ImmutableList.Builder<Component> tooltipLines = new ImmutableList.Builder<>();
        tooltipLines.add(Component.translatable("jei.jeac.fluid.count", count));
        slot.addRichTooltipCallback((slotView, tooltip) -> tooltip.addAll(tooltipLines.build()));
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, int x, int y, HasCauldronSimple cauldronSimple) {
        Fluid fluid = getFluidInHasCauldronSimple(cauldronSimple);
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addInputSlot(x, y).addFluidStack(fluid);
        addFluidAmountTooltips(slot, 1000);
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, int x, int y, Block block) {
        if (isFluid(block)) {
            Fluid fluid = getFluidInBlock(block);
            if (fluid == null || fluid == Fluids.EMPTY) return;
            int nowLevel = getLevelInNowBlock(block);
            int maxLevel = getLevelInMaxBlock(block);
            float levelAmount = (float) 1000 / maxLevel;
            int nowAmount = (int) (levelAmount * nowLevel);
            IRecipeSlotBuilder slot = builder.addInputSlot(x, y).addFluidStack(fluid);
            addFluidAmountTooltips(slot, nowAmount);
        } else {
            if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON)) {
                addItemStackInputSlots(builder, Items.POWDER_SNOW_BUCKET.getDefaultInstance(), x, y);
            } else if (block.defaultBlockState().is(ModBlocks.HONEY_CAULDRON)) {
                addItemStackInputSlots(builder, Items.HONEY_BOTTLE.getDefaultInstance(), x, y);
            }
        }
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, int x, int y, HasCauldronSimple cauldronSimple) {
        Fluid fluid = getFluidInHasCauldronSimple(cauldronSimple);
        int consume = cauldronSimple.getConsume();
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(fluid);
        if (consume > 0) {
            addFluidAmountTooltips(slot, 1000);
        }
    }

    public static void addItemStackInputSlots(IRecipeLayoutBuilder builder, ItemStack itemStack, int x, int y) {
        builder.addInputSlot(x, y).addItemStack(itemStack);
    }
}
