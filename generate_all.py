#!/usr/bin/env python3
import os
import json
import shutil
import subprocess
from jinja2 import Environment, FileSystemLoader
from PIL import Image

# 1. Configuration
MODID      = 'cpsc298minecraft'
BASE_PATH  = '/Users/spencerau/Documents/GitHub/cpsc298-minecraft'
ASSETS_DIR = os.path.join(BASE_PATH, 'src', 'main', 'resources', 'assets', MODID)
JAVA_SRC   = os.path.join(BASE_PATH, 'src', 'main', 'java', 'io', 'github', 'spencerau', 'cpsc298')
TEX_SRC    = os.path.join(BASE_PATH, 'textures')
TEMPLATES  = os.path.join(BASE_PATH, 'templates')

# 2. Define your content
blocks = [
    {'name': 'example_block', 'texture': 'example_block', 'material': 'STONE'},
    # add more blocks here...
]

recipes = [
    {'name': 'example_block_from_stone',
     'type': 'crafting_shaped',
     'pattern': ['SSS','S S','SSS'],
     'key': {'S': {'item': 'minecraft:stone'}},
     'result': {'item': f'{MODID}:example_block', 'count': 1}},
    # add more recipes...
]

# 3. Setup Jinja2
env = Environment(loader=FileSystemLoader(TEMPLATES))
bs_tmpl      = env.get_template('blockstate.json.j2')
bm_java_tmpl = env.get_template('block_model.java.j2')
recipe_tmpl  = env.get_template('recipe.json.j2')
provider_tmpl= env.get_template('ModBlockStates.java.j2')

# 4. Ensure output dirs exist
dirs = [
    os.path.join(ASSETS_DIR, 'blockstates'),
    os.path.join(ASSETS_DIR, 'models', 'block'),
    os.path.join(ASSETS_DIR, 'models', 'item'),
    os.path.join(ASSETS_DIR, 'textures', 'block'),
    os.path.join(BASE_PATH, 'build', 'generated', 'resources', 'data', MODID, 'recipes')
]
for d in dirs:
    os.makedirs(d, exist_ok=True)

# 5. Generate assets
for blk in blocks:
    name, tex, mat = blk['name'], blk['texture'], blk['material']

    # 5a. Copy & validate texture
    src_png = os.path.join(TEX_SRC, f'{tex}.png')
    img = Image.open(src_png)
    if img.width != img.height:
        raise RuntimeError(f'Texture {tex}.png is not square: {img.size}')
    dst_png_dir = os.path.join(ASSETS_DIR, 'textures', 'block')
    shutil.copy(src_png, os.path.join(dst_png_dir, f'{tex}.png'))

    # 5b. JSON blockstate
    bs = bs_tmpl.render(modid=MODID, name=name)
    with open(os.path.join(ASSETS_DIR, 'blockstates', f'{name}.json'), 'w') as f:
        json.dump(bs, f, indent=4)

    # 5c. Java block model provider snippet
    bm_java = bm_java_tmpl.render(modid=MODID, name=name)
    with open(os.path.join(JAVA_SRC, f'{name.title()}ModelProvider.java'), 'w') as f:
        f.write(bm_java)

# 6. Generate recipes in data pack
for rec in recipes:
    js = recipe_tmpl.render(modid=MODID, rec=rec)
    with open(os.path.join(
        BASE_PATH, 'build', 'generated', 'resources', 'data', MODID, 'recipes', f'{rec["name"]}.json'
    ), 'w') as f:
        json.dump(js, f, indent=4)

# 7. Generate the Java data-gen class
registrations = "\n    ".join(
    f'models().withExistingParent("{b["name"]}", modLoc("block/{b["texture"]}"))'
    for b in blocks
)
provider_java = provider_tmpl.render(modid=MODID, registrations=registrations)
with open(os.path.join(JAVA_SRC, 'ModBlockStates.java'), 'w') as f:
    f.write(provider_java)

# 8. Run Gradle data gen, then build
subprocess.run(['./gradlew', 'runData'], cwd=BASE_PATH, check=True)
subprocess.run(['./gradlew', 'build'],   cwd=BASE_PATH, check=True)

print("Assets generated, datagen run, and mod built successfully.")