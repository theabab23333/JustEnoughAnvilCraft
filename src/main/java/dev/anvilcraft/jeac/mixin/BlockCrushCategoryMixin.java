package dev.anvilcraft.jeac.mixin;

import dev.anvilcraft.jeac.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.BlockCrushCategory;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCrushRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceBlockState;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockCrushCategory.class)
public abstract class BlockCrushCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("HEAD"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<BlockCrushRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        BlockCrushRecipe recipe = recipeHolder.value();
//        JeaSlotUtil.addBlockInputSlots(builder, recipe.getInputBlocks());
//        List<ChanceBlockState> chanceBlockStates = new ArrayList<>();
//        chanceBlockStates.add(recipe.getFirstResultBlock());
//        JeaSlotUtil.addOutputSlots(builder, chanceBlockStates, 120, 15);
    }
}
