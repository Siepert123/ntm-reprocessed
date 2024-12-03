package com.nuclearnuisances.ntm.hazard.type;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nuclearnuisances.ntm.hazard.modifier.HazardModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class HazardTypeCoal extends HazardTypeBase {
    @Override
    public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
        //TODO: coal lungs
    }

    @Override
    public void updateEntity(EntityItem item, float level) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addHazardInformation(EntityPlayer player, List<String> tooltips, float level, ItemStack stack, List<HazardModifier> modifiers) {
        tooltips.add(ChatFormatting.DARK_GRAY + "[" + I18n.translateToLocal("trait.coal") + "]");
    }
}
