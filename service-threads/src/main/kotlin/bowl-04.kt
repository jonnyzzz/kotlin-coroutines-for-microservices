package org.jonnyzzz.threads

import com.sun.source.tree.Tree

abstract class TreeNode


class LeafNode(val name: String) : TreeNode()
class BinaryNode(val left: TreeNode, val right: TreeNode) : TreeNode()


fun printTree(node: TreeNode) {
  if (node is LeafNode) {
    println("Leaf(${node.name})")
  }

  if (node is BinaryNode) {
    println("Binary node:")
    println(printTree(node.left))
    println(printTree(node.right))
  }
}




/// use `buildString` extension function
/// add nullability
/// use sealed classes
/// convert to `when` expression

