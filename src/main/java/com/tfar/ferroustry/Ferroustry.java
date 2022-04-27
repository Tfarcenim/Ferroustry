package com.tfar.ferroustry;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
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
import java.util.Map;
import java.util.Set;

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
    Map<Block, Block> map = new HashMap<>(AxeItem.STRIPABLES);
    RegistryEvents.MOD_BLOCKS.stream().filter(block -> {
      String name = block.getRegistryName().getPath();
      return (name.endsWith("log") || name.endsWith("wood")) && !name.startsWith("stripped");
    })
            .forEach(block -> map.put(block, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "stripped_" +
            block.getRegistryName().getPath()))));
    AxeItem.STRIPABLES = map;
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
  @SuppressWarnings({"unused", "deprecation"})
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private RegistryEvents() {}

    protected static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
      // register a new blocks here
      AbstractBlock.Properties log = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2, 4).sound(SoundType.WOOD);
      AbstractBlock.Properties leaves = AbstractBlock.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion();
      AbstractBlock.Properties sapling = AbstractBlock.Properties.of(Material.LEAVES).noCollission().randomTicks().strength(0).sound(SoundType.GRASS);
      AbstractBlock.Properties plank = AbstractBlock.Properties.of(Material.WOOD, MaterialColor.NONE).strength(2, 6).sound(SoundType.WOOD);
      for (OreType material : OreType.values()) {
        Block logBlock = new Block(log);
        LeavesBlock leavesBlock = new LeavesBlock(leaves);
        register(logBlock, material + "_log", event.getRegistry());
        register(leavesBlock, material + "_leaves", event.getRegistry());

        Feature<BaseTreeFeatureConfig> feature = new TreeFeature(BaseTreeFeatureConfig.CODEC);
        BaseTreeFeatureConfig treeFeatureConfig = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()),
                new SimpleBlockStateProvider(leavesBlock.defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))
                .ignoreVines().build();
        Feature<BaseTreeFeatureConfig> bigFeature = new TreeFeature(BaseTreeFeatureConfig.CODEC);

        BaseTreeFeatureConfig hugeTreeFeatureConfig1 = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()),
                new SimpleBlockStateProvider(leavesBlock.defaultBlockState()), new MegaPineFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), FeatureSpread.of(13, 4)),
                new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))
                .decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.defaultBlockState())))).build();
        BaseTreeFeatureConfig hugeTreeFeatureConfig2 = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(logBlock.defaultBlockState()),
                new SimpleBlockStateProvider(leavesBlock.defaultBlockState()), new MegaPineFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), FeatureSpread.of(3, 4)),
                new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))
                .decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.defaultBlockState())))).build();
        SaplingBlock resourceSaplingBlock = new SaplingBlock(new ResourceTree(feature, treeFeatureConfig,
                bigFeature, hugeTreeFeatureConfig1, hugeTreeFeatureConfig2), sapling);

        register(resourceSaplingBlock,material + "_sapling", event.getRegistry());
        FlowerPotBlock flowerPotBlock = new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT,() -> resourceSaplingBlock,AbstractBlock.Properties.of(Material.DECORATION).strength(0).noOcclusion());
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(resourceSaplingBlock.getRegistryName(),() -> flowerPotBlock);
        register(flowerPotBlock,"potted_"+material+"_sapling",event.getRegistry());
        Block planks = new Block(plank);
        register(planks, material + "_planks", event.getRegistry());
        register(new SlabBlock(AbstractBlock.Properties.copy(planks)), material + "_slab", event.getRegistry());
        register(new StairsBlock(planks.defaultBlockState(), AbstractBlock.Properties.copy(planks)), material + "_stairs", event.getRegistry());
        register(new FenceBlock(plank), material + "_fence", event.getRegistry());
        register(new RotatedPillarBlock(log), material + "_wood", event.getRegistry());
        register(new Block(log), "stripped_" + material + "_log", event.getRegistry());
        register(new RotatedPillarBlock(log), "stripped_" + material + "_wood", event.getRegistry());
      }
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
      // register a new blocks here
      Item.Properties properties = new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS);
      for (Block block : MOD_BLOCKS) {
        if (!(block instanceof FlowerPotBlock)) {
          register(new BlockItem(block, properties), block.getRegistryName().getPath(), event.getRegistry());
        }
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
