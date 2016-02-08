import java.util.Random;


public class SortTest {
    
//    public final static int list_size = 8;
//    public static Random r = new Random();
//    public static Sort sort = new Sort();
//    
//    public static void buildArray(Comparable[] array){
//        for (int i = 0; i < list_size; i++){
//            array[i] = r.nextInt(200);
//        }
//    }
//    
//    public static void buildIntArray(int[] array){
//        for (int i = 0; i < list_size; i++){
//            array[i] = r.nextInt();
//        }
//    }

	public static void main(String[] args) {
	       
        long startTime, endTime;
        double duration;
        int i, list_size, NUMITER;
        list_size = 200;
//        list_size = 1000;
//        list_size = 5000;
//        list_size = 10000;
//        list_size = 50000;
//        list_size = 75000;
//        list_size = 100000;
//        list_size = 500000;
        NUMITER = 5000;
//        NUMITER = 2000;
//        NUMITER = 1000;
//        NUMITER = 100;
//        NUMITER = 50;
//        NUMITER = 20;
//        NUMITER = 1;


        Random randomGenerator = new Random();
        Sort sorter = new Sort();
//        LLNode list = null;
        Comparable[] list = new Comparable[NUMITER];
//        LLNode list = new LLNode();
//        int[] list = new int[list_size];
        
        startTime = System.currentTimeMillis();
        for(i=0; i<NUMITER; i++) {
            for (int j=0; j<list.length; j++) {
                list[j] = randomGenerator.nextInt();
            }

            sorter.insertionSort(list, 0, list.length-1, false);
//            sorter.insertionSort(list, 0, list.length-1, true);
//            sorter.selectionSort(list, 0, list.length-1, false);
//            sorter.selectionSort(list, 0, list.length-1, true);
//            sorter.shellSort(list, 0, list.length-1, false);
//            sorter.shellSort(list, 0, list.length-1, true);
//            sorter.bucketSort(list, 0, list.length-1, false);
//            sorter.bucketSort(list, 0, list.length-1, true);
//            sorter.heapSort(list, 0, list.length-1, false);
//            sorter.heapSort(list, 0, list.length-1, true);
//            sorter.quicksort(list, 0, list.length-1, false);
//            sorter.quicksort(list, 0, list.length-1, true);
//            sorter.radixSort(list, 0, list.length-1, false);
//            sorter.radixSort(list, 0, list.length-1, true);       
//            sorter.optimizedQuicksort(list, 0, list.length-1, false);       
//            sorter.optimizedQuicksort(list, 0, list.length-1, true);

        }
        endTime = System.currentTimeMillis();

        duration = ((double) (endTime - startTime)) / NUMITER;

//        System.out.println("Overhead time: " + duration);
        System.out.println("insertionSort Running Time:" + duration);
//        System.out.println("selectionSort Running Time:" + duration);
//        System.out.println("shellSort Running Time:" + duration);
//        System.out.println("bucketSort Running Time:" + duration);
//        System.out.println("heapSort Running Time:" + duration);
//        System.out.println("quickSort Running Time:" + duration);
//        System.out.println("radixSort Running Time:" + duration);
//        System.out.println("optimizedQuickSort Running Time:" + duration);

        
//      My tests
//      Comparable[] A = new Comparable[list_size];
//      buildArray(A);
//      
//      Comparable[] a = A.clone(); //insetionSort
//      Comparable[] b = A.clone(); //selectionSort
//      Comparable[] c = A.clone(); //shellSort
//      Comparable[] d = A.clone(); //heapsort
//      Comparable[] e = A.clone(); //quicksort
//      Comparable[] g = A.clone(); //optimizedQuicksor
//      
//      sort.insertionSort(a, 0, a.length-1, false);
//      System.out.println("insertionSort: " + Arrays.toString(a));
//      
//      sort.insertionSort(a, 0, a.length-1, true);
//      System.out.println("insertionSort: " + Arrays.toString(a));
//      
//      sort.selectionSort(b, 0, b.length-1, false);
//      System.out.println("selectionSort: " + Arrays.toString(b));
//      
//      sort.selectionSort(b, 0, b.length-1, true);
//      System.out.println("selectionSort: " + Arrays.toString(b));
//      
//      sort.shellSort(c, 0, c.length-1, false);
//      System.out.println("shellSort: " + Arrays.toString(c));
//      
//      sort.shellSort(c, 0, c.length-1, true);
//      System.out.println("shellSort: " + Arrays.toString(c));
//      
//      sort.heapSort(d, 0, d.length-1, false);
//      System.out.println("heapSort: " + Arrays.toString(d));
//      
//      sort.heapSort(d, 0, d.length-1, true);
//      System.out.println("heapSort: " + Arrays.toString(d));
//      
//      sort.quicksort(e, 0, e.length-1, false);
//      System.out.println("quickSort: " + Arrays.toString(e));
//      
//      sort.quicksort(e, 0, e.length-1, true);
//      System.out.println("quickSort: " + Arrays.toString(e));
//      
//      sort.optimizedQuicksort(g, 0, g.length-1, false);
//      System.out.println("optimizedQuickSort: " + Arrays.toString(g));
//      
//      sort.optimizedQuicksort(g, 0, g.length-1, true);
//      System.out.println("optimizedQuickSort: " + Arrays.toString(g));
//      
//      int[] B = new int[list_size];
//      buildIntArray(B);
//      int[] f = B.clone(); //bucketSort
//      int[] h = B.clone(); //radixSort
//      System.out.println();
//      System.out.println("intArray: "+ Arrays.toString(f));
//      
//      sort.bucketSort(f, 0, f.length-1, false);
//      System.out.println("bucketSort: " + Arrays.toString(f));
//      
//      sort.bucketSort(f, 0, f.length-1, true);
//      System.out.println("bucketSort: " + Arrays.toString(f));
//      
//      sort.radixSort(h, 0, h.length-1, false);
//      System.out.println("radixSort: " + Arrays.toString(h));
//      
//      sort.radixSort(h, 0, h.length-1, true);
//      System.out.println("radixSort: " + Arrays.toString(h));
    }
}
