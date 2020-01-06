package com.tfar.ferroustry;

import com.google.common.collect.ImmutableList;
import com.tfar.ferroustry.block.ResourceSaplingBlock;
import com.tfar.ferroustry.block.ResourceStairsBlock;
import com.tfar.ferroustry.tree.ResourceTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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

import static net.minecraft.world.gen.surfacebuilders.SurfaceBuilder.PODZOL;
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
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
  }

  public static boolean DEV;

  static {
    try {
      Items.class.getField("field_190931_a");
      DEV = false;
    } catch (NoSuchFieldException e) {
      DEV = true;
    }
  }

  private void setup(final FMLCommonSetupEvent event) {
    Map<Block, Block> map = new HashMap<>(AxeItem.STRIPABLES);
    RegistryEvents.MOD_BLOCKS.stream().filter(block -> {
      String name = block.getRegistryName().getPath();
      return (name.endsWith("log") || name.endsWith("wood")) && !name.startsWith("stripped");
    })
            .forEach(block -> map.put(block, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "stripped_" +
            block.getRegistryName().getPath()))));
    AxeItem.STRIPABLES = map;
    if (DEV) EVENT_BUS.register(Scripts.JsonCrap.class);
  }

  private void client(final FMLClientSetupEvent event) {
    RegistryEvents.MOD_BLOCKS.stream()
            .filter(block -> block instanceof SaplingBlock || block instanceof LeavesBlock || block instanceof FlowerPotBlock)
            .forEach(block -> {
              RenderType renderType = block instanceof LeavesBlock ? RenderType.cutoutMipped() : RenderType.cutout();
              RenderTypeLookup.setRenderLayer(block, renderType);
            });
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {

    public static final Set<Block> MOD_BLOCKS = new HashSet<>();

    public static final Set<Feature<?>> FEATURES = new HashSet<>();


    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
      // register a new blocks here
      Block.Properties log = Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2, 4).sound(SoundType.WOOD);
      Block.Properties leaves = Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion();
      Block.Properties sapling = Block.Properties.of(Material.LEAVES).noCollission().randomTicks().strength(0).sound(SoundType.GRASS);
      Block.Properties plank = Block.Properties.of(Material.WOOD, MaterialColor.NONE).strength(2, 6).sound(SoundType.WOOD);
      for (OreType material : OreType.values()) {
        LogBlock logBlock = new LogBlock(MaterialColor.NONE, log);
        LeavesBlock leavesBlock = new LeavesBlock(leaves);
        register(logBlock, material + "_log", event.getRegistry());
        register(leavesBlock, material + "_leaves", event.getRegistry());
        Feature<TreeFeatureConfig> feature = new TreeFeature(TreeFeatureConfig::deserialize);
        TreeFeatureConfig treeFeatureConfig = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()),
                new SimpleBlockStateProvider(leavesBlock.defaultBlockState()), new BlobFoliagePlacer(2, 0)).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()/*.setSapling((net.minecraftforge.common.IPlantable)sapling)*/.build();
        Feature<HugeTreeFeatureConfig> bigFeature = new MegaPineTree(HugeTreeFeatureConfig::deserialize);

        HugeTreeFeatureConfig hugeTreeFeatureConfig1 = new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()), new SimpleBlockStateProvider(leavesBlock.defaultBlockState())).baseHeight(13).heightInterval(15).crownHeight(13).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL))))./*setSapling((net.minecraftforge.common.IPlantable)sapling).*/build();
        HugeTreeFeatureConfig hugeTreeFeatureConfig2 = new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()), new SimpleBlockStateProvider(leavesBlock.defaultBlockState())).baseHeight(13).heightInterval(15).crownHeight(3).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL))))./*setSapling((net.minecraftforge.common.IPlantable)sapling).*/build();
        ResourceSaplingBlock resourceSaplingBlock = new ResourceSaplingBlock(new ResourceTree(feature, treeFeatureConfig,
                bigFeature, hugeTreeFeatureConfig1, hugeTreeFeatureConfig2), sapling);
        register(resourceSaplingBlock,
                material + "_sapling", event.getRegistry());
        FlowerPotBlock flowerPotBlock = new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT,() -> resourceSaplingBlock,Block.Properties.of(Material.DECORATION).strength(0).noOcclusion());
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(resourceSaplingBlock.getRegistryName(),() -> flowerPotBlock);
        register(flowerPotBlock,"potted_"+material+"_sapling",event.getRegistry());
        Block planks = new Block(plank);
        register(planks, material + "_planks", event.getRegistry());
        register(new SlabBlock(Block.Properties.copy(planks)), material + "_slab", event.getRegistry());
        register(new ResourceStairsBlock(planks.defaultBlockState(), Block.Properties.copy(planks)), material + "_stairs", event.getRegistry());
        register(new FenceBlock(plank), material + "_fence", event.getRegistry());
        register(new RotatedPillarBlock(log), material + "_wood", event.getRegistry());
        register(new LogBlock(MaterialColor.WOOD, log), "stripped_" + material + "_log", event.getRegistry());
        register(new RotatedPillarBlock(log), "stripped_" + material + "_wood", event.getRegistry());
      }
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
      // register a new blocks here
      Item.Properties properties = new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS);
      for (Block block : MOD_BLOCKS) {
        if (!(block instanceof FlowerPotBlock))
        register(new BlockItem(block, properties), block.getRegistryName().getPath(), event.getRegistry());
      }
      Item.Properties properties1 = new Item.Properties().tab(ItemGroup.TAB_MATERIALS);
      register(new Item(properties1), "aluminum_ingot", event.getRegistry());
      register(new Item(properties1), "copper_ingot", event.getRegistry());
      register(new Item(properties1), "silver_ingot", event.getRegistry());
      register(new Item(properties1), "lead_ingot", event.getRegistry());
      register(new Item(properties1), "tin_ingot", event.getRegistry());
      register(new Item(properties1), "bismuth_ingot", event.getRegistry());
    }

    private static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
      registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
      if (obj instanceof Block) MOD_BLOCKS.add((Block) obj);
    }
  }
}
