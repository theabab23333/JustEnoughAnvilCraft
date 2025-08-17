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

import static dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil.addSlotWithCount;

public class JeaSlotUtil {
    // 部分(大多数)代码来自本体JeiSlotUtil

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

        int size = ingerdientList.size();
        int startX = 0;
        int startY = 0;
        if (size == 0) return;
        if (size == 1) {
            ItemIngredientPredicate ingredient = ingerdientList.getFirst();
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 0, 0);
            slot.addIngredients(Ingredient.of(ingredient.getItems()));
        } else if (size <= 4) {
            for (int index = 0; index < size; index++) {
                int row = index / 2;
                int col = index % 2;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        } else {
            for (int index = 0; index < size; index++) {
                int row = index / 3;
                int col = index % 3;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        }
    }

    public static void drawOutputSlots(
        IRecipeLayoutBuilder builder,
        BlockCompressRecipe recipe
    ) {
        List<ChanceItemStack> chanceItemStacks = new ArrayList<>();
        for (ChanceBlockState chanceBlockState : recipe.getResults()) {
            BlockState blockState = chanceBlockState.getState();
            Block block = blockState.getBlock();
            Item item = block.asItem();
            ItemStack itemStack = new ItemStack(item);
            ChanceItemStack chanceItemStack = ChanceItemStack.of(itemStack);
            chanceItemStacks.add(chanceItemStack);
            JeiSlotUtil.addOutputSlots(builder, chanceItemStacks);
        }
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
