package net.shadowfacts.noportals;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

/**
 * @author shadowfacts
 */
@Mod(modid = NoPortals.modId, name = NoPortals.name, version = NoPortals.version)
public class NoPortals {

	public static final String modId = "NoPortals";
	public static final String name = "NoPortals";
	public static final String version = "1.0.0";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
	}

	public class ForgeEventHandler {
		@SubscribeEvent
		public void onLivingDrops(LivingDropsEvent event) {
			if (event.entity instanceof EntityDragon) {
				EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Blocks.dragon_egg));
				event.drops.add(item);
			}
		}
	}

}
