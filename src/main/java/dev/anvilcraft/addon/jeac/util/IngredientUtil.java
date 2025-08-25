package dev.anvilcraft.addon.jeac.util;

import dev.anvilcraft.lib.recipe.component.ItemIngredientPredicate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public class IngredientUtil {
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
