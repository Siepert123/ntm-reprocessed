package com.nuclearnuisances.ntm.hazard;

import com.nuclearnuisances.ntm.hazard.modifier.HazardModifier;
import com.nuclearnuisances.ntm.hazard.transformer.HazardTransformerBase;
import com.nuclearnuisances.ntm.hazard.type.HazardTypeBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import com.nuclearnuisances.ntm.inventory.RecipesCommon.*;

public class HazardSystem {
    public static final Map<String, HazardData> oreMap = new HashMap<>();

    public static final Map<Item, HazardData> itemMap = new HashMap<>();

    public static final Map<ComparableStack, HazardData> stackMap = new HashMap<>();

    public static final Set<ComparableStack> stackBlacklist = new HashSet<>();
    public static final Set<String> dictBlacklist = new HashSet<>();

    public static List<HazardTransformerBase> trafos = new ArrayList<>();

    public static void register(String ore, HazardData data) {
        oreMap.put(ore, data);
    }
    public static void register(Item item, HazardData data) {
        itemMap.put(item, data);
    }
    public static void register(Block block, HazardData data) {
        itemMap.put(Item.getItemFromBlock(block), data);
    }
    public static void register(ComparableStack stack, HazardData data) {
        stackMap.put(stack, data);
    }

    public static void blacklist(ItemStack stack) {
        stackBlacklist.add(new ComparableStack(stack).makeSingular());
    }
    public static void blacklist(String ore) {
        dictBlacklist.add(ore);
    }

    public static boolean isItemBlacklisted(ItemStack stack) {
        if (stackBlacklist.contains(new ComparableStack(stack).makeSingular())) return true;

        int[] IDs = OreDictionary.getOreIDs(stack);
        for (int ID : IDs) {
            String ore = OreDictionary.getOreName(ID);

            if (dictBlacklist.contains(ore)) return true;
        }

        return false;
    }

    public static List<HazardEntry> getHazardsFromStack(ItemStack stack) {

        if(isItemBlacklisted(stack)) {
            return new ArrayList();
        }

        List<HazardData> chronological = new ArrayList();

        /// ORE DICT ///
        int[] ids = OreDictionary.getOreIDs(stack);
        for(int id : ids) {
            String name = OreDictionary.getOreName(id);

            if(oreMap.containsKey(name))
                chronological.add(oreMap.get(name));
        }

        /// ITEM ///
        if(itemMap.containsKey(stack.getItem()))
            chronological.add(itemMap.get(stack.getItem()));

        /// STACK ///
        ComparableStack comp = new ComparableStack(stack).makeSingular();
        if(stackMap.containsKey(comp))
            chronological.add(stackMap.get(comp));

        List<HazardEntry> entries = new ArrayList();

        for(HazardTransformerBase trafo : trafos) {
            trafo.transformPre(stack, entries);
        }

        int mutex = 0;

        for(HazardData data : chronological) {
            //if the current data is marked as an override, purge all previous data
            if(data.doesOverride)
                entries.clear();

            if((data.getMutex() & mutex) == 0) {
                entries.addAll(data.entries);
                mutex = mutex | data.getMutex();
            }
        }

        for(HazardTransformerBase trafo : trafos) {
            trafo.transformPost(stack, entries);
        }

        return entries;
    }

    public static float getHazardLevelFromStack(ItemStack stack, HazardTypeBase hazard) {
        List<HazardEntry> entries = getHazardsFromStack(stack);

        for(HazardEntry entry : entries) {
            if(entry.type == hazard) {
                return HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods);
            }
        }

        return 0F;
    }

    /**
     * Will grab and iterate through all assigned hazards of the given stack and apply their effects to the holder.
     * @param stack
     * @param entity
     */
    public static void applyHazards(ItemStack stack, EntityLivingBase entity) {
        List<HazardEntry> hazards = getHazardsFromStack(stack);

        for(HazardEntry hazard : hazards) {
            hazard.applyHazard(stack, entity);
        }
    }

    /**
     * Will apply the effects of all carried items, including the armor inventory.
     * @param player player
     */
    public static void updatePlayerInventory(EntityPlayer player) {

        for(int i = 0; i < player.inventory.mainInventory.size(); i++) {

            ItemStack stack = player.inventory.mainInventory.get(i);
            if(stack != null) {
                applyHazards(stack, player);

                if(stack.getCount() == 0) {
                    player.inventory.mainInventory.set(i, ItemStack.EMPTY);
                }
            }
        }

        for(ItemStack stack : player.inventory.armorInventory) {
            if(stack != null) {
                applyHazards(stack, player);
            }
        }
    }

    public static void updateLivingInventory(EntityLivingBase entity) {
        Iterator<ItemStack> iterator = entity.getEquipmentAndArmor().iterator();
        for(int i = 0; i < 5; i++) {
            ItemStack stack = iterator.next();

            if(stack != null) {
                applyHazards(stack, entity);
            }
        }
    }

    public static void updateDroppedItem(EntityItem entity) {

        ItemStack stack = entity.getItem();

        if(entity.isDead || stack == null || stack.getItem() == null || stack.getCount() <= 0) return;

        List<HazardEntry> hazards = getHazardsFromStack(stack);
        for(HazardEntry entry : hazards) {
            entry.type.updateEntity(entity, HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void addFullTooltip(ItemStack stack, EntityPlayer player, List list) {

        List<HazardEntry> hazards = getHazardsFromStack(stack);

        for(HazardEntry hazard : hazards) {
            hazard.type.addHazardInformation(player, list, hazard.baseLevel, stack, hazard.mods);
        }
    }
}
