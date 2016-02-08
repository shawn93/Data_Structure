class LLNode
{

    public LLNode(Comparable elem, LLNode next)
    {
        _elem = elem;
        _next = next;
    }

    public LLNode(int val)
    {
        _val = val;
        _next = null;
    }
    
    public LLNode(int val, LLNode next)
    {
        _val = val;
        _next = next;
    }

    public int val() {
    	return _val;
    }
    
    public Comparable elem()
    {
        return _elem;
    }

    public LLNode next()
    {
        return _next;
    }

    public void setElem(Comparable elem)
    {
        _elem = elem;
    }

    public void setNext(LLNode next)
    {
        _next = next;
    }

    private Comparable _elem;
    private LLNode _next;
    private int _val;

}