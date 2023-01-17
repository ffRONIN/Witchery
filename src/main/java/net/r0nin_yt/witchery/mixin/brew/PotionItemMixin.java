

package net.r0nin_yt.witchery.mixin.brew;


import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
	@Inject(method = "finishUsing", at = @At("HEAD"))
	private void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> callbackInfo) {
		if (!world.isClient) {
			WitcheryComponents.POLYMORPH_COMPONENT.maybeGet(user).ifPresent(polymorphComponent -> {
				NbtCompound compound = stack.getNbt();
				if (compound != null) {
					String name = compound.getString("PolymorphName");
					if (!name.isEmpty()) {
						polymorphComponent.setUuid(compound.getUuid("PolymorphUUID"));
						polymorphComponent.setName(name);
					}
				}
			});
		}
	}
}
