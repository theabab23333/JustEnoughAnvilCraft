package dev.anvilcraft.jeac.mixin;

import dev.anvilcraft.jeac.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.ConcreteCategory;
import dev.dubhe.anvilcraft.integration.jei.recipe.ColoredConcreteRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConcreteCategory.class)
public class ConcreteCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        ColoredConcreteRecipe recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
//        JeaSlotUtil.addConcreteCategoryFluidSlots(builder, recipeHolder);
    }
}
