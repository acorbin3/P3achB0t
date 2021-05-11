package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Tile

class BankLocations(ctx: Context) {
    val LUMBRIDGE_BANK = Area(Tile(3207, 3222,2), Tile(3210, 3215, 2),ctx=ctx)
    val VARROCK_WEST_BANK = Area(Tile(3180, 3447), Tile(3190, 3433, 0),ctx=ctx)
    val VARROCK_EAST_BANK = Area(Tile(3250, 3424), Tile(3257, 3416, 0),ctx=ctx)
    val GRAND_EXCHANGE_BANK = Area(Tile(3160, 3495), Tile(3170, 3484, 0),ctx=ctx)
    val EDGEVILLE_BANK = Area(Tile(3091, 3499), Tile(3098, 3488, 0),ctx=ctx)
//    val FALADOR_EAST_BANK: Area = Area.union(Area(Tile(3009, 3358), 3018, 3353, 0), Area(Tile(3019, 3356), 3021, 3353, 0))
//    val FALADOR_WEST_BANK: Area = Area.union(Area(Tile(2943, 3373), 2947, 3368, 0), Area(Tile(2949, 3369), 2945, 3366, 0))
    val DRAYNOR_BANK = Area(Tile(3088, 3246),Tile( 3097, 3240, 0),ctx=ctx)
    val DUEL_ARENA_BANK = Area(Tile(3380, 3273),Tile( 3384, 3267, 0),ctx=ctx)
    val SHANTAY_PASS_BANK = Area(Tile(3304, 3125),Tile( 3312, 3117, 0),ctx=ctx)
    val AL_KHARID_BANK = Area(Tile(3265, 3173),Tile( 3272, 3161, 0),ctx=ctx)
    val CATHERBY_BANK = Area(Tile(2806, 3445),Tile( 2812, 3438, 0),ctx=ctx)
    val SEERS_VILLAGE_BANK = Area(Tile(2721, 3493),Tile( 2730, 3490, 0),ctx=ctx)
    val ARDOUGNE_NORTH_BANK = Area(Tile(2612, 3335),Tile( 2621, 3330, 0),ctx=ctx)
    val ARDOUGNE_SOUTH_BANK = Area(Tile(2649, 3287),Tile( 2658, 3280, 0),ctx=ctx)
    val PORT_KHAZARD_BANK = Area(Tile(2659, 3164),Tile( 2665, 3158),ctx=ctx)
    val YANILLE_BANK = Area(Tile(2609, 3097),Tile( 2616, 3088, 0),ctx=ctx)
    val CORSAIR_COVE_BANK = Area(Tile(2568, 2867),Tile( 2572, 2863, 0),ctx=ctx)
    val CASTLE_WARS_BANK = Area(Tile(2442, 3084), Tile(2445, 3082, 0),ctx=ctx)
    val LLETYA_BANK = Area(Tile(2350, 3166),Tile( 2354, 3161, 0),ctx=ctx)
    val GRAND_TREE_WEST_BANK = Area(Tile(2438, 3489),Tile( 2442, 3487, 1),ctx=ctx)
    val GRAND_TREE_SOUTH_BANK = Area(Tile(2448, 3482),Tile( 2450, 3478, 1),ctx=ctx)
    val TREE_GNOME_STRONGHOLD_BANK = Area(Tile(2443, 3427), Tile(2448, 3422, 1),ctx=ctx)
    val SHILO_VILLAGE_BANK = Area(Tile(2851, 2957),Tile( 2853, 2951, 0),ctx=ctx)
    val NEITIZNOT_BANK = Area(Tile(2334, 3808),Tile( 2339, 3805, 0),ctx=ctx)
    val JATIZSO_BANK = Area(Tile(2415, 3803),Tile( 2418, 3799, 0),ctx=ctx)
    val BARBARIAN_OUTPOST_BANK = Area(Tile(2533, 3576), Tile(2537, 3572, 0),ctx=ctx)
    val ETCETARIA_BANK = Area(Tile(2618, 3896, 2621),Tile( 3893, 0),ctx=ctx)
    val DARKMEYER_BANK = Area(Tile(3601, 3370, 3609), Tile(3365, 0),ctx=ctx)
    val CHARCOAL_BURNERS_BANK = Area(Tile(1711, 3469),Tile( 1723, 3460, 0),ctx=ctx)
    val HOSIDIUS_BANK = Area(Tile(1749, 3594), Tile(1745, 3603, 0),ctx=ctx)
    val PORT_PISCARILIUS_BANK = Area(Tile(1794, 3793),Tile( 1811, 3784),ctx=ctx)
//    val ANY: Area = Area.union(
//        LUMBRIDGE_BANK,
//        VARROCK_WEST_BANK,
//        VARROCK_EAST_BANK,
//        GRAND_EXCHANGE_BANK,
//        EDGEVILLE_BANK,
//        FALADOR_EAST_BANK,
//        FALADOR_WEST_BANK,
//        DRAYNOR_BANK,
//        DUEL_ARENA_BANK,
//        SHANTAY_PASS_BANK,
//        AL_KHARID_BANK,
//        CATHERBY_BANK,
//        SEERS_VILLAGE_BANK,
//        ARDOUGNE_NORTH_BANK,
//        ARDOUGNE_SOUTH_BANK,
//        PORT_KHAZARD_BANK,
//        YANILLE_BANK,
//        CORSAIR_COVE_BANK,
//        CASTLE_WARS_BANK,
//        LLETYA_BANK,
//        GRAND_TREE_WEST_BANK,
//        GRAND_TREE_SOUTH_BANK,
//        TREE_GNOME_STRONGHOLD_BANK,
//        SHILO_VILLAGE_BANK,
//        NEITIZNOT_BANK,
//        JATIZSO_BANK,
//        BARBARIAN_OUTPOST_BANK,
//        ETCETARIA_BANK,
//        DARKMEYER_BANK,
//        CHARCOAL_BURNERS_BANK,
//        HOSIDIUS_BANK
//    )
}