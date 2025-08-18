package dev.anvilcraft.addon.jeac.mixin;

import dev.anvilcraft.addon.jeac.util.RecipeUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.ItemInjectCategory;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInjectCategory.class)
public class ItemInjectCategoryMixin {

    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<ItemInjectRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        RecipeUtil.findItemInjectCategory(builder, recipeHolder.value());
    }
}
