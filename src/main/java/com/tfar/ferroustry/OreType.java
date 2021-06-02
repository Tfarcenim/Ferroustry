package com.tfar.ferroustry;

import net.minecraft.block.Block;

import static com.tfar.ferroustry.Ferroustry.MODID;

public enum OreType {
  //vanilla
  coal(false, false),
  diamond(false, false),
  emerald(false, false),
  gold(false, true),
  iron(false, true),
  lapis(false, false),
  quartz(false, false),
  redstone(false, false),
  //modded
  aluminum(true, true),
  copper(true, true),
  lead(true, true),
  //bismuth(true, true),
  //platinum(true, true),
  //zinc(true, true),
  //nickel(true, true),
  //uranium(true, true),
  //osmium(true, true),
  silver(true, true),
  tin(true, true);

  public final boolean isModded;
  public final boolean isIngot;

  OreType(boolean isModded, boolean isIngot) {
    this.isModded = isModded;
    this.isIngot = isIngot;
  }

  public String trueName(){
    switch (this){
      case lapis:return "lapis_lazuli";
        default:
          return toString();
    }
  }

  public String getBasicState(BasicStateType type) {
    return "{\n" +
            "  \"variants\": {\n" +
            "    \"\": {\n" +
            "      \"model\": \"ferroustry:block/" + toString() + "_" + type.toString() + "\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
  }

  public String getPottedState() {
    return "{\n" +
            "  \"variants\": {\n" +
            "    \"\": {\n" +
            "      \"model\": \"ferroustry:block/potted_" + toString() + "_sapling" + "\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
  }

  public String getLogState(boolean stripped, boolean bark) {
    String s1 = stripped ? "stripped_" : "";
    String s2 = bark ? "wood" : "log";
    return "{\n" +
            "    \"variants\": {\n" +
            "        \"axis=y\":    { \"model\": \"" + MODID + ":block/" + s1 + toString() + "_" + s2 + "\" },\n" +
            "        \"axis=z\":    { \"model\": \"" + MODID + ":block/" + s1 + toString() + "_" + s2 + "\", \"x\": 90 },\n" +
            "        \"axis=x\":    { \"model\": \"" + MODID + ":block/" + s1 + toString() + "_" + s2 + "\", \"x\": 90, \"y\": 90 }\n" +
            "    }\n" +
            "}\n";
  }

  public String getSlabState() {
    return "{\n" +
            "    \"variants\": {\n" +
            "        \"type=bottom\": { \"model\": \"" + MODID + ":block/" + toString() + "_slab\" },\n" +
            "        \"type=top\": { \"model\": \"" + MODID + ":block/" + toString() + "_slab_top\" },\n" +
            "        \"type=double\": { \"model\": \"" + MODID + ":block/" + toString() + "_planks\" }\n" +
            "    }\n" +
            "}\n";
  }

  public String getStairsState() {
    return "{\n" +
            "    \"variants\": {\n" +
            "        \"facing=east,half=bottom,shape=straight\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\" },\n" +
            "        \"facing=west,half=bottom,shape=straight\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=bottom,shape=straight\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=bottom,shape=straight\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=east,half=bottom,shape=outer_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\" },\n" +
            "        \"facing=west,half=bottom,shape=outer_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=bottom,shape=outer_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=bottom,shape=outer_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=east,half=bottom,shape=outer_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=west,half=bottom,shape=outer_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=south,half=bottom,shape=outer_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\" },\n" +
            "        \"facing=north,half=bottom,shape=outer_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=east,half=bottom,shape=inner_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\" },\n" +
            "        \"facing=west,half=bottom,shape=inner_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=bottom,shape=inner_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=bottom,shape=inner_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=east,half=bottom,shape=inner_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=west,half=bottom,shape=inner_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=south,half=bottom,shape=inner_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\" },\n" +
            "        \"facing=north,half=bottom,shape=inner_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=east,half=top,shape=straight\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"x\": 180, \"uvlock\": true },\n" +
            "        \"facing=west,half=top,shape=straight\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=top,shape=straight\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=top,shape=straight\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=east,half=top,shape=outer_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=west,half=top,shape=outer_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=south,half=top,shape=outer_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=north,half=top,shape=outer_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"uvlock\": true },\n" +
            "        \"facing=east,half=top,shape=outer_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"uvlock\": true },\n" +
            "        \"facing=west,half=top,shape=outer_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=top,shape=outer_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=top,shape=outer_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=east,half=top,shape=inner_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=west,half=top,shape=inner_right\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
            "        \"facing=south,half=top,shape=inner_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=north,half=top,shape=inner_right\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"uvlock\": true },\n" +
            "        \"facing=east,half=top,shape=inner_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"uvlock\": true },\n" +
            "        \"facing=west,half=top,shape=inner_left\":  { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
            "        \"facing=south,half=top,shape=inner_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
            "        \"facing=north,half=top,shape=inner_left\": { \"model\": \"" + MODID + ":block/" + toString() + "_stairs_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true }\n" +
            "    }\n" +
            "}\n";
  }

  public String getFenceState() {
    return "{\n" +
            "    \"multipart\": [\n" +
            "        {   \"apply\": { \"model\": \"" + MODID + ":block/" + toString() + "_fence_post\" }},\n" +
            "        {   \"when\": { \"north\": \"true\" },\n" +
            "            \"apply\": { \"model\": \"" + MODID + ":block/" + toString() + "_fence_side\", \"uvlock\": true }\n" +
            "        },\n" +
            "        {   \"when\": { \"east\": \"true\" },\n" +
            "            \"apply\": { \"model\": \"" + MODID + ":block/" + toString() + "_fence_side\", \"y\": 90, \"uvlock\": true }\n" +
            "        },\n" +
            "        {   \"when\": { \"south\": \"true\" },\n" +
            "            \"apply\": { \"model\": \"" + MODID + ":block/" + toString() + "_fence_side\", \"y\": 180, \"uvlock\": true }\n" +
            "        },\n" +
            "        {   \"when\": { \"west\": \"true\" },\n" +
            "            \"apply\": { \"model\": \"" + MODID + ":block/" + toString() + "_fence_side\", \"y\": 270, \"uvlock\": true }\n" +
            "        }\n" +
            "    ]\n" +
            "}";
  }

  public String getPlanksModel() {
    return "{\n" +
            "    \"parent\": \"block/cube_all\",\n" +
            "    \"textures\": {\n" +
            "        \"all\": \"" + MODID + ":block/" + toString() + "_planks\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getLeavesModel() {
    return "{\n" +
            "    \"parent\": \"block/cube_all\",\n" +
            "    \"textures\": {\n" +
            "        \"all\": \"" + MODID + ":block/" + toString() + "_leaves\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getFenceModel(FenceModelType type) {
    return "{\n" +
            "    \"parent\": \"block/fence_" + type.toString() + "\",\n" +
            "    \"textures\": {\n" +
            "        \"texture\": \"" + MODID + ":block/" + toString() + "_planks\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getLogModel(boolean stripped, boolean bark) {
    if (bark) {
      if (stripped) {
        return "{\n" +
                "    \"parent\": \"block/cube_column\",\n" +
                "    \"textures\": {\n" +
                "        \"end\": \"" + MODID + ":block/stripped_" + toString() + "_log\",\n" +
                "        \"side\": \"" + MODID + ":block/stripped_" + toString() + "_log\"\n" +
                "    }\n" +
                "}\n";
      }
      return "{\n" +
              "    \"parent\": \"block/cube_column\",\n" +
              "    \"textures\": {\n" +
              "        \"end\": \"" + MODID + ":block/" + toString() + "_log\",\n" +
              "        \"side\": \"" + MODID + ":block/" + toString() + "_log\"\n" +
              "    }\n" +
              "}\n";

    } else {
      if (stripped) {
        return "{\n" +
                "    \"parent\": \"block/cube_column\",\n" +
                "    \"textures\": {\n" +
                "        \"end\": \"" + MODID + ":block/stripped_" + toString() + "_log_top\",\n" +
                "        \"side\": \"" + MODID + ":block/stripped_" + toString() + "_log\"\n" +
                "    }\n" +
                "}\n";
      }
      return "{\n" +
              "    \"parent\": \"block/cube_column\",\n" +
              "    \"textures\": {\n" +
              "        \"end\": \"" + MODID + ":block/" + toString() + "_log_top\",\n" +
              "        \"side\": \"" + MODID + ":block/" + toString() + "_log\"\n" +
              "    }\n" +
              "}\n";
    }
  }

  public String getSaplingModel() {
    return "{\n" +
            "    \"parent\": \"block/cross\",\n" +
            "    \"textures\": {\n" +
            "        \"cross\": \"" + MODID + ":block/" + toString() + "_sapling\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getSlabModel(boolean top) {
    return top ? "{\n" +
            "    \"parent\": \"block/slab_top\",\n" +
            "    \"textures\": {\n" +
            "        \"bottom\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"top\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"side\": \"" + MODID + ":block/" + toString() + "_planks\"\n" +
            "    }\n" +
            "}\n" : "{\n" +
            "    \"parent\": \"block/slab\",\n" +
            "    \"textures\": {\n" +
            "        \"bottom\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"top\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"side\": \"" + MODID + ":block/" + toString() + "_planks\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getStairModel(StairsModelType type) {
    return "{\n" +
            "    \"parent\": \"block/" + type.toString() + "\",\n" +
            "    \"textures\": {\n" +
            "        \"bottom\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"top\": \"" + MODID + ":block/" + toString() + "_planks\",\n" +
            "        \"side\": \"" + MODID + ":block/" + toString() + "_planks\"\n" +
            "    }\n" +
            "}\n";
  }

  public String getPottedModel() {
    return "{\n" +
            "    \"parent\": \"block/flower_pot_cross\",\n" +
            "    \"textures\": {\n" +
            "        \"plant\":\"" + MODID + ":block/" + toString() + "_sapling\"\n" +
            "    }\n" +
            "}";
  }

  public String getBasicItemmodel(BasicItemModelType type) {
    switch (type) {

      case stripped_log:
        return "{\n" +
                "\"parent\": \"" + MODID + ":block/stripped_" + toString() + "_log\"\n" +
                "}\n";
      case stripped_wood:
        return "{\n" +
                "\"parent\": \"" + MODID + ":block/stripped_" + toString() + "_wood\"\n" +
                "}\n";
      default:
        return "{\n" +
                "\"parent\": \"" + MODID + ":block/" + toString() + "_" + type.toString() + "\"\n" +
                "}\n";
    }
  }

  public String getSaplingItemmodel() {
    return "{\n" +
            "\"parent\": \"item/generated\",\n" +
            "\"textures\": {" +
            "\"layer0\": \"" + MODID + ":block/" + toString() + "_sapling\"\n" +
            "}\n" +
            "}\n";
  }

  public String getFenceItemmodel() {
    return "{\n" +
            "\"parent\":\"" + MODID + ":block/" + toString() + "_fence_inventory\"\n" +
            "}\n";
  }

  public static String getLootTable(Block b) {
    String name = b.getRegistryName().toString();

    return "{\n" +
            "  \"type\": \"minecraft:block\",\n" +
            "  \"pools\": [\n" +
            "    {\n" +
            "      \"name\": \"" + MODID + "\",\n" +
            "      \"rolls\": 1,\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:item\",\n" +
            "          \"name\": \"" + name + "\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"conditions\": [\n" +
            "        {\n" +
            "          \"condition\": \"minecraft:survives_explosion\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
  }

  public String getLootTableLeaves() {
    String appendIngot = isIngot ? "_ingot":"";
    String line = isModded ? "          \"name\": \"" + MODID + ":" + trueName() +appendIngot+ "\"\n" : "          \"name\": \"minecraft:" + trueName() +appendIngot+ "\"\n";
    return "{\n" +
            "  \"type\": \"minecraft:block\",\n" +
            "  \"pools\": [\n" +
            "    {\n" +
            "      \"rolls\": 1,\n" +
            "      \"name\": \"ferroustry\",\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:alternatives\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"type\": \"minecraft:item\",\n" +
            "              \"conditions\": [\n" +
            "                {\n" +
            "                  \"condition\": \"minecraft:alternative\",\n" +
            "                  \"terms\": [\n" +
            "                    {\n" +
            "                      \"condition\": \"minecraft:match_tool\",\n" +
            "                      \"predicate\": {\n" +
            "                        \"item\": \"minecraft:shears\"\n" +
            "                      }\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"condition\": \"minecraft:match_tool\",\n" +
            "                      \"predicate\": {\n" +
            "                        \"enchantments\": [\n" +
            "                          {\n" +
            "                            \"enchantment\": \"minecraft:silk_touch\",\n" +
            "                            \"levels\": {\n" +
            "                              \"min\": 1\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              ],\n" +
            "              \"name\": \"ferroustry:" + toString() + "_leaves\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"type\": \"minecraft:item\",\n" +
            "              \"conditions\": [\n" +
            "                {\n" +
            "                  \"condition\": \"minecraft:survives_explosion\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"condition\": \"minecraft:table_bonus\",\n" +
            "                  \"enchantment\": \"minecraft:fortune\",\n" +
            "                  \"chances\": [\n" +
            "                    0.05,\n" +
            "                    0.0625,\n" +
            "                    0.083333336,\n" +
            "                    0.1\n" +
            "                  ]\n" +
            "                }\n" +
            "              ],\n" +
            "              \"name\": \"ferroustry:" + toString() + "_sapling\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"rolls\": 1,\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:item\",\n" +
            "          \"conditions\": [\n" +
            "            {\n" +
            "              \"condition\": \"minecraft:table_bonus\",\n" +
            "              \"enchantment\": \"minecraft:fortune\",\n" +
            "              \"chances\": [\n" +
            "                0.002,\n" +
            "                0.0022222223,\n" +
            "                0.0025,\n" +
            "                0.0033333335,\n" +
            "                0.01\n" +
            "              ]\n" +
            "            }\n" +
            "          ],\n" +
            "          \"functions\": [\n" +
            "            {\n" +
            "              \"function\": \"minecraft:set_count\",\n" +
            "              \"count\": {\n" +
            "                \"min\": 1.0,\n" +
            "                \"max\": 2.0,\n" +
            "                \"type\": \"minecraft:uniform\"\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"function\": \"minecraft:explosion_decay\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"name\": \"minecraft:stick\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"conditions\": [\n" +
            "        {\n" +
            "          \"condition\": \"minecraft:inverted\",\n" +
            "          \"term\": {\n" +
            "            \"condition\": \"minecraft:alternative\",\n" +
            "            \"terms\": [\n" +
            "              {\n" +
            "                \"condition\": \"minecraft:match_tool\",\n" +
            "                \"predicate\": {\n" +
            "                  \"item\": \"minecraft:shears\"\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"condition\": \"minecraft:match_tool\",\n" +
            "                \"predicate\": {\n" +
            "                  \"enchantments\": [\n" +
            "                    {\n" +
            "                      \"enchantment\": \"minecraft:silk_touch\",\n" +
            "                      \"levels\": {\n" +
            "                        \"min\": 1\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"rolls\": 1,\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:item\",\n" +
            "          \"conditions\": [\n" +
            "            {\n" +
            "              \"condition\": \"minecraft:survives_explosion\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"condition\": \"minecraft:table_bonus\",\n" +
            "              \"enchantment\": \"minecraft:fortune\",\n" +
            "              \"chances\": [\n" +
            "                0.05,\n" +
            "                0.055555557,\n" +
            "                0.0625,\n" +
            "                0.08333334,\n" +
            "                0.25\n" +
            "              ]\n" +
            "            }\n" +
            "          ],\n" +
            line +
            "        }\n" +
            "      ],\n" +
            "      \"conditions\": [\n" +
            "        {\n" +
            "          \"condition\": \"minecraft:inverted\",\n" +
            "          \"term\": {\n" +
            "            \"condition\": \"minecraft:alternative\",\n" +
            "            \"terms\": [\n" +
            "              {\n" +
            "                \"condition\": \"minecraft:match_tool\",\n" +
            "                \"predicate\": {\n" +
            "                  \"item\": \"minecraft:shears\"\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"condition\": \"minecraft:match_tool\",\n" +
            "                \"predicate\": {\n" +
            "                  \"enchantments\": [\n" +
            "                    {\n" +
            "                      \"enchantment\": \"minecraft:silk_touch\",\n" +
            "                      \"levels\": {\n" +
            "                        \"min\": 1\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
  }

  public String getPottedLootTable(){
    return "{\n" +
            "  \"type\": \"minecraft:block\",\n" +
            "  \"pools\": [\n" +
            "    {\n" +
            "      \"rolls\": 1,\n" +
            "      \"name\": \"ferroustry\",\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:item\",\n" +
            "          \"name\": \"minecraft:flower_pot\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"conditions\": [\n" +
            "        {\n" +
            "          \"condition\": \"minecraft:survives_explosion\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"rolls\": 1,\n" +
            "      \"name\": \"ferroustry\",\n" +
            "      \"entries\": [\n" +
            "        {\n" +
            "          \"type\": \"minecraft:item\",\n" +
            "          \"name\": \"ferroustry:"+toString()+"_sapling\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"conditions\": [\n" +
            "        {\n" +
            "          \"condition\": \"minecraft:survives_explosion\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
  }

  public String getPlanksRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shapeless\",\n" +
            "  \"group\": \"planks\",\n" +
            "  \"ingredients\": [\n" +
            "    {\n" +
            "      \"tag\": \"ferroustry:" + toString() + "_logs\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_planks\",\n" +
            "    \"count\": 4\n" +
            "  }\n" +
            "}";
  }

  public String getFenceRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"group\": \"wooden_fence\",\n" +
            "  \"pattern\": [\n" +
            "    \"W#W\",\n" +
            "    \"W#W\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"tag\": \"forge:rods/wooden\"\n" +
            "    },\n" +
            "    \"W\": {\n" +
            "      \"item\": \"ferroustry:" + this + "_planks\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_fence\",\n" +
            "    \"count\": 3\n" +
            "  }\n" +
            "}";
  }

  public String getSlabRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"group\": \"wooden_slab\",\n" +
            "  \"pattern\": [\n" +
            "    \"###\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"item\": \"ferroustry:" + this + "_planks\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_slab\",\n" +
            "    \"count\": 6\n" +
            "  }\n" +
            "}";
  }

  public String getStairsRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"group\": \"wooden_stairs\",\n" +
            "  \"pattern\": [\n" +
            "    \"#  \",\n" +
            "    \"## \",\n" +
            "    \"###\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"item\": \"ferroustry:" + this + "_planks\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_stairs\",\n" +
            "    \"count\": 4\n" +
            "  }\n" +
            "}";
  }

  public String getWoodRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"group\": \"bark\",\n" +
            "  \"pattern\": [\n" +
            "    \"##\",\n" +
            "    \"##\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"item\": \"ferroustry:" + this + "_log\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_wood\",\n" +
            "    \"count\": 3\n" +
            "  }\n" +
            "}";
  }

  public String getSaplingRecipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"pattern\": [\n" +
            "    \"###\",\n" +
            "    \"#s#\",\n" +
            "    \"###\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"tag\": \"forge:ores/" + this + "\"\n" +
            "    },\n" +
            "    \"s\": {\n" +
            "      \"tag\": \"minecraft:saplings\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_sapling\"\n" +
            "  }\n" +
            "}";
  }

  public String getRecyclingRecipe() {
    String domain = isModded ? MODID : "minecraft";
    String s1 = isIngot ? "_ingot" : "";
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"group\": \"recycling\",\n" +
            "  \"pattern\": [\n" +
            "    \"###\",\n" +
            "    \"###\",\n" +
            "    \"###\"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"item\": \"ferroustry:" + this + "_sapling\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \""+domain+":" + trueName() + s1+"\"\n" +
            "  }\n" +
            "}";
  }

  public String getSapling2Recipe() {
    return "{\n" +
            "  \"type\": \"minecraft:crafting_shaped\",\n" +
            "  \"pattern\": [\n" +
            "    \" # \",\n" +
            "    \"#s#\",\n" +
            "    \" # \"\n" +
            "  ],\n" +
            "  \"key\": {\n" +
            "    \"#\": {\n" +
            "      \"tag\": \"forge:storage_blocks/" + this + "\"\n" +
            "    },\n" +
            "    \"s\": {\n" +
            "      \"tag\": \"minecraft:saplings\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"result\": {\n" +
            "    \"item\": \"ferroustry:" + this + "_sapling\"\n" +
            "  }\n" +
            "}";
  }

  public String getBlastingRecipe() {
    String domain = isModded ? MODID : "minecraft";
    String s1 = isIngot ? "_ingot" : "";
    return "{\n" +
            "  \"type\": \"minecraft:blasting\",\n" +
            "  \"ingredient\": {\n" +
            "    \"tag\": \"ferroustry:" + this + "_logs\"\n" +
            "  },\n" +
            "  \"result\": \"" + domain + ":" + trueName() + s1 + "\",\n" +
            "  \"experience\": 1,\n" +
            "  \"cookingtime\": 100\n" +
            "}";
  }

  public String getLogTags() {
    return "{\n" +
            "  \"replace\": false,\n" +
            "  \"values\": [\n" +
            "    \"ferroustry:" + this + "_log\",\n" +
            "    \"ferroustry:" + this + "_wood\",\n" +
            "    \"ferroustry:stripped_" + this + "_log\",\n" +
            "    \"ferroustry:stripped_" + this + "_wood\"\n" +
            "  ]\n" +
            "}";
  }

  public enum BasicItemModelType {
    log, stripped_log, wood, stripped_wood, planks, stairs, slab, leaves
  }

  public enum FenceModelType {
    post, side, inventory
  }

  public enum StairsModelType {
    stairs, outer_stairs, inner_stairs
  }

  public enum BasicStateType {
    planks, sapling, leaves
  }
}
