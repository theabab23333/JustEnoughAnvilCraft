package dev.anvilcraft.jea.mixin;

import dev.dubhe.anvilcraft.integration.jei.category.anvil.AbstractProgressCategory;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractProgressCategory.class)
public abstract class AbstractProgressCategoryMixin<T extends AbstractProcessRecipe<?>> implements IRecipeCategory<RecipeHolder<T>> {

}
