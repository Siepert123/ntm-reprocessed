package com.nuclearnuisances.ntm;

import com.nuclearnuisances.ntm.block.NTMBlocks;
import com.nuclearnuisances.ntm.item.NTMItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class NuclearTechMod {

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /* ----- REGISTRY EVENTS ----- */
    @SubscribeEvent public void registerItems(RegistryEvent.Register<Item> event) { NTMItems.registerItems(event); }
    @SubscribeEvent public void registerBlocks(RegistryEvent.Register<Block> event) { NTMBlocks.registerBlocks(event); }
    @SideOnly(Side.CLIENT) @SubscribeEvent public void registerModels(ModelRegistryEvent event) { NTMItems.registerModel(event); }

    /* ----- EVENTS ----- */
}
