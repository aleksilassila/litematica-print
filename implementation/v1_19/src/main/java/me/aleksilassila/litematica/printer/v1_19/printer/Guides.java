package me.aleksilassila.litematica.printer.v1_19.printer;

import me.aleksilassila.litematica.printer.v1_19.printer.guide.*;
import net.minecraft.block.*;
import net.minecraft.util.Pair;

import java.util.ArrayList;

public class Guides {
    protected final static ArrayList<Pair<Class<? extends AbstractGuide>, Class<? extends Block>[]>> guides = new ArrayList<>();

    @SafeVarargs
    protected static void registerGuide(Class<? extends AbstractGuide> guideClass, Class<? extends Block>... blocks) {
        guides.add(new Pair<>(guideClass, blocks));
    }

    static {
        registerGuide(SkipGuide.class, SignBlock.class, SkullBlock.class);
        registerGuide(SlabGuide.class, SlabBlock.class);
        registerGuide(WallTorchGuide.class, WallTorchBlock.class, WallRedstoneTorchBlock.class);
        registerGuide(GuesserGuide.class);

        registerGuide(CycleClickGuide.class,
                DoorBlock.class, FenceGateBlock.class, TrapdoorBlock.class,
                LeverBlock.class,
                RepeaterBlock.class, ComparatorBlock.class, NoteBlock.class);
        registerGuide(IncreaseGuide.class, SnowBlock.class, SeaPickleBlock.class, CandleBlock.class);
    }

    public ArrayList<Pair<Class<? extends AbstractGuide>, Class<? extends Block>[]>> getGuides() {
        return guides;
    }

    public AbstractGuide[] getInteractionGuides(SchematicBlockState state) {
        ArrayList<Pair<Class<? extends AbstractGuide>, Class<? extends Block>[]>> guides = getGuides();

        ArrayList<AbstractGuide> applicableGuides = new ArrayList<>();
        for (Pair<Class<? extends AbstractGuide>, Class<? extends Block>[]> guidePair : guides) {
            try {
                if (guidePair.getRight().length == 0) {
                    applicableGuides.add(guidePair.getLeft().getConstructor(SchematicBlockState.class).newInstance(state));
                    continue;
                }

                for (Class<? extends Block> clazz : guidePair.getRight()) {
                    if (clazz.isInstance(state.targetState.getBlock())) {
                        applicableGuides.add(guidePair.getLeft().getConstructor(SchematicBlockState.class).newInstance(state));
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return applicableGuides.toArray(AbstractGuide[]::new);
    }
}