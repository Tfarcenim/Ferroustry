package com.tfar.ferroustry.client;

import com.tfar.ferroustry.Ferroustry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LogColor {

  private static final Minecraft mc = Minecraft.getInstance();

  public static final Map<String, Integer> TINTS = new HashMap<>();

  static {
    //TINTS.put("redstone", 0xE50606);
    //TINTS.put("netherquartz", 0xD4CABA);
    //TINTS.put("lapislazuli", 0x1855BD);
    //TINTS.put("iron", 0xFFFFFF);
    //TINTS.put("gold", 0xFFDD00);
    //TINTS.put("emerald", 0x17DD62);
    //TINTS.put("diamond", 0x00FFEE);
    //TINTS.put("coal", 0x606060);
  }

  @SubscribeEvent
  public static void colors(FMLClientSetupEvent e) {
    final BlockColors colors = mc.getBlockColors();
    final IBlockColor treecolor = (state, blockAccess, pos, tintIndex) -> getColor(state);
    for (final Block block : Ferroustry.RegistryEvents.MOD_BLOCKS)
      colors.register(treecolor, block);

    final ItemColors itemColors = mc.getItemColors();

    final IItemColor itemBlockColor = (stack, tintIndex) -> {
      final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
      return colors.getColor(state, null, null, 0);
    };
    for (final Block block : Ferroustry.RegistryEvents.MOD_BLOCKS)
      itemColors.register(itemBlockColor, block);
  }

  private static int getColor(BlockState state) {
    String[] material = state.getBlock().getRegistryName().getPath().split("_");
    return TINTS.getOrDefault(material[0], 0xFFFFFF);
  }
}

