package com.cozary.ore_creeper.entities;

import com.cozary.ore_creeper.init.ParticleList;
import com.cozary.ore_creeper.util.ConfigurationHandler;
import com.cozary.ore_creeper.util.ExplosionTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class RedstoneCreeperEntity extends AbstractOreCreeperEntity {


    public RedstoneCreeperEntity(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    public static boolean canOreCreeperSpawn(EntityType<? extends AbstractOreCreeperEntity> creeper, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() < ConfigurationHandler.GENERAL.redstoneCreeperMaxSpawnYLevel.get() && (world.getBlockState(pos.below()).is(Blocks.STONE) || world.getBlockState(pos.below()).is(Blocks.DEEPSLATE)) && isDarkEnoughToSpawn(world, pos, random) && checkMobSpawnRules(creeper, world, reason, pos, random);
    }

    @Override
    public void explodeCreeper() {
        double d0 = this.random.nextGaussian() * 0.02D;
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        if (!this.level().isClientSide) {
            this.dead = true;
            new ExplosionTypes().oreExplosionEffect(this, this.level(), this.getX(), this.getY(), this.getZ(), ExplosionTypes.OreType.REDSTONE);
            ((ServerLevel) this.getCommandSenderWorld()).sendParticles(ParticleList.REDSTONE_EXPLOSION.get(), this.getX() + 0.5, this.getY(), this.getZ() + 0.5, 500, d1, d2, d0, 0.5);
            this.discard();
            this.spawnLingeringCloud();
        }

    }


}
