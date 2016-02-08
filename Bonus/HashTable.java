public class HashTable {

    private int size;
    private Node[] nodes;
    
    public HashTable(int size) {
        this.size = size;
        nodes = new Node[size];
    }
    
    public int Hash(String city, int vertex, boolean lookup) {
        int hashTable, hashIndex, i;
        hashTable = hashIndex = i = 0;
        
        //The graph with a cost of 4.
        for (i = 0; i < city.length(); i++) {
            hashTable = (hashTable << 4) + city.charAt(i);
        }
        hashIndex = hashTable % size;
        
        if (lookup != true) {
            Node node = new Node(city, vertex, null);
            node.setNext(nodes[hashIndex]);
            nodes[hashIndex] = node;
        }
        return hashIndex;
    }
    
    public Node getNode(int hash) {
        return nodes[hash];
    }
    
    public int getVertex(String city) {
        int vertex = -1;
        int hash = Hash(city, vertex, true);
        Node node = getNode(hash);
        
        while (node != null) {
            if (node.getElem().equals(city)) {
                vertex = node.getVertex();
                break;
            } 
            node = node.getNext();
        }
        
        return vertex;
    }
}