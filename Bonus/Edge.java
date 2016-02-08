public class Edge {

    private String elem;
    private int neighbor;
    private int cost;
    private Edge next;
    
    public Edge(String elem, int neighbor, int cost, Edge next) {
        this.elem = elem;
        this.neighbor = neighbor;
        this.cost = cost;
        this.next = next;
    }
    
    public void setelem(String elem) {
        this.elem = elem;
    }
    
    public void setNeighbor(int neighbor) {
        this.neighbor = neighbor;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public void setNext(Edge next) {
        this.next = next;
    }
    
    public String getElem() {
        return elem;
    }
    
    public int getNeighbor() {
        return neighbor;
    }
    
    public int getCost() {
        return cost;
    }
    
    public Edge getNext() {
        return next;
    }
}