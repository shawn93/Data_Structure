abstract class ElemIterator implements java.util.Iterator<MatrixElem>
{
    public abstract boolean iteratingRow();
    public abstract boolean iteratingCol();
    public abstract int nonIteratingIndex();
    @Override
    public abstract MatrixElem next();
    @Override
    public abstract boolean hasNext();
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}