package com.tfar.ferroustry.tree;

import com.tfar.ferroustry.Ferroustry;
import com.tfar.ferroustry.ResourceTreeFeature;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;

public class ResourceTree extends Tree {

  public final LogBlock log;
  public final LeavesBlock leaf;
  private String material;

  public ResourceTree(LogBlock log, LeavesBlock leaf, String material) {
    this.log = log;
    this.leaf = leaf;
    this.material = material;
  }
  @Nullable
  @Override
  protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
    return new ResourceTreeFeature(NoFeatureConfig::deserialize, true, false,log,leaf,
            ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Ferroustry.MODID,material+"_sapling")));
  }
}
