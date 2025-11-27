package implementations;

public class BSTreeNode<E> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    E element;
    BSTreeNode<E> left;
    BSTreeNode<E> right;
    BSTreeNode<E> parent;

    public BSTreeNode(E element) {
        this.element = element;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E value) {
        this.element = value;
    }

    public BSTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BSTreeNode<E> left) {
        this.left = left;
    }

    public BSTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BSTreeNode<E> right) {
        this.right = right;
    }

    public BSTreeNode<E> getParent() {
        return parent;
    }

    public void setParent(BSTreeNode<E> parent) {
        this.parent = parent;
    }
}
