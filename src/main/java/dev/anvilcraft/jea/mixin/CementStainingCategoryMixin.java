package dev.anvilcraft.jea.mixin;

import dev.anvilcraft.jea.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.CementStainingCategory;
import dev.dubhe.anvilcraft.integration.jei.recipe.CementStainingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CementStainingCategory.class)
public class CementStainingCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        CementStainingRecipe recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        JeaSlotUtil.addCementStainingCategoryFluidSlots(builder, recipeHolder);
    }
}
