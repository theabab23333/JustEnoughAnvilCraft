package dev.anvilcraft.addon.jeac.util;

import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.integration.jei.recipe.CementStainingRecipe;
import dev.dubhe.anvilcraft.integration.jei.recipe.ColoredConcreteRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BulgingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;

import static dev.anvilcraft.addon.jeac.util.JeaSlotUtil.*;

public class RecipeUtil {
    public static <T extends AbstractProcessRecipe<?>> void findAbstractHasCauldron(IRecipeLayoutBuilder builder, RecipeHolder<T> recipe) {
        if (recipe.value().getHasCauldron() != null) {
            int consume = recipe.value().getHasCauldron().getConsume();
            if (consume < 0) addFluidStackInputSlots(builder, 21, 41, recipe.value().getHasCauldron());
            if (consume >= 0) addFluidStackOutputSlots(builder, 125, 41, recipe.value().getHasCauldron());
        }
    }

    public static void findBulgingCategory(IRecipeLayoutBuilder builder, BulgingRecipe recipe) {
        if (recipe.getHasCauldron() != null) {
            if (recipe.isConsumeFluid()) addFluidStackInputSlots(builder, 21, 41, recipe.getHasCauldron().getFluidCauldron());
        }
    }

    public static void findCementStainingCategory(IRecipeLayoutBuilder builder, CementStainingRecipe recipe) {
        if (recipe.resultBlock() != null) {
            addFluidStackOutputSlots(builder, 125, 41, recipe.resultBlock());
        }
    }

    public static void findConcreteCategoryFluidSlots(IRecipeLayoutBuilder builder, ColoredConcreteRecipe recipe) {
        BlockState blockState = ModBlocks.CEMENT_CAULDRONS.get(recipe.color()).getDefaultState();
        addFluidStackInputSlots(builder, 46, 36, blockState.getBlock());
    }

    public static void findItemInjectCategorySlots(IRecipeLayoutBuilder builder, ItemInjectRecipe recipe) {
        addBlockStatePredicateInputSlots(builder, recipe.getInputBlocks(), 21, 41);
        addChanceBlockStateInputSlots(builder, recipe.getResultBlocks(), 125, 41);
    }


    //    public static void addBlockInputSlots(IRecipeLayoutBuilder builder, List<BlockStatePredicate> blockStatePredicateList, int startX, int startY) {
//        List<ItemIngredientPredicate> ingerdientList = new ArrayList<>();
//        for (BlockStatePredicate blockStatePredicate : blockStatePredicateList) {
//            for (BlockState blockState : blockStatePredicate.constructStatesForRender()) {
//                Block block = blockState.getBlock();
//                Item item = block.asItem();
//                ingerdientList.addAll(ingredientPredicateList(item));
//            }
//        }
//
//        int size = ingerdientList.size();
//        if (size == 0) return;
//        if (size <= 4) {
//            for (int index = 0; index < size; index++) {
//                int row = index / 2;
//                int col = index % 2;
//                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
//            }
//        } else {
//            for (int index = 0; index < size; index++) {
//                if (index > 12) break;
//                startX = 0;
//                startY = 0;
//                int row = index / 3;
//                int col = index % 3;
//                addSlotWithCount(builder, startX + 16 * col, startY + 16 * row, ingerdientList.get(index));
//            }
//        }
//    }
//

//    public static void addItemInputSlots(IRecipeLayoutBuilder builder, List<ItemIngredientPredicate> mergedIngredients) {
//        int size = mergedIngredients.size();
//        if (size == 0) return;
//        if (size == 1) {
//            ItemIngredientPredicate ingredient = mergedIngredients.getFirst();
//            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 35, 24);
//            slot.addIngredients(Ingredient.of(ingredient.getItems()));
//        }
//    }
//
//    public static void addBlockInputSlots(IRecipeLayoutBuilder builder, BlockStatePredicate blockStatePredicate) {
//        List<BlockStatePredicate> predicateList = new ArrayList<>();
//        predicateList.add(blockStatePredicate);
//        addBlockInputSlots(builder, predicateList);
//    }
//
//    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y, int amount) {
//        if (amount == 0) {
//            builder.addInputSlot(x, y).addFluidStack(fluid);
//        } else {
//            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, x, y).addFluidStack(fluid, amount);
//            JeaSlotUtil.addFluidAmountTooltips(slot, amount);
//        }
//    }
//
//    public static void addFluidStackInputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y) {
//        addFluidStackInputSlots(builder, fluid, x, y, 0);
//    }
//
//    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y, int amount) {
//        if (amount == 0) {
//            builder.addOutputSlot(x, y).addFluidStack(fluid);
//        } else {
//            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addFluidStack(fluid, amount);
//            JeaSlotUtil.addFluidAmountTooltips(slot, amount);
//        }
//    }
//
//    public static void addFluidStackOutputSlots(IRecipeLayoutBuilder builder, Fluid fluid, int x, int y) {
//        addFluidStackOutputSlots(builder, fluid, x, y, 0);
//    }
//
//    public static void addOutputSlots(IRecipeLayoutBuilder builder, List<ChanceBlockState> chanceBlockStates, int x, int y) {
//        for (ChanceBlockState chanceBlockState : chanceBlockStates) {
//            Item item = chanceBlockState.getState().getBlock().asItem();
//            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(item.getDefaultInstance());
//            JeiRecipeUtil.addFluidAmountTooltips(slot, 1, chanceBlockState.getChance());
//        }
//    }
//


//
//    public static void addItemInjectCategorySlots(IRecipeLayoutBuilder builder, ItemInjectRecipe recipe) {
//        addBlockInputSlots(builder, recipe.getInputBlocks(), 35, 42);
//        if (!recipe.getResultBlocks().isEmpty()) addOutputSlots(builder, recipe.getResultBlocks(), 110, 42);
//    }
//
//    public static void addSqueezingCategorySlots(IRecipeLayoutBuilder builder, SqueezingRecipe recipe) {
//        List<ChanceBlockState> chanceBlockStates = new ArrayList<>();
//        chanceBlockStates.add(recipe.getFirstResultBlock());
//        addOutputSlots(builder, chanceBlockStates, 120, 20);
//        addBlockInputSlots(builder, recipe.getInputBlocks(), 40, 20);
//        Block block = recipe.getHasCauldron().getTransformCauldron();
//        CauldronFluidContent cauldronFluidContent = CauldronFluidContent.getForBlock(block);
//
//
//        if (block.defaultBlockState().is(Blocks.POWDER_SNOW_CAULDRON))
//            builder.addOutputSlot(120, 40).addItemLike(Items.POWDER_SNOW_BUCKET);
//
//        if (block.defaultBlockState().is(ModBlocks.LAVA_CAULDRON)) {
//            Fluid fluid = Fluids.LAVA;
//            int amount = 250;
//            addFluidStackOutputSlots(builder, fluid, 120, 40, amount);
//            return;
//        }
//
//        if (cauldronFluidContent == null) return;
//        Fluid fluid = cauldronFluidContent.fluid;
//        addFluidStackOutputSlots(builder, fluid, 120, 40, 333);
//    }
//
//    public static void addTimeWarpCategorySlots(IRecipeLayoutBuilder builder, TimeWarpRecipe recipe) {
//
//    }
}
