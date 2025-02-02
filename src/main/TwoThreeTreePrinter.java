package main;
import data_structures.*;
import data_structures.two_three_tree.TwoThreeLeaf;
import data_structures.two_three_tree.TwoThreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class TwoThreeTreePrinter<T extends Comparable<T>, E> {

    public static <T extends Comparable<T>, E> void printTree(TwoThreeNode<T, E> root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        Queue<NodeWithParent<T, E>> queue = new LinkedList<>();
        queue.add(new NodeWithParent<>(root, null));  // Root has no parent
        int leafcount = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // Nodes at the current level

            for (int i = 0; i < levelSize; i++) {
                NodeWithParent<T, E> nodeWrapper = queue.poll();
                TwoThreeNode<T, E> node = nodeWrapper.node;
                TwoThreeNode<T, E> parent = nodeWrapper.parent;

                if (node == null) {
                    System.out.print("[NULL] ");
                    continue;
                }

                // Print node contents with parent info
                System.out.print("[key: " + node.getKey());

                // Print parent info if applicable
                if (parent != null) {
                    System.out.print(", parent is " + parent.getKey());
                }

                System.out.print("] ");

                // Enqueue children with their parent
                if (!(node instanceof TwoThreeLeaf)) {
                    queue.add(new NodeWithParent<>(node.getLeft(), node));
                    queue.add(new NodeWithParent<>(node.getMiddle(), node));
                    if (node.getRight() != null) { // Right child exists in 3-node case
                        queue.add(new NodeWithParent<>(node.getRight(), node));
                    }
                } else {
                    leafcount++;
                }
            }
            System.out.println();  // Newline for next level
        }
        System.out.println("Total leaf nodes: " + leafcount + "\n");

    }

    // Helper class to track a node and its parent
    private static class NodeWithParent<T extends Comparable<T>, E> {
        TwoThreeNode<T, E> node;
        TwoThreeNode<T, E> parent;

        NodeWithParent(TwoThreeNode<T, E> node, TwoThreeNode<T, E> parent) {
            this.node = node;
            this.parent = parent;
        }
    }
}
