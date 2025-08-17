package dev.anvilcraft.jea.mixin;

import dev.anvilcraft.jea.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.AbstractProgressCategory;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractProgressCategory.class)
public abstract class AbstractProgressCategoryMixin<T extends AbstractProcessRecipe<?>> implements IRecipeCategory<RecipeHolder<T>> {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<T> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        HasCauldronSimple hasCauldronSimple = recipeHolder.value().getHasCauldron();
        JeaSlotUtil.addAbstractProgressCategoryFluidSlots(builder, hasCauldronSimple);
    }
}
