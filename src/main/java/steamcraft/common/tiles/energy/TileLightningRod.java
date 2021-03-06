/**
 * This class was created by BrassGoggledCoders modding team.
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 */
package steamcraft.common.tiles.energy;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;

import steamcraft.common.config.ConfigBalance;
import steamcraft.common.config.ConfigGeneral;
import steamcraft.common.entities.living.EntityFleshGolem;
import steamcraft.common.init.InitBlocks;

/**
 * @author warlordjones, MrIbby
 * 
 */
public class TileLightningRod extends TileEntity implements IEnergyProvider
{
	private static final ArrayList<EntityLightningBolt> unnaturalLightningBolts = new ArrayList<EntityLightningBolt>();
	private static Class weather2Class;
	private final EnergyStorage buffer = new EnergyStorage(30000, 10000);

	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isRemote)
		{
			boolean isLightningSpawned = false;

			if(this.worldObj.getWorldInfo().isThundering() && this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)
					&& !BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord), Type.SANDY)
					&& ConfigGeneral.unnaturalLightningStrikes)
			{
				Random random = this.worldObj.rand;
				int chance = random.nextInt(ConfigBalance.lightningRodHitChance);
				if(chance == 0)
				{
					EntityLightningBolt lightningBolt = new EntityLightningBolt(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					unnaturalLightningBolts.add(lightningBolt);
					this.worldObj.addWeatherEffect(lightningBolt);
					this.buffer.receiveEnergy(ConfigBalance.lightningRodEnergyProduction, false);
					isLightningSpawned = true;
				}
			}

			if(ConfigGeneral.naturalLightningStrikes)
			{
				AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) this.xCoord - 1, (double) this.yCoord - 1,
						(double) this.zCoord - 1, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
				List list = this.worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, axisalignedbb);

				if(!list.isEmpty())
					isLightningSpawned = true;

				for(Object obj : list)
				{
					if(unnaturalLightningBolts.remove(obj))
						continue;
					this.buffer.receiveEnergy(ConfigBalance.lightningRodEnergyProduction, false);
				}
			}

			if(ConfigGeneral.weather2LightningStrikes)
			{
				if(weather2Class == null)
				{
					try
					{
						weather2Class = Class.forName("weather2.entity.EntityLightningBolt");
					}
					catch(ClassNotFoundException exception)
					{
					}
				}

				if(weather2Class != null)
				{
					AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) this.xCoord - 1, (double) this.yCoord - 1,
							(double) this.zCoord - 1, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
					List list = this.worldObj.getEntitiesWithinAABB(weather2Class, axisalignedbb);

					if(!list.isEmpty())
						isLightningSpawned = true;

					for(Object obj : list)
						this.buffer.receiveEnergy(ConfigBalance.lightningRodEnergyProduction, false);
				}
			}

			if(isLightningSpawned && (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == InitBlocks.blockCopperWire)
					&& (this.worldObj.getBlock(this.xCoord, this.yCoord - 2, this.zCoord) == InitBlocks.blockFlesh)
					&& (this.worldObj.getBlock(this.xCoord, this.yCoord - 3, this.zCoord) == InitBlocks.blockFlesh))
			{
				EntityFleshGolem golem = new EntityFleshGolem(this.worldObj);
				golem.setPosition(this.xCoord, this.yCoord, this.zCoord);
				this.worldObj.spawnEntityInWorld(golem);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 1, this.zCoord);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 2, this.zCoord);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 3, this.zCoord);
			}
			if(this.buffer.getEnergyStored() >= 40)
			{
				byte usedEnergy = 0;
				byte outputEnergy = 40;

				for(ForgeDirection direction : EnumSet.allOf(ForgeDirection.class))
					if(outputEnergy > 0)
					{
						TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord - direction.offsetX, this.yCoord - direction.offsetY,
								this.zCoord - direction.offsetZ);

						if(tileEntity instanceof IEnergyHandler)
						{
							usedEnergy += ((IEnergyHandler) tileEntity).receiveEnergy(direction.getOpposite(), outputEnergy, false);
							outputEnergy -= usedEnergy;
						}
					}

				this.buffer.modifyEnergyStored(-usedEnergy);
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
		// return from == ForgeDirection.UP;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return this.buffer.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return this.buffer.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return this.buffer.getMaxEnergyStored();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.buffer.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		this.buffer.writeToNBT(tag);
	}

}
