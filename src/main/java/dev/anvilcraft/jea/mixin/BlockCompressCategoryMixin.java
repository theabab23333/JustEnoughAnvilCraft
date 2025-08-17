package dev.anvilcraft.jea.mixin;

import dev.anvilcraft.jea.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.BlockCompressCategory;
import dev.dubhe.anvilcraft.integration.jei.util.JeiRenderHelper;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
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

@Mixin(BlockCompressCategory.class)
public abstract class BlockCompressCategoryMixin {

    @Shadow @Final private ITickTimer timer;

    @Shadow @Final private IDrawable progress;

    @Inject(method = "setRecipe*", at = @At("HEAD"))
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<BlockCompressRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        BlockCompressRecipe recipe = recipeHolder.value();
        JeaSlotUtil.addInputSlots(builder, recipe);
        JeaSlotUtil.drawOutputSlots(builder, recipe);
    }

    @Inject(method = "draw*", at = @At("HEAD"), cancellable = true)
    public void draw(
        RecipeHolder<BlockCompressRecipe> recipeHolder,
        IRecipeSlotsView recipeSlotsView,
        GuiGraphics guiGraphics,
        double mouseX,
        double mouseY,
        CallbackInfo ci) {
        BlockCompressRecipe recipe = recipeHolder.value();

        float anvilYOffset = JeiRenderHelper.getAnvilAnimationOffset(this.timer);
        this.progress.draw(guiGraphics, 73, 30);

        RenderHelper.renderBlock(
            guiGraphics,
            Blocks.ANVIL.defaultBlockState(),
            60,
            12 + anvilYOffset,
            20,
            12,
            RenderHelper.SINGLE_BLOCK
        );

        for (int i = recipe.getInputs().size() - 1; i >= 0; i--) {
            List<BlockState> input = recipe.getInputs().get(i).constructStatesForRender();
            if (input.isEmpty()) continue;
            BlockState renderedState = input.get((int) ((System.currentTimeMillis() / 1000) % input.size()));
            if (renderedState == null) continue;
            RenderHelper.renderBlock(
                guiGraphics,
                renderedState,
                60,
                30 + 10 * i,
                10 - 10 * i,
                12,
                RenderHelper.SINGLE_BLOCK);
        }

        RenderHelper.renderBlock(
            guiGraphics, Blocks.ANVIL.defaultBlockState(), 110, 30, 10, 12, RenderHelper.SINGLE_BLOCK);
        RenderHelper.renderBlock(
            guiGraphics, recipe.getResults().get((int) ((System.currentTimeMillis() / 1000) % recipe.getResults().size())).getState(),
            110, 40, 0, 12, RenderHelper.SINGLE_BLOCK);
        ci.cancel();
    }
}
