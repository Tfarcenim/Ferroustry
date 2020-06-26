package com.tfar.ferroustry.tree;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class ResourceTree extends BigTree {

  private final BaseTreeFeatureConfig baseTreeFeatureConfig;

  public ResourceTree(BaseTreeFeatureConfig treeFeatureConfigFeature) {
    this.baseTreeFeatureConfig = treeFeatureConfigFeature;
  }

  @Nullable
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_) {
    return Feature.TREE.configured(baseTreeFeatureConfig);
  }

  @Nullable
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random p_225547_1_) {
    return Feature.TREE.configured(p_225547_1_.nextBoolean() ? DefaultBiomeFeatures.MEGA_SPRUCE_TREE_CONFIG : DefaultBiomeFeatures.MEGA_PINE_TREE_CONFIG);
  }
}
