public class Node {

    private String elem;
    private int vertex;
    private Node next;
    
    public Node(String elem, int vertex, Node next) {
        this.elem = elem;
        this.vertex = vertex;
        this.next = next;
    }
    
    public void setElem(String elem) {
        this.elem = elem;
    }
    
    public void setVertex(int vertex) {
        this.vertex = vertex;
    }
    
    public void setNext(Node next) {
        this.next = next;
    }
    
    public String getElem() {
        return elem;
    }
    
    public int getVertex() {
        return vertex;
    }
    
    public Node getNext() {
        return next;
    }
}