package dev.anvilcraft.addon.jeac.mixin;

import dev.anvilcraft.addon.jeac.util.RecipeUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.BlockCompressCategory;
import dev.dubhe.anvilcraft.integration.jei.util.BlockTagUtil;
import dev.dubhe.anvilcraft.integration.jei.util.JeiRenderHelper;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
import dev.dubhe.anvilcraft.util.RenderHelper;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
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
        RecipeUtil.findBlockCompressCategory(builder, recipeHolder.value());
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
        this.progress.draw(guiGraphics, 74, 30);

        RenderHelper.renderBlock(
            guiGraphics,
            Blocks.ANVIL.defaultBlockState(),
            60,
            12 + anvilYOffset,
            20,
            12,
            RenderHelper.SINGLE_BLOCK
        );

        for (int i = recipe.getInputBlocks().size() - 1; i >= 0; i--) {
            List<BlockState> input = recipe.getInputBlocks().get(i).constructStatesForRender();
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
            guiGraphics, recipe.getFirstResultBlock().getState(), 110, 40, 0, 12, RenderHelper.SINGLE_BLOCK
        );
        ci.cancel();
    }

    @Inject(method = "getTooltip*", at = @At("HEAD"), cancellable = true)
    public void getTooltip(
        ITooltipBuilder tooltip,
        RecipeHolder<BlockCompressRecipe> recipeHolder,
        IRecipeSlotsView recipeSlotsView,
        double mouseX,
        double mouseY,
        CallbackInfo ci
    ) {
        BlockCompressRecipe recipe = recipeHolder.value();
        if (mouseX >= 50 && mouseX <= 68) {
            if (mouseY >= 24 && mouseY < 42) {
                tooltip.addAll(BlockTagUtil.getTooltipsForInput(recipe.getInputBlocks().getFirst()));
            }
            if (mouseY >= 42 && mouseY <= 52) {
                tooltip.addAll(BlockTagUtil.getTooltipsForInput(recipe.getInputBlocks().getLast()));
            }
        }
        if (mouseX >= 110 && mouseX <= 130) {
            if (mouseY >= 42 && mouseY <= 52) {
                tooltip.add(recipe.getFirstResultBlock().getState().getBlock().getName());
            }
        }
        ci.cancel();
    }
}
