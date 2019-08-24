package com.tfar.ferroustry;

import com.tfar.ferroustry.block.ResourceSaplingBlock;
import com.tfar.ferroustry.block.ResourceStairsBlock;
import com.tfar.ferroustry.tree.ResourceTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ferroustry.MODID)
public class Ferroustry {
  // Directly reference a log4j logger.

  public static final String MODID = "ferroustry";

  private static final Logger LOGGER = LogManager.getLogger();

  public Ferroustry() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
  }

  public static boolean DEV;

  static {
    try {
      Items.class.getField("field_190931_a");
      DEV = false;
    } catch (NoSuchFieldException e){
      DEV = true;
    }
  }

  private void setup(final FMLCommonSetupEvent event) {
    Map<Block,Block> map = new HashMap<>(AxeItem.BLOCK_STRIPPING_MAP);
    RegistryEvents.MOD_BLOCKS.forEach(block -> {
      String name = block.getRegistryName().getPath();
      if ((name.endsWith("log") || name.endsWith("wood")) && !name.startsWith("stripped")) {
        map.put(block, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "stripped_" +
                block.getRegistryName().getPath())));
      }
    });
    AxeItem.BLOCK_STRIPPING_MAP = map;
    if (DEV) EVENT_BUS.register(Scripts.JsonCrap.class);
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {

    public static final String[] materials = new String[]
            {"coal", "diamond", "emerald", "iron", "gold", "lapis", "quartz", "redstone"};
    public static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
      // register a new blocks here
      Block.Properties log = Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2, 4).sound(SoundType.WOOD);
      Block.Properties leaves = Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT);
      Block.Properties sapling = Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.PLANT);
      Block.Properties plank = Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2, 6).sound(SoundType.WOOD);
      for (String material : materials) {
        LogBlock logBlock = new LogBlock(MaterialColor.AIR, log);
        LeavesBlock leavesBlock = new LeavesBlock(leaves);
        register(logBlock, material + "_log", event.getRegistry());
        register(leavesBlock, material + "_leaves", event.getRegistry());
        register(new ResourceSaplingBlock(new ResourceTree(logBlock, leavesBlock, material), sapling),
                material + "_sapling", event.getRegistry());
        Block planks = new Block(plank);
        register(planks, material + "_planks", event.getRegistry());
        register(new SlabBlock(Block.Properties.from(planks)), material+"_slab",event.getRegistry());
        register(new ResourceStairsBlock(planks.getDefaultState(),Block.Properties.from(planks)), material+"_stairs",event.getRegistry());
        register(new FenceBlock(plank),material+ "_fence",event.getRegistry());
        register(new RotatedPillarBlock(log),material + "_wood",event.getRegistry());
        register(new LogBlock(MaterialColor.WOOD,log), "stripped_" + material + "_log",event.getRegistry());
        register(new RotatedPillarBlock(log),"stripped_" + material + "_wood",event.getRegistry());
      }
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
      // register a new blocks here
      Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
      for (Block block : MOD_BLOCKS) {
        register(new BlockItem(block, properties), block.getRegistryName().getPath(), event.getRegistry());
      }
    }

    private static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
      registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
      if (obj instanceof Block) MOD_BLOCKS.add((Block) obj);
    }
  }
}
