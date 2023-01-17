
package net.r0nin_yt.witchery.common.network.packet;

import io.netty.buffer.Unpooled;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.WitcheryAPI;

public class TogglePressingForwardPacket {
	public static final Identifier ID = new Identifier(Witchery.MOD_ID, "toggle_pressing_forward");

	public static void send(boolean pressingForward) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBoolean(pressingForward);
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		boolean pressingForward = buf.readBoolean();
		server.execute(() -> {
	//		if (pressingForward && WitcheryAPI.getFamiliar(player) != WitcheryEntityTypes.OWL) {
				if (!WitcheryAPI.drainMagic(player, 1, true)) {
					return;
				}
				if (player.age % 60 == 0) {
					WitcheryAPI.drainMagic(player, 1, false);
				}
	//		}
	//		WitcheryComponents.BROOM_USER_COMPONENT.get(player).setPressingForward(pressingForward);
		});
	}
}
