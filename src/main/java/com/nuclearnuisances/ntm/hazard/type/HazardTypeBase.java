package com.nuclearnuisances.ntm.hazard.type;

import com.nuclearnuisances.ntm.hazard.modifier.HazardModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class HazardTypeBase {
    public abstract void onUpdate(EntityLivingBase target, float level, ItemStack stack);
    public abstract void updateEntity(EntityItem item, float level);
    public abstract void addHazardInformation(EntityPlayer player, List<String> tooltips, float level, ItemStack stack, List<HazardModifier> modifiers);
}
