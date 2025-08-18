package dev.anvilcraft.addon.jeac.mixin;

import dev.anvilcraft.addon.jeac.util.RecipeUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.ItemInjectCategory;
import dev.dubhe.anvilcraft.integration.jei.util.JeiRenderHelper;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import dev.dubhe.anvilcraft.util.RenderHelper;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemInjectCategory.class)
public class ItemInjectCategoryMixin {

    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<ItemInjectRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        RecipeUtil.findItemInjectCategorySlots(builder, recipeHolder.value());
    }
}
