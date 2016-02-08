//import java.io.BufferedReader;
//import java.io.FileReader;
//    
//public class Driver {
//    private final static int size = 100;
//    
//    public static void main(String[] args) {
//        HashTable hashtable = new HashTable(size);
//        AdjacencyList adjacencylist = new AdjacencyList(size);
//        
//        BufferedReader br = null;
//        br = new BufferedReader(new FileReader(inputFile));
//        String s;
//        int i;
//        while ((s = br.readLine()) != null) {
//            if (s != ".") {
//                if (Integer.parseInt(s)) {
//                    adjacencylist.makeList(s, size, i, 0);
//                }
//                else {
//                    hashtable.Hash(s, i, false);    
//                }
//                System.out.println("Add.");
//            }
//            i++;
//        }
//    }
//}