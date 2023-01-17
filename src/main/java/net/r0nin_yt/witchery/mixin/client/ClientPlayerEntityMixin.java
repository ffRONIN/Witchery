
package net.r0nin_yt.witchery.mixin.client;

import com.mojang.authlib.GameProfile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.r0nin_yt.witchery.common.block.entity.WitchCauldronBlockEntity;
import net.r0nin_yt.witchery.common.network.packet.CauldronTeleportPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}
	//public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile, @Nullable PlayerPublicKey publicKey) {
	//	super(world, profile);
	//}

////	@Inject(method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
//	private void sendChatMessage(String message, Text preview, CallbackInfo ci) {
	//	if (!message.startsWith("/")) {
	//		for (int i = 0; i < 1; i++) {
	//			if (world.getBlockEntity(getBlockPos().down(i)) instanceof WitchCauldronBlockEntity witchCauldron && witchCauldron.mode == WitchCauldronBlockEntity.Mode.TELEPORTATION) {
	//				CauldronTeleportPacket.send(witchCauldron.getPos(), message);
	//				ci.cancel();
//	return;
		//		}
		//	}
	//	}
//	}
}
