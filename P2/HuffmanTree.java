import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HuffmanTree {

    private Node root;
    private int size;
    private Map<Character, Integer> frequencies;
    private ArrayList<Character> characterList = new ArrayList<Character>();
    private Map<Character, ArrayList<Integer>> encodingTable;
    private Map<String, Character> decodingTable;
    private StringBuilder stringBuilder;
    
    //For decompression
    public HuffmanTree() {
        
    }   
    
    //For compression
    public HuffmanTree(Map<Character, Integer> frequencies) {
        this.frequencies = frequencies;
        Object[] keyArray = frequencies.keySet().toArray();
        for (Object o : keyArray) {
            characterList.add((Character) o);
        }
    }

    private class Node implements Comparable {
        
        public Node left;
        public Node right;
        public Character character;
        public Integer frequency;

        public Node() {
            
        }

        public Node(Character character) {
            this.character = character;
        }

        public Node(Character character, Integer frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Object o) {

            Node node = (Node) o;
            if (node.frequency < this.frequency) {
                return 1;
            } else if (node.frequency > this.frequency) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    
    public int height() {
        return height(root);
    }
    
    private int height(Node tree) {
        if (tree == null)
            return 0;
        return Math.max((1 + height(tree.left)), height(tree.right));
    }
    
        public int getSize() {
        return size;
    }
        
    public Map<Character, Integer> getFrequencies() {
        return frequencies;
    }
    
    /*
     * Print tree in format.
     */
    public void printTree(int offset) {
        printTree(root, offset);
    }
    
    private void printTree(Node tree, int offset) {
        if (tree != null) {
            for (int i = 0; i < offset; i++) {
                System.out.print("\t");
            }
            char c = ' ';
            if(tree.character != null) {
                c = tree.character;
                if (c == '\n') {
                    c = ' ';
                }
            }

            System.out.println("(" + c + " : " + tree.frequency + ")");
            printTree(tree.left, offset + 1);
            printTree(tree.right, offset-1);
        }
    }
    
    /*
     * Build tree of compression.
     */
    public void buildCompressionTree() {
//    	System.out.println("Building tree of compression");
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for (Character c : characterList) {
            nodeList.add(new Node(c, frequencies.get(c)));
        }
        buildCompressionTree(nodeList);
    }

    private void buildCompressionTree(ArrayList<Node> nodeList) {
        if (characterList.size() == 1) {
            int sum = nodeList.get(0).frequency;
            root = new Node(null, sum);
            root.left = new Node(nodeList.get(0).character, nodeList.get(0).frequency);
            root.right = new Node();
        } 
        
        if (nodeList.size() > 1) {
            Collections.sort(nodeList);
            int sum = nodeList.get(0).frequency + nodeList.get(1).frequency;
            root = new Node(null, sum);
            root.left = nodeList.remove(0);
            root.right = nodeList.remove(0);
            nodeList.add(root);
            buildCompressionTree(nodeList);
        }    
    }

    /*
     * Build encoding table.
     */
    public Map<Character, ArrayList<Integer>> buildEncodingTable() {
        encodingTable = new HashMap<Character, ArrayList<Integer>>();
        buildEncodingTable(root, new ArrayList<Integer>());
        return encodingTable;
    }

    private void buildEncodingTable(Node tree, ArrayList<Integer> list) {
//    	System.out.println("Build encoding table......");
        if (tree.left == null && tree.right == null) {
            // 9 bits for one leaf
            size += 9;
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            tmp.addAll(list);
            encodingTable.put(tree.character, tmp);
        } else {
            
            // 1 bit for an internal node
            if (tree.left != null) {
                size += 1;
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.addAll(list);
                tmp.add(0);
                buildEncodingTable(tree.left, tmp);
            }
            
            if (tree.right != null) {
                size += 1;
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.addAll(list);
                tmp.add(1);
                buildEncodingTable(tree.right, tmp);
            }
        }
    }
    
    /*
     * Serialize tree.
     */
    private void serializeTree(Node tree, BinaryFile outputFile) {
//    	System.out.println("Serializing tree......");
        if (tree != null) {
            if (tree.left == null && tree.right == null) {
                // leaf
                stringBuilder.append(0);
                stringBuilder.append(tree.character);
            } else {
                // internal node
                stringBuilder.append(1);
            }
            serializeTree(tree.left, outputFile);
            serializeTree(tree.right, outputFile);
        }
    }
    
    /*
     * Write compressed file.
     */
    public void writeCompressed(String input, BinaryFile outputFile, int paddingNum) {
        // write magic number
        outputFile.writeChar('H');
        outputFile.writeChar('F');
        // write padding count
        outputFile.writeChar((char)paddingNum);
        // write tree
        stringBuilder = new StringBuilder();
        serializeTree(root, outputFile);
        String serialized = stringBuilder.toString();
        for (char c : serialized.toCharArray()) {
            if (c == '0') {
                outputFile.writeBit(false);
            } else if (c == '1') {
                outputFile.writeBit(true);
            } else {
                outputFile.writeChar(c);
            }
        }
        // write encoded text
//     	System.out.println("Writing encoded text......");
        TextFile inputFile = new TextFile(input, 'r');
        encodeText(inputFile, outputFile);
        
        if (paddingNum > 0) {
            for (int i = 0; i < paddingNum; i++) {
                outputFile.writeBit(false);
            }
        }

        inputFile.close();
    }
    
    /*
     * Encode text.
     */
    private void encodeText(TextFile inputFile, BinaryFile outputFile) {
//    	System.out.println("Start Encoding......");
        while (!inputFile.EndOfFile()) {
            Character c = new Character(inputFile.readChar());
            // write encoded character
            for (Integer i : encodingTable.get(c)) {
                if (i.equals(0)) {
                    outputFile.writeBit(false);
                } else if (i.equals(1)) {
                    outputFile.writeBit(true);
                } else {
                    System.err.println("Error.Characters are not encoded to 0 or 1.");
                }
            }
        }
//        System.out.println("End Encoding");
    }

    /*
     * Build tree of decompression.
     */
    public void buildDecompressionTree(BinaryFile binaryFile) {
        // root is internal node
        boolean bit = binaryFile.readBit();
        if (bit) {
            root = new Node();
        }
        buildDecompressionTree(binaryFile, root);
    }

    private void buildDecompressionTree(BinaryFile binaryFile, Node tree) {
//    	System.out.println("Building tree of decompression......");
        if (!binaryFile.EndOfFile()) {
            boolean bit = binaryFile.readBit();
            // internal node
            if (bit) {
                tree.left = new Node();
                buildDecompressionTree(binaryFile, tree.left);
            } else {
                // leaf
                char c = binaryFile.readChar();
                tree.left = new Node(c);
            }

            bit = binaryFile.readBit();
            // internal node
            if (bit) {
                tree.right = new Node();
                buildDecompressionTree(binaryFile, tree.right);
            } else {
                // leaf
                char c = binaryFile.readChar();
                tree.right = new Node(c);
            }
        }
    }
    
    /*
     * Build decoding table.
     */
    public Map<String, Character> buildDecodingTable() {
        decodingTable = new HashMap<String, Character>();
        buildDecodingTable(root, new ArrayList<Integer>());
        return decodingTable;
    }

    public void buildDecodingTable(Node tree, ArrayList<Integer> arraylist) {
//    	System.out.println("Building decoding table......");
        if (tree.left == null && tree.right == null) {
            String codes = "";
            
            for (int elem : arraylist) {
                codes += elem;              
            }
            
            decodingTable.put(codes, tree.character);
        } else {
            if (tree.left != null) {
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.addAll(arraylist);
                tmp.add(0);
                buildDecodingTable(tree.left, tmp);
            }
            if (tree.right != null) {
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.addAll(arraylist);
                tmp.add(1);
                buildDecodingTable(tree.right, tmp);
            }
        }
    }
    
    /*
     * Decode text.
     */
    public void decodeText(BinaryFile inputFile, TextFile outputFile) {
//    	System.out.println("Start decoding......");
        frequencies = new HashMap<Character, Integer>();
        while (!inputFile.EndOfFile()) {
            String codes = "";
            while (decodingTable.get(codes) == null) {
                boolean b = inputFile.readBit();
                if (b) {
                    codes +=1;
                } else {
                    codes += 0;
                }
            }
            
            Character c = decodingTable.get(codes);
            Integer currFreq = frequencies.get(c);
            if (currFreq == null) {
                currFreq = 0;
            }
            currFreq++;
            frequencies.put(c, currFreq);

            outputFile.writeChar(c);
        }
//    	System.out.println("End decoding......");
    }
}
