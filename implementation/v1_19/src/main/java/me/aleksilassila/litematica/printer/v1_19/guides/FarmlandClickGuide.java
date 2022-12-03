package me.aleksilassila.litematica.printer.v1_19.guides;

import me.aleksilassila.litematica.printer.v1_19.SchematicBlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class FarmlandClickGuide extends AbstractClickGuide {
    static final Item[] HOE_ITEMS = new Item[]{
            Items.NETHERITE_HOE,
            Items.DIAMOND_HOE,
            Items.GOLDEN_HOE,
            Items.IRON_HOE,
            Items.STONE_HOE,
            Items.WOODEN_HOE
    };

    public FarmlandClickGuide(SchematicBlockState state) {
        super(state);
    }

    @Override
    public boolean canExecute(ClientPlayerEntity player) {
        if (!super.canExecute(player)) return false;

        return Arrays.stream(FarmlandGuide.TILLABLE_BLOCKS).anyMatch(b -> b == currentState.getBlock());
    }

    @Override
    protected @NotNull List<ItemStack> getRequiredItems() {
        return Arrays.stream(HOE_ITEMS).map(ItemStack::new).toList();
    }
}