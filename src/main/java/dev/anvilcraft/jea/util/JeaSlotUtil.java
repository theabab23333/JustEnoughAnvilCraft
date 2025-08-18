package dev.anvilcraft.jea.util;

import com.google.common.collect.ImmutableList;
import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.integration.jei.recipe.CementStainingRecipe;
import dev.dubhe.anvilcraft.integration.jei.recipe.ColoredConcreteRecipe;
import dev.dubhe.anvilcraft.integration.jei.util.JeiRecipeUtil;
import dev.dubhe.anvilcraft.recipe.anvil.util.BlockStatePredicate;
import dev.dubhe.anvilcraft.recipe.anvil.util.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BulgingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.SqueezingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.TimeWarpRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.ChanceBlockState;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.components.HasCauldronSimple;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.CauldronFluidContent;

import java.util.ArrayList;
import java.util.List;

import static dev.dubhe.anvilcraft.integration.jei.util.JeiSlotUtil.addSlotWithCount;

public class JeaSlotUtil {
    // 部分(大多数)代码来自本体JeiSlotUtil

    public static void addTooltips(IRecipeSlotBuilder slot, int count) {
        ImmutableList.Builder<Component> tooltipLines = new ImmutableList.Builder<>();
        tooltipLines.add(Component.translatable("jei.jeac.fluid.count", count));
        slot.addRichTooltipCallback((slotView, tooltip) -> tooltip.addAll(tooltipLines.build()));
    }

    public static void addInputSlots(IRecipeLayoutBuilder builder, List<BlockStatePredicate> blockStatePredicateList) {
        List<ItemIngredientPredicate> ingerdientList = new ArrayList<>();
        for (BlockStatePredicate blockStatePredicate : blockStatePredicateList) {
            for (BlockState blockState : blockStatePredicate.constructStatesForRender()) {
                Block block = blockState.getBlock();
                Item item = block.asItem();
                ingerdientList.addAll(ingredientPredicateList(item));
            }
        }

        int size = ingerdientList.size();
        if (size == 0) return;
        if (size <= 4) {
            for (int index = 0; index < size; index++) {
                int startX = 11;
                int startY = 15;
                int row = index / 2;
                int col = index % 2;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (index > 12) break;
                int startX = 0;
                int startY = 0;
                int row = index / 3;
                int col = index % 3;
                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
            }
        }
    }

    public static void addInputSlots(IRecipeLayoutBuilder builder, List<BlockStatePredicate> blockStatePredicateList, int x, int y) {
        List<ItemIngredientPredicate> ingerdientList = new ArrayList<>();
        for (BlockStatePredicate blockStatePredicate : blockStatePredicateList) {
            for (BlockState blockState : blockStatePredicate.constructStatesForRender()) {
                Block block = blockState.getBlock();
                Item item = block.asItem();
                ingerdientList.addAll(ingredientPredicateList(item));
            }
        }
        int size = ingerdientList.size();
        if (size <= 4) {
            for (int index = 0; index < size; index++) {
                int row = index / 2;
                int col = index % 2;
                addSlotWithCount(builder, x + 16 * col, y + 16 * row, ingerdientList.get(index));
            }
        }
    }

    public static void addInputSlots(IRecipeLayoutBuilder builder, BlockStatePredicate blockStatePredicate) {
        List<BlockStatePredicate> predicateList = new ArrayList<>();
        predicateList.add(blockStatePredicate);
        addInputSlots(builder, predicateList);
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y, int amount) {
        if (amount == 0) {
            builder.addInputSlot(x, y).addFluidStack(fluid);
        } else {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, x, y).addFluidStack(fluid, amount);
            JeaSlotUtil.addTooltips(slot, amount);
        }
    }

    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y) {
        addFluidStackInputSlots(builder, fluid, x, y, 0);
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y, int amount) {
        if (amount == 0) {
            builder.addOutputSlot(x, y).addFluidStack(fluid);
        } else {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addFluidStack(fluid, amount);
            JeaSlotUtil.addTooltips(slot, amount);
        }
    }

    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y) {
        addFluidStackOutputSlots(builder, fluid, x, y, 0);
    }

    public static void addOutputSlots(IRecipeLayoutBuilder builder, List<ChanceBlockState> chanceBlockStates, int x, int y) {
        for (ChanceBlockState chanceBlockState : chanceBlockStates) {
            Item item = chanceBlockState.getState().getBlock().asItem();
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(item.getDefaultInstance());
            JeiRecipeUtil.addTooltips(slot, 1, chanceBlockState.getChance());
        }
    }

    public static void addBulgingCategoryFluidSlots(IRecipeLayoutBuilder builder, BulgingRecipe recipe) {
        HasCauldronSimple cauldronSimple = recipe.getHasCauldron();
        Block block = cauldronSimple.getFluidCauldron();
        // 不会写了 所以特判
        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON))
            builder.addInputSlot(47, 37).addItemLike(Items.POWDER_SNOW_BUCKET);
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        if (recipe.isFromWater()) {
            addFluidStackOutputSlots(builder, fluid, 47, 37);
        } else if (recipe.isConsumeFluid()) {
            addFluidStackInputSlots(builder, fluid, 47, 37, 333);
        }

        if (recipe.getResultItems().isEmpty()) {
            block = recipe.getHasCauldron().getTransformCauldron();
            cauldronFluidContent = CauldronFluidContent.getForBlock(block);
            if (cauldronFluidContent == null) return;
            fluid = cauldronFluidContent.fluid;
            addFluidStackOutputSlots(builder, fluid, 107, 37);
        }
    }

    public static void addCementStainingCategoryFluidSlots(IRecipeLayoutBuilder builder, CementStainingRecipe recipe) {
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(recipe.resultBlock());
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        addFluidStackOutputSlots(builder, fluid, 107, 37);
    }

    public static void addConcreteCategoryFluidSlots(IRecipeLayoutBuilder builder, ColoredConcreteRecipe recipe) {
        BlockState blockState = ModBlocks.CEMENT_CAULDRONS.get(recipe.color()).getDefaultState();
        Block block = blockState.getBlock();
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        addFluidStackInputSlots(builder, fluid, 47, 37);
    }

    public static void addItemInjectCategorySlots(IRecipeLayoutBuilder builder, ItemInjectRecipe recipe) {
        addInputSlots(builder, recipe.getInputBlocks(), 21, 42);
        if (!recipe.getResultBlocks().isEmpty()) addOutputSlots(builder, recipe.getResultBlocks(), 110, 42);
    }

    public static void addSqueezingCategorySlots(IRecipeLayoutBuilder builder, SqueezingRecipe recipe) {
        List<ChanceBlockState> chanceBlockStates = new ArrayList<>();
        chanceBlockStates.add(recipe.getFirstResultBlock());
        addOutputSlots(builder, chanceBlockStates, 120, 20);
        addInputSlots(builder, recipe.getInputBlocks(), 40, 20);
        Block block = recipe.getHasCauldron().getTransformCauldron();
        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);


        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON))
            builder.addOutputSlot(120, 40).addItemLike(Items.POWDER_SNOW_BUCKET);

        if (block.defaultBlockState().is(ModBlocks.LAVA_CAULDRON)) {
            Fluid fluid = Fluids.LAVA;
            int amount = 250;
            addFluidStackOutputSlots(builder, fluid, 120, 40, amount);
            return;
        }

        if (cauldronFluidContent == null) return;
        Fluid fluid = cauldronFluidContent.fluid;
        addFluidStackOutputSlots(builder, fluid, 120, 40, 333);
    }

    public static void addTimeWarpCategorySlots(IRecipeLayoutBuilder builder, TimeWarpRecipe recipe) {

    }

    public static <T extends AbstractProcessRecipe<?>> void addAbstractProgressCategoryFluidSlots(IRecipeLayoutBuilder builder, RecipeHolder<T> recipeHolder) {
        if (recipeHolder.value().getHasCauldron() != null) {
            HasCauldronSimple cauldronSimple = recipeHolder.value().getHasCauldron();
            CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(cauldronSimple.getFluidCauldron());
            if (cauldronFluidContent == null) return;
            Fluid fluid = cauldronFluidContent.fluid;
            addFluidStackInputSlots(builder, fluid, 47, 37);
        }
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemStack itemStack) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(itemStack).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (TagKey<Item> ingredient, int count) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(ingredient).withCount(count).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (TagKey<Item> ingredient) {
        return ingredientPredicateList(ingredient, 1);
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemLike ingredient, int count) {
        List<ItemIngredientPredicate> itemIngredients = new ArrayList<>();
        itemIngredients.add(ItemIngredientPredicate.Builder.item().of(ingredient).withCount(count).build());
        return itemIngredients;
    }

    public static List<ItemIngredientPredicate> ingredientPredicateList (ItemLike ingredient) {
        return ingredientPredicateList(ingredient, 1);
    }
}
