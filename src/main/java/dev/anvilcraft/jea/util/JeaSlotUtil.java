package dev.anvilcraft.jea.util;

import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.integration.jei.recipe.CementStainingRecipe;
import dev.dubhe.anvilcraft.integration.jei.recipe.ColoredConcreteRecipe;
import dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil;
import dev.dubhe.anvilcraft.recipe.anvil.util.BlockStatePredicate;
import dev.dubhe.anvilcraft.recipe.anvil.util.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BulgingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceBlockState;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceItemStack;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.CauldronFluidContent;

import java.util.ArrayList;
import java.util.List;

import static dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil.addSlotWithCount;

public class JeaSlotUtil {
    // 部分(大多数)代码来自本体JeiSlotUtil

    public static void addInputSlots(
        IRecipeLayoutBuilder builder,
        List<BlockStatePredicate> blockStatePredicateList
    ) {
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
                int startX = 11;
                int startY = 15;
                int row = index / 2;
                int col = index % 2;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (index > 12) break;
                int startX = 0;
                int startY = 0;
                int row = index / 3;
                int col = index % 3;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        }
    }

    public static void addInputSlots(
        IRecipeLayoutBuilder builder,
        BlockStatePredicate blockStatePredicate
    ) {
        List<BlockStatePredicate> predicateList = new ArrayList<>();
        predicateList.add(blockStatePredicate);
        addInputSlots(builder, predicateList);
    }

    public static void addOutputSlots(
        IRecipeLayoutBuilder builder,
        List<ChanceBlockState> itemStackList
    ) {
        List<ChanceItemStack> chanceItemStacks = new ArrayList<>();
        for (ChanceBlockState chanceBlockState : itemStackList) {
            BlockState blockState = chanceBlockState.getState();
            Block block = blockState.getBlock();
            Item item = block.asItem();
            ItemStack itemStack = new ItemStack(item);
            ChanceItemStack chanceItemStack = ChanceItemStack.of(itemStack);
            chanceItemStacks.add(chanceItemStack);
            JeiSlotUtil.addOutputSlots(builder, chanceItemStacks);
        }
    }

    public static void addBulgingCategoryFluidSlots(
        IRecipeLayoutBuilder builder,
        BulgingRecipe recipe
    ) {
        HasCauldronSimple cauldronSimple = recipe.getHasCauldron();
        Block block = cauldronSimple.getFluidCauldron();
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        int consume = 1000;
        if (recipe.isConsumeFluid()) {
            consume = 250;
        }
        if (recipe.isFromWater()) {
            builder.addInputSlot(47, 37).addFluidStack(fluid, consume);
        } else if (recipe.isProduceFluid()) {
            builder.addOutputSlot(107, 37).addFluidStack(fluid);
        }
    }

    public static void addCementStainingCategoryFluidSlots(
        IRecipeLayoutBuilder builder,
        CementStainingRecipe recipe
    ) {
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(recipe.resultBlock());
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        builder.addOutputSlot(107, 37).addFluidStack(fluid);
    }

    public static void addConcreteCategoryFluidSlots(
        IRecipeLayoutBuilder builder,
        ColoredConcreteRecipe recipe
    ) {
        BlockState blockState = ModBlocks.CEMENT_CAULDRONS.get(recipe.color()).getDefaultState();
        Block block = blockState.getBlock();
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        builder.addInputSlot(47, 37).addFluidStack(fluid);
    }

    @SuppressWarnings("deprecation")
    public static void tryRenderFluidStack(
        IRecipeLayoutBuilder builder,
        HasCauldronSimple cauldronSimple
    ) {
        if (cauldronSimple == null) return;
        Block block = cauldronSimple.getFluidCauldron();
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        int amount = cauldronSimple.getConsume();
        if (amount == 0) amount = 1000;
        builder.addInputSlot(0, 0).addFluidStack(fluid, amount);
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemStack itemStack) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(itemStack).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (TagKey<Item> ingredient, int count) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(ingredient).withCount(count).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (TagKey<Item> ingredient) {
        return ingredientPredicateList(ingredient, 1);
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemLike ingredient, int count) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(ingredient).withCount(count).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemLike ingredient) {
        return ingredientPredicateList(ingredient, 1);
    }
}
