package com.tfar.ferroustry.tree;

import com.tfar.ferroustry.Ferroustry;
import com.tfar.ferroustry.feature.BigResourceTreeFeature;
import com.tfar.ferroustry.feature.ResourceTreeFeature;
import com.tfar.ferroustry.feature.Tall2x2ResourceTreeFeature;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.trees.BigTree;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;

public class ResourceTree extends BigTree {

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
  protected AbstractTreeFeature<NoFeatureConfig> getBigTreeFeature(Random random) {
    return new Tall2x2ResourceTreeFeature(NoFeatureConfig::deserialize, true,true,log,leaf,
            ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Ferroustry.MODID,material+"_sapling")));
  }

  @Nullable
  @Override
  protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
    return random.nextInt(10) == 0 ? new BigResourceTreeFeature(NoFeatureConfig::deserialize, true,log,leaf,
            ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Ferroustry.MODID,material+"_sapling"))) :
            new ResourceTreeFeature(NoFeatureConfig::deserialize, true, false,log,leaf,
                    ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Ferroustry.MODID,material+"_sapling")));
  }
}
