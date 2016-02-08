
public class ListNode
{    
    private int RowColNmber;
    private ListNode next;
    private MyMatrixElem MatrixElem;

    public ListNode(int RowColNmber)
    {
        this.RowColNmber = RowColNmber;
    }
    
    public int getIndex(){
        return RowColNmber;
    } 
    
    public ListNode getNext(){
        return next;
    }  
    
    public MyMatrixElem getElem(){
        return MatrixElem;
    }

    public void setNextRowNode(ListNode nextRowNode) {
        this.next = nextRowNode;
    }
    
    public void setMatrixElem(MyMatrixElem MatrixElem){
        this.MatrixElem = MatrixElem;
    }

}