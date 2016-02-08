import java.util.ArrayList;

class BinarySearchTree<T extends Comparable <T>> {

   private class BSTNode {
       public T data;
       public BSTNode left;
       public BSTNode right;

       BSTNode(T newdata) 
       {
          data = newdata;
       }
   }

   private BSTNode root;

   public void Insert(T elem) 
   {
      root = Insert(root, elem);
   }

   public boolean Find(T elem) 
   {
      return Find(root,elem);
   }

   public void Delete(T elem) 
   {
      root = Delete(root, elem);
   }

   public T ElementAt(int i) 
   {
        return ElementAt(root, i);
   }

    public T ElementAt(BSTNode tree, int i) {
        if(tree == null) {
            return null;            
        }
        
        int leftRoot = Count(tree.left) + 1;

        if(i == leftRoot - 1) {
            return tree.data;
        }

        if (i < leftRoot) {
            return ElementAt(tree.left, i);         
        }
        return ElementAt(tree.right, i - leftRoot);

    }   
    
    public int Count(BSTNode tree){
        if (tree == null) {
            return 0;
        }
        return Count(tree.left) + Count(tree.right) + 1;
    }

    public void ToArray(BSTNode tree, ArrayList<T> array){
        if (tree != null) {
            ToArray(tree.left, array);
            array.add(tree.data);
            ToArray(tree.right, array);
        }
    }
    
    public int NumLarger(T elem) 
   {
        ArrayList<T> tmp = new ArrayList<T>(); 
        ToArray(root, tmp);
        int num = 0;
        for (T e : tmp) {
            if (e.compareTo(elem) > 0) {
                num++;
            }
        }
    return num;
   }

    private void BalanceBST(ArrayList<T> array, int first, int last) {
        int size = last - first;
        
        if(size == 0) {
            Insert(array.get(first));
            return;
        }
        
        if(size == 1) {
            Insert(array.get(first));
            Insert(array.get(last));
            return;
        }

        int mid = first + size/2;
        Insert(array.get(mid));
        BalanceBST(array, first, mid - 1);
        BalanceBST(array, mid + 1, last);
    }    
    
   public void Balance()
   {
        ArrayList<T> tmp = new ArrayList<T>();
        ToArray(root, tmp);
        root = null;
        BalanceBST(tmp, 0, tmp.size()-1);
   }

   public void Print() {
       Print(root);
   }

   public int Height()
   {
       return Height(root);
   }
   

   private int Height(BSTNode tree)
   {
      if (tree == null)
      {
         return 0;
      }
      return 1 + Math.max(Height(tree.left), Height(tree.right));
   }

   private boolean Find(BSTNode tree, T elem) 
   {
      if (tree == null)
      {
         return false;
      }
      if (elem.compareTo(tree.data) == 0)
      { 
         return true;
      }
      if (elem.compareTo(tree.data) < 0)
      {
         return Find(tree.left, elem);
      }
      else
      {
         return Find(tree.right, elem);
      }
   }

   private T Minimum(BSTNode tree) 
   {
      if (tree == null)
      {
         return null;
      }
      if (tree.left == null)
      {
         return tree.data;
      }
      else
      {
         return Minimum(tree.left);
      }
   }

   private void Print(BSTNode tree) 
   {
      if (tree != null) 
      {
         Print(tree.left);
         System.out.println(tree.data);
         Print(tree.right);
      }
   }

   private BSTNode Insert(BSTNode tree, T elem) 
   {
    if (tree == null) 
        {
        return new BSTNode(elem);
    }
    if (elem.compareTo(tree.data) < 0) 
        {
        tree.left = Insert(tree.left, elem);
        return tree;
    } 
        else  {
        tree.right = Insert(tree.right, elem);
        return tree;
        }
    }


   private BSTNode Delete(BSTNode tree, T elem) 
   {
      if (tree == null) 
      {
         return null;
      }
      if (tree.data.compareTo(elem) == 0) 
      {
         if (tree.left == null)
         {
        return tree.right;
         }
         else if (tree.right == null)
         {
       return tree.left;
         }
         else 
         {
        if (tree.right.left == null) 
            {
               tree.data = tree.right.data;
               tree.right = tree.right.right;
               return tree;
            } 
            else 
            {         
               tree.data = RemoveSmallest(tree.right);
               return tree;
            }
         }
      } 
      else  if (elem.compareTo(tree.data) < 0) 
      {
         tree.left = Delete(tree.left, elem);
         return tree;
      } 
      else 
      {
         tree.right = Delete(tree.right, elem);
         return tree;
     }
   }  
  
   int Size(BSTNode tree)
   {
       if (tree == null)
       {
           return 0;
       }
       return 1 + Size(tree.left) + Size(tree.right);
   }
 
   T RemoveSmallest(BSTNode tree) 
   {
      if (tree.left.left == null) 
      {
         T smallest = tree.left.data;
         tree.left = tree.left.right;
         return smallest;
      } 
      else 
      {
         return RemoveSmallest(tree.left);
      }
   }
    public static void main(String args[])

    {
    BinarySearchTree<Integer> t= new BinarySearchTree<Integer>();
    for (int x = 0; x < 31; x++)
        t.Insert(new Integer(x));
    System.out.println(t.ElementAt(new Integer(5)));
    System.out.println(t.NumLarger(new Integer(8)));
    System.out.println(t.Height());
    t.Balance();
    System.out.println(t.ElementAt(new Integer(5)));
    System.out.println(t.NumLarger(new Integer(8)));
    System.out.println(t.Height());
    }

}