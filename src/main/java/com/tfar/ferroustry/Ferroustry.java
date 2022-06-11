package com.tfar.ferroustry;

import com.tfar.ferroustry.tree.ResourceTree;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluid;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

import static com.tfar.ferroustry.Ferroustry.RegistryEvents.MOD_BLOCKS;

@Mod(Ferroustry.MODID)
public class Ferroustry {
  public static final String MODID = "ferroustry";
  public static final CreativeModeTab TAB = new CreativeModeTab("ferroustry") {
    @Override
    public @NotNull ItemStack makeIcon() {
      return Items.OAK_SAPLING.getDefaultInstance();
    }
  };

  public Ferroustry() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
  }

  private void setup(final FMLCommonSetupEvent event) {
    Map<Block, Block> map = new HashMap<>();
    MOD_BLOCKS.stream().filter(block -> {
      String name = getRegistryName(block).getPath();
      return (name.endsWith("log") || name.endsWith("wood")) && !name.startsWith("stripped");
    })
            .forEach(block -> map.put(block, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "stripped_" +
            getRegistryName(block).getPath()))));
  }

  private void client(final FMLClientSetupEvent event) {
    MOD_BLOCKS.stream()
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
    public static void onBlocksRegistry(final RegisterEvent event) {
      if (!event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)) {
        return;
      }
      // register a new blocks here
      BlockBehaviour.Properties log = BlockBehaviour.Properties.copy(Blocks.OAK_LOG);
      BlockBehaviour.Properties leaves = BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES);
      BlockBehaviour.Properties sapling = BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING);
      BlockBehaviour.Properties plank = BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
      for (OreType material : OreType.values()) {
        RotatedPillarBlock logBlock = new RotatedPillarBlock(log);
        LeavesBlock leavesBlock = new LeavesBlock(leaves);
        register(event, material + "_log", logBlock);
        register(event, material + "_leaves", leavesBlock);

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

        register(event, material + "_sapling", resourceSaplingBlock);
        FlowerPotBlock flowerPotBlock = new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT,() -> resourceSaplingBlock,BlockBehaviour.Properties.of(Material.DECORATION).strength(0).noOcclusion());
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(getRegistryName(resourceSaplingBlock),() -> flowerPotBlock);
        register(event, "potted_"+material+"_sapling", flowerPotBlock);
        Block planks = new Block(plank);
        register(event, material + "_planks", planks);
        register(event, material + "_slab", new SlabBlock(BlockBehaviour.Properties.copy(planks)));
        register(event, material + "_stairs", new StairBlock(planks.defaultBlockState(), BlockBehaviour.Properties.copy(planks)));
        register(event, material + "_fence", new FenceBlock(plank));
        register(event, material + "_wood", new RotatedPillarBlock(log));
        register(event, "stripped_" + material + "_log", new RotatedPillarBlock(log));
        register(event, "stripped_" + material + "_wood", new RotatedPillarBlock(log));
      }
    }

    private static RotatedPillarBlock log(MaterialColor p_235430_0_, MaterialColor p_235430_1_) {
      return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (blockState) ->
                      blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_235430_0_ : p_235430_1_)
              .strength(2.0F).sound(SoundType.WOOD));
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegisterEvent event) {
      if (!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
        return;
      }
      // register a new blocks here
      Item.Properties properties = new Item.Properties().tab(TAB);
      for (Block block : MOD_BLOCKS) {
        if (!(block instanceof FlowerPotBlock)) {
          register(event, getRegistryName(block).getPath(), new BlockItem(block, properties));
        }
      }
      register(event, "aluminum_ingot", new Item(properties));
      register(event, "copper_ingot", new Item(properties));
      register(event, "silver_ingot", new Item(properties));
      register(event, "lead_ingot", new Item(properties));
      register(event, "tin_ingot", new Item(properties));
      register(event, "bismuth_ingot", new Item(properties));
    }
  }

  public static ResourceLocation getRegistryName(Object obj) {
    ResourceLocation rl;
    if (obj instanceof Block block) {
      rl = ForgeRegistries.BLOCKS.getKey(block);
    } else if (obj instanceof Item item) {
      rl = ForgeRegistries.ITEMS.getKey(item);
    } else if (obj instanceof Fluid fluid) {
      rl = ForgeRegistries.FLUIDS.getKey(fluid);
    } else {
      rl = new ResourceLocation("null");
    }
    return rl;
  }

  public static void register(RegisterEvent event, String name, Object obj) {
    if (obj instanceof Block block && event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)) {
      event.register(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(MODID, name), () -> block);
      MOD_BLOCKS.add(block);
    } else if (obj instanceof Item item && event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
      event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(MODID, name), () -> item);
    } else if (obj instanceof Fluid fluid && event.getRegistryKey().equals(ForgeRegistries.Keys.FLUIDS)) {
      event.register(ForgeRegistries.Keys.FLUIDS, new ResourceLocation(MODID, name), () -> fluid);
    }
  }
}
