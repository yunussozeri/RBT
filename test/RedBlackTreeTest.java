package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tree.RedBlackTree;

class RedBlackTreeTest {

	@Test
	void testRedBlackTree() {
		fail("Not yet implemented");
	}

	@Test
	void testInsert() {
		 RedBlackTree<Integer> tree = new RedBlackTree<>();
	        assertTrue(tree.isEmpty());

	        tree.insert(10);
	        assertFalse(tree.isEmpty());
	        assertTrue(tree.contains(10));

	        tree.insert(5);
	        assertTrue(tree.contains(5));

	        tree.insert(15);
	        assertTrue(tree.contains(15));

	        tree.insert(12);
	        assertTrue(tree.contains(12));
	}

	@Test
	void testContains() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.isEmpty());

        tree.insert(10);
        assertFalse(tree.isEmpty());
        assertTrue(tree.contains(10));

		
	}

	@Test
	void testClear() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.isEmpty());
        tree.insert(10);
        assertFalse(tree.isEmpty());
        assertTrue(tree.contains(10));

        tree.insert(20);
        
        tree.clear();
        assertTrue(tree.isEmpty());

		
	}
	
	@Test
    public void testRotations() {
        // Test left rotation
        RedBlackTree<Integer> treeLeft = new RedBlackTree<>();
        treeLeft.insert(10);
        treeLeft.insert(15);
        treeLeft.insert(5);
        assertEquals(Integer.valueOf(10), treeLeft.getRoot().getValue());
        assertEquals(Integer.valueOf(5), treeLeft.getRoot().getLeftChild().getValue());
        assertEquals(Integer.valueOf(15), treeLeft.getRoot().getRightChild().getValue());

        // Test right rotation
        RedBlackTree<Integer> treeRight = new RedBlackTree<>();
        treeRight.insert(15);
        treeRight.insert(10);
        treeRight.insert(20);
        assertEquals(Integer.valueOf(15), treeRight.getRoot().getValue());
        assertEquals(Integer.valueOf(10), treeRight.getRoot().getLeftChild().getValue());
        assertEquals(Integer.valueOf(20), treeRight.getRoot().getRightChild().getValue());

        // Test left-right (double) rotation
        RedBlackTree<Integer> treeLeftRight = new RedBlackTree<>();
        treeLeftRight.insert(10);
        treeLeftRight.insert(5);
        treeLeftRight.insert(8);
        assertEquals(Integer.valueOf(8), treeLeftRight.getRoot().getValue());
        assertEquals(Integer.valueOf(5), treeLeftRight.getRoot().getLeftChild().getValue());
        assertEquals(Integer.valueOf(10), treeLeftRight.getRoot().getRightChild().getValue());
    }

    @Test
    public void testColorFixing() {
        // Test color fixing after insertion
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(12);
        assertTrue(tree.getRoot().isBlack());
        assertTrue(tree.getRoot().getLeftChild().isBlack());
        assertTrue(tree.getRoot().getRightChild().isBlack());
        assertTrue(tree.getRoot().getRightChild().getLeftChild().isRed());
    }


}
