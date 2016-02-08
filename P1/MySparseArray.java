public class MySparseArray implements SparseArray {

    Object defaultValue;
    ListNode firstRowNode;
    ListNode firstColNode;

    public MySparseArray(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

    class MyElemIterator extends ElemIterator {
        MyMatrixElem currElem; 
        boolean RowCheck;
        int index = 0;
        ListNode currNode;

        MyElemIterator(ListNode currNode, boolean RowCheck) {
            this.RowCheck = RowCheck;
            this.currNode = currNode;
        }

        @Override
        public boolean iteratingRow() {
            return RowCheck;
        }

        @Override
        public boolean iteratingCol() {
            return !RowCheck;
        }

        @Override
        public int nonIteratingIndex() {
            return index;
        }

        @Override
        public MatrixElem next() {
            if(RowCheck) {
                currElem = currNode.getElem();
                index = currElem.rowIndex();
                //currNode = currNode.getNext();
            } else {
                currElem = currNode.getElem();
                index = currElem.columnIndex();
                //currNode = currNode.getNext();
            }
            return currElem;
        }
        @Override
        public boolean hasNext() {
            return currElem != null;
        }
    }
    
    @Override
    public RowIterator iterateRows() {
        class MyRowIterator extends RowIterator  {
            ListNode currRow;
            MyElemIterator tmpIter;
            MyRowIterator() {
                currRow = firstRowNode;
            }
            
            @Override
            public ElemIterator next() {
                if(currRow == null) {
                    return null;
                }
                tmpIter = new MyElemIterator(currRow, true);
                currRow = currRow.getNext();
                return tmpIter;
            }
            
            @Override
            public boolean hasNext() {
                return currRow != null;
            }
        }
        return new MyRowIterator();
    }

    @Override
    public ColumnIterator iterateColumns() {
        class MyColumnIterator extends ColumnIterator{
            ListNode currCol;
            MyElemIterator tmpIter;
            MyColumnIterator() {
                currCol = firstColNode;
            }
            
            @Override
            public ElemIterator next() {
                if(currCol == null) {
                    return null;
                }
                tmpIter = new MyElemIterator(currCol, false);
                currCol = currCol.getNext();
                return tmpIter;
            }
            
            @Override
            public boolean hasNext() {
                return currCol != null;
            }
        }
        return new MyColumnIterator();
    }

    @Override
    public Object elementAt(int row, int col) {
        //walk through rows
        ListNode tmpRowNode = firstRowNode;
        while(tmpRowNode != null && tmpRowNode.getIndex() < row) {
            tmpRowNode = tmpRowNode.getNext();
        }
        if(tmpRowNode == null || tmpRowNode.getIndex() != row) {
            return defaultValue;
        }
        //walk through cols
        MyMatrixElem tmpElem = tmpRowNode.getElem();

        while(tmpElem != null && tmpElem.columnIndex() < col) {
            tmpElem = tmpElem.nextColNode();
        }
        if(tmpElem == null || tmpElem.columnIndex() != col) {
            return defaultValue;
        }
        return tmpElem.value();
    }

    @Override
    public void setValue(int row, int col, Object value) {
        if(elementAt(row, col) == value) {
            return;
        }
        if (value.equals(defaultValue)) {
            removeOldValue(row, col);           
        } else {
            addNewValue(row, col, value);           
        }
    }   

    private void removeOldValue(int row, int col) {
        ListNode currRowNode = firstRowNode;
        ListNode prevRowNode = null;
        MyMatrixElem preRowElem = null;
        
        //From row
        while(currRowNode.getIndex() < row) {
            prevRowNode = currRowNode;
            currRowNode = currRowNode.getNext();
        }
        
        MyMatrixElem currRowElem = currRowNode.getElem();
        
        while(currRowElem.columnIndex() < col) {
            preRowElem = currRowElem;
            currRowElem = currRowElem.nextColNode();
        }

        if(preRowElem != null) {
            preRowElem.setNextCol(currRowElem.nextColNode());
        } else if(currRowElem.nextColNode() != null) {
            currRowNode.setMatrixElem(currRowElem.nextColNode());
        } else if(prevRowNode != null) {
            prevRowNode.setNextRowNode(currRowNode.getNext());
        } else {
            firstRowNode = currRowNode.getNext();
        }            
    }

    private ListNode CreateRow(int row) {
        ListNode currRowNode = firstRowNode;
        ListNode preRowNode = null;

        while(currRowNode != null && currRowNode.getIndex() < row) {
            preRowNode = currRowNode;
            currRowNode = currRowNode.getNext();
        }
        
        if(currRowNode != null && currRowNode.getIndex() == row) {
            return currRowNode;
        }
        
        ListNode newRowNode = new ListNode(row);
        if(preRowNode != null) {
            newRowNode.setNextRowNode(preRowNode.getNext());
            preRowNode.setNextRowNode(newRowNode);
        } else { 
            newRowNode.setNextRowNode(firstRowNode);
            firstRowNode = newRowNode;
        }
        return newRowNode;
    }

    private ListNode CreateCol(int col) {
        ListNode currColNode = firstColNode;
        ListNode preColNode = null;
        
        while(currColNode != null && currColNode.getIndex() < col) {
            preColNode = currColNode;
            currColNode = currColNode.getNext();
        }
        
        if(currColNode != null && currColNode.getIndex() == col) {
            return currColNode;
        }
        
        ListNode newColNode = new ListNode(col);
        if(preColNode != null) { 
            newColNode.setNextRowNode(preColNode.getNext());
            preColNode.setNextRowNode(newColNode);
        } else{ 
            newColNode.setNextRowNode(firstColNode);
            firstColNode = newColNode;
        }
        return newColNode;
    }

    private void addNewValue(int row, int col, Object value) {
        MyMatrixElem newElem;
        ListNode rowNode = CreateRow(row);
        MyMatrixElem currElem = rowNode.getElem();
        MyMatrixElem preElem = null;

        while(currElem != null && currElem.columnIndex() < col) {
            preElem = currElem;
            currElem = currElem.nextColNode();
        } 
        
        if(currElem != null && currElem.columnIndex() == col) {
            currElem.setValue(value);
            return;
        }
        newElem = new MyMatrixElem(row, col, value);
        newElem.setNextCol(currElem);
        
        if(preElem != null) {
            preElem.setNextCol(newElem);
        } else { 
            rowNode.setMatrixElem(newElem);
        }
        
        ListNode colNode = CreateCol(col);

        currElem = colNode.getElem();
        preElem = null;

        while(currElem != null && currElem.columnIndex() < row) {
            preElem = currElem;
            currElem = currElem.nextRowNode();
        } 
        
        if(currElem != null && currElem.rowIndex() == row) {
            currElem.setValue(value);
            return;
        }
        newElem.setNextRow(currElem);
        
        if(preElem != null) {
            preElem.setNextRow(newElem);
        } else { 
            colNode.setMatrixElem(newElem);
        }
    }
}