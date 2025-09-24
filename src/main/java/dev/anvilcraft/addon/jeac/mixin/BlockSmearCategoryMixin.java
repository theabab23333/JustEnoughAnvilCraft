package dev.anvilcraft.addon.jeac.mixin;

import dev.dubhe.anvilcraft.integration.jei.category.anvil.BlockSmearCategory;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockSmearRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(BlockSmearCategory.class)
public class BlockSmearCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<BlockSmearRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        builder.addInputSlot(21, 30).addIngredients(
            Ingredient.of(recipeHolder.value().getInputBlocks().stream().flatMap(
                blockStatePredicate -> Arrays.stream(blockStatePredicate.getBlocks().stream().map(
                    blockHolder -> new ItemStack(blockHolder.value().asItem())
                ).toArray(ItemStack[]::new))
            ))
        );

        builder.addOutputSlot(123, 30).addIngredients(
            Ingredient.of(recipeHolder.value().getResultBlocks().stream().map(
                chanceBlockState -> new ItemStack(chanceBlockState.state().getBlock().asItem())
            ))
        );
    }
}
