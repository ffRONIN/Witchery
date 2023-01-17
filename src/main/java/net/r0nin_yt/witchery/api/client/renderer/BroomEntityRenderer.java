

package net.r0nin_yt.witchery.api.client.renderer;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.r0nin_yt.witchery.WitcheryClient;
import net.r0nin_yt.witchery.api.client.model.BroomEntityModel;
import net.r0nin_yt.witchery.api.entity.BroomEntity;

@Environment(EnvType.CLIENT)
public abstract class BroomEntityRenderer<T extends BroomEntity> extends EntityRenderer<T> {
	private final BroomEntityModel model;

	protected BroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		model = new BroomEntityModel(ctx.getPart(WitcheryClient.BROOM_MODEL_LAYER));
	}

	@Override
	public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.translate(0, -1, 0);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90 - yaw + 90));
		matrices.translate(0, 0, -0.35);
		model.setAngles(entity, yaw, 0, entity.age + entity.getId(), 0, 0);
		model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}
}
