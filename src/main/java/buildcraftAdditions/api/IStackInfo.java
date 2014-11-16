package buildcraftAdditions.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author Speiger
 * @Note: this kind of way can Provide Fluids and Entities and more.
 * @Note: Add Comparing between Item and Block Data would be good but not Required.
 * But for later its a TODO
 * 
 */
public interface IStackInfo
{
	ItemStack getResult();
	
	
	public static class ItemData implements IStackInfo
	{
		Item item;
		int meta;
		
		public ItemData(Item par1)
		{
			this(par1, 0);
		}
		
		public ItemData(ItemStack par1)
		{
			this(par1.getItem(), par1.getItemDamage());
		}
		
		public ItemData(Item par1, int par2)
		{
			item = par1;
			meta = par2;
		}

		@Override
		public ItemStack getResult()
		{
			return new ItemStack(item, 1, meta);
		}
		
		@Override
		public int hashCode()
		{
			return Item.getIdFromItem(item)+meta;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if(obj == null)
			{
				return false;
			}
			
			if(!(obj instanceof ItemData))
			{
				return false;
			}
			ItemData data = (ItemData) obj;
			//Comparing Numbers is Better than Comparing classes. They can Change
			if(Item.getIdFromItem(data.item) != Item.getIdFromItem(item))
			{
				return false;
			}
			if(data.meta != meta)
			{
				return false;
			}
			
			return true;
		}
	}
	
	public static class BlockData implements IStackInfo
	{
		Block block;
		int meta;
		
		public BlockData(Block par1)
		{
			this(par1, 0);
		}
		
		public BlockData(Block par1, int par2)
		{
			block = par1;
			meta = par2;
		}
		
		@Override
		public ItemStack getResult()
		{
			return new ItemStack(block, 1, meta);
		}
		
		@Override
		public int hashCode()
		{
			return Block.getIdFromBlock(block)+meta;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if(obj == null)
			{
				return false;
			}
			
			if(!(obj instanceof BlockData))
			{
				return false;
			}
			BlockData data = (BlockData) obj;
			//Comparing Numbers is Better than Comparing classes. They can Change
			if(Block.getIdFromBlock(data.block) != Block.getIdFromBlock(block))
			{
				return false;
			}
			if(data.meta != meta)
			{
				return false;
			}
			
			return true;
		}
		
	}
}
