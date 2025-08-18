package dev.anvilcraft.jeac.mixin;

import dev.anvilcraft.jeac.util.JeaSlotUtil;
import dev.dubhe.anvilcraft.integration.jei.category.anvil.ItemInjectCategory;
import dev.dubhe.anvilcraft.integration.jei.util.JeiRenderHelper;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import dev.dubhe.anvilcraft.util.RenderHelper;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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
    @Shadow @Final private ITickTimer timer;

    @Shadow @Final private IDrawable arrowIn;

    @Shadow @Final private IDrawable arrowOut;

    @Shadow @Final private IDrawable slot;

    @Inject(method = "setRecipe*", at = @At("HEAD"), cancellable = true)
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<ItemInjectRecipe> recipeHolder,
        IFocusGroup focuses,
        CallbackInfo ci) {
        ItemInjectRecipe recipe = recipeHolder.value();
        JeaSlotUtil.addItemInjectCategorySlots(builder, recipe);

        JeaSlotUtil.addItemInputSlots(builder, recipe.getInputItems());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT)
            .addIngredients(Ingredient.of(
                recipe.getFirstInputBlock().getBlocks().stream().map(state -> new ItemStack(state.value())).toArray(ItemStack[]::new)));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT)
            .addItemStack(new ItemStack(recipe.getFirstResultBlock().getState().getBlock()));
        ci.cancel();
    }

    @Inject(method = "draw*", at = @At("HEAD"), cancellable = true)
    public void draw(
        RecipeHolder<ItemInjectRecipe> recipeHolder,
        IRecipeSlotsView recipeSlotsView,
        GuiGraphics guiGraphics,
        double mouseX,
        double mouseY,
        CallbackInfo ci) {
        ItemInjectRecipe recipe = recipeHolder.value();
        float anvilYOffset = JeiRenderHelper.getAnvilAnimationOffset(timer);
        RenderHelper.renderBlock(
            guiGraphics,
            Blocks.ANVIL.defaultBlockState(),
            81,
            22 + anvilYOffset,
            20,
            12,
            RenderHelper.SINGLE_BLOCK);

        List<BlockState> input = recipe.getFirstInputBlock().constructStatesForRender();
        if (input.isEmpty()) return;
        BlockState renderedState = input.get((int) ((System.currentTimeMillis() / 1000) % input.size()));
        if (renderedState == null) return;
        RenderHelper.renderBlock(guiGraphics, renderedState, 81, 40, 10, 12, RenderHelper.SINGLE_BLOCK);

        arrowIn.draw(guiGraphics, 54, 32);
        arrowOut.draw(guiGraphics, 92, 31);

        slot.draw(guiGraphics, 34, 23);
        RenderHelper.renderBlock(
            guiGraphics, recipe.getFirstResultBlock().getState(), 118, 28, 0, 12, RenderHelper.SINGLE_BLOCK);
        ci.cancel();
    }
}
