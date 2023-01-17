
package net.r0nin_yt.witchery.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.component.*;
import net.r0nin_yt.witchery.common.component.entity.*;

public class 	WitcheryComponents implements EntityComponentInitializer {
	public static final ComponentKey<ContractsComponent> CONTRACTS_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "contracts"), ContractsComponent.class);
	public static final ComponentKey<FortuneComponent> FORTUNE_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "fortune"), FortuneComponent.class);
	public static final ComponentKey<MagicComponent> MAGIC_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "magic"), MagicComponent.class);
	public static final ComponentKey<PledgeComponent> PLEDGE_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "pledge"), PledgeComponent.class);
	public static final ComponentKey<TransformationComponent> TRANSFORMATION_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "transformation"), TransformationComponent.class);
	public static final ComponentKey<BloodComponent> BLOOD_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "blood"), BloodComponent.class);
	public static final ComponentKey<CursesComponent> CURSES_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "curses"), CursesComponent.class);
// 	public static final ComponentKey<WerewolfVillagerComponent> WEREWOLF_VILLAGER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "werewolf_villager"), WerewolfVillagerComponent.class);
public static final ComponentKey<PolymorphComponent> POLYMORPH_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "polymorph"), PolymorphComponent.class);
	public static final ComponentKey<RespawnTimerComponent> RESPAWN_TIMER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "respawn_timer"), RespawnTimerComponent.class);
	public static final ComponentKey<AdditionalWerewolfDataComponent> ADDITIONAL_WEREWOLF_DATA_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "additional_werewolf_data"), AdditionalWerewolfDataComponent.class);
	public static final ComponentKey<MinionComponent> MINION_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "minion"), MinionComponent.class);
	public static final ComponentKey<BroomUserComponent> BROOM_USER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "broom_user"), BroomUserComponent.class);
	public static final ComponentKey<FullInvisibilityComponent> FULL_INVISIBILITY_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Witchery.MOD_ID, "full_invisibility"), FullInvisibilityComponent.class);



	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(CONTRACTS_COMPONENT, ContractsComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(FORTUNE_COMPONENT, FortuneComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(MAGIC_COMPONENT, MagicComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(PLEDGE_COMPONENT, PledgeComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(TRANSFORMATION_COMPONENT, TransformationComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.beginRegistration(LivingEntity.class, BLOOD_COMPONENT).respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY).end(BloodComponent::new);
		registry.beginRegistration(LivingEntity.class, CURSES_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(CursesComponent::new);
	}
}
