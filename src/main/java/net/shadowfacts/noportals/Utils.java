package net.shadowfacts.noportals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * @author shadowfacts
 */
public class Utils {

	public static void spawnEgg(Entity entity) {
		EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.dragon_egg));
		entity.worldObj.spawnEntityInWorld(item);
	}

}
