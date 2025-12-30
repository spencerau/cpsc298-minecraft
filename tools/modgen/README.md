# NeoForge Mod Content Generator

Generate all mod content from a single YAML file - no manual Java or JSON editing required.

## Quick Start

### 1. Install Dependencies
```bash
pip install -r tools/modgen/requirements.txt
```

### 2. Add Textures & Sounds
Place your files in:
- `assets/textures/*.png` - Item and block textures
- `assets/sounds/*.ogg` - Sound files

### 3. Edit Content Spec
Edit `tools/modgen/content.yaml`:
```yaml
items:
  - id: ruby
    display_name: Ruby
    texture: ruby
    stack_size: 64

blocks:
  - id: ruby_block
    display_name: Block of Ruby
    textures:
      all: ruby_block
    hardness: 5.0
```

### 4. Generate Everything
```bash
./gradlew genContent
```

### 5. One-Time Java Integration
Add this ONE line to your mod constructor in `src/main/java/io/github/spencerau/cpsc298/CPSC298Minecraft.java`:

```java
@Mod(CPSC298Minecraft.MODID)
public class CPSC298Minecraft {
    public static final String MODID = "cpsc298minecraft";

    public CPSC298Minecraft(IEventBus modEventBus, ModContainer modContainer) {
        // ADD THIS:
        io.github.spencerau.cpsc298.generated.GeneratedRegistries.init();
        
        // ... rest of existing code ...
    }
}
```

### 6. Build & Test
```bash
./gradlew build
./gradlew runClient
```

## What Gets Generated

Running `./gradlew genContent` creates:
- Java classes in `src/main/java/.../generated/` (don't edit these)
- Custom stubs in `src/main/java/.../custom/` (edit these for behavior)
- JSON assets (models, blockstates, lang)
- Data files (recipes, loot tables)
- Copies textures/sounds to proper locations

## Content Spec Reference

### Items
```yaml
items:
  - id: ruby
    display_name: Ruby
    texture: ruby
    stack_size: 64
    rarity: rare                      # common|uncommon|rare|epic
    food:                             # Makes item edible
      nutrition: 8
      saturation: 1.2
    custom_class: true                # Generates custom stub
```

### Blocks
```yaml
blocks:
  - id: trampoline_block
    display_name: Trampoline
    textures:
      all: trampoline_block           # Or specify per-face: north/south/east/west/up/down
    hardness: 1.0
    resistance: 2.0
    sound_type: wool                  # stone|wood|gravel|grass|metal|glass|wool|sand
    light_level: 10                   # 0-15
    drops: self                       # self|nothing|<item_id>
    behavior:                         # Custom behavior (generates stub)
      bounce_strength: 1.5
      fall_damage_multiplier: 0.0
```

### Recipes
```yaml
recipes:
  # Shaped
  - id: ruby_block_from_rubies
    type: shaped
    pattern: ["RRR", "RRR", "RRR"]
    key:
      R: {item: cpsc298minecraft:ruby}
    result: ruby_block
    
  # Shapeless  
  - id: rubies_from_ruby_block
    type: shapeless
    ingredients: [{item: cpsc298minecraft:ruby_block}]
    result: ruby
    count: 9
    
  # Smelting
  - id: magic_apple_smelting
    type: smelting
    ingredient: {item: minecraft:apple}
    result: magic_apple
    experience: 0.5
    cooking_time: 200
```

See `content.yaml` for complete examples including creative tabs and sounds.

## Troubleshooting

**Error: "cannot find symbol GeneratedRegistries"**  
Run the generator first: `./gradlew genContent`

**Items/blocks not appearing in game**  
1. Check the init line is added to your mod constructor
2. Rebuild: `./gradlew build`

**Textures not appearing**  
1. Verify PNG files are in `assets/textures/`
2. Check texture filenames in YAML match actual files

**Changes not taking effect**  
1. Run `./gradlew genContent` after editing `content.yaml`
2. Rebuild: `./gradlew build`
