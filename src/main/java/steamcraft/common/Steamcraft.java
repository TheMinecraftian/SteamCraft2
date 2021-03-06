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
package steamcraft.common;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.gen.structure.MapGenStructureIO;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import steamcraft.client.GuiHandler;
import steamcraft.common.compat.CompatabilityLayer;
import steamcraft.common.config.Config;
import steamcraft.common.config.ConfigGeneral;
import steamcraft.common.config.ConfigWorldGen;
import steamcraft.common.init.InitAchievements;
import steamcraft.common.init.InitBiomes;
import steamcraft.common.init.InitBlocks;
import steamcraft.common.init.InitEntities;
import steamcraft.common.init.InitItems;
import steamcraft.common.init.InitMisc;
import steamcraft.common.init.InitPackets;
import steamcraft.common.init.InitRecipes;
import steamcraft.common.lib.BucketHandler;
import steamcraft.common.lib.CommandSteamcraft;
import steamcraft.common.lib.CreativeTabSteamcraft;
import steamcraft.common.lib.LoggerSteamcraft;
import steamcraft.common.lib.ModInfo;
import steamcraft.common.lib.events.EventHandlerClient;
import steamcraft.common.lib.events.EventHandlerFML;
import steamcraft.common.lib.events.EventHandlerForge;
import steamcraft.common.worldgen.WorldGenSteamcraft;
import steamcraft.common.worldgen.dimension.WorldProviderDeeps;
import steamcraft.common.worldgen.structure.MapGenCustomScatteredFeature;
import steamcraft.common.worldgen.structure.StructureUndercityPieces;
import steamcraft.common.worldgen.structure.StructureUndercityStart;
import boilerplate.common.compathandler.FMPCompatHandler;

/**
 * @author Surseance
 * 
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, guiFactory = ModInfo.CONFIG_GUI, dependencies = "required-after:boilerplate")
public class Steamcraft
{
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(ModInfo.ID)
	public static Steamcraft instance;

	public static CreativeTabs tabSC2 = new CreativeTabSteamcraft(CreativeTabs.getNextID(), "steamcraft");

	public static File configFolder;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LoggerSteamcraft.info("Starting Preinit");

		configFolder = new File(event.getModConfigurationDirectory(), "sc2");
		Config.initialise(configFolder);

		InitBlocks.init();
		InitItems.init();

		BucketHandler.BUCKETS.put(InitBlocks.blockBoilingMud, InitItems.itemBoilingMudBucket);
		BucketHandler.BUCKETS.put(InitBlocks.blockBoilingWater, InitItems.itemBoilingWaterBucket);
		MinecraftForge.EVENT_BUS.register(new BucketHandler());

		CompatabilityLayer.initCompatItems();

		LoggerSteamcraft.info("Finished Preinit");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LoggerSteamcraft.info("Starting Init");

		CompatabilityLayer.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		InitPackets.init();

		InitEntities.init();
		proxy.init();

		MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
		MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
		FMLCommonHandler.instance().bus().register(new EventHandlerFML());
		FMLCommonHandler.instance().bus().register(new EventHandlerClient());

		MapGenStructureIO.registerStructure(StructureUndercityStart.class, ModInfo.ID + "Undercity");
		StructureUndercityPieces.registerStructurePieces();

		MapGenStructureIO.registerStructure(MapGenCustomScatteredFeature.class, ModInfo.ID + "CustomScatteredFeature");

		if(ConfigWorldGen.generationEnabled)
			GameRegistry.registerWorldGenerator(new WorldGenSteamcraft(), 1);

		DimensionManager.registerProviderType(ConfigGeneral.deepsDimensionID, WorldProviderDeeps.class, false);
		DimensionManager.registerDimension(ConfigGeneral.deepsDimensionID, ConfigGeneral.deepsDimensionID);

		InitBiomes.init();

		FMPCompatHandler.doRegister();

		LoggerSteamcraft.info("Finished Init");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LoggerSteamcraft.info("Starting Postinit");

		CompatabilityLayer.postInit();

		InitRecipes.init();
		InitAchievements.init();
		InitMisc.initDungeonLoot();

		LoggerSteamcraft.info("Finished Postinit");
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSteamcraft());
	}
}
