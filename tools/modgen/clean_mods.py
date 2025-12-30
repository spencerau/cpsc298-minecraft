import sys
import shutil
from pathlib import Path

def clean_generated_content(project_root: Path):
    print("Cleaning generated mod content...")
    print("=" * 70)
    
    base_package_generated_java = project_root / "src/generated/java/io/github/spencerau/cpsc298"
    base_package_main_java = project_root / "src/main/java/io/github/spencerau/cpsc298"
    resources_path = project_root / "src/main/resources"
    generated_resources_path = project_root / "src/generated/resources"
    
    paths_to_remove = [
        base_package_main_java / "generated",
        base_package_generated_java,
        base_package_generated_java / "custom",
        resources_path / "assets/cpsc298minecraft/models",
        resources_path / "assets/cpsc298minecraft/blockstates",
        resources_path / "assets/cpsc298minecraft/textures",
        resources_path / "assets/cpsc298minecraft/items",
        resources_path / "assets/cpsc298minecraft/sounds.json",
        resources_path / "assets/cpsc298minecraft/lang",
        resources_path / "data/cpsc298minecraft/recipe",
        resources_path / "data/cpsc298minecraft/loot_table",
        generated_resources_path,
    ]
    
    removed_count = 0
    for path in paths_to_remove:
        if path.exists():
            if path.is_dir():
                shutil.rmtree(path)
                print(f"[DELETE] {path.relative_to(project_root)}/")
            else:
                path.unlink()
                print(f"[DELETE] {path.relative_to(project_root)}")
            removed_count += 1
    
    print("=" * 70)
    print(f"Removed {removed_count} paths")
    print("Clean complete!")

if __name__ == '__main__':
    project_root = Path(__file__).parent.parent.parent
    clean_generated_content(project_root)
