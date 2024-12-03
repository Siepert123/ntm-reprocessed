package com.nuclearnuisances.ntm.hazard.transformer;

import com.nuclearnuisances.ntm.hazard.HazardEntry;
import net.minecraft.item.ItemStack;

import java.util.List;

public class HazardTransformerRadiationContainer extends HazardTransformerBase {
    @Override
    public void transformPre(ItemStack stack, List<HazardEntry> entries) {

    }

    @Override
    public void transformPost(ItemStack stack, List<HazardEntry> entries) {
        boolean isCrate = false; //TODO: Block.getBlockFromItem(stack.getItem()) instanceof BlockStorageCrate;
        boolean isBox = false; //TODO: stack.getItem() == ModItems.containment_box;
        boolean isBag = false; //TODO: stack.getItem() == ModItems.plastic_bag;

        if(!isCrate && !isBox && !isBag) return;
        if(!stack.hasTagCompound()) return;

        float radiation = 0;

        //TODO: put this in place once the requirements are met
    }
}
