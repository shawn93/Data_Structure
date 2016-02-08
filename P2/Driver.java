import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Driver {

    private static String input;
    private static String output;
    private static HuffmanTree tree;
    private static Map<Character, Integer> frequencies;
    private static Map<Character, ArrayList<Integer>> encodingTable;
    private static Map<String, Character> decodingTable;
    
    public static void main(String[] args) {
        boolean isCompressed = true; 
        boolean verbose = false;
        boolean force = false;

        if (args.length < 3 || args.length > 5) {
            System.err.println("Missing some command line arguments.");
            System.exit(-1);
        }

        if (args[0].equals("-c")) {
            isCompressed = true;
        } else if (args[0].equals("-u")) {
            isCompressed = false;
        } else {
            System.err.println("First argument is not '-c' or '-u'.");
            System.exit(-1);
        }

        output = args[args.length - 1];
        input = args[args.length - 2];

        // remove existing output file
        File f = new File(output);
        f.delete();

        if (output == null || input == null || output.startsWith("-") || input.startsWith("-")) {
            System.err.println("Incorrect input or output file.");
            System.exit(-1);
        }

        if (args[1].equals("-v") || args[2].equals("-v")) {
            verbose = true;
        }
        if (args[1].equals("-f") || args[2].equals("-f")) {
            force = true;
        }

        boolean decompress = true;
        
        if (isCompressed) {
            compress(force);
        } else {
            decompress = decompress();
        }

        if (verbose && decompress) {
            print(isCompressed);
        }
    }
    
    /*
     * Calculate the Frequencies of Characters
     */
    public static void compress(boolean force) {
//    	System.out.println("Calculating frequencies......");
        TextFile textFile = new TextFile(input, 'r');
        frequencies = new HashMap<Character, Integer>();
        while (!textFile.EndOfFile()) {
            Character character = new Character(textFile.readChar());
            Integer currFrequency = frequencies.get(character);
            if (currFrequency == null) {
                currFrequency = new Integer(1);
            } else {
                int newFrequency = currFrequency.intValue() + 1;
                currFrequency = new Integer(newFrequency);
            }
            frequencies.put(character, currFrequency);
        }

        textFile.close();

        MapComparator comparator = new MapComparator(frequencies);
        Map<Character, Integer> sortedFrequencies = new TreeMap<Character, Integer>(comparator);
        sortedFrequencies.putAll(frequencies);

        int numCharacters = 0;
        for (Character c : sortedFrequencies.keySet()) {
            int sortedFreq = sortedFrequencies.get(c);
            numCharacters += sortedFreq;
        }

        // Size of uncompressed file = (# of characters in the input file) * 8
        long sizeOfU = numCharacters * 8;

        // Build Huffman Tree
        tree = new HuffmanTree(sortedFrequencies);
        tree.buildCompressionTree();

        // Build lookup Table
        encodingTable = tree.buildEncodingTable();

        // Size of the compressed file (bits)
        long sizeOfC = 0;

        for (Character c : frequencies.keySet()) {
            // For each character c in the input file, (frequency of c) * size of the encoding for c
            // Size of the tree (1 bit for each internal node, 9 bits for each leaf
            // An extra 2 bytes (16 bits) for the magic number
            // An extra 4 bytes (32 bits) for header information used in the BinaryFile class
            sizeOfC += (frequencies.get(c) * encodingTable.get(c).size());
        }

        // 8 more for padding count
        sizeOfC += tree.getSize() + 16 + 32 + 8;

        // make file size a multiple of 8
        int paddingNum = 0;
        if (sizeOfC % 8 != 0) {
            paddingNum = 8 - (int) sizeOfC % 8;
            sizeOfC += paddingNum;
        }

        if (force || (sizeOfC < sizeOfU)) {
            BinaryFile binaryFile = new BinaryFile(output, 'w');
            if(sizeOfC >= sizeOfU) {
                System.out.println("Forced compression.");
            }
            System.out.println("Writing compressed file");
            tree.writeCompressed(input, binaryFile, paddingNum);
            binaryFile.close();
        } else {
            System.out.println("This file cannot be compressed to a smaller size.");
        }
    }
    
    /*
     * Determine whether it is a compressed file
     */
    public static boolean decompress() {
//    	System.out.println("Compressing......");
        BinaryFile binaryFile = new BinaryFile(input, 'r');
        char magicH = binaryFile.readChar();
        char magicF = binaryFile.readChar();

        if (magicH != 'H' || magicF != 'F') {
            System.out.println("This is not a compressed file.");
            binaryFile.close();
            return false;
        }

        tree = new HuffmanTree();
        // Build Lookup Table 
        tree.buildDecompressionTree(binaryFile);
        decodingTable = tree.buildDecodingTable();
        
        //decoding text
        System.out.println("Decoding......");
        TextFile textFile = new TextFile(output, 'w');
        tree.decodeText(binaryFile, textFile);
        frequencies = tree.getFrequencies();
        
        binaryFile.close();     
        return true;
    }
    
    /*
     * Print character frequencies in the input file 
     */
    public static void print(boolean compress) {
        if (compress) {
            System.out.println("ASCII Character Frequency:");
            for (Character c : frequencies.keySet()) {
                System.out.println((int) c + "\t" + frequencies.get(c));
            }
            System.out.println("\n");
        } else {
            System.out.println("ASCII Character Frequency:");
            for (Character c : frequencies.keySet()) {
                System.out.println((int) c + "\t" + frequencies.get(c));
            }
            System.out.println("\n");
        }

        // Print Huffman tree 
        tree.printTree((tree.height() / 2));

        // Print Huffman codes for character that has a code 
        if (compress) {
            System.out.println("ASCII Character Huffman Codes:");
            
            for (Character c : encodingTable.keySet()) {
                if(c != null) {
                    System.out.print((int) c + "\t");
                    ArrayList<Integer> codes = encodingTable.get(c);
                    for (Integer i : codes) {
                        System.out.print(i);
                    }
                    System.out.println();
                }
            }
        } else {
            System.out.println("Huffman Code ASCII Character:");
            for (String code : decodingTable.keySet()) {
                System.out.print(code);
                System.out.print("\t" + (int) (decodingTable.get(code)));
                System.out.println();
            }
        }
    }
}
