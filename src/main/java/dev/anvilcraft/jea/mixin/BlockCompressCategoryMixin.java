package dev.anvilcraft.jea.mixin;

import dev.anvilcraft.jea.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.BlockCompressCategory;
import dev.dubhe.anvilcraft.recipe.anvil.util.BlockStatePredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceBlockState;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BlockCompressCategory.class)
public class BlockCompressCategoryMixin {
    @Inject(method = "setRecipe*", at = @At("TAIL"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<BlockCompressRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        BlockCompressRecipe recipe = recipeHolder.value();
        JeaSlotUtil.addInputSlots(builder, recipe);
        JeaSlotUtil.drawOutputSlots(builder, recipe);
    }
}
