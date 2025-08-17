package dev.anvilcraft.jea.util;

import dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil;
import dev.dubhe.anvilcraft.recipe.anvil.util.BlockStatePredicate;
import dev.dubhe.anvilcraft.recipe.anvil.util.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceBlockState;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceItemStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class JeaSlotUtil {
    // 来自本体的JeiSlotUtil

    public static void addInputSlots(
        IRecipeLayoutBuilder builder,
        BlockCompressRecipe recipe
    ) {
        List<ItemIngredientPredicate> ingerdientList = new ArrayList<>();
        for (BlockStatePredicate blockStatePredicate : recipe.getInputs()) {
            for (BlockState blockState : blockStatePredicate.constructStatesForRender()) {
                Block block = blockState.getBlock();
                Item item = block.asItem();
                ingerdientList.addAll(ingredientPredicateList(item));
            }
        }
        JeiSlotUtil.addInputSlots(builder, ingerdientList);
    }

    public static void drawOutputSlots(
        IRecipeLayoutBuilder builder
    ) {

    }

    public static void drawOutputSlots(
        IRecipeLayoutBuilder builder,
        ChanceBlockState chanceBlockState
    ) {

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
