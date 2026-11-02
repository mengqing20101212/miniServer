package ly.sceneserver.common;

public final class SceneRouteVisibility {
    private SceneRouteVisibility() {}

    public static boolean intersects(ScenePoint from, ScenePoint to, double minX, double minY,
                                     double maxX, double maxY) {
        double enter = 0, exit = 1;
        double[] origins = {from.x(), from.y()};
        double[] deltas = {to.x() - from.x(), to.y() - from.y()};
        double[] minima = {minX, minY}, maxima = {maxX, maxY};
        for (int axis = 0; axis < 2; axis++) {
            if (deltas[axis] == 0) {
                if (origins[axis] < minima[axis] || origins[axis] > maxima[axis]) return false;
            } else {
                double first = (minima[axis] - origins[axis]) / deltas[axis];
                double last = (maxima[axis] - origins[axis]) / deltas[axis];
                enter = Math.max(enter, Math.min(first, last));
                exit = Math.min(exit, Math.max(first, last));
                if (enter > exit) return false;
            }
        }
        return true;
    }
}
