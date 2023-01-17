
package net.r0nin_yt.witchery.mixin.brew;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.r0nin_yt.witchery.Witchery;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Unique
	private static final MutableText POTION = (MutableText) Text.of("item." + Witchery.MOD_ID + ".potion");
	@Unique
	private static final MutableText SPLASH_POTION = (MutableText) Text.of("item." + Witchery.MOD_ID + ".splash_potion");
	@Unique
	private static final MutableText LINGERING_POTION = (MutableText) Text.of("item." + Witchery.MOD_ID + ".lingering_potion");
	@Unique
	private static final MutableText TIPPED_ARROW = (MutableText) Text.of("item." + Witchery.MOD_ID + ".tipped_arrow");

	@Shadow
	@Nullable
	public abstract NbtCompound getNbt();

	@Shadow
	public abstract Item getItem();

	@Inject(method = "getName", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true)
	private void getName(CallbackInfoReturnable<Text> callbackInfo) {
		NbtCompound nbt = getNbt();
		if (nbt != null && nbt.getBoolean("BewitchmentBrew")) {
			if (getItem() == Items.POTION) {
				callbackInfo.setReturnValue(POTION);
			} else if (getItem() == Items.SPLASH_POTION) {
				callbackInfo.setReturnValue(SPLASH_POTION);
			} else if (getItem() == Items.LINGERING_POTION) {
				callbackInfo.setReturnValue(LINGERING_POTION);
			} else if (getItem() == Items.TIPPED_ARROW) {
				callbackInfo.setReturnValue(TIPPED_ARROW);
			}
		}
	}
}
