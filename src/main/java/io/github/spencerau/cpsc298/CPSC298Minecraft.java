package io.github.spencerau.cpsc298;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


// runs on client and server
// @Mod(MODID) declaration
// deferred register setup (items, blocks, tabs, etc)
// setup event listeners (FMLCommonSetupEvent, etc); for example, play sound everytime a block is broken etc.
// load in config file and setup logging

// logic that applies to both client and server
// such as registering items, recipes, handling networking packets, initializing capabilities/config


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CPSC298Minecraft.MODID)
public class CPSC298Minecraft {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cpsc298minecraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "cpsc298minecraft" namespace
    // TASK: Locate where your mod’s block is registered
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "cpsc298minecraft" namespace
    // TASK: Locate where your mod’s item is registered
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "cpsc298minecraft" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // // Creates a new Block with the id "cpsc298minecraft:example_block", combining the namespace and path
    // public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // // Creates a new BlockItem with the id "cpsc298minecraft:example_block", combining the namespace and path
    // public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // lol block
    public static final DeferredBlock<Block> lol_block = BLOCKS.register(
        "lol_block", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .strength(999f, 3000000f) // hardness and explosive resistance
            // .destroyTime(999f)
            // .explosionResistance(3000000f)
            //.sound(SoundType.GRAVEL)
            .lightLevel(state -> 15)
            .friction(0.99f)
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
        ));
    // block item for lol block in inventory
    public static final DeferredItem<BlockItem> lol_block_item = 
        ITEMS.registerSimpleBlockItem("lol_block", lol_block);


    // cheese block
    public static final DeferredBlock<Block> cheese_block = BLOCKS.register(
        "cheese_block", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            //.strength(999f, 3000000f) // hardness and explosive resistance
            // .destroyTime(999f)
            // .explosionResistance(3000000f)
            //.sound(SoundType.GRAVEL)
            .lightLevel(state -> 15)
            .friction(0.99f)
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
        ));
    // block item for cheese block in inventory
    public static final DeferredItem<BlockItem> cheese_block_item = 
        ITEMS.registerSimpleBlockItem("cheese_block", cheese_block);

    // register to creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CUSTOM_ITEMS_TAB = 
        CREATIVE_MODE_TABS.register("custom_items_2", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.cp298"))
        .withTabsBefore(CreativeModeTabs.BUILDING_BLOCKS)
        .icon(() -> lol_block_item.get().getDefaultInstance())      // The icon for the CreativeModeTab
        .displayItems((parameters, output) -> {
            output.accept(lol_block_item.get()); // Add the lol block item to the tab
            output.accept(cheese_block_item.get()); // Add the cheese block item to the tab
        }).build());


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CPSC298Minecraft(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Add a log line that prints when your item is registered
        LOGGER.info("Add a log line that prints when your item is registered");
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (CPSC298Minecraft) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Task: Basic Startup & Logging - Make it personal or funny");

        //LOGGER.info("Minecraft version: {}", SharedConstants.getCurrentVersion().getNameVersionString());
        LOGGER.info("Minecraft version: 1.21.8");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));


    }

    // Add the block items to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(lol_block_item.get());
            event.accept(cheese_block_item.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


}
