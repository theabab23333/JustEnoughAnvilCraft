package dev.anvilcraft.addon.jeac.mixin;

import dev.dubhe.anvilcraft.integration.jei.category.AnvilCollisionCraftCategory;
import dev.dubhe.anvilcraft.recipe.anvil.collision.AnvilCollisionCraftRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilCollisionCraftCategory.class)
public class AnvilCollisionCraftCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<AnvilCollisionCraftRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        AnvilCollisionCraftRecipe recipe = recipeHolder.value();
        builder.addInputSlot(72, 0).addIngredients(
            Ingredient.of(recipe.hitBlock().getBlocks().stream().map(
                blockHolder -> new ItemStack(blockHolder.value().asItem()))
            )
        );

        if (!recipe.transformBlocks().isEmpty()) {
            builder.addOutputSlot(130, 43).addIngredients(
                Ingredient.of(recipe.transformBlocks().getLast().outputBlock().state().getBlock().asItem())
            );

            builder.addInputSlot(130, 3).addIngredients(
                Ingredient.of(recipe.transformBlocks().getLast().inputBlock().getBlocks().stream().map(
                    blockHolder -> new ItemStack(blockHolder.value().asItem())
                ))
            );
        }
    }
}
