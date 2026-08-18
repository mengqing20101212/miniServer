package ly.sceneserver.common;

/** 假地图使用的地形编号；正式项目应由 config 策划表映射到移动代价。 */
public final class SceneTerrainType {
    public static final short PLAIN = 1;
    public static final short WATER = 2;
    public static final short ROAD = 3;
    public static final short FOREST = 4;
    public static final short MOUNTAIN = 5;

    private SceneTerrainType() {
    }
}
