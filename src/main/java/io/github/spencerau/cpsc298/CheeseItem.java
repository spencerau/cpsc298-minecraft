package io.github.spencerau.cpsc298;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;


public class CheeseItem extends Item {
    public CheeseItem(Properties properties) {
        super(properties);
    }
    private static final int DURATION = 999;
    private static final java.util.Map<java.util.UUID, Integer> lightningTimers = new java.util.HashMap<>();

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.SPEED, DURATION, 9999999));
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, DURATION, 999));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, DURATION, 300));
            player.setInvulnerable(true);
            player.igniteForSeconds(DURATION);
            // player.hasImpulse = false;
            // Start lightning effect for DURATION ticks
            lightningTimers.put(player.getUUID(), DURATION);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    //@Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            java.util.UUID uuid = player.getUUID();
            int timer = lightningTimers.getOrDefault(uuid, 0);
            if (timer > 0) {
                // Strike lightning every 20 ticks (1 second)
                if (timer % 20 == 0 && level instanceof ServerLevel serverLevel) {
                    BlockPos pos = player.blockPosition();
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
                    bolt.setPos(pos.getX(), pos.getY(), pos.getZ());
                    serverLevel.addFreshEntity(bolt);
                }
                lightningTimers.put(uuid, timer - 1);
            }
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos pos = player.blockPosition();

            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
            bolt.setPos(pos.getX(), pos.getY(), pos.getZ());
            serverLevel.addFreshEntity(bolt);
        }

        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }
}

