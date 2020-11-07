package com.tfar.ferroustry.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber
public class Config {

    public static class Common {
        public static ForgeConfigSpec.BooleanValue enable_aluminum;
        public static ForgeConfigSpec.BooleanValue enable_bismuth;
        public static ForgeConfigSpec.BooleanValue enable_copper;
        public static ForgeConfigSpec.BooleanValue enable_lead;
        public static ForgeConfigSpec.BooleanValue enable_platinum;
        public static ForgeConfigSpec.BooleanValue enable_zinc;
        public static ForgeConfigSpec.BooleanValue enable_nickel;
        public static ForgeConfigSpec.BooleanValue enable_uranium;
        public static ForgeConfigSpec.BooleanValue enable_osmium;
        public static ForgeConfigSpec.BooleanValue enable_silver;
        public static ForgeConfigSpec.BooleanValue enable_tin;

        public Common(ForgeConfigSpec.Builder server) {
            server.comment("Enable Ore Trees");
            enable_aluminum = server.comment("Enable aluminum").define("ferroustry.enable_aluminum", true);
            enable_bismuth = server.comment("Enable bismuth").define("ferroustry.enable_bismuth", false);
            enable_copper = server.comment("Enable copper").define("ferroustry.enable_copper", true);
            enable_lead = server.comment("Enable lead").define("ferroustry.enable_lead", true);
            enable_platinum = server.comment("Enable platinum").define("ferroustry.enable_platinum", false);
            enable_zinc = server.comment("Enable zinc").define("ferroustry.enable_zinc", false);
            enable_nickel = server.comment("Enable nickel").define("ferroustry.enable_nickel", true);
            enable_uranium = server.comment("Enable uranium").define("ferroustry.enable_uranium", false);
            enable_osmium = server.comment("Enable osmium").define("ferroustry.enable_osmium", false);
            enable_silver = server.comment("Enable silver").define("ferroustry.enable_silver", true);
            enable_tin = server.comment("Enable tin").define("ferroustry.enable_tin", true);

        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
