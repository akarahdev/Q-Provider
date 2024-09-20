package dev.akarah.provider;

import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    public static List<Runnable> ON_NEXT_TICK = new ArrayList();
}
