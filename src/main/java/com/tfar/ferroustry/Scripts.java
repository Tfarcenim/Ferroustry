package com.tfar.ferroustry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.*;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tfar.ferroustry.Ferroustry.MODID;

public class Scripts {


  public static Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

  private static JsonArray pattern = new JsonArray();

  public static void scripts() {

    //if (true) return;

    for (Block block : Ferroustry.RegistryEvents.MOD_BLOCKS) {

      //handle blockstates
      blockstates(block);
      blockmodel(block);
      item(block);
      loottable(block);
    }
    lang();
  }

  private static void lang() {
    JsonObject lang = new JsonObject();
    for (Block block : Ferroustry.RegistryEvents.MOD_BLOCKS){
      String[] translations = block.getTranslationKey().split("\\.");

      List<String> a1 = new ArrayList<>(Arrays.asList(translations));
      a1.remove(0);
      a1.remove(0);

      translations = a1.toArray(new String[0]);
      String translation = Arrays.toString(translations);
      translations = translation.split("_");

      for (int i = 0 ; i < translations.length ; i++){
        translations[i] = translations[i].substring(0,1).toUpperCase() + translations[i].substring(1);
      }

      translation = Arrays.toString(translations);
      translation = translation.replace(",","");
      translation = translation.replace("[","");
      translation = translation.replace("]","");
      translation = translation.substring(0,1).toUpperCase() + translation.substring(1);

      lang.addProperty(block.getTranslationKey(),translation);

    }
    File file = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\lang\\en_us.json");

    try {

      FileWriter writer1 = new FileWriter(file);
      writer1.write(g.toJson(lang));
      writer1.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void loottable(Block block){
    if (block instanceof LeavesBlock)return;
  BufferedInputStream in = new BufferedInputStream(Scripts.class.getResourceAsStream("/coal_log.json"));
  //hardcoded into the mod, used for oredict, jsons and modids
    String s = null;
    try {
      s = IOUtils.toString(in, Charset.defaultCharset());
      //System.out.println(s);
    } catch (IOException e){
      e.printStackTrace();
    }

    String s1 = s.replace("coal_log",block.getRegistryName().getPath());
  File file = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\data\\ferroustry\\loot_tables\\blocks\\" + block.getRegistryName().getPath() + ".json");

  try {

    FileWriter writer1 = new FileWriter(file);
    writer1.write(s1);
    writer1.flush();
  } catch (IOException e) {
    e.printStackTrace();
  }
}


  private static void item(Block block) {
    JsonObject parent = new JsonObject();
    if (!(block instanceof FenceBlock) && !(block instanceof SaplingBlock))
    parent.addProperty("parent",MODID + ":block/"+block.getRegistryName().getPath());

    else if (block instanceof FenceBlock)
      parent.addProperty("parent",MODID + ":block/"+block.getRegistryName().getPath()+"_inventory");

    else {
      parent.addProperty("parent","item/generated");
      JsonObject layer0 = new JsonObject();
      layer0.addProperty("layer0",MODID + ":block/" + block.getRegistryName().getPath());
      parent.add("textures",layer0);
    }

    File file = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\item\\" + block.getRegistryName().getPath() + ".json");
    //if (file.exists()) return;
    try {

      FileWriter writer1 = new FileWriter(file);
      writer1.write(g.toJson(parent));
      writer1.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void blockmodel(Block block) {

    String s = "";
    String s1 = block.getRegistryName().getPath();
    String s3 = s1.split("_")[0];
    String s5 = block.getRegistryName().getPath().split("_")[1];

    if (block.getRegistryName().getPath().endsWith("planks")) {
      s = "{\n" +
              "    \"parent\": \"block/cube_all\",\n" +
              "    \"textures\": {\n" +
              "        \"all\": \""+ MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";
    } else if (block instanceof LeavesBlock){
      s = "{\n" +
              "    \"parent\": \"block/leaves\",\n" +
              "    \"textures\": {\n" +
              "        \"all\": \""+ MODID +":block/"+s1+"\"\n" +
              "    }\n" +
              "}\n";
    } else if (block instanceof FenceBlock){
      s = "{\n" +
              "    \"parent\": \"block/fence_post\",\n" +
              "    \"textures\": {\n" +
              "        \"texture\": \""+ MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      String s2 = "{\n" +
              "    \"parent\": \"block/fence_side\",\n" +
              "    \"textures\": {\n" +
              "        \"texture\": \""+ MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      String s4 = "{\n" +
              "    \"parent\": \"block/fence_inventory\",\n" +
              "    \"textures\": {\n" +
              "        \"texture\": \""+ MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      File file1 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_post.json");

      File file2 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_side.json");

      File file3 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_inventory.json");


      try {
        FileWriter writer1 = new FileWriter(file1);
        FileWriter writer2 = new FileWriter(file2);
        FileWriter writer3 = new FileWriter(file3);
        writer1.write(s);
        writer1.flush();
        writer2.write(s2);
        writer2.flush();
        writer3.write(s4);
        writer3.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    } else if (block instanceof LogBlock){
      s = "{\n" +
              "    \"parent\": \"block/cube_column\",\n" +
              "    \"textures\": {\n" +
              "        \"end\": \""+ MODID +":block/"+s1+"_top\",\n" +
              "        \"side\": \""+ MODID +":block/"+s1+"\"\n" +
              "    }\n" +
              "}\n";

    } else if (block instanceof RotatedPillarBlock){
      if (s1.contains("stripped"))
        s = "{\n" +
                "    \"parent\": \"block/cube_column\",\n" +
                "    \"textures\": {\n" +
                "        \"end\": \""+ MODID +":block/stripped_"+s5+"_log\",\n" +
                "        \"side\": \""+ MODID +":block/stripped_"+s5+"_log\"\n" +
                "    }\n" +
                "}\n";
        else
      s = "{\n" +
              "    \"parent\": \"block/cube_column\",\n" +
              "    \"textures\": {\n" +
              "        \"end\": \""+ MODID +":block/"+s3+"_log\",\n" +
              "        \"side\": \""+ MODID +":block/"+s3+"_log\"\n" +
              "    }\n" +
              "}\n";
    } else if (block instanceof SaplingBlock){
      s = "{\n" +
              "    \"parent\": \"block/cross\",\n" +
              "    \"textures\": {\n" +
              "        \"cross\": \""+MODID+":block/"+s1+"\"\n" +
              "    }\n" +
              "}\n";

    } else if (block instanceof SlabBlock){
       s  = "{\n" +
               "    \"parent\": \"block/slab\",\n" +
               "    \"textures\": {\n" +
               "        \"bottom\": \""+MODID+":block/"+s3+"_planks\",\n" +
               "        \"top\": \""+MODID+":block/"+s3+"_planks\",\n" +
               "        \"side\": \""+MODID+":block/"+s3+"_planks\"\n" +
               "    }\n" +
               "}\n";

       String s2 = "{\n" +
              "    \"parent\": \"block/slab_top\",\n" +
              "    \"textures\": {\n" +
              "        \"bottom\": \""+MODID+":block/"+s3+"_planks\",\n" +
              "        \"top\": \""+MODID+":block/"+s3+"_planks\",\n" +
              "        \"side\": \""+MODID+":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      File file1 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + ".json");

      File file2 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_top.json");

      try {
        FileWriter writer1 = new FileWriter(file1);
        FileWriter writer2 = new FileWriter(file2);
        writer1.write(s);
        writer1.flush();
        writer2.write(s2);
        writer2.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    } else if (block instanceof StairsBlock){
      s = "{\n" +
              "    \"parent\": \"block/stairs\",\n" +
              "    \"textures\": {\n" +
              "        \"bottom\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"top\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"side\": \"" + MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      String outer = "{\n" +
              "    \"parent\": \"block/outer_stairs\",\n" +
              "    \"textures\": {\n" +
              "        \"bottom\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"top\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"side\": \"" + MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";

      String inner = "{\n" +
              "    \"parent\": \"block/inner_stairs\",\n" +
              "    \"textures\": {\n" +
              "        \"bottom\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"top\": \"" + MODID +":block/"+s3+"_planks\",\n" +
              "        \"side\": \"" + MODID +":block/"+s3+"_planks\"\n" +
              "    }\n" +
              "}\n";
      File file1 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_outer.json");
      File file2 = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + "_inner.json");

      try {
        FileWriter writer1 = new FileWriter(file1);
        FileWriter writer2 = new FileWriter(file2);
        writer1.write(outer);
        writer1.flush();
        writer2.write(inner);
        writer2.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

      File file = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\models\\block\\" + s1 + ".json");
    try {
      FileWriter writer = new FileWriter(file);
      writer.write(s);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void blockstates(Block block) {

    File blockstate = new File("C:\\Users\\xluser\\Documents\\MinecraftMods\\mods\\ResourceTrees\\src\\main\\resources\\assets\\ferroustry\\blockstates\\" + block.getRegistryName().getPath() + ".json");
    //if (blockstate.exists()) return;
    String s = "";
    if (block instanceof LeavesBlock || block instanceof SaplingBlock || block.getRegistryName().getPath().endsWith("planks")) {
      s = "{\n" +
              "  \"variants\": {\n" +
              "    \"\": {\n" +
              "      \"model\": \"ferroustry:block/"+block.getRegistryName().getPath()+"\"\n" +
              "    }\n" +
              "  }\n" +
              "}";
    } else if (block instanceof RotatedPillarBlock) {
      String s1 = block.getRegistryName().getPath();
      s = "{\n" +
              "    \"variants\": {\n" +
              "        \"axis=y\":    { \"model\": \""+ MODID +":block/"+s1+"\" },\n" +
              "        \"axis=z\":     { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 90 },\n" +
              "        \"axis=x\":     { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 90, \"y\": 90 }\n" +
              "    }\n" +
              "}\n";
    } else if (block instanceof SlabBlock){
      String s1 = block.getRegistryName().getPath();
      String s2 = s1.split("_")[0];
      s = "{\n" +
              "    \"variants\": {\n" +
              "        \"type=bottom\": { \"model\": \""+ MODID +":block/"+s1+"\" },\n" +
              "        \"type=top\": { \"model\": \""+ MODID +":block/"+s1+"_top\" },\n" +
              "        \"type=double\": { \"model\": \""+ MODID +":block/"+s2+"_planks\" }\n" +
              "    }\n" +
              "}\n";
    }

    else if (block instanceof StairsBlock){

      String s1 = block.getRegistryName().getPath();

      s = "{\n" +
              "    \"variants\": {\n" +
              "        \"facing=east,half=bottom,shape=straight\":  { \"model\": \""+ MODID +":block/"+s1+"\" },\n" +
              "        \"facing=west,half=bottom,shape=straight\":  { \"model\": \""+ MODID +":block/"+s1+"\", \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=bottom,shape=straight\": { \"model\": \""+ MODID +":block/"+s1+"\", \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=bottom,shape=straight\": { \"model\": \""+ MODID +":block/"+s1+"\", \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=east,half=bottom,shape=outer_right\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\" },\n" +
              "        \"facing=west,half=bottom,shape=outer_right\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=bottom,shape=outer_right\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=bottom,shape=outer_right\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=east,half=bottom,shape=outer_left\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=west,half=bottom,shape=outer_left\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=south,half=bottom,shape=outer_left\": { \"model\": \""+ MODID +":block/"+s1+"_outer\" },\n" +
              "        \"facing=north,half=bottom,shape=outer_left\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=east,half=bottom,shape=inner_right\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\" },\n" +
              "        \"facing=west,half=bottom,shape=inner_right\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=bottom,shape=inner_right\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=bottom,shape=inner_right\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=east,half=bottom,shape=inner_left\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=west,half=bottom,shape=inner_left\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=south,half=bottom,shape=inner_left\": { \"model\": \""+ MODID +":block/"+s1+"_inner\" },\n" +
              "        \"facing=north,half=bottom,shape=inner_left\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=east,half=top,shape=straight\":  { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 180, \"uvlock\": true },\n" +
              "        \"facing=west,half=top,shape=straight\":  { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=top,shape=straight\": { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=top,shape=straight\": { \"model\": \""+ MODID +":block/"+s1+"\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=east,half=top,shape=outer_right\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=west,half=top,shape=outer_right\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=south,half=top,shape=outer_right\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=north,half=top,shape=outer_right\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"uvlock\": true },\n" +
              "        \"facing=east,half=top,shape=outer_left\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"uvlock\": true },\n" +
              "        \"facing=west,half=top,shape=outer_left\":  { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=top,shape=outer_left\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=top,shape=outer_left\": { \"model\": \""+ MODID +":block/"+s1+"_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=east,half=top,shape=inner_right\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=west,half=top,shape=inner_right\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
              "        \"facing=south,half=top,shape=inner_right\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=north,half=top,shape=inner_right\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"uvlock\": true },\n" +
              "        \"facing=east,half=top,shape=inner_left\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"uvlock\": true },\n" +
              "        \"facing=west,half=top,shape=inner_left\":  { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
              "        \"facing=south,half=top,shape=inner_left\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
              "        \"facing=north,half=top,shape=inner_left\": { \"model\": \""+ MODID +":block/"+s1+"_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true }\n" +
              "    }\n" +
              "}\n";


    } else if (block instanceof FenceBlock) {
      String s1 = block.getRegistryName().getPath();
      s = "{\n" +
              "    \"multipart\": [\n" +
              "        {   \"apply\": { \"model\": \""+ MODID +":block/"+s1+"_post\" }},\n" +
              "        {   \"when\": { \"north\": \"true\" },\n" +
              "            \"apply\": { \"model\": \""+ MODID +":block/"+s1+"_side\", \"uvlock\": true }\n" +
              "        },\n" +
              "        {   \"when\": { \"east\": \"true\" },\n" +
              "            \"apply\": { \"model\": \""+ MODID +":block/"+s1+"_side\", \"y\": 90, \"uvlock\": true }\n" +
              "        },\n" +
              "        {   \"when\": { \"south\": \"true\" },\n" +
              "            \"apply\": { \"model\": \""+ MODID +":block/"+s1+"_side\", \"y\": 180, \"uvlock\": true }\n" +
              "        },\n" +
              "        {   \"when\": { \"west\": \"true\" },\n" +
              "            \"apply\": { \"model\": \""+ MODID +":block/"+s1+"_side\", \"y\": 270, \"uvlock\": true }\n" +
              "        }\n" +
              "    ]\n" +
              "}";
    }

    try {
      FileWriter writer = new FileWriter(blockstate);
      writer.write(s);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Mod.EventBusSubscriber
  public static class tesing {

    @SubscribeEvent
    public static void script(PlayerInteractEvent.RightClickItem e){
      if (e.getItemStack().getItem() == Items.DEBUG_STICK)
        scripts();
    }
  }

}