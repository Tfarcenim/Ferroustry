package com.tfar.ferroustry.tree;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class ResourceTree extends BigTree {

  private final Feature<BaseTreeFeatureConfig> treeFeatureConfigFeature;
  private final BaseTreeFeatureConfig treeFeatureConfig;
  private final Feature<BaseTreeFeatureConfig> hugeTreeFeatureConfigFeature;
  private final BaseTreeFeatureConfig hugeTreeFeatureConfig1;
  private final BaseTreeFeatureConfig hugeTreeFeatureConfig2;

  public ResourceTree(Feature<BaseTreeFeatureConfig> treeFeatureConfigFeature,
                      BaseTreeFeatureConfig treeFeatureConfig, Feature<BaseTreeFeatureConfig> hugeTreeFeatureConfigFeature,
                      BaseTreeFeatureConfig hugeTreeFeatureConfig1,BaseTreeFeatureConfig hugeTreeFeatureConfig2) {
    this.treeFeatureConfigFeature = treeFeatureConfigFeature;
    this.treeFeatureConfig = treeFeatureConfig;
    this.hugeTreeFeatureConfigFeature = hugeTreeFeatureConfigFeature;
    this.hugeTreeFeatureConfig1 = hugeTreeFeatureConfig1;
    this.hugeTreeFeatureConfig2 = hugeTreeFeatureConfig2;
  }

  @Override
  protected ConfiguredFeature<BaseTreeFeatureConfig,?> getConfiguredMegaFeature(Random random) {
    return hugeTreeFeatureConfigFeature.configured(random.nextBoolean()
            ? hugeTreeFeatureConfig1
            : hugeTreeFeatureConfig2);
  }

  @Override
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean b) {
    return treeFeatureConfigFeature.configured(treeFeatureConfig);
  }
}
