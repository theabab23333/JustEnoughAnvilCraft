package dev.anvilcraft.addon.jeac.util;

import com.google.common.collect.ImmutableList;
import dev.anvilcraft.lib.recipe.component.BlockStatePredicate;
import dev.anvilcraft.lib.recipe.component.ChanceBlockState;
import dev.anvilcraft.lib.recipe.component.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.block.ModFluids;
import dev.dubhe.anvilcraft.recipe.component.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

import static dev.anvilcraft.addon.jeac.util.CauldronFluidUtil.*;
import static dev.anvilcraft.addon.jeac.util.IngredientUtil.*;
import static dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil.*;

public class JeaSlotUtil {

    // Do you like my special judgment?

    public static void addFluidAmountTooltips(IRecipeSlotBuilder slot, int count) {
        ImmutableList.Builder<Component> tooltipLines = new ImmutableList.Builder<>();
        tooltipLines.add(Component.translatable("jei.jeac.fluid.count", count));
        slot.addRichTooltipCallback((slotView, tooltip) -> tooltip.addAll(tooltipLines.build()));
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, HasCauldronSimple cauldronSimple, int x, int y) {
        Fluid fluid = getFluidInHasCauldronSimple(cauldronSimple);
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addInputSlot(x, y).addFluidStack(fluid);
        addFluidAmountTooltips(slot, 1000);
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, Block block, int x, int y) {
        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON)) {
            addItemStackInputSlots(builder, Items.POWDER_SNOW_BUCKET.getDefaultInstance(), x, y);
        } else if (block.defaultBlockState().is(ModBlocks.HONEY_CAULDRON)) {
            addItemStackInputSlots(builder, Items.HONEY_BOTTLE.getDefaultInstance(), x, y);
        } else if (block.defaultBlockState().is(ModBlocks.FIRE_CAULDRON)) {
            IRecipeSlotBuilder itemSlot = builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y - 32)
                .addItemStack(Items.FLINT_AND_STEEL.getDefaultInstance());
            itemSlot.addRichTooltipCallback((slotView, tooltip) ->
                tooltip.add(Component.translatable("jei.jeac.fluid.fire")));
            IRecipeSlotBuilder fluidSlot = builder.addInputSlot(x, y).addFluidStack(ModFluids.OIL.get());
            addFluidAmountTooltips(fluidSlot, 1000);
        } else if (isFluid(block)) {
            Fluid fluid = getFluidInBlock(block);
            if (fluid == null || fluid == Fluids.EMPTY) return;
            int nowLevel = getLevelInNowBlock(block);
            int maxLevel = getLevelInMaxBlock(block);
            float levelAmount = (float) 1000 / maxLevel;
            int nowAmount = (int) (levelAmount * nowLevel);
            IRecipeSlotBuilder slot = builder.addInputSlot(x, y).addFluidStack(fluid);
            addFluidAmountTooltips(slot, nowAmount);
        }
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, HasCauldronSimple cauldronSimple, int x, int y) {
        Fluid fluid = getFluidInHasCauldronSimple(cauldronSimple);
        int consume = cauldronSimple.consume();
        if (fluid == Fluids.EMPTY) return;
        IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(fluid);
        if (consume > 0) {
            addFluidAmountTooltips(slot, 1000);
        }
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, Block block, int x, int y) {
        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON)) {
            addItemStackOutputSlots(builder, Items.POWDER_SNOW_BUCKET.getDefaultInstance(), x, y);
        } else if (block.defaultBlockState().is(ModBlocks.HONEY_CAULDRON)) {
            addItemStackOutputSlots(builder, Items.HONEY_BOTTLE.getDefaultInstance(), x, y);
        } else if (block.defaultBlockState().is(ModBlocks.LAVA_CAULDRON)) {
            IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(Fluids.LAVA);
            addFluidAmountTooltips(slot, 250);
        } else if (block.defaultBlockState().is(ModBlocks.FIRE_CAULDRON)) {
            addItemStackOutputSlots(builder, Items.FLINT_AND_STEEL.getDefaultInstance(), x, y);
            IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(ModFluids.OIL.get());
            addFluidAmountTooltips(slot, 250);
        } else if (isFluid(block)) {
            Fluid fluid = getFluidInBlock(block);
            if (fluid == null || fluid == Fluids.EMPTY) return;
            int nowLevel = getLevelInNowBlock(block);
            int maxLevel = getLevelInMaxBlock(block);
            float levelAmount = (float) 1000 / maxLevel;
            int nowAmount = (int) (levelAmount * nowLevel);
            IRecipeSlotBuilder slot = builder.addOutputSlot(x, y).addFluidStack(fluid);
            addFluidAmountTooltips(slot, nowAmount);
        }
    }

    public static void addItemStackInputSlots(IRecipeLayoutBuilder builder, ItemStack itemStack, int x, int y) {
        builder.addInputSlot(x, y).addItemStack(itemStack);
    }

    public static void addItemStackOutputSlots(IRecipeLayoutBuilder builder, ItemStack itemStack, int x, int y) {
        builder.addInputSlot(x, y).addItemStack(itemStack);
    }

    public static void addBlockStatePredicateInputSlots(IRecipeLayoutBuilder builder, List<BlockStatePredicate> blockStatePredicateList, int startX, int startY) {
        List<ItemIngredientPredicate> ingerdientList = new ArrayList<>();
        for (BlockStatePredicate blockStatePredicate : blockStatePredicateList) {
            for (BlockState blockState : blockStatePredicate.constructStatesForRender()) {
                Block block = blockState.getBlock();
                Item item = block.asItem();
                ingerdientList.addAll(ingredientPredicateList(item));
            }
        }
        int size = ingerdientList.size();
        if (size == 0) return;
        if (size <= 4) {
            for (int index = 0; index < size; index++) {
                int row = index / 2;
                int col = index % 2;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (index > 12) break;
                startX = 0;
                startY = 0;
                int row = index / 3;
                int col = index % 3;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        }
    }

    public static void addChanceBlockStateInputSlots(IRecipeLayoutBuilder builder, List<ChanceBlockState> chanceBlockStates, int x, int y) {
        for (ChanceBlockState chanceBlockState : chanceBlockStates) {
            Item item = chanceBlockState.state().getBlock().asItem();
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(item.getDefaultInstance());
        }
    }
}
