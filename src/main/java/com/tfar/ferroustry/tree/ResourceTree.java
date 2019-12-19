package com.tfar.ferroustry.tree;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class ResourceTree extends BigTree {

  private final Feature<TreeFeatureConfig> treeFeatureConfigFeature;
  private final TreeFeatureConfig treeFeatureConfig;
  private final Feature<HugeTreeFeatureConfig> hugeTreeFeatureConfigFeature;
  private final HugeTreeFeatureConfig hugeTreeFeatureConfig1;
  private final HugeTreeFeatureConfig hugeTreeFeatureConfig2;

  public ResourceTree(Feature<TreeFeatureConfig> treeFeatureConfigFeature,
                      TreeFeatureConfig treeFeatureConfig, Feature<HugeTreeFeatureConfig> hugeTreeFeatureConfigFeature,
                      HugeTreeFeatureConfig hugeTreeFeatureConfig1,HugeTreeFeatureConfig hugeTreeFeatureConfig2) {
    this.treeFeatureConfigFeature = treeFeatureConfigFeature;
    this.treeFeatureConfig = treeFeatureConfig;
    this.hugeTreeFeatureConfigFeature = hugeTreeFeatureConfigFeature;
    this.hugeTreeFeatureConfig1 = hugeTreeFeatureConfig1;
    this.hugeTreeFeatureConfig2 = hugeTreeFeatureConfig2;
  }

  @Nullable
  @Override
  protected ConfiguredFeature<TreeFeatureConfig, ?> getConfiguredFeature(Random random) {
    return treeFeatureConfigFeature.configured(treeFeatureConfig);
  }

  @Nullable
  @Override
  protected ConfiguredFeature<HugeTreeFeatureConfig,?> getConfiguredMegaFeature(Random random) {
    return hugeTreeFeatureConfigFeature.configured(random.nextBoolean()
            ? hugeTreeFeatureConfig1
            : hugeTreeFeatureConfig2);
  }

}
