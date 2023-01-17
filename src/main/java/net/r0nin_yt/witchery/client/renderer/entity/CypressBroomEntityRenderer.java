

package net.r0nin_yt.witchery.client.renderer.entity;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.client.renderer.BroomEntityRenderer;
import net.r0nin_yt.witchery.api.entity.BroomEntity;

public class CypressBroomEntityRenderer extends BroomEntityRenderer<BroomEntity> {
	private static final Identifier TEXTURE = new Identifier(Witchery.MOD_ID, "textures/entity/broom/cypress.png");

	public CypressBroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(BroomEntity entity) {
		return TEXTURE;
	}
}
