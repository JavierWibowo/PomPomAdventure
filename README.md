# PoMPoM AdVeNtUrE

A top-down 2D maze game built in pure Java (Swing + `javax.sound`). Every playthrough generates a brand-new maze, scatters traps and power-ups across it, and gives you 120 seconds to find one of four hidden exits before time runs out.


## Gameplay

You wake up in the center of a procedurally generated maze. Four exits are hidden somewhere along the outer walls — find and reach any one of them before the clock hits zero to win.

Along the way you'll run into:

| Object | Effect |
|---|---|
| **Speed Potion** | Boosts your movement speed |
| **Slow Orb** | Temporarily slows you down |
| **Electric Orb** | Freezes you in place for a few seconds |
| **Teleport Trap** | Warps you several tiles backward in the direction you're facing |

Run out of time and it's game over.


## Controls

| Key | Action |
|---|---|
| `W` | Move up |
| `A` | Move left |
| `S` | Move down |
| `D` | Move right |

## Features

- **Procedural maze generation** — a new layout is carved out (recursive backtracking) every time you launch the game
- **Random exit placement** — four possible exits are placed along the borders each run
- **Power-ups & hazards** — speed potions, slow orbs, electric orbs, and teleport traps are scattered randomly through the maze
- **Sound effects & music** — footsteps, collisions, pickups, and win/lose stingers
- **Animated sprite-based movement** with directional walking frames

## Tech Stack

- **Java** (Swing for rendering/UI, `javax.sound.sampled` for audio)
- No external dependencies or build tool required

## Getting Started

### Prerequisites

- JDK 8 or later installed and on your `PATH`

### Run it

Clone the repo and run the following from the project root (resource paths are loaded relative to the root, so the working directory matters):

```bash
git clone https://github.com/JavierWibowo/PomPomAdventure.git
cd PomPomAdventure

# Compile (keeps the existing package layout in place)
javac -d . main/*.java entity/*.java object/*.java tile/*.java

# Run
java -cp . main.rpgGame
```

A game window should open automatically — start moving with `WASD` to begin.

## Project Structure

```
PomPomAdventure/
├── main/             # Game loop, window setup, controller, UI, collision, audio manager
│   ├── rpgGame.java       # Entry point
│   ├── RpgPanel.java      # Core game loop & rendering
│   ├── Controller.java    # Keyboard input (WASD)
│   ├── CollisionCheck.java
│   ├── AssetSetter.java   # Places objects/exits in the maze
│   ├── UI.java            # HUD, timer, win/lose screens
│   └── Music.java         # Sound effect & music playback
├── entity/           # Base Entity class & Player
├── object/           # Pickups/hazards (potions, traps, end tile, win/lose screens)
├── tile/             # Tile definitions & maze tile map
├── Tiles/            # Tile sprite assets (floor, wall, water, etc.)
├── walkingSprites/   # Player walking animation frames
├── Music/            # Sound effects & background music
├── Screens/           # Win/lose screen images
└── Maps/             # Legacy hand-authored map files (not currently used — maps are generated procedurally)
```

## Notes

- Maze size is fixed at 50×50 tiles, rendered through a 16×12 tile camera viewport.
- The `Maps/` folder contains hand-built map text files from an earlier version of the project; the current build generates the maze at runtime instead of loading them.

## License

No license has been specified for this project yet.
