package com.tfar.ferroustry;

import com.tfar.ferroustry.tree.ResourceTree;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

@Mod(Ferroustry.MODID)
public class Ferroustry {
  public static final String MODID = "ferroustry";

  public Ferroustry() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
  }

  public static final boolean DEV;

  static {
    boolean DEV1;
    try {
      Items.class.getField("field_190931_a");
      DEV1 = false;
    } catch (NoSuchFieldException e) {
      DEV1 = true;
    }
    DEV = DEV1;
  }

  private void setup(final FMLCommonSetupEvent event) {
    Map<Block, Block> map = new HashMap<>();
    RegistryEvents.MOD_BLOCKS.stream().filter(block -> {
      String name = block.getRegistryName().getPath();
      return (name.endsWith("log") || name.endsWith("wood")) && !name.startsWith("stripped");
    })
            .forEach(block -> map.put(block, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "stripped_" +
            block.getRegistryName().getPath()))));
  }

  private void client(final FMLClientSetupEvent event) {
    RegistryEvents.MOD_BLOCKS.stream()
            .filter(block -> block instanceof SaplingBlock || block instanceof LeavesBlock || block instanceof FlowerPotBlock)
            .forEach(block -> {
              RenderType renderType = block instanceof LeavesBlock ? RenderType.cutoutMipped() : RenderType.cutout();
              ItemBlockRenderTypes.setRenderLayer(block, renderType);
            });
  }
  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @SuppressWarnings({"unused", "deprecation"})
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private RegistryEvents() {}

    protected static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
      // register a new blocks here
      BlockBehaviour.Properties log = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2, 4).sound(SoundType.WOOD);
      BlockBehaviour.Properties leaves = BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion();
      BlockBehaviour.Properties sapling = BlockBehaviour.Properties.of(Material.LEAVES).noCollission().randomTicks().strength(0).sound(SoundType.GRASS);
      BlockBehaviour.Properties plank = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.NONE).strength(2, 6).sound(SoundType.WOOD);
      for (OreType material : OreType.values()) {
        RotatedPillarBlock logBlock = new RotatedPillarBlock(log);
        LeavesBlock leavesBlock = new LeavesBlock(leaves);
        register(logBlock, material + "_log", event.getRegistry());
        register(leavesBlock, material + "_leaves", event.getRegistry());

        Feature<TreeConfiguration> feature = new TreeFeature(TreeConfiguration.CODEC);
        Holder<ConfiguredFeature<TreeConfiguration, ?>> resource_tree = FeatureUtils.register("resource_tree_" + material.trueName(), feature, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(logBlock.defaultBlockState()), new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(leavesBlock.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        Feature<TreeConfiguration> bigFeature = new TreeFeature(TreeConfiguration.CODEC);
        Holder<ConfiguredFeature<TreeConfiguration, ?>> mega_resource_tree = FeatureUtils.register("mega_resource_tree_" + material.trueName(), bigFeature,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(logBlock.defaultBlockState()),
                new GiantTrunkPlacer(13, 2, 14),BlockStateProvider.simple(leavesBlock.defaultBlockState()),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 4)),
                 new TwoLayersFeatureSize(1, 1, 2))
                .decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL.defaultBlockState())))).build());
        SaplingBlock resourceSaplingBlock = new SaplingBlock(new ResourceTree(resource_tree, mega_resource_tree), sapling);

        register(resourceSaplingBlock,material + "_sapling", event.getRegistry());
        FlowerPotBlock flowerPotBlock = new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT,() -> resourceSaplingBlock,BlockBehaviour.Properties.of(Material.DECORATION).strength(0).noOcclusion());
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(resourceSaplingBlock.getRegistryName(),() -> flowerPotBlock);
        register(flowerPotBlock,"potted_"+material+"_sapling",event.getRegistry());
        Block planks = new Block(plank);
        register(planks, material + "_planks", event.getRegistry());
        register(new SlabBlock(BlockBehaviour.Properties.copy(planks)), material + "_slab", event.getRegistry());
        register(new StairBlock(planks.defaultBlockState(), BlockBehaviour.Properties.copy(planks)), material + "_stairs", event.getRegistry());
        register(new FenceBlock(plank), material + "_fence", event.getRegistry());
        register(new RotatedPillarBlock(log), material + "_wood", event.getRegistry());
        register(new RotatedPillarBlock(log), "stripped_" + material + "_log", event.getRegistry());
        register(new RotatedPillarBlock(log), "stripped_" + material + "_wood", event.getRegistry());
      }
    }

    private static RotatedPillarBlock log(MaterialColor p_235430_0_, MaterialColor p_235430_1_) {
      return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (blockState) ->
                      blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_235430_0_ : p_235430_1_)
              .strength(2.0F).sound(SoundType.WOOD));
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
      // register a new blocks here
      Item.Properties properties = new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
      for (Block block : MOD_BLOCKS) {
        if (!(block instanceof FlowerPotBlock)) {
          register(new BlockItem(block, properties), block.getRegistryName().getPath(), event.getRegistry());
        }
      }
      Item.Properties properties1 = new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS);
      register(new Item(properties1), "aluminum_ingot", event.getRegistry());
      register(new Item(properties1), "copper_ingot", event.getRegistry());
      register(new Item(properties1), "silver_ingot", event.getRegistry());
      register(new Item(properties1), "lead_ingot", event.getRegistry());
      register(new Item(properties1), "tin_ingot", event.getRegistry());
      register(new Item(properties1), "bismuth_ingot", event.getRegistry());
    }

    private static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
      registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
      if (obj instanceof Block block) MOD_BLOCKS.add(block);
    }
  }
}
