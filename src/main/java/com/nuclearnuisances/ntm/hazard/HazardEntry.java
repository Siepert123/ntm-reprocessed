package com.nuclearnuisances.ntm.hazard;

import com.nuclearnuisances.ntm.hazard.modifier.HazardModifier;
import com.nuclearnuisances.ntm.hazard.type.HazardTypeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HazardEntry {
    HazardTypeBase type;
    float baseLevel;

    /*
     * Modifiers are evaluated in the order they're being applied to the entry.
     */
    List<HazardModifier> mods = new ArrayList<>();

    public HazardEntry(HazardTypeBase type) {
        this(type, 1F);
    }

    public HazardEntry(HazardTypeBase type, float level) {
        this.type = type;
        this.baseLevel = level;
    }

    public HazardEntry addMod(HazardModifier mod) {
        this.mods.add(mod);
        return this;
    }

    public void applyHazard(ItemStack stack, EntityLivingBase entity) {
        type.onUpdate(entity, HazardModifier.evalAllModifiers(stack, entity, baseLevel, mods), stack);
    }

    public HazardTypeBase getType() {
        return this.type;
    }

    public HazardEntry clone() {
        return clone(1F);
    }

    public HazardEntry clone(float mult) {
        HazardEntry clone = new HazardEntry(type, baseLevel * mult);
        clone.mods = this.mods;
        return clone;
    }
}
