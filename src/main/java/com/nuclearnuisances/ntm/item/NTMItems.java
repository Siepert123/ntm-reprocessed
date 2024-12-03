package com.nuclearnuisances.ntm.item;

import com.nuclearnuisances.ntm.Tags;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class NTMItems {

    private static final List<Item> ITEMS = new ArrayList<>();

    // public static final Item EXAMPLE_ITEM = register(new Item(), "example_item");

    public static <T extends Item> T register(T item, String name) {
        item.setRegistryName(Tags.MOD_ID, name).setTranslationKey(Tags.MOD_ID + "." + name);
        ITEMS.add(item);
        return item;
    }

    public static <T extends Item> T register(T item, ResourceLocation name) {
        item.setRegistryName(name).setTranslationKey(name.getNamespace() + "." + name.getPath());
        ITEMS.add(item);
        return item;
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
    }
}
