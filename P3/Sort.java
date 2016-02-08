public class Sort implements SortInterface {
    
    public Sort() {
        
    }
    
    @Override
    public void insertionSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        int i, j;
        Comparable curr;
        
        if (!reversed) { 
            // Not reversed
            for (i = lowindex + 1; i <= highindex; i++) {
                curr = array[i];
                for (j = i - 1; j >= lowindex && array[j].compareTo(curr) > 0; j--) {
                    array[j + 1] = array[j];                    
                }
                array[j + 1] = curr;
            }
        }
        else { 
            // Reversed 
            for (i = lowindex + 1; i <= highindex; i++) {
                curr = array[i];
                for (j = i - 1; j >= lowindex && array[j].compareTo(curr) < 0; j--) {
                    array[j + 1] = array[j];
                }
                array[j + 1] = curr;
            }
        }   
    }
    
    @Override
    public void selectionSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        int i, j, min;
        Comparable tmp;
        
        if (!reversed) { 
            // Not reversed
            for (i = lowindex; i <= highindex; i++) {
                min = i;
                tmp = array[i];
                for (j = i + 1; j <= highindex; j++) {
                    if (array[j].compareTo(tmp) < 0) {
                        tmp = array[j];
                        min = j;
                    }
                }
                array[min] = array[i];
                array[i] = tmp;
            }
        }
        
        else { 
            // Reversed
            for (i = lowindex; i <= highindex; i++) {
                min = i;
                tmp = array[i];
                for (j = i + 1; j <= highindex; j++) {
                    if (array[j].compareTo(tmp) > 0) {
                        tmp = array[j];
                        min = j;
                    }
                }
                array[min] = array[i];
                array[i] = tmp;
            }           
        }   
    }
    
    @Override
    public void shellSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        int n = highindex - lowindex;
        int increment, offset;
        for (increment = n / 2; increment > 0; increment = increment / 2) {
            for (offset = 0; offset < increment; offset++) {
                shellInsertionSort(array, offset, increment, lowindex, highindex, reversed);
            }
        }   
    }
    
    private static void shellInsertionSort(Comparable[] array, int offset, int increment, int lowindex, int highindex, boolean reversed) {
        int i, j;
        for (i = offset + increment; i <= highindex; i = i + increment) {
            Comparable insert_elem = array[i];
            if (!reversed) {
                for (j = i - increment; j >= 0 && insert_elem.compareTo(array[j]) < 0; j = j - increment) {
                    array[j + increment] = array[j];
                }
            }
            else {
                for (j = i - increment; j >= 0 && insert_elem.compareTo(array[j]) > 0; j = j - increment) {
                    array[j + increment] = array[j];
                }                
            }
            
            array[j + increment] = insert_elem;
        }
    }
    
    @Override
    public void bucketSort(int[] array, int lowindex, int highindex, boolean reversed) {
        int buckets = (highindex - lowindex + 1) / 2;
        int curr, i, j;
        curr = i = j = 0;
        LLNode[] node = new LLNode[buckets];
        int min, max, interval;
        min = max = array[0];
         
        // Finds min and max
        for (i = lowindex; i <= highindex; i++) {
            curr = array[i];
            if (max < curr) {
                max = curr;
            }
            if (min > curr) {
                min = curr;
            }
        }
        
//        System.out.println("max: "+max);
//        System.out.println("min: "+ min);        
        
        interval = (max - min) / buckets;
        interval = Math.abs(interval);
        
//        System.out.println("interval: "+interval);
         
       // Initializes linked list index
       for (i = 0; i < buckets; i++) {
           node[i] = new LLNode(-1, null);
       }
       
        // BucketSort
        LLNode tmp1, tmp2;
        for (i = lowindex; i <= highindex; i++) {
            j = Math.abs(array[i]) / (interval + 1);
            if (j >= buckets) {
                j = buckets - 1;                
            }
             
            // Inserts first element
            if (node[j].next() == null) {
                tmp1 = new LLNode(array[i], null);
                node[j].setNext(tmp1);
            }
             
            else {
                tmp2 = node[j];
                
                if (!reversed) { 
                    // Not reversed
                    while (tmp2.next() != null && tmp2.next().val() < array[i]) {
                        tmp2 = tmp2.next();
                    }
                     
                    // End of list
                    if (tmp2.next() == null) {
                        tmp1 = new LLNode(array[i], null);
                        tmp2.setNext(tmp1);
                    }
                     
                    // Insert element to the list
                    else {
                        tmp1 = new LLNode(array[i], tmp2.next());
                        tmp2.setNext(tmp1);
                    }
                }
                 
                else { 
                    // Reversed
                    while (tmp2.next() != null && tmp2.next().val() > array[i]) {
                        tmp2 = tmp2.next();
                    }
                     
                    // End of list
                    if (tmp2.next() == null) {
                        tmp1 = new LLNode(array[i], null);
                        tmp2.setNext(tmp1);                      
                    }
                     
                    // Insert element to the list
                    else {
                        tmp1 = new LLNode(array[i], tmp2.next());
                        tmp2.setNext(tmp1);                      
                    }
                }
            }
        }
        
        // Transfer Bucket elements into array
        j = lowindex;
        if (!reversed) { 
            // Not reversed
            for (i = 0; i < buckets; i++) {
                while (node[i].next() != null) {
                    array[j] = node[i].next().val();
                    node[i] = node[i].next();
                    j++;
                }
            }       
        }
         
        else { 
            // Reversed
            for (i = buckets - 1; i >= 0; i--) {
                while (node[i].next() != null) {
                    array[j] = node[i].next().val();
                    node[i] = node[i].next();
                    j++;
                }
            }   
        }
    }
    
    @Override
    public void heapSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        Comparable temp;
        int sib, maxIndex, parent;
        
        if (highindex == 1){
            return;
        }
        
        for (int i = highindex; i > lowindex; i = i - 2) {
            sib = i - 1;
            maxIndex = maxIndex(array, i, sib, reversed);
            parent = (maxIndex - 1) / 2;
            maxIndex = maxIndex(array, maxIndex, parent, reversed);
            
            if (maxIndex != parent) { 
                // Switch parent and sibling
                temp = array[parent];
                array[parent] = array[maxIndex];
                array[maxIndex] = temp;             
            }
        }           
        
        // Swap highest element to the end
        temp = array[lowindex];
        array[lowindex] = array[highindex];
        array[highindex] = temp;
        
        heapSort(array, lowindex, highindex - 1, reversed);     
    }   
    
    private int maxIndex(Comparable[] array, int i, int sib, boolean reversed) {
            
            if (!reversed) { 
                // Finds max
                if (array[i].compareTo(array[sib]) < 0) {
                    return sib;
                }
                else {
                    return i;
                }
            }
            else { 
                // Finds min
                if (array[i].compareTo(array[sib]) < 0) {
                    return i;
                }
                else {
                    return sib;
                }     
            }
        }
    
    @Override
    public void quicksort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        int length = highindex - lowindex + 1;
        Comparable first = array[lowindex];
        Comparable middle = array[(highindex + lowindex) / 2];
        Comparable last = array[highindex];
        
        if (length <= 1) {
            return;
        }
        
        else if (length == 2) {
            Comparable temp;
            if (!reversed) {
                // Not reversed
                if (array[lowindex].compareTo(array[highindex]) > 0) { 
                    temp = array[lowindex];
                    array[lowindex] = array[highindex];
                    array[highindex] = temp;
                }           
            }
            
            else {
                // Reversed
                if (array[lowindex].compareTo(array[highindex]) < 0) { 
                    temp = array[lowindex];
                    array[lowindex] = array[highindex];
                    array[highindex] = temp;
                }           
            }
        }
    
        else {
            Comparable<Integer> pivot = getPivot(first, middle, last);
            int pivotIndex = partition(array, pivot, lowindex + 1, highindex, reversed);
            quicksort(array, lowindex, pivotIndex - 1, reversed);
            quicksort(array, pivotIndex, highindex, reversed);
        }
//      System.out.println(Arrays.toString(array));
    }
    
    private Comparable<Integer> getPivot(Comparable first, Comparable middle, Comparable last) {
       Comparable<Integer> temp, pivot = 0;
       Comparable[] array = {first, middle, last};
            
       for (int i = 0; i < 2; i++) {
           if (array[i].compareTo(array[i + 1]) > 0) {
              temp = array[i];
              array[i] = array[i + 1];
              array[i + 1] = temp;
           }
                
            if (i == 1) {
                if (array[i].compareTo(array[i - 1]) < 0) {
                    pivot = array[i - 1];
                }
                else {
                    pivot = array[i];
                }
            }
       }
       return pivot;
    }
    
    private int partition(Comparable[] array, Comparable<Integer> pivot, int lowindex, int highindex, boolean reversed) {
        Comparable<Integer> temp;
        int i = lowindex;
        int j = highindex;
        
        // Swaps pivot element
        if (array[lowindex - 1].compareTo(pivot) != 0) {
            for (int k = lowindex; k <= highindex; k++) {
                if (array[k].compareTo(pivot) == 0) {
                    temp = array[lowindex - 1];
                    array[lowindex - 1] = pivot;
                    array[k] = temp;
                }
            }
        }
        
        if (!reversed) {
            // Not reversed
            while (i < j) {
                if (array[i].compareTo(pivot) > 0) {
                    while (array[j].compareTo(pivot) > 0) {
                        j--;
                    }
                    
                    if (i <= j) {
                        temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
    
                    else {
                        temp = array[j];
                        array[j] = pivot;
                        array[lowindex - 1] = temp;
                    }
                }
                i++;
            }
        }
        
        else { 
            // Reversed
            while (i < j) {
                if (array[i].compareTo(pivot) < 0) {
                    while (array[j].compareTo(pivot) < 0) {
                        j--;
                    }
                    
                    if (i <= j) {
                        temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                    else {
                        temp = array[j];
                        array[j] = pivot;
                        array[lowindex - 1] = temp;
                    }
                }               
                i++;
            }
        }
        return j;
    }

    @Override
    public void radixSort(int [] array, int lowindex, int highindex, boolean reversed) {
        int k = array.length; // number of digits
        int r = highindex - lowindex + 1; // base of each digit(number of elements)
        int i, j, rtok;
        int[] tmp = new int[r];
        int[] count = new int[r];
        
        for(i=0, rtok = 1; i<k; i++, rtok *= r) {
            //Not reversed
            for (j=0; j<r; j++){
                count[j] = 0;    
            }
            for (j=0; j<array.length; j++){
                if (rtok != 0) {
                    count[Math.abs((array[j]/rtok)%r)]++;
                }
            }
//           System.out.println(Arrays.toString(count));
            for (j=1; j<k; j++){
                count[j] = count[j-1] + count[j];
            }
            for (j=array.length-1; j>=0; j--){
                if (rtok != 0) {    
                    tmp[--count[Math.abs((array[j]/rtok)%r)]] = array[j];
                }
            }
//            System.out.println(Arrays.toString(tmp));
            for(j=0;  j<array.length; j++){
               array[j] = tmp[j];
            }
        }
        if (reversed){
            // reversed
            for(j=0;  j<array.length; j++){
                array[j] = tmp[array.length-j-1];
            }
        }
    }
    
    @Override
    public LLNode selectionSortLL(LLNode list, boolean reversed) {
        LLNode A = mySelectionSortLL(list, reversed);
        return A;
        
    }
    
    public LLNode mySelectionSortLL(LLNode list, boolean reversed) {
        LLNode i, j, head, tmp;
        i = j = head = list;
        
        if (!reversed){
            // Not reversed
            
        }
        else {
            // Reversed
            
        }
        return list;
        
    }
    
    @Override
    public LLNode mergeSortLL(LLNode list, boolean reversed) {
        LLNode A = myMergeSortLL(list, reversed);
        return A;
    }
    
    public LLNode myMergeSortLL(LLNode list, boolean reversed) {
        LLNode i, j, head, tmp;
        i = j = head = list;
        
        if (!reversed){
            // Not reversed
            
        }
        else {
            // Reversed
            
        }
        return list;
    }
    
    @Override
    public LLNode insertionSortLL(LLNode list, boolean reversed) {
        LLNode A = myInsertionSortLL(list, reversed);
        return A;
    }
    private LLNode myInsertionSortLL(LLNode list, boolean reversed) {
        
        LLNode i, j, head, tmp;
        i = j = head = list;
        
        if (!reversed) { 
            // Not reversed
            if (i.next() != null) {
                while (i.next() != null) {
                    if (i.elem().compareTo(i.next().elem()) > 0) {
                        tmp = i.next();
                        i.setNext(tmp.next());
    
                        j = head;
                        while (j.next() != null) {
                            if (j == head) {
                                if (j.elem().compareTo(tmp.elem()) > 0) {
                                    tmp.setNext(j);
                                    head = tmp;
                                    i = head;
                                    break;
                                }
    
                                else {
                                    if (j.next().elem().compareTo(tmp.elem()) > 0) {
                                        j.next().setNext(tmp.next());
                                        j.setNext(tmp);
                                        tmp.setNext(j.next());
                                        i = tmp;
                                        break;
                                    }
    
                                    else {
                                        if (j.next().elem()
                                                .compareTo(tmp.elem()) < 0) {
                                            j.next().setNext(tmp.next());
                                            j.setNext(tmp);
                                            tmp.setNext(j.next());
                                            i = tmp;
                                            break;
                                        }
                                    }
                                }
                            }
    
                            else {
                                if (j.next().elem().compareTo(tmp.elem()) > 0) {
                                    j.next().setNext(tmp.next());
                                    j.setNext(tmp);
                                    tmp.setNext(j.next());
                                    i = tmp;
                                    break;
                                }
                            }
                            j = j.next();
                        }
                    }
                    if (i.next() != null)
                        i = i.next();
                }
                list = head;
            }
        }
        
        else { 
            // Reversed
            while (i.next() != null) {
                if (i.elem().compareTo(i.next().elem()) < 0) {
                    tmp = i.next();
                    i.setNext(tmp.next());
    
                    j = head;
                    while (j.next() != null) {
                        if (j == head) {
                            if (j.elem().compareTo(tmp.elem()) < 0) {
                                tmp.setNext(j);
                                head = tmp;
                                i = head;
                                break;
                            }
                            
                            else {
                                if (j.next().elem().compareTo(tmp.elem()) < 0) {
                                    j.next().setNext(tmp.next());
                                    j.setNext(tmp);
                                    tmp.setNext(j.next());
                                    i = tmp;
                                    break;
                                }
    
                                else {
                                    if (j.next().elem().compareTo(tmp.elem()) > 0) {
                                        j.next().setNext(tmp.next());
                                        j.setNext(tmp);
                                        tmp.setNext(j.next());     
                                        i = tmp;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        else {
                            if (j.next().elem().compareTo(tmp.elem()) < 0) {
                                j.next().setNext(tmp.next());
                                j.setNext(tmp);
                                tmp.setNext(j.next());     
                                i = tmp;
                                break;
                            }
                        }
                        j = j.next();
                    }
                }
                if (i.next() != null)
                    i = i.next();
            }
            list = head;            
        }
        return list;
    }
    
    @Override
    public void optimizedQuicksort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        int length = highindex - lowindex + 1;
        Comparable first = array[lowindex];
        Comparable middle = array[(highindex + lowindex) / 2];
        Comparable last = array[highindex];
        
        if (length <= 100) {
            Sort sort = new Sort();
            sort.insertionSort(array, lowindex, highindex, reversed);
        }
    
        else {
            Comparable<Integer> pivot = getPivot(first, middle, last);
            int pivotIndex = partition(array, pivot, lowindex + 1, highindex, reversed);
            optimizedQuicksort(array, lowindex, pivotIndex - 1, reversed);
            optimizedQuicksort(array, pivotIndex, highindex, reversed);
        }
    }

}
