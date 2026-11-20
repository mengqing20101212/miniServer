package ly.sceneserver.common.persistence;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import java.nio.file.*;
import java.util.*;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ly.sceneserver.common.*;

public final class TmxSceneStaticMapLoader implements SceneStaticMapLoader {
    private final Path directory;
    public TmxSceneStaticMapLoader(Path directory) { this.directory = directory; }

    public int regionCount() {
        try { return JSON.parseObject(Files.readString(directory.resolve("world-regions.json")))
                .getJSONArray("regionDefs").size(); }
        catch (java.io.IOException error) { throw new IllegalStateException(error); }
    }

    @Override public void load(SceneConfig config, SceneStaticMap map) {
        try {
            JSONObject regions = JSON.parseObject(Files.readString(directory.resolve("world-regions.json")));
            if (regions.getIntValue("width") != map.width() || regions.getIntValue("height") != map.height())
                throw new IllegalArgumentException("区域文件尺寸与场景不一致");
            int[] regionIds = regions.getJSONArray("cellRegionIds").toJavaObject(int[].class);
            if (regionIds.length != map.cellCount()) throw new IllegalArgumentException("区域格子数量错误");
            new FlatSceneStaticMapLoader().load(config, map);
            map.loadRegionIds(regionIds);
            List<Integer> ids = new ArrayList<>();
            Set<Integer> spawnRegions = new HashSet<>();
            for (JSONObject region : regions.getJSONArray("regionDefs").toList(JSONObject.class)) {
                int id = region.getIntValue("id");
                ids.add(id);
                if (region.getBooleanValue("isSpawn")) spawnRegions.add(id);
            }
            map.loadRegionDefinitions(ids);
            map.loadSpawnRegions(spawnRegions);
            SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Handler handler = new Handler(map);
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();
            // 251MiB地图的装饰属性包含大量内置XML转义，不能套用小文档的累计实体长度限制；DTD仍禁用。
            parser.setProperty("http://www.oracle.com/xml/jaxp/properties/maxGeneralEntitySizeLimit", "0");
            parser.setProperty("http://www.oracle.com/xml/jaxp/properties/totalEntitySizeLimit", "0");
            parser.parse(directory.resolve("WorldMap2.tmx").toFile(), handler);
            handler.finish();
        } catch (Exception error) {
            throw new IllegalStateException("读取公司地图失败: " + directory, error);
        }
    }

    private static final class Handler extends DefaultHandler {
        private final SceneStaticMap map;
        private final Set<Integer> lakeIds = new HashSet<>();
        private final Set<String> layers = new HashSet<>();
        private final int[] ground;
        private int depth, firstGid, index;
        private boolean lake, data;
        private String layer;
        Handler(SceneStaticMap map) { this.map = map; ground = new int[map.cellCount()]; }
        @Override public void startElement(String uri, String local, String name, Attributes attributes) {
            depth++;
            if (depth == 1 && (!"map".equals(name)
                    || Integer.parseInt(attributes.getValue("width")) != map.width()
                    || Integer.parseInt(attributes.getValue("height")) != map.height()))
                throw new IllegalArgumentException("TMX地图尺寸错误");
            if (depth == 2 && name.equals("tileset")) {
                lake = "Lake".equalsIgnoreCase(attributes.getValue("name"));
                firstGid = Integer.parseInt(attributes.getValue("firstgid"));
            }
            if (depth == 3 && name.equals("tile") && lake)
                lakeIds.add(firstGid + Integer.parseInt(attributes.getValue("id")));
            if (depth == 2 && name.equals("layer")) {
                String candidate = attributes.getValue("name");
                if (Set.of("地表", "地表装饰", "物体").contains(candidate)) {
                    layer = candidate; index = 0; layers.add(candidate);
                }
            }
            if (layer != null && depth == 3 && name.equals("data")) {
                if (attributes.getValue("encoding") != null || attributes.getValue("compression") != null)
                    throw new IllegalArgumentException("需要未压缩XML tile图层");
                data = true;
            }
            if (data && depth == 4 && name.equals("tile")) {
                String gidText = attributes.getValue("gid");
                int gid = gidText == null || gidText.isBlank() ? 0 : (int) (Long.parseLong(gidText) & 0x0fffffffL);
                int x = index % map.width(), y = index / map.width();
                if (layer.equals("地表")) ground[index] = gid;
                else if (gid != 0) map.set(x, y, SceneTerrainType.MOUNTAIN, gid, (byte) 0, 0);
                index++;
            }
        }
        @Override public void endElement(String uri, String local, String name) {
            if (depth == 3 && name.equals("data") && data) {
                if (index != map.cellCount()) throw new IllegalArgumentException("TMX图层格子数量错误: " + layer);
                data = false;
            }
            if (depth == 2 && name.equals("layer")) layer = null;
            if (depth == 2 && name.equals("tileset")) lake = false;
            depth--;
        }
        void finish() {
            if (!layers.equals(Set.of("地表", "地表装饰", "物体")))
                throw new IllegalArgumentException("TMX缺少地形图层");
            for (int index = 0; index < ground.length; index++) {
                if (lakeIds.contains(ground[index]))
                    map.set(index % map.width(), index / map.width(), SceneTerrainType.WATER, ground[index], (byte) 0, 0);
            }
        }
    }
}
