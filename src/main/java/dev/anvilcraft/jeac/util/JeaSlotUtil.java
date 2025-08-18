package dev.anvilcraft.jeac.util;

import com.google.common.collect.ImmutableList;
import dev.dubhe.anvilcraft.recipe.anvil.util.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.CauldronFluidContent;

import java.util.ArrayList;
import java.util.List;

import static dev.anvilcraft.jeac.util.CauldronFluidUtil.getFluidInCauldronFluidContent;

public class JeaSlotUtil {
    // 部分(大多数)代码来自本体JeiSlotUtil

    public static void addTooltips(IRecipeSlotBuilder slot, int count) {
        ImmutableList.Builder<Component> tooltipLines = new ImmutableList.Builder<>();
        tooltipLines.add(Component.translatable("jei.jeac.fluid.count", count));
        slot.addRichTooltipCallback((slotView, tooltip) -> tooltip.addAll(tooltipLines.build()));
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, int x, int y, HasCauldronSimple cauldronSimple) {
        Fluid fluid = getFluidInCauldronFluidContent(cauldronSimple);
        int consume = cauldronSimple.getConsume();
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addInputSlot(x, y).addFluidStack(fluid);
        if (consume < 0) {
            addTooltips(slot, 1000);
        }
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, int x, int y, HasCauldronSimple cauldronSimple) {
        Fluid fluid = getFluidInCauldronFluidContent(cauldronSimple);
        int consume = cauldronSimple.getConsume();
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(fluid);
        if (consume > 0) {
            addTooltips(slot, 1000);
        }
    }
}
