package dev.anvilcraft.addon.jeac.util;

import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.integration.jei.recipe.CementStainingRecipe;
import dev.dubhe.anvilcraft.integration.jei.recipe.ColoredConcreteRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCrushRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BulgingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.SqueezingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.TimeWarpRecipe;
import dev.dubhe.anvilcraft.util.CauldronUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import static dev.anvilcraft.addon.jeac.util.JeaSlotUtil.*;
import static dev.anvilcraft.addon.jeac.util.JeaSlotUtil.addFluidStackOutputSlots;

public class RecipeUtil {
    public static <T extends AbstractProcessRecipe<?>> void findAbstractHasCauldron(IRecipeLayoutBuilder builder, RecipeHolder<T> recipe) {
        if (recipe.value().getHasCauldron() != null) addFluidStackOutputSlots(builder, recipe.value().getHasCauldron(), 125, 41);
    }

    public static void findBulgingCategory(IRecipeLayoutBuilder builder, BulgingRecipe recipe) {
        if (recipe.getHasCauldron() != null) {
            if (recipe.isConsumeFluid()) addFluidStackInputSlots(builder, recipe.getHasCauldron().getFluidCauldron(), 21, 41);
        }
    }

    public static void findCementStainingCategory(IRecipeLayoutBuilder builder, CementStainingRecipe recipe) {
        if (recipe.resultBlock() != null) {
            addFluidStackOutputSlots(builder, recipe.resultBlock(), 125, 41);
        }
    }

    public static void findConcreteCategory(IRecipeLayoutBuilder builder, ColoredConcreteRecipe recipe) {
        BlockState blockState = ModBlocks.CEMENT_CAULDRONS.get(recipe.color()).getDefaultState();
        addFluidStackInputSlots(builder, blockState.getBlock(), 46, 36);
    }

    public static void findItemInjectCategory(IRecipeLayoutBuilder builder, ItemInjectRecipe recipe) {
        addBlockStatePredicateInputSlots(builder, recipe.getInputBlocks(), 21, 41);
        addChanceBlockStateInputSlots(builder, recipe.getResultBlocks(), 125, 41);
    }

    public static void findSqueezingCategory(IRecipeLayoutBuilder builder, SqueezingRecipe recipe) {
        addBlockStatePredicateInputSlots(builder, recipe.getInputBlocks(), 24, 20);
        addChanceBlockStateInputSlots(builder, recipe.getResultBlocks(), 120, 20);
        addFluidStackOutputSlots(builder, recipe.getHasCauldron().getTransformCauldron(), 120, 40);
    }

    public static void findTimeWarpCategory(IRecipeLayoutBuilder builder, TimeWarpRecipe recipe) {
        if (recipe.isConsumeFluid()) {

            Block result = recipe.getHasCauldron().getFluidCauldron();
            BlockState cauldronState;
            cauldronState = CauldronUtil.getStateFromContentAndLevel(result, CauldronUtil.maxLevel(result) - 1);
            if (cauldronState.is(Blocks.CAULDRON)) cauldronState = CauldronUtil.fullState(result);
            addFluidStackInputSlots(builder, cauldronState.getBlock(), 46, 36);
        } else if (recipe.isProduceFluid()) {
            Block result = recipe.getHasCauldron().getTransformCauldron();
            BlockState cauldronState = CauldronUtil.getStateFromContentAndLevel(result, 1);
            addFluidStackOutputSlots(builder, cauldronState.getBlock(), 125, 41);
        }
    }

    public static void findBlockCompressCategory(IRecipeLayoutBuilder builder, BlockCompressRecipe recipe) {
        addBlockStatePredicateInputSlots(builder, recipe.getInputBlocks(), 15, 14);
        addChanceBlockStateInputSlots(builder, recipe.getResultBlocks(), 120, 14);
    }

    public static void findBlockCrushCategory(IRecipeLayoutBuilder builder, BlockCrushRecipe recipe) {
        addBlockStatePredicateInputSlots(builder, recipe.getInputBlocks(), 23, 35);
        addChanceBlockStateInputSlots(builder, recipe.getResultBlocks(), 121, 35);
    }
}
