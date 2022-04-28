package com.tfar.ferroustry.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class ResourceTree extends AbstractMegaTreeGrower {
  public Holder<ConfiguredFeature<TreeConfiguration, ?>> RESOURCE_TREE;
  public Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_RESOURCE_TREE;

  public ResourceTree(Holder<ConfiguredFeature<TreeConfiguration, ?>> resource_tree, Holder<ConfiguredFeature<TreeConfiguration, ?>> mega_resource_tree) {
    this.RESOURCE_TREE = resource_tree;
    this.MEGA_RESOURCE_TREE = mega_resource_tree;
  }

  @Override
  protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
    return MEGA_RESOURCE_TREE;
  }

  @Override
  protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean b) {
    return RESOURCE_TREE;
  }
}
