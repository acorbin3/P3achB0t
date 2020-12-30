package com.p3achb0t.api.wrappers.utils

object CollisionFlag {
    const val WALK_NORTHWEST = 1
    const val WALK_NORTH = 1 shl 1
    const val WALK_NORTHEAST = 1 shl 2
    const val WALK_EAST = 1 shl 3
    const val WALK_SOUTHEAST = 1 shl 4
    const val WALK_SOUTH = 1 shl 5
    const val WALK_SOUTHWEST = 1 shl 6
    const val WALK_WEST = 1 shl 7
    const val WALK_OBJECT = 1 shl 8
    const val PROJECTILE_NORTHWEST = 1 shl 9
    const val PROJECTILE_NORTH = 1 shl 10
    const val PROJECTILE_NORTHEAST = 1 shl 11
    const val PROJECTILE_EAST = 1 shl 12
    const val PROJECTILE_SOUTHEAST = 1 shl 13
    const val PROJECTILE_SOUTH = 1 shl 14
    const val PROJECTILE_SOUTHWEST = 1 shl 15
    const val PROJECTILE_WEST = 1 shl 16
    const val PROJECTILE_OBJECT = 1 shl 17
    const val WALK_GROUND = 1 shl 21

    val BASE_LENGTH = 104
    val BASE_BOUNDARY = BASE_LENGTH
    val BLOCK_NORTH_WEST = 1
    val BLOCK_NORTH = 2
    val BLOCK_NORTH_EAST = 4
    val BLOCK_EAST = 8
    val BLOCK_SOUTH_EAST = 16
    val BLOCK_SOUTH = 32
    val BLOCK_SOUTH_WEST = 64
    val BLOCK_WEST = 128
    val BLOCKED_OBJECT = 256
    val BLOCKED_FLOOR_DECO = 262144
    val BLOCKED_FLOOR = 2097152 // Eg. water

    val BLOCKED = BLOCKED_OBJECT or BLOCKED_FLOOR_DECO or BLOCKED_FLOOR

    //NORMAL/'STEP' FLAG BLOCKS FROM ACTUALLY ENTERING THE TILE
    //HIGH is an optional flag indicating it blocks projectiles
    //PATH if not set it lets you path through it. Sort of like an invisble wall, used for all traps and a few things like bank booths, border guards, ... lets you path 'through' the booth by clicking inside the bank, instead of running to the wall outside of the bank etc

    //NORMAL/'STEP' FLAG BLOCKS FROM ACTUALLY ENTERING THE TILE
    //HIGH is an optional flag indicating it blocks projectiles
    //PATH if not set it lets you path through it. Sort of like an invisble wall, used for all traps and a few things like bank booths, border guards, ... lets you path 'through' the booth by clicking inside the bank, instead of running to the wall outside of the bank etc
    var NORTH_WEST_PILLAR = 0x1
    var NORTH_WEST_PILLAR_HIGH = 0x200
    var NORTH_WEST_PILLAR_PATH = 0x400000

    /**
     * Tile has a wall on the north side
     */
    var NORTH_WALL = 0x2

    /**
     * Tile has a wall on the north side that is high enough to block projectiles
     */
    var NORTH_WALL_HIGH = 0x400

    /**
     * Tile has a wall on the north side that doesn't allow interactions through it
     */
    var NORTH_WALL_PATH = 0x800000

    var NORTH_EAST_PILLAR = 0x4
    var NORTH_EAST_PILLAR_HIGH = 0x800
    var NORTH_EAST_PILLAR_PATH = 0x1000000

    var EAST_WALL = 8
    var EAST_WALL_HIGH = 4096
    var EAST_WALL_PATH = 33554432

    var SOUTH_EAST_PILLAR = 16
    var SOUTH_EAST_PILLAR_HIGH = 8192
    var SOUTH_EAST_PILLAR_PATH = 67108864

    var SOUTH_WALL = 32
    var SOUTH_WALL_HIGH = 16384
    var SOUTH_WALL_PATH = 134217728

    var SOUTH_WEST_PILLAR = 64
    var SOUTH_WEST_PILLAR_HIGH = 32768
    var SOUTH_WEST_PILLAR_PATH = 268435456

    var WEST_WALL = 128
    var WEST_WALL_HIGH = 65536
    var WEST_WALL_PATH = 536870912

    /**
     * Tile is blocked by an object.
     */
    var OBSTACLE = 256

    /**
     * Tile has an object high enough to block all projectiles.
     */
    var OBSTACLE_HIGH = 131072

    /**
     * Tile is blocking interaction
     */
    var OBSTACLE_PATH = 1073741824

    /**
     * Tile is blocked due to a floor decoration object
     */
    var FLOOR_DECORATION_BLOCKED = 262144

    /**
     * If this flag is set you can interact (in the destination reachability check) with wall objects from the sides
     *
     *
     * Example with this flag
     *
     * <pre>
     * x x
     * x|x
     * x x
    </pre> *
     *
     *
     * Without this flag it would be
     *
     * <pre>
     * x|x
    </pre> *
     *
     *
     * NOTE: There is seems to be no code that actually sets this flag.
     */
    var INTERACT_SIDE_UNUSED = 524288

    /**
     * Tile is blocked from entering by render rule CLIPPED (0x1) (water...)
     */
    var TILE_BLOCKED = 0x200000

    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx100000 = 0x100000;

    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx80000000 = 0x80000000;
    //Computed flags to make pathfinding more readable

    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx100000 = 0x100000;
    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx80000000 = 0x80000000;
    //Computed flags to make pathfinding more readable
    /**
     * Combination of flags to check if a tile can be entered at all
     */
    var BLOCK_ENTER = TILE_BLOCKED or FLOOR_DECORATION_BLOCKED or OBSTACLE

    /**
     * Combination of flags to check there are no extra constraints that would block entering a north western tile
     */
    var BLOCK_ENTER_NORTH_WEST = SOUTH_EAST_PILLAR or SOUTH_WALL or EAST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a northern tile
     */
    var BLOCK_ENTER_NORTH = SOUTH_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a north eastern tile
     */
    var BLOCK_ENTER_NORTH_EAST = SOUTH_WEST_PILLAR or SOUTH_WALL or WEST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a eastern tile
     */
    var BLOCK_ENTER_EAST = WEST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a south eastern tile
     */
    var BLOCK_ENTER_SOUTH_EAST = NORTH_WEST_PILLAR or NORTH_WALL or WEST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a southern tile
     */
    var BLOCK_ENTER_SOUTH = NORTH_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a south western tile
     */
    var BLOCK_ENTER_SOUTH_WEST = NORTH_EAST_PILLAR or NORTH_WALL or EAST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a western tile
     */
    var BLOCK_ENTER_WEST = EAST_WALL or BLOCK_ENTER

    /**
     * Combination of flags to check if a projectile can fly over this tile at all
     */
    var BLOCK_PROJECTILE =  /*TILE_BLOCKED | FLOOR_DECORATION_BLOCKED | */OBSTACLE_HIGH

    /**
     * Combination of flags to check if a projectile can enter a north western tile
     */
    var BLOCK_PROJECTILE_NORTH_WEST = SOUTH_EAST_PILLAR_HIGH or SOUTH_WALL_HIGH or EAST_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_NORTH = SOUTH_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_NORTH_EAST = SOUTH_WEST_PILLAR_HIGH or SOUTH_WALL_HIGH or WEST_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_EAST = WEST_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_SOUTH_EAST = NORTH_WEST_PILLAR_HIGH or NORTH_WALL_HIGH or WEST_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_SOUTH = NORTH_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_SOUTH_WEST = NORTH_EAST_PILLAR_HIGH or NORTH_WALL_HIGH or EAST_WALL_HIGH or BLOCK_PROJECTILE
    var BLOCK_PROJECTILE_WEST = EAST_WALL_HIGH or BLOCK_PROJECTILE

    /**
     * Combination of flags to check if a tile allows interaction through it (bankers behind bank booths, door behind key doors, ...)
     */
    var BLOCK_PATH = TILE_BLOCKED or FLOOR_DECORATION_BLOCKED or OBSTACLE_PATH

    var BLOCK_PATH_NORTH_WEST = SOUTH_EAST_PILLAR_PATH or SOUTH_WALL_PATH or EAST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_NORTH = SOUTH_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_NORTH_EAST = SOUTH_WEST_PILLAR_PATH or SOUTH_WALL_PATH or WEST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_EAST = WEST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_SOUTH_EAST = NORTH_WEST_PILLAR_PATH or NORTH_WALL_PATH or WEST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_SOUT = NORTH_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_SOUTH_WEST = NORTH_EAST_PILLAR_PATH or NORTH_WALL_PATH or EAST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_WEST = EAST_WALL_PATH or BLOCK_PATH

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a southern tile
     */
    var BLOCK_PATH_SOUTH_SIDE = NORTH_WALL or INTERACT_SIDE_UNUSED or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a western tile
     */
    var BLOCK_PATH_WEST_SIDE = EAST_WALL or INTERACT_SIDE_UNUSED or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a northern tile
     */
    var BLOCK_PATH_NORTH_SIDE = SOUTH_WALL or INTERACT_SIDE_UNUSED or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a eastern tile
     */
    var BLOCK_PATH_EAST_SIDE = WEST_WALL or INTERACT_SIDE_UNUSED or BLOCK_ENTER

    /**
     * Combination of flags to check there are no extra constraints that would block entering a northern tile for non corners of a big unit
     * For example any of these walls (or pillars) would block the unit, on the corners it doesn't need to check one wall
     *
     * <pre>
     * |_|
     * x x x
     * x x x
     * x x x
    </pre> *
     */
    var BLOCK_PATH_NORTH_BIG = WEST_WALL_PATH or SOUTH_WEST_PILLAR_PATH or SOUTH_WALL_PATH or SOUTH_EAST_PILLAR_PATH or EAST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_EAST_BIG = NORTH_WALL_PATH or NORTH_WEST_PILLAR_PATH or WEST_WALL_PATH or SOUTH_WEST_PILLAR_PATH or SOUTH_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_SOUTH_BIG = WEST_WALL_PATH or NORTH_WEST_PILLAR_PATH or NORTH_WALL_PATH or NORTH_EAST_PILLAR_PATH or EAST_WALL_PATH or BLOCK_PATH
    var BLOCK_PATH_WEST_BIG = NORTH_WALL_PATH or NORTH_EAST_PILLAR_PATH or EAST_WALL_PATH or SOUTH_EAST_PILLAR_PATH or SOUTH_WALL_PATH or BLOCK_PATH

    var BLOCK_ENTER_NORTH_BIG = WEST_WALL or SOUTH_WEST_PILLAR or SOUTH_WALL or SOUTH_EAST_PILLAR or EAST_WALL or BLOCK_ENTER
    var BLOCK_ENTER_EAST_BIG = NORTH_WALL or NORTH_WEST_PILLAR or WEST_WALL or SOUTH_WEST_PILLAR or SOUTH_WALL or BLOCK_ENTER
    var BLOCK_ENTER_SOUTH_BIG = WEST_WALL or NORTH_WEST_PILLAR or NORTH_WALL or NORTH_EAST_PILLAR or EAST_WALL or BLOCK_ENTER
    var BLOCK_ENTER_WEST_BIG = NORTH_WALL or NORTH_EAST_PILLAR or EAST_WALL or SOUTH_EAST_PILLAR or SOUTH_WALL or BLOCK_ENTER

}