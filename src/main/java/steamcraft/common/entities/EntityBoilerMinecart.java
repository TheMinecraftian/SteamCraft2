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
package steamcraft.common.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import steamcraft.common.init.InitBlocks;

public class EntityBoilerMinecart extends EntityMinecart
{
	private int fuel;
	public double pushX;
	public double pushZ;

	public EntityBoilerMinecart(World p_i1718_1_)
	{
		super(p_i1718_1_);
	}

	public EntityBoilerMinecart(World p_i1719_1_, double p_i1719_2_, double p_i1719_4_, double p_i1719_6_)
	{
		super(p_i1719_1_, p_i1719_2_, p_i1719_4_, p_i1719_6_);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if(this.fuel > 0)
		{
			--this.fuel;
		}

		if(this.fuel <= 0)
		{
			this.pushX = this.pushZ = 0.0D;
		}

		this.setMinecartPowered(this.fuel > 0);

		if(this.isMinecartPowered() && (this.rand.nextInt(4) == 0))
		{
			this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);

		if(!p_94095_1_.isExplosion())
		{
			this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
		}
	}

	@Override
	protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_)
	{
		super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
		double d2 = (this.pushX * this.pushX) + (this.pushZ * this.pushZ);

		if((d2 > 1.0E-4D) && (((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) > 0.001D))
		{
			d2 = MathHelper.sqrt_double(d2);
			this.pushX /= d2;
			this.pushZ /= d2;

			if(((this.pushX * this.motionX) + (this.pushZ * this.motionZ)) < 0.0D)
			{
				this.pushX = 0.0D;
				this.pushZ = 0.0D;
			}
			else
			{
				this.pushX = this.motionX;
				this.pushZ = this.motionZ;
			}
		}
	}

	@Override
	protected void applyDrag()
	{
		double d0 = (this.pushX * this.pushX) + (this.pushZ * this.pushZ);

		if(d0 > 1.0E-4D)
		{
			d0 = MathHelper.sqrt_double(d0);
			this.pushX /= d0;
			this.pushZ /= d0;
			double d1 = 0.05D;
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.800000011920929D;
			this.motionX += this.pushX * d1;
			this.motionZ += this.pushZ * d1;
		}
		else
		{
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.9800000190734863D;
		}

		super.applyDrag();
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(EntityPlayer p_130002_1_)
	{
		// TODO Open GUI

		this.pushX = this.posX - p_130002_1_.posX;
		this.pushZ = this.posZ - p_130002_1_.posZ;
		return true;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setDouble("PushX", this.pushX);
		tag.setDouble("PushZ", this.pushZ);
		tag.setShort("Fuel", (short) this.fuel);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		this.pushX = tag.getDouble("PushX");
		this.pushZ = tag.getDouble("PushZ");
		this.fuel = tag.getShort("Fuel");
	}

	protected boolean isMinecartPowered()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	protected void setMinecartPowered(boolean powered)
	{
		if(powered)
		{
			this.dataWatcher.updateObject(16, (byte) (this.dataWatcher.getWatchableObjectByte(16) | 1));
		}
		else
		{
			this.dataWatcher.updateObject(16, (byte) (this.dataWatcher.getWatchableObjectByte(16) & -2));
		}
	}

	@Override
	public Block func_145817_o()
	{
		return InitBlocks.blockSteamBoiler;
	}

	@Override
	public int getDefaultDisplayTileData()
	{
		return 2;
	}

	@Override
	public int getMinecartType()
	{
		return 2;
	}
}