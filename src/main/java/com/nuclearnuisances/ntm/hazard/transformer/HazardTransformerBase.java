package com.nuclearnuisances.ntm.hazard.transformer;

import com.nuclearnuisances.ntm.hazard.HazardEntry;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class HazardTransformerBase {
    public abstract void transformPre(ItemStack stack, List<HazardEntry> entries);
    public abstract void transformPost(ItemStack stack, List<HazardEntry> entries);
}
