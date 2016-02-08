import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Life {
    public static void main(String[] args) {
        
        MySparseArray currGen = new MySparseArray(0);
        MySparseArray numOfNeighbors = new MySparseArray(0);
        MySparseArray nextGen = new MySparseArray(0);
        int numOfGen = Integer.parseInt(args[2]);
        
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(args[0]));
            try {
                String Line = null;
                while((Line = bReader.readLine()) != null) {
                    String[] tmp = Line.split("\\W");
                    currGen.setValue(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        
        for(int i = 0; i < numOfGen; i++) {
            RowIterator row = currGen.iterateRows();
            
            while (row.hasNext()) {
                ElemIterator elmItr = row.next();
                
                while (elmItr.hasNext()) {
                    MatrixElem maxtrixElem = elmItr.next();
                  
                    if(!currGen.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() - 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() - 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() - 1) + 1);
                    }
                    if(!currGen.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() + 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() + 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex() + 1) + 1);
                    }
                    if(!currGen.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() + 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() + 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() + 1) + 1);
                    }
                    if(!currGen.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() + 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() + 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() + 1) + 1);
                    }
                    if (!currGen.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex()).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex()) + 1);
                    }
                    if (!currGen.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex()).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex()) + 1);
                    }
                    if (!currGen.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() - 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() - 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() + 1, maxtrixElem.columnIndex() - 1) + 1);
                    }
                    if (!currGen.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() - 1).equals(0)) {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()) + 1);
                    } else {
                        numOfNeighbors.setValue(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() - 1, (Integer) numOfNeighbors.elementAt(maxtrixElem.rowIndex() - 1, maxtrixElem.columnIndex() - 1) + 1);
                    }
                }       
            }
                    
            row = numOfNeighbors.iterateRows();
            
            while (row.hasNext()) {
            	
               ElemIterator elmItr = row.next();
               
               while (elmItr.hasNext()) {
            	   
                  MatrixElem maxtrixElem = elmItr.next();
                  
                  if (currGen.elementAt(maxtrixElem.rowIndex(), maxtrixElem.columnIndex()).equals(0)) {
                      if (maxtrixElem.value().equals(3)) {
                          nextGen.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), 1);
                      }
                  } else {
                      if (maxtrixElem.value().equals(2) || maxtrixElem.value().equals(3)) {
                          nextGen.setValue(maxtrixElem.rowIndex(), maxtrixElem.columnIndex(), 1);
                      }
                  }
               }
            }
            currGen = nextGen;
        }
        
        PrintWriter writer = null;
        
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        RowIterator row = currGen.iterateRows();
        
        while (row.hasNext()) {
        	
           ElemIterator elmItr = row.next();
           
           while (elmItr.hasNext()) {
              MatrixElem maxtrixElem = elmItr.next();
              writer.println(maxtrixElem.rowIndex() + ", " +maxtrixElem.columnIndex());
           }
        }
        
        writer.flush();
        writer.close();     
    }
}
