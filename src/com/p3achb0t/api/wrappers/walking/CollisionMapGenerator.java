//package com.p3achb0t.api.wrappers.walking;
//
//import com.google.common.reflect.TypeToken;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.IOException;
//import java.io.UncheckedIOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class CollisionMapGenerator {
//    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
//    private static final Gson GSON = new GsonBuilder().create();
//    private static final URI XTEA_URI = URI.create("https://archive.runestats.com/osrs/xtea/2021-05-05-rev195.json");
//    private static final Set<String> DOOR_NAMES = Set.of("Door", "Gate", "Large door");
//    private static final Set<Integer> REGULAR_DOORS = new HashSet<>();
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        CacheSystem.init();
//
//        var maxId = Arrays.stream(CacheSystem.cache.archive(2).group(6).fileIds).max().getAsInt();
//        for (var id = 0; id < maxId - 2; id++) {
//            if (!isDoorWithAction(id, "Open")) {
//                continue; // not a door
//            }
//
//            if (isDoorWithAction(id + 1, "Close")) {
//                REGULAR_DOORS.add(id);
//                REGULAR_DOORS.add(id + 1);
//                System.out.println(id + " " + (id + 1));
//                continue;
//            }
//
//            if (!isDoorWithAction(id + 1, "Open") && isDoorWithAction(id + 2, "Close")) {
//                REGULAR_DOORS.add(id);
//                REGULAR_DOORS.add(id + 2);
//                System.out.println(id + " " + (id + 2));
//            }
//        }
//
//        // Doors that don't return to their default id when closed
//        REGULAR_DOORS.add(1727);
//        REGULAR_DOORS.add(1728);
//        REGULAR_DOORS.add(15056);
//
//        // Doors that don't open to id + 1
//        REGULAR_DOORS.add(28479);
//        REGULAR_DOORS.add(28481);
//        REGULAR_DOORS.add(17601);
//        REGULAR_DOORS.add(18032);
//        REGULAR_DOORS.add(11766);
//        REGULAR_DOORS.add(11767);
//        REGULAR_DOORS.add(11770);
//        REGULAR_DOORS.add(11771);
//
//        var keys = GSON.<List<RegionKey>>fromJson(
//                HTTP_CLIENT.send(HttpRequest.newBuilder(XTEA_URI).build(), HttpResponse.BodyHandlers.ofString()).body(),
//                new TypeToken<List<RegionKey>>() {}.getType()
//        ).stream().collect(Collectors.toMap(p -> p.mapsquare, p -> p.key));
//
//        var regions = new ArrayList<Integer>();
//
//        for (var regionX = 0; regionX < 256; regionX++) {
//            for (var regionY = 0; regionY < 256; regionY++) {
//                var regionId = (regionX << 8) + regionY;
//                var group = CacheSystem.cache.archive(5).group("l" + regionX + "_" + regionY);
//
//                if (group == null) {
//                    continue;
//                }
//
//                try {
//                    var key = keys.get(regionId);
//                    group.buildFiles(key);
//                    group.file(0);
//                } catch (UncheckedIOException e) {
//                    System.out.println("new Area(" + regionX * 64 + ", " + regionY * 64 + ", " + (regionX * 64 + 63) + ", " + (regionY * 64 + 63) + ")");
////                    System.out.println("Missing key for region " + regionId);
//                    continue;
//                }
//
//                regions.add(regionId);
//            }
//        }
//
//        var map = buildCollisionMap(regions);
//        Files.write(Path.of("src/main/resources/collision-map"), Util.gzip(map.toBytes()));
//    }
//
//    private static boolean isDoorWithAction(int id, String action) {
//        if (Arrays.stream(CacheSystem.cache.archive(2).group(6).fileIds).noneMatch(i -> i == id)) {
//            return false;
//        }
//
//        var object = CacheSystem.getObject(id);
//        return object.name != null && DOOR_NAMES.contains(object.name) && Arrays.asList(object.actions).contains(action);
//    }
//
//    private static CollisionMap buildCollisionMap(List<Integer> regions) {
//        var map = new CollisionMap();
//
//        for (int region : regions) {
//            map.createRegion(region);
//        }
//
//        for (int region : regions) {
//            buildCollisionMap(map, region);
//        }
//
//        return map;
//    }
//
//    private static void buildCollisionMap(CollisionMap map, int region) {
//        var regionX = region >> 8;
//        var regionY = region & 0xff;
//
//        var objects = GameObjectInstance.decodeRegion(regionX, regionY, CacheSystem.cache.archive(5).group("l" + regionX + "_" + regionY).file(0));
//        var tiles = TileData.decodeRegion(regionX, regionY, CacheSystem.cache.archive(5).group("m" + regionX + "_" + regionY).file(0));
//
//        for (var dx = 0; dx < 64; dx++) {
//            for (var dy = 0; dy < 64; dy++) {
//                for (var dz = 0; dz < 4; dz++) {
//                    if ((tiles[dz][dx][dy].flags() & 1) == 0) {
//                        continue;
//                    }
//
//                    var x = regionX * 64 + dx;
//                    var y = regionY * 64 + dy;
//                    var z = dz;
//
//                    if ((tiles[1][dx][dy].flags() & 2) != 0) {
//                        z--;
//
//                        if (z < 0) {
//                            continue;
//                        }
//                    }
//
//                    markBlocking(map, x, y, z, 0);
//                    markBlocking(map, x, y, z, 1);
//                    markBlocking(map, x, y, z, 2);
//                    markBlocking(map, x, y, z, 3);
//                }
//            }
//        }
//
//        for (var instance : objects) {
//            var baseObject = CacheSystem.getObject(instance.object);
//            var transforms = baseObject.transforms != null ? baseObject.transforms : new int[]{instance.object};
//
//            for (var transformId : transforms) {
//                if (transformId == -1) {
//                    continue;
//                }
//
//                markObject(map, tiles, instance, CacheSystem.getObject(transformId));
//            }
//        }
//    }
//
//    private static void markObject(CollisionMap map, TileData[][][] tiles, GameObjectInstance instance, ObjectDefinition object) {
//        if (REGULAR_DOORS.contains(object.id)) {
//            return;
//        }
//
//        var x = instance.position.x();
//        var y = instance.position.y();
//        var z = instance.position.z();
//        var type = ObjectType.values()[instance.type];
//        var category = type.category;
//
//        if (type == ObjectType.WALL_CONNECTOR || type == ObjectType.WALL_PILLAR) {
//            return;
//        }
//
//        var flags0 = tiles[0][x % 64][y % 64].flags();
//        var flags1 = tiles[1][x % 64][y % 64].flags();
//        var flagsP = tiles[z][x % 64][y % 64].flags();
//
//        if ((flags0 & 0x10) != 0 && (flagsP & 2) == 0) {
//            return;
//        }
//
//        if (object.interactType == 0 || object.hollow) {
//            return;
//        }
//
//        if ((flags1 & 2) != 0) {
//            if (z == 0) {
//                return;
//            }
//
//            z--;
//        }
//
////        switch (category) {
////            case REGULAR -> {
////                int sizeX;
////                int sizeY;
////                if (instance.orientation % 2 == 0) {
////                    sizeX = object.sizeX;
////                    sizeY = object.sizeY;
////                } else {
////                    sizeY = object.sizeX;
////                    sizeX = object.sizeY;
////                }
////
////                for (var dx = 0; dx < sizeX; dx++) {
////                    for (var dy = 0; dy < sizeY; dy++) {
////                        markBlocking(map, x + dx, y + dy, z, 0);
////                        markBlocking(map, x + dx, y + dy, z, 1);
////                        markBlocking(map, x + dx, y + dy, z, 2);
////                        markBlocking(map, x + dx, y + dy, z, 3);
////                    }
////                }
////            }
////
////            case FLOOR_DECORATION -> {
////                if (object.interactType == 1 && (object.wall != 0 || object.blocksGround)) {
////                    markBlocking(map, x, y, z, 0);
////                    markBlocking(map, x, y, z, 1);
////                    markBlocking(map, x, y, z, 2);
////                    markBlocking(map, x, y, z, 3);
////                }
////            }
////
////            case WALL -> {
////                markBlocking(map, x, y, z, instance.orientation);
////
////                if (type == ObjectType.WALL_CORNER) {
////                    markBlocking(map, x, y, z, (instance.orientation + 1) % 4);
////                }
////            }
////        }
//    }
//
//    private static void markBlocking(CollisionMap map, int x, int y, int z, int orientation) {
//        switch (orientation) {
//            case 0 -> set(map, x - 1, y, z, 1); // west
//            case 1 -> set(map, x, y, z, 0); // north
//            case 2 -> set(map, x, y, z, 1); // east
//            case 3 -> set(map, x, y - 1, z, 0); // south
//        }
//    }
//
//    private static void set(CollisionMap map, int x, int y, int z, int w) {
//        if (x == 3105 && y == 3162 && z == 2) {
//            System.out.println();
//        }
//        map.set(x, y, z, w, false);
//    }
//
//    private static class RegionKey {
//        public final int mapsquare;
//        public final int[] key;
//
//        private RegionKey(int mapsquare, int[] key) {
//            this.mapsquare = mapsquare;
//            this.key = key;
//        }
//    }
//}
