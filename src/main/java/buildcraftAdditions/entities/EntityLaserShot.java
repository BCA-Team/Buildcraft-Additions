package buildcraftAdditions.entities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.power.ILaserTarget;
import buildcraft.api.power.ILaserTargetBlock;

import buildcraftAdditions.config.ConfigurationHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EntityLaserShot extends EntityThrowable implements IEntityAdditionalSpawnData, IThrowableEntity {

	private float strength;
	private EntityLivingBase thrower;

	public EntityLaserShot(World world) {
		super(world);
		setSize(0.5F, 0.5F);
	}

	public EntityLaserShot(World world, EntityPlayer player, float strength) {
		super(world, player);
		this.strength = strength;
		setSize(0.5F, 0.5F);
		setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		worldObj.spawnParticle("smoke", posX + rand.nextDouble() / 4 - 0.125, posY + rand.nextDouble() / 4 - 0.125, posZ + rand.nextDouble() / 4 - 0.125, 0, 0, 0);
		worldObj.spawnParticle("flame", posX + rand.nextDouble() / 4 - 0.125, posY + rand.nextDouble() / 4 - 0.125, posZ + rand.nextDouble() / 4 - 0.125, 0, 0, 0);
		if (ticksExisted > 6000)
			setDead();
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop == null) return;
		boolean hit = false;
		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mop.entityHit != null && mop.entityHit != this && mop.entityHit != thrower && mop.entityHit != super.getThrower()) {
			hit = true;
			if (ConfigurationHandler.portableLaserEntityBurnTime > 0)
				mop.entityHit.setFire((int) (strength * ConfigurationHandler.portableLaserEntityBurnTime));
			if (ConfigurationHandler.portableLaserEntityDamage > 0)
				mop.entityHit.attackEntityFrom(new EntityDamageSourceIndirect("bcaLaser", this, thrower).setFireDamage().setDamageBypassesArmor().setProjectile(), (int) (strength * ConfigurationHandler.portableLaserEntityDamage));
		} else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			hit = true;
			if (worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ) instanceof ILaserTargetBlock) {
				TileEntity tile = worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				if (tile != null && tile instanceof ILaserTarget) {
					ILaserTarget target = (ILaserTarget) tile;
					if (!target.isInvalidTarget() && target.requiresLaserEnergy())
						target.receiveLaserEnergy((int) (strength * ConfigurationHandler.portableLaserLaserPower));
				}
			}
		}
		if (hit) {
			for (int i = 0; i < 32; i++) {
				worldObj.spawnParticle("smoke", posX + rand.nextDouble() / 4 - 0.125, posY + rand.nextDouble() / 4 - 0.125, posZ + rand.nextDouble() / 4 - 0.125, 0, 0, 0);
				worldObj.spawnParticle("flame", posX + rand.nextDouble() / 4 - 0.125, posY + rand.nextDouble() / 4 - 0.125, posZ + rand.nextDouble() / 4 - 0.125, 0, 0, 0);
			}
			if (!worldObj.isRemote)
				setDead();
		}

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("strength", strength);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		strength = nbt.getFloat("strength");
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(strength);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		strength = additionalData.readFloat();
	}

	@Override
	public void setThrower(Entity entity) {
		if (entity == null)
			thrower = null;
		else if (entity instanceof EntityLivingBase)
			thrower = (EntityLivingBase) entity;
	}

	@Override
	public EntityLivingBase getThrower() {
		return thrower;
	}

	@Override
	protected float getGravityVelocity() {
		return 0;
	}

	@Override
	protected float func_70182_d() {
		return strength * 2 + 1;
	}

	public float getStrength() {
		return strength;
	}

	@Override
	public float getBrightness(float partialTicks) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float partialTicks) {
		return 15728880;
	}

}
