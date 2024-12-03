import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HW04 {
    public static void main(String[] args) {

          // Tree - BSTNode test
          System.out.println("Testing BSTNode:");
          BSTNode<Integer> bstNode = new BSTNode<>();
          bstNode.insert(10);
          bstNode.insert(5);
          bstNode.insert(20);
          bstNode.insert(15);
          bstNode.insert(30);
  
          List<Integer> inorderList = new ArrayList<>();
          bstNode.inorder(inorderList);
          System.out.println("Inorder traversal: " + inorderList);
  
          List<Integer> levelOrderList = new ArrayList<>();
          bstNode.levelorder(levelOrderList);
          System.out.println("Level order traversal: " + levelOrderList);
  
          System.out.println("Contains 15? " + bstNode.contains(15));
          System.out.println("Removing 20...");
          bstNode.remove(20);
  
          List<Integer> updatedInorderList = new ArrayList<>();
          bstNode.inorder(updatedInorderList);
          System.out.println("Updated Inorder traversal: " + updatedInorderList);
  
          // Tree - BSTArray test
          System.out.println("\nTesting BSTArray:");
          BSTArray<Integer> bstArray = new BSTArray<>(10);
          bstArray.insert(10);
          bstArray.insert(5);
          bstArray.insert(20);
          bstArray.insert(15);
          bstArray.insert(30);
  
          List<Integer> arrayInorderList = new ArrayList<>();
          bstArray.inorder(arrayInorderList);
          System.out.println("Inorder traversal: " + arrayInorderList);
  
          System.out.println("Contains 5? " + bstArray.contains(5));
          System.out.println("Removing 15...");
          bstArray.remove(15);
  
          List<Integer> updatedArrayInorderList = new ArrayList<>();
          bstArray.inorder(updatedArrayInorderList);
          System.out.println("Updated Inorder traversal: " + updatedArrayInorderList);
  
          // Priority Queue - SortedPriorityQueue test
          System.out.println("\nTesting SortedPriorityQueue:");
          SortedPriorityQueue<Integer, String> sortedPQ = new SortedPriorityQueue<>();
          sortedPQ.insert(5, "Task A");
          sortedPQ.insert(3, "Task B");
          sortedPQ.insert(8, "Task C");
          sortedPQ.insert(1, "Task D");
  
          System.out.println("Peek: " + sortedPQ.peek());
          System.out.println("Removing: " + sortedPQ.remove());
          System.out.println("Peek after removal: " + sortedPQ.peek());
          System.out.println("Queue size: " + sortedPQ.size());
  
          // Priority Queue - UnsortedPriorityQueue test
          System.out.println("\nTesting UnsortedPriorityQueue:");
          UnsortedPriorityQueue<Integer, String> unsortedPQ = new UnsortedPriorityQueue<>();
          unsortedPQ.insert(5, "Task A");
          unsortedPQ.insert(3, "Task B");
          unsortedPQ.insert(8, "Task C");
          unsortedPQ.insert(1, "Task D");
  
          System.out.println("Peek: " + unsortedPQ.peek());
          System.out.println("Removing: " + unsortedPQ.remove());
          System.out.println("Peek after removal: " + unsortedPQ.peek());
          System.out.println("Queue size: " + unsortedPQ.size());

    }
}

interface Tree<T> {
    int size();
    boolean isEmpty();
    void insert(T element);
    boolean remove(T element);
    boolean contains(T element);
    void levelorder(List<T> list);
    void inorder(List<T> list);
    void preorder(List<T> list);
    void postorder(List<T> list);
}


class TreeNode<T> {
    T element;
    TreeNode<T> left, right;

    public TreeNode(T element) {
        this.element = element;
    }
}

/*
 * Node-based BST implementation
 */
class BSTNode <T extends Comparable<? super T>> implements Tree<T> {
    private TreeNode<T> root;
    private int size = 0;

    public TreeNode<T> getRoot() {
        // Convenience method for me
        return root;
    }
    /*
     * Constructor()
     */

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void insert(T element) {
        root = insertRec(root,element);
        size++;
    }

    private TreeNode<T> insertRec(TreeNode<T> root, T element) {
        if(root == null) {
            root = new TreeNode<>(element);
            return root;
        }

        if(element.compareTo(root.element) < 0){
            root.left = insertRec(root.left, element);
        }else if(element.compareTo(root.element) > 0){
            root.right = insertRec(root.right, element);
        }

        return root;
    }

    @Override
    public boolean remove(T element) {
        if(contains(element)){
            root = removeRec(root,element);
            size--;
            return false;
        }

        return false;
    }

    private TreeNode<T> removeRec(TreeNode<T> root, T element) {
        if(root == null) return root;

        if(element.compareTo(root.element) < 0){
            root.left = removeRec(root.left, element);
        }else if(element.compareTo(root.element) > 0){
            root.right = removeRec(root.right, element);
        }else {
            if(root.left == null){
                return root.right;
            }else if(root.right == null){
                return root.left;
            }

            root.element = minValue(root.right);
            root.right = removeRec(root.right, root.element);
        }

        return root;
    }

    private T minValue(TreeNode<T> root) {
        T min = root.element;

        while(root.left != null) {
            min = root.left.element;
            root = root.left;
        }

        return min;
    }

    @Override
    public boolean contains(T element) {
        return containsRec(root,element);
    }

    private boolean containsRec(TreeNode<T> root, T element) {
        if(root == null)
            return false;

        if(element.compareTo(root.element) < 0){
            return containsRec(root.left, element);
        }else if(element.compareTo(root.element ) > 0){
            return containsRec(root.right, element);
        }

        return true;
    }

    @Override
    public void levelorder(List<T> list) {
        if(root == null) return;

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            TreeNode<T> node = queue.poll();
            list.add(node.element);

            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);

        }
    }

    @Override
    public void inorder(List<T> list) {
        inorderRec(root,list);
    }

    private void inorderRec(TreeNode<T> root , List<T> list){
        if(root != null){
            inorderRec(root.left, list);
            list.add(root.element);
            inorderRec(root.right, list);
        }
    }

    @Override
    public void preorder(List<T> list) {
        preorderRec(root , list);
    }

    private void preorderRec(TreeNode<T> root , List<T> list) {
        if(root != null) {
            list.add(root.element);
            preorderRec(root.left, list);
            preorderRec(root.right, list);
        }
    }


    @Override
    public void postorder(List<T> list) {
        postorderRec(root , list);
    }

    private void postorderRec(TreeNode<T> root , List<T> list){
        if(root != null) {
            postorderRec(root.left, list);
            postorderRec(root.right, list);
            list.add(root.element);
        }
    }



}

/*
 * Array-based BST implementation
 */
class BSTArray <T extends Comparable<? super T>> implements Tree<T> {
    private T[] array;
    private int size;

    public T[] getArray() {
        return array;
    }

    public BSTArray(int capacity) {
        array = (T[]) new Comparable[capacity];
        size = 0;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void insert(T element) {
        if(size == array.length) throw new IllegalStateException("Array is full");
        array[size++] = element;
        Arrays.sort(array,0,size);
    }

    @Override
    public boolean remove(T element) {
        for(int i = 0; i < size; i++) {
            if(array[i].compareTo(element) == 0){
                System.arraycopy(array, i+1, array, i, size-i-1);
                size--;
                return false;
            }
        }

        return false;
    }


    @Override
    public boolean contains(T element) {
        for(int i= 0; i < size; i++){
            if(array[i].compareTo(element) == 0){
                return true;
            }
        }

        return false;
    }

    @Override
    public void levelorder(List<T> list) {
        
    }

    @Override
    public void inorder(List<T> list) {
        Arrays.sort(array,0,size);
        list.addAll(Arrays.asList(array));
    }

    @Override
    public void preorder(List<T> list) {
        
    }

    @Override
    public void postorder(List<T> list) {
        
    }


    

}

class ListNode <T> {
    private T data;
    private ListNode<T> next;

    public ListNode(T data, ListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }
}

class Entry <K extends Comparable<? super K>, V> {
    /*
     * key-value data fields
     * Constructor(K, V)
     * getKey()
     * getValue()
     */

     private K key;
     private V value;

     public Entry(K key, V value){
        this.key = key;
        this.value = value;
     }

     public K getKey() {
         return key;
     }

     public V getValue() {
         return value;
     }

     @Override
     public String toString() {
         return "(" + key + "," + value + ")";
     }

}

interface IPriorityQueue <P extends Comparable<? super P>, E> {
    void insert(P priority, E element);
    E remove();
    E peek();
    boolean isEmpty();
    int size();
}

abstract class AbstractPriorityQueue <P extends Comparable<? super P>, E> implements IPriorityQueue<P, E> {

    protected ListNode<Entry<P, E>> head;

    public ListNode<Entry<P, E>> getHead() {
        return head;
    }

    public AbstractPriorityQueue() {
        head = null;
    }
}

/*
 * Sorted PQ implementation
 */
class SortedPriorityQueue <P extends Comparable<? super P>, E> extends AbstractPriorityQueue <P, E> {

    @Override
    public void insert(P priority, E element) {
        Entry<P,E> newEntry = new Entry<>(priority, element);

        if(getHead() == null || priority.compareTo(getHead().getData().getKey()) < 0){
            head = new ListNode<>(newEntry, head); 
        }else {
            ListNode<Entry<P,E>> current = getHead();
            while(current.getNext() != null && current.getNext().getData().getKey().compareTo(priority) <= 0) {
                current = current.getNext();
            }
            current.setNext(new ListNode<>(newEntry, current.getNext()));
        }
    }

    @Override
    public E remove() {
        if(getHead() == null) return null;

        E element = getHead().getData().getValue();
        head = getHead().getNext();
        return element;
    }

    

    @Override
    public E peek() {
        if(getHead() == null) return null;
        return getHead().getData().getValue();
    }

    @Override
    public boolean isEmpty() {
        return getHead() == null;
    }

    @Override
    public int size() {
        int size = 0;

            
        ListNode<Entry<P,E>> current = getHead();

        while(current != null){
            size++;
            current = current.getNext();
        }

        return size;
    }

}

/*
 * Unsorted PQ implementation
 */
class UnsortedPriorityQueue <P extends Comparable<? super P>, E> extends AbstractPriorityQueue <P, E> {

    @Override
    public void insert(P priority, E element) {
        Entry<P,E> newEntry = new Entry(priority, element);
        ListNode<Entry<P,E>> newNode = new ListNode<>(newEntry, getHead());
        head = newNode;
    }

    @Override
    public E remove() {
        if(getHead() == null) return null;

        ListNode<Entry<P,E>> current = getHead();
        ListNode<Entry<P,E>> maxNode = current;
        while(current != null){
           if(current.getData().getKey().compareTo(maxNode.getData().getKey()) > 0) {
            maxNode = current;
           }
           current = current.getNext();
        }

        E element = maxNode.getData().getValue();

        if(maxNode == getHead()){
            head = getHead().getNext();
        }else{
            current = getHead();
            while(current.getNext() != maxNode) {
                current = current.getNext();
            }
            current.setNext(maxNode.getNext());
        }

        return element;

    }

    @Override
    public E peek() {
        if(getHead() == null) return null;
        ListNode<Entry<P,E>> current = getHead();
        ListNode<Entry<P,E>> maxNode = current;
        while(current != null){
            if(current.getData().getKey().compareTo(maxNode.getData().getKey()) < 0){
                maxNode = current;
            }

            current = current.getNext();
        }

        return maxNode.getData().getValue();
    }

    @Override
    public boolean isEmpty() {
        return getHead() == null;
    }

    @Override
    public int size() {
        int size = 0;

        ListNode<Entry<P,E>> current = getHead();

        while (current != null) {
            size++;
            current = current.getNext();
        }

        return size;
    }

}