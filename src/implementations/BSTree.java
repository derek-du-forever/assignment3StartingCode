package implementations;

import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {

    private BSTreeNode<E> root;
    private int size;

    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    public BSTree(E element) {
        this.root = new BSTreeNode<>(element);
        this.size = 1;
    }

    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (root == null) {
            throw new NullPointerException("The tree is empty. No root node exists.");
        }
        return root;
    }

    @Override
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(BSTreeNode<E> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = calculateHeight(node.getLeft());
        int rightHeight = calculateHeight(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
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
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("The element being searched for cannot be null.");
        }
        return search(entry) != null;
    }

    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("The element being searched for cannot be null.");
        }
        return searchRecursive(root, entry);
    }

    private BSTreeNode<E> searchRecursive(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return null;
        }
        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchRecursive(node.getLeft(), entry);
        } else {
            return searchRecursive(node.getRight(), entry);
        }
    }

    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) {
            throw new NullPointerException("The element being added cannot be null.");
        }
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size = 1;
            return true;
        }
        addRecursive(root, newEntry);
        return true;
    }

    private boolean addRecursive(BSTreeNode<E> node, E newEntry) throws NullPointerException {
        if (node == null) {
            throw new NullPointerException("Comparison node cannot be null.");
        }
        int cmp = newEntry.compareTo(node.getElement());
        if (cmp == 0) {
            return false; // Duplicate value, do not add
        }
        if (cmp < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(newEntry));
                node.getLeft().setParent(node);
                size++;
            } else {
                addRecursive(node.getLeft(), newEntry);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(newEntry));
                node.getRight().setParent(node);
                size++;
            } else {
                addRecursive(node.getRight(), newEntry);
            }
        }
        return true;
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null;
        }
        if (size == 1) {
            BSTreeNode<E> temp = root;
            root = null;
            size--;
            return temp;
        }
        return removeMinRecursive(root);
    }

    private BSTreeNode<E> removeMinRecursive(BSTreeNode<E> node) throws NullPointerException {
        if (node == null) {
            throw new NullPointerException("Comparison node cannot be null.");
        }
        if (node.getLeft() == null) {
            BSTreeNode<E> parent = node.getParent();
            if (parent != null) {
                parent.setLeft(node.getRight());
                if (node.getRight() != null) {
                    node.getRight().setParent(parent);
                }
            } else {
                root = node.getRight();
                if (root != null) {
                    root.setParent(null);
                }
            }
            size--;
            return node;
        }
        return removeMinRecursive(node.getLeft());
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null;
        }
        if (size == 1) {
            BSTreeNode<E> temp = root;
            root = null;
            size--;
            return temp;
        }
        return removeMaxRecursive(root);
    }

    private BSTreeNode<E> removeMaxRecursive(BSTreeNode<E> node) throws NullPointerException {
        if (node == null) {
            throw new NullPointerException("Comparison node cannot be null.");
        }
        if (node.getRight() == null) {
            BSTreeNode<E> parent = node.getParent();
            if (parent != null) {
                parent.setRight(node.getLeft());
                if (node.getLeft() != null) {
                    node.getLeft().setParent(parent);
                }
            } else {
                root = node.getLeft();
                if (root != null) {
                    root.setParent(null);
                }
            }
            size--;
            return node;
        }
        return removeMaxRecursive(node.getRight());
    }

    @Override
    public Iterator<E> inorderIterator() {
        return new Iterator<E>() {

            java.util.Stack<BSTreeNode<E>> stack = new java.util.Stack<>();
            BSTreeNode<E> current = root;

            @Override
            public boolean hasNext() {
                return (current != null || !stack.isEmpty());
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();

                while (current != null) {
                    stack.push(current);
                    current = current.getLeft();
                }

                BSTreeNode<E> node = stack.pop();
                current = node.getRight();
                return node.getElement();
            }

        };
    }

    @Override
    public Iterator<E> preorderIterator() {
        return new Iterator<E>() {
            java.util.Stack<BSTreeNode<E>> stack = new java.util.Stack<>();

            {
                if (root != null) {
                    stack.push(root);
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();

                BSTreeNode<E> node = stack.pop();
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                return node.getElement();
            }

        };
    }

    @Override
    public Iterator<E> postorderIterator() {
        return new Iterator<E>() {

            java.util.Stack<BSTreeNode<E>> stack1 = new java.util.Stack<>();
            java.util.Stack<BSTreeNode<E>> stack2 = new java.util.Stack<>();

            {
                if (root != null) {
                    stack1.push(root);
                }
                while (!stack1.isEmpty()) {
                    BSTreeNode<E> node = stack1.pop();
                    stack2.push(node);
                    if (node.getLeft() != null) {
                        stack1.push(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        stack1.push(node.getRight());
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return !stack2.isEmpty();
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();

                BSTreeNode<E> node = stack2.pop();
                return node.getElement();
            }
        };
    }
}
