public class Vertex {
    
    private int distance;
    private int path;
    private boolean isKnown;
    
    public Vertex(int distance, int path, boolean known) {
        this.distance = distance;
        this.path = path;
        this.isKnown = isKnown;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public void setPath(int path) {
        this.path = path;
    }
    
    public void setKnown(boolean known) {
        this.isKnown = isKnown;
    }
    
    public int getDistance() {
        return distance;
    }
    
    public int getPath() {
        return path;
    }
    
    public boolean getKnown() {
        return isKnown;
    }
}