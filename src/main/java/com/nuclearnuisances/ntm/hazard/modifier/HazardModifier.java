package com.nuclearnuisances.ntm.hazard.modifier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class HazardModifier {
    public abstract float modify(ItemStack stack, EntityLivingBase holder, float level);

    public static float evalAllModifiers(ItemStack stack, EntityLivingBase entity, float level, List<HazardModifier> mods) {
        for (HazardModifier mod : mods) {
            level = mod.modify(stack, entity, level);
        }

        return level;
    }
}
