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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import steamcraft.common.blocks.BaseBlock;
import steamcraft.common.blocks.BlockBrassLeaves;
import steamcraft.common.blocks.BlockBrassLog;
import steamcraft.common.blocks.BlockBrassPlaque;
import steamcraft.common.blocks.BlockCastIronFence;
import steamcraft.common.blocks.BlockCastIronGate;
import steamcraft.common.blocks.BlockCastIronLamp;
import steamcraft.common.blocks.BlockCopperWire;
import steamcraft.common.blocks.BlockCrystal;
import steamcraft.common.blocks.BlockCustomOre;
import steamcraft.common.blocks.BlockEngravedSolid;
import steamcraft.common.blocks.BlockEngravedVanilla;
import steamcraft.common.blocks.BlockEtherium;
import steamcraft.common.blocks.BlockFluidSteam;
import steamcraft.common.blocks.BlockLamp;
import steamcraft.common.blocks.BlockMetal;
import steamcraft.common.blocks.BlockSlate;
import steamcraft.common.blocks.BlockTeaPlant;
import steamcraft.common.blocks.BlockTimeBomb;
import steamcraft.common.blocks.BlockUranium;
import steamcraft.common.blocks.FluidSteam;
import steamcraft.common.blocks.machines.BlockArmorEditor;
import steamcraft.common.blocks.machines.BlockBattery;
import steamcraft.common.blocks.machines.BlockBloomery;
import steamcraft.common.blocks.machines.BlockCopperPipe;
import steamcraft.common.blocks.machines.BlockDropHammerAnvil;
import steamcraft.common.blocks.machines.BlockIntake;
import steamcraft.common.blocks.machines.BlockLightningRod;
import steamcraft.common.blocks.machines.BlockSteamBoiler;
import steamcraft.common.blocks.machines.BlockTeslaCoil;
import steamcraft.common.blocks.machines.BlockTurbine;
import steamcraft.common.itemblocks.ItemBlockCustomOre;
import steamcraft.common.itemblocks.ItemBlockEngravedSolid;
import steamcraft.common.itemblocks.ItemBlockEngravedVanilla;
import steamcraft.common.itemblocks.ItemBlockMetal;
import steamcraft.common.itemblocks.ItemBlockSlate;
import steamcraft.common.tiles.TileArmorEditor;
import steamcraft.common.tiles.TileBattery;
import steamcraft.common.tiles.TileBloomery;
import steamcraft.common.tiles.TileBrassPlaque;
import steamcraft.common.tiles.TileCopperPipe;
import steamcraft.common.tiles.TileCopperWire;
import steamcraft.common.tiles.TileCrystal;
import steamcraft.common.tiles.TileDropHammer;
import steamcraft.common.tiles.TileIntake;
import steamcraft.common.tiles.TileLightningRod;
import steamcraft.common.tiles.TileSteamBoiler;
import steamcraft.common.tiles.TileTeslaCoil;
import steamcraft.common.tiles.TileTurbine;
import boilerplate.common.RegistryHelper;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Surseance
 *
 */
public class InitBlocks
{
	/* Decorative */

	// Engraved Blocks
	public static Block blockEngraved, blockEngravedVanilla;

	// Cast Iron
	public static Block blockCastIronLampI, blockCastIronLampA;
	public static Block blockCastIronFence, blockCastIronGate;

	public static Block blockLamp;

	/* Ores */

	public static Block blockCustomOre;
	public static Block blockSlate;

	// Metals
	public static Block blockMetal, blockUranium, blockEtherium;

	/* Machines */

	public static Block blockSteamBoiler, blockIntake, blockTurbine, blockBattery;
	public static Block blockLightningRod, blockTeslaCoil;

	public static Block blockBloomery;

	public static Block blockArmorEditor;

	public static Block blockCopperPipe, blockCopperWire;
	public static Block blockCopperTank;

	/* Fluids */

	public static Fluid steamFluid;
	public static Block blockSteam;

	/* Others */

	public static Block blockCrystal;

	public static Block blockDropHammerAnvil;

	// Wood
	public static Block blockBrassLog, blockBrassLeaves;

	public static Block blockTeaPlant, blockHatch;

	public static Block blockBrassPlaque;

	public static Block blockTimeBomb;

	public static void init()
	{
		initializeDecorative();
		initializeOres();
		initializeMachines();
		initializeFluids();
		initializeOthers();
	}

	private static void initializeOres()
	{
		// Ores
		blockCustomOre = new BlockCustomOre().setBlockName("blockCustomOre");
		blockSlate = new BlockSlate().setBlockName("blockSlate");

		GameRegistry.registerBlock(blockCustomOre, ItemBlockCustomOre.class, "BlockCustomOre");
		GameRegistry.registerBlock(blockSlate, ItemBlockSlate.class, "BlockSlate");

		// Metals
		blockMetal = new BlockMetal();
		blockUranium = new BlockUranium(Material.iron);
		blockEtherium = new BlockEtherium(Material.iron).setBlockName("blockEtherium").setResistance(-1);

		GameRegistry.registerBlock(blockMetal, ItemBlockMetal.class, "BlockMetal");
		GameRegistry.registerBlock(blockUranium, "BlockUranium");
		GameRegistry.registerBlock(blockEtherium, "BlockEtherium");
	}

	private static void initializeDecorative()
	{
		// Engraved Blocks
		blockEngraved = new BlockEngravedSolid();
		blockEngravedVanilla = new BlockEngravedVanilla();

		GameRegistry.registerBlock(blockEngraved, ItemBlockEngravedSolid.class, "BlockEngravedSolid");
		GameRegistry.registerBlock(blockEngravedVanilla, ItemBlockEngravedVanilla.class, "BlockEngravedVanilla");

		// Cast Iron
		blockCastIronLampI = new BlockCastIronLamp(false).setBlockName("blockCastIronLampOff");
		blockCastIronLampA = new BlockCastIronLamp(true).setBlockName("blockCastIronLampOn");

		// GameRegistry.registerBlock(blockCastIronLampI, "BlockCastIronLampI");
		// GameRegistry.registerBlock(blockCastIronLampA, "BlockCastIronLampA");

		blockCastIronFence = new BlockCastIronFence();
		blockCastIronGate = new BlockCastIronGate();

		GameRegistry.registerBlock(blockCastIronFence, "BlockCastIronFence");
		GameRegistry.registerBlock(blockCastIronGate, "BlockCastIronGate");

		blockLamp = new BlockLamp();

		GameRegistry.registerBlock(blockLamp, "BlockLamp");
	}

	private static void initializeMachines()
	{
		// Steam related
		blockSteamBoiler = new BlockSteamBoiler().setBlockName("blockSteamBoiler");
		blockIntake = new BlockIntake().setBlockName("blockIntake");

		RegistryHelper.registerContainerBlockWithDesc(blockSteamBoiler, TileSteamBoiler.class, "BlockSteamBoiler");
		RegistryHelper.registerContainerBlockWithDesc(blockIntake, TileIntake.class, "BlockIntake");

		// Energy related
		blockTurbine = new BlockTurbine().setBlockName("blockTurbine");
		blockBattery = new BlockBattery().setBlockName("blockBattery");
		blockLightningRod = new BlockLightningRod(Material.iron).setBlockName("blockLightningRod");
		blockTeslaCoil = new BlockTeslaCoil(Material.iron).setBlockName("blockTeslaCoil");

		RegistryHelper.registerContainerBlockWithDesc(blockTurbine, TileTurbine.class, "BlockTurbine");
		RegistryHelper.registerContainerBlockWithDesc(blockBattery, TileBattery.class, "BlockBattery");
		RegistryHelper.registerContainerBlockWithDesc(blockLightningRod, TileLightningRod.class, "BlockLightningRod");
		RegistryHelper.registerContainerBlockWithDesc(blockTeslaCoil, TileTeslaCoil.class, "BlockTeslaCoil");

		// Bloomery
		blockBloomery = new BlockBloomery(Material.rock).setBlockName("blockBloomery");

		RegistryHelper.registerContainerBlockWithDesc(blockBloomery, TileBloomery.class, "BlockBloomery");

		// Armor Editor
		blockArmorEditor = new BlockArmorEditor(Material.iron);

		RegistryHelper.registerContainerBlock(blockArmorEditor, TileArmorEditor.class, "BlockArmorEditor");

		// Pipes
		blockCopperPipe = new BlockCopperPipe(Material.iron).setBlockName("blockCopperPipe");

		RegistryHelper.registerContainerBlock(blockCopperPipe, TileCopperPipe.class, "BlockCopperPipe");

		// Wires
		blockCopperWire = new BlockCopperWire(Material.iron).setBlockName("blockCopperWire");

		RegistryHelper.registerContainerBlock(blockCopperWire, TileCopperWire.class, "BlockCopperWire");

		// Tanks
		blockCopperTank = new BaseBlock(Material.iron).setBlockName("blockCopperTank");

		GameRegistry.registerBlock(blockCopperTank, "BlockCopperTank");
	}

	private static void initializeFluids()
	{
		// Steam
		steamFluid = new FluidSteam("steam");

		if (!FluidRegistry.registerFluid(steamFluid) && !FluidRegistry.isFluidRegistered("steam"))
			steamFluid = FluidRegistry.getFluid("steam");

		blockSteam = new BlockFluidSteam(steamFluid, Material.lava);

		GameRegistry.registerBlock(blockSteam, "blockSteam");
	}

	private static void initializeOthers()
	{
		blockCrystal = new BlockCrystal();

		RegistryHelper.registerContainerBlock(blockCrystal, TileCrystal.class, "BlockCrystal");

		// Wood
		blockBrassLog = new BlockBrassLog(Material.wood);
		blockBrassLeaves = new BlockBrassLeaves(Material.iron);

		GameRegistry.registerBlock(blockBrassLog, "BlockBrassLog");
		GameRegistry.registerBlock(blockBrassLeaves, "BlockBrassLeaves");

		blockDropHammerAnvil = new BlockDropHammerAnvil(Material.anvil).setBlockName("blockDropHammerAnvil");

		RegistryHelper.registerContainerBlock(blockDropHammerAnvil, TileDropHammer.class, "BlockDropHammerAnvil");

		blockTeaPlant = new BlockTeaPlant().setBlockName("blockTeaPlant");
		GameRegistry.registerBlock(blockTeaPlant, "BlockTeaPlant");

		blockBrassPlaque = new BlockBrassPlaque(TileBrassPlaque.class, true).setBlockName("blockBrassPlaque");
		RegistryHelper.registerContainerBlock(blockBrassPlaque, TileBrassPlaque.class, "BlockBrassPlaque");

		blockTimeBomb = new BlockTimeBomb(Material.tnt).setBlockName("blockTimeBomb");
		GameRegistry.registerBlock(blockTimeBomb, "BlockTimeBomb");
	}

}