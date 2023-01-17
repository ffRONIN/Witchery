package net.r0nin_yt.witchery.common.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.Witchery;


public class WitcheryItemGroup {
    public static final ItemGroup WITCHERY = FabricItemGroupBuilder.build(new Identifier(Witchery.MOD_ID,
            "witchery"), () -> new ItemStack(WitcheryItems.BABASHAT));

}
