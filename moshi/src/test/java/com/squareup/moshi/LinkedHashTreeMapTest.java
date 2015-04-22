/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.moshi;

import com.squareup.moshi.LinkedHashTreeMap.AvlBuilder;
import com.squareup.moshi.LinkedHashTreeMap.AvlIterator;
import com.squareup.moshi.LinkedHashTreeMap.Node;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class LinkedHashTreeMapTest {
  public LinkedHashTreeMap<String, String> helperBuildTree(){
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    map.put("c", "cola");
    map.put("b", "bbq");
    return map;
  }

  /* Project tests - LinkedHashTreeMapTest - beginning */
  // t1
  @Test public void removeNullNode() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("t", "test");
    map.remove(null);
    assertEquals(1, map.size());
  }

  // t2
  @Test public void removeExistentNode() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("t", "test");
    map.remove("t");
    assertEquals(0, map.size());
  }

  // t3
  @Test public void doNotInsertNodeIfNotFound() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.find("t", false);
    assertEquals(false, map.containsKey("t"));
    assertEquals(0, map.size());
  }

  // t4
  @Test public void insertNullNodeIfNotFound() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.find("t", true);
    assertEquals(true, map.containsKey("t"));
    assertEquals(1, map.size());
    assertEquals(null, map.get("t"));
  }

  // t5
  @Test public void updateNodeInsertedWithFind() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    Node<String, String> node = map.find("t", true);
    node.setValue("test");
    assertEquals("test", map.get("t"));
  }

  // t6
  @Test(expected = ClassCastException.class)
  public void cantCompareDifferentTypesOnInsert() {
    LinkedHashTreeMap<Object, Object> map = new LinkedHashTreeMap<>();
    map.find("1", true);
    map.find(2, true);
  }

  // t7
  @Test public void findByEntryChecksValue() {
    LinkedHashTreeMap<String, String> m1 = new LinkedHashTreeMap<>();
    LinkedHashTreeMap<String, String> m2 = new LinkedHashTreeMap<>();
    m1.put("t", "test");
    m2.put("t", "Test");
    Node n1 = m1.findByObject("t");
    Node n2 = m2.findByObject("t");

    assertEquals(null, m1.findByEntry(n2));
    assertEquals(null, m2.findByEntry(n1));
  }

  // t8
  @Test public void getFirstNode() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("t", "test");
    map.put("c", "child");
    Node n = map.findByObject("t");
    Node c = map.findByObject("c");
    n.left = c;

    assertEquals(c, n.first());
  }

  // t9
  @Test public void getLastNode() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("t", "test");
    map.put("c", "child");
    Node n = map.findByObject("t");
    Node c = map.findByObject("c");
    n.right = c;

    assertEquals(c, n.last());
  }


  // Test m1
  @Test
  public void entrySetSize(){
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertEquals(0, entries.size());

    map.put("a", "android");
    entries = map.entrySet();
    assertEquals(1, entries.size());

    map.put("b", "bbq");
    entries = map.entrySet();
    assertEquals(2, entries.size());

    map.put("c", "cola");
    entries = map.entrySet();
    assertEquals(3, entries.size());

    map.remove("a");
    entries = map.entrySet();
    assertEquals(2, entries.size());
  }


  // Test m2
  @Test
  public void entryContainsElement(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertFalse(entries.contains(null));

    Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<>("a", "android");
    assertTrue(entries.contains(entry));
  }

  // Test m3
  @Test
  public void entryRemoveNullEntry(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertFalse("Should not be able to remove entry `null`", entries.remove(null));
  }


  // Test m4
  @Test
  public void entryRemoveInvalidInstance(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertFalse("Should not remove element when argument is not an instance of Entry", entries.remove(new Integer(1)));
  }


  // Test m5
  @Test
  public void entryRemoveValidEntry(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertFalse("Should not be able to remove entry `null`", entries.remove(null));

    Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<>("a", "android");
    assertTrue("Should be able to remove valid entry <`a`, `android`>", entries.remove(entry));

    assertFalse("Should return null when not argument is not instance of Entry", entries.remove(new Integer(1)));
  }


  // Test m6
  @Test
  public void entryRemoveElement(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertFalse("Should not be able to remove entry `null`", entries.remove(null));

    Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<>("a", "android");
    assertTrue("Should be able to remove valid entry <\"a\", \"android\">", entries.remove(entry));

    assertFalse("Should not be able to remove element after is was just removed", entries.remove(entry));
  }


  // Test m7
  @Test
  public void entrySetClear(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    Set<Map.Entry<String, String>> entries = map.entrySet();

    assertEquals("Check that initial starting tree is not empty, in this case it has three elements", 3, entries.size());

    entries.clear();
    assertEquals("Ensure that number of entries is zero after the tree is cleared", 0, entries.size());
  }



  // Test m11
  @Test
  public void keySetSize(){
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    LinkedHashTreeMap.KeySet keySet = (LinkedHashTreeMap.KeySet) map.keySet();

    assertEquals(0, keySet.size());

    map.put("a", "android");
    keySet = (LinkedHashTreeMap.KeySet) map.keySet();
    assertEquals(1, keySet.size());

    map.put("b", "bbq");
    keySet = (LinkedHashTreeMap.KeySet) map.keySet();
    assertEquals(2, keySet.size());

    map.put("c", "cola");
    keySet = (LinkedHashTreeMap.KeySet) map.keySet();
    assertEquals(3, keySet.size());
  }


  // Test m12
  @Test
  public void keySetContainsNull(){
    LinkedHashTreeMap<String, String> map = helperBuildTree();
    LinkedHashTreeMap.KeySet keySet = (LinkedHashTreeMap.KeySet) map.keySet();

    assertFalse(keySet.contains(null));

    map.put("null", "IShouldHaveWorkedOnThisSooner");
    keySet = (LinkedHashTreeMap.KeySet) map.keySet();
    assertFalse(keySet.contains(null));

    map.put("", "values");
    keySet = (LinkedHashTreeMap.KeySet) map.keySet();
    assertTrue(keySet.contains(""));
    assertFalse(keySet.contains(null));
  }

  // Test m13
  @Test
  public void mapEmptyKey(){
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("", "values");
    assertTrue("Should be able to use empty String as Key", map.keySet().contains(""));
  }
  /* Project tests - LinkedHashTreeMapTest - end */

  @Test public void iterationOrder() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    map.put("c", "cola");
    map.put("b", "bbq");
    assertIterationOrder(map.keySet(), "a", "c", "b");
    assertIterationOrder(map.values(), "android", "cola", "bbq");
  }

  @Test public void removeRootDoesNotDoubleUnlink() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    map.put("c", "cola");
    map.put("b", "bbq");
    Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
    it.next();
    it.next();
    it.next();
    it.remove();
    assertIterationOrder(map.keySet(), "a", "c");
  }

  @Test public void putNullKeyFails() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    try {
      map.put(null, "android");
      fail();
    } catch (NullPointerException expected) {
    }
  }

  @Test public void putNonComparableKeyFails() {
    LinkedHashTreeMap<Object, String> map = new LinkedHashTreeMap<>();
    try {
      map.put(new Object(), "android");
      fail();
    } catch (ClassCastException expected) {}
  }

  @Test public void ContainsNonComparableKeyReturnsFalse() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    assertFalse(map.containsKey(new Object()));
  }

  @Test public void containsNullKeyIsAlwaysFalse() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    assertFalse(map.containsKey(null));
  }

  @Test public void putOverrides() throws Exception {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    assertNull(map.put("d", "donut"));
    assertNull(map.put("e", "eclair"));
    assertNull(map.put("f", "froyo"));
    assertEquals(3, map.size());

    assertEquals("donut", map.get("d"));
    assertEquals("donut", map.put("d", "done"));
    assertEquals(3, map.size());
  }

  @Test public void emptyStringValues() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "");
    assertTrue(map.containsKey("a"));
    assertEquals("", map.get("a"));
  }

  // NOTE that this does not happen every time, but given the below predictable random,
  // this test will consistently fail (assuming the initial size is 16 and rehashing
  // size remains at 3/4)
  @Test public void forceDoublingAndRehash() throws Exception {
    Random random = new Random(1367593214724L);
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    String[] keys = new String[1000];
    for (int i = 0; i < keys.length; i++) {
      keys[i] = Integer.toString(Math.abs(random.nextInt()), 36) + "-" + i;
      map.put(keys[i], "" + i);
    }

    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      assertTrue(map.containsKey(key));
      assertEquals("" + i, map.get(key));
    }
  }

  @Test public void clear() {
    LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
    map.put("a", "android");
    map.put("c", "cola");
    map.put("b", "bbq");
    map.clear();
    assertIterationOrder(map.keySet());
    assertEquals(0, map.size());
  }

  @Test public void equalsAndHashCode() throws Exception {
    LinkedHashTreeMap<String, Integer> map1 = new LinkedHashTreeMap<>();
    map1.put("A", 1);
    map1.put("B", 2);
    map1.put("C", 3);
    map1.put("D", 4);

    LinkedHashTreeMap<String, Integer> map2 = new LinkedHashTreeMap<>();
    map2.put("C", 3);
    map2.put("B", 2);
    map2.put("D", 4);
    map2.put("A", 1);

    assertEquals(map1, map2);
    assertEquals(map1.hashCode(), map2.hashCode());
  }

  @Test public void avlWalker() {
    assertAvlWalker(node(node("a"), "b", node("c")),
        "a", "b", "c");
    assertAvlWalker(node(node(node("a"), "b", node("c")), "d", node(node("e"), "f", node("g"))),
        "a", "b", "c", "d", "e", "f", "g");
    assertAvlWalker(node(node(null, "a", node("b")), "c", node(node("d"), "e", null)),
        "a", "b", "c", "d", "e");
    assertAvlWalker(node(null, "a", node(null, "b", node(null, "c", node("d")))),
        "a", "b", "c", "d");
    assertAvlWalker(node(node(node(node("a"), "b", null), "c", null), "d", null),
        "a", "b", "c", "d");
  }

  private void assertAvlWalker(Node<String, String> root, String... values) {
    AvlIterator<String, String> iterator = new AvlIterator<>();
    iterator.reset(root);
    for (String value : values) {
      assertEquals(value, iterator.next().getKey());
    }
    assertNull(iterator.next());
  }

  @Test public void avlBuilder() {
    assertAvlBuilder(1, "a");
    assertAvlBuilder(2, "(. a b)");
    assertAvlBuilder(3, "(a b c)");
    assertAvlBuilder(4, "(a b (. c d))");
    assertAvlBuilder(5, "(a b (c d e))");
    assertAvlBuilder(6, "((. a b) c (d e f))");
    assertAvlBuilder(7, "((a b c) d (e f g))");
    assertAvlBuilder(8, "((a b c) d (e f (. g h)))");
    assertAvlBuilder(9, "((a b c) d (e f (g h i)))");
    assertAvlBuilder(10, "((a b c) d ((. e f) g (h i j)))");
    assertAvlBuilder(11, "((a b c) d ((e f g) h (i j k)))");
    assertAvlBuilder(12, "((a b (. c d)) e ((f g h) i (j k l)))");
    assertAvlBuilder(13, "((a b (c d e)) f ((g h i) j (k l m)))");
    assertAvlBuilder(14, "(((. a b) c (d e f)) g ((h i j) k (l m n)))");
    assertAvlBuilder(15, "(((a b c) d (e f g)) h ((i j k) l (m n o)))");
    assertAvlBuilder(16, "(((a b c) d (e f g)) h ((i j k) l (m n (. o p))))");
    assertAvlBuilder(30, "((((. a b) c (d e f)) g ((h i j) k (l m n))) o "
        + "(((p q r) s (t u v)) w ((x y z) A (B C D))))");
    assertAvlBuilder(31, "((((a b c) d (e f g)) h ((i j k) l (m n o))) p "
        + "(((q r s) t (u v w)) x ((y z A) B (C D E))))");
  }

  private void assertAvlBuilder(int size, String expected) {
    char[] values = "abcdefghijklmnopqrstuvwxyzABCDE".toCharArray();
    AvlBuilder<String, String> avlBuilder = new AvlBuilder<>();
    avlBuilder.reset(size);
    for (int i = 0; i < size; i++) {
      avlBuilder.add(node(Character.toString(values[i])));
    }
    assertTree(expected, avlBuilder.root());
  }

  @Test public void doubleCapacity() {
    @SuppressWarnings("unchecked") // Arrays and generics don't get along.
    Node<String, String>[] oldTable = new Node[1];
    oldTable[0] = node(node(node("a"), "b", node("c")), "d", node(node("e"), "f", node("g")));

    Node<String, String>[] newTable = LinkedHashTreeMap.doubleCapacity(oldTable);
    assertTree("(b d f)", newTable[0]); // Even hash codes!
    assertTree("(a c (. e g))", newTable[1]); // Odd hash codes!
  }

  @Test public void doubleCapacityAllNodesOnLeft() {
    @SuppressWarnings("unchecked") // Arrays and generics don't get along.
        Node<String, String>[] oldTable = new Node[1];
    oldTable[0] = node(node("b"), "d", node("f"));

    Node<String, String>[] newTable = LinkedHashTreeMap.doubleCapacity(oldTable);
    assertTree("(b d f)", newTable[0]); // Even hash codes!
    assertNull(newTable[1]); // Odd hash codes!

    for (Node<?, ?> node : newTable) {
      if (node != null) {
        assertConsistent(node);
      }
    }
  }

  private static final Node<String, String> head = new Node<>();

  private Node<String, String> node(String value) {
    return new Node<>(null, value, value.hashCode(), head, head);
  }

  private Node<String, String> node(Node<String, String> left, String value,
      Node<String, String> right) {
    Node<String, String> result = node(value);
    if (left != null) {
      result.left = left;
      left.parent = result;
    }
    if (right != null) {
      result.right = right;
      right.parent = result;
    }
    return result;
  }

  private void assertTree(String expected, Node<?, ?> root) {
    assertEquals(expected, toString(root));
    assertConsistent(root);
  }

  private void assertConsistent(Node<?, ?> node) {
    int leftHeight = 0;
    if (node.left != null) {
      assertConsistent(node.left);
      assertSame(node, node.left.parent);
      leftHeight = node.left.height;
    }
    int rightHeight = 0;
    if (node.right != null) {
      assertConsistent(node.right);
      assertSame(node, node.right.parent);
      rightHeight = node.right.height;
    }
    if (node.parent != null) {
      assertTrue(node.parent.left == node || node.parent.right == node);
    }
    if (Math.max(leftHeight, rightHeight) + 1 != node.height) {
      fail();
    }
  }

  private String toString(Node<?, ?> root) {
    if (root == null) {
      return ".";
    } else if (root.left == null && root.right == null) {
      return String.valueOf(root.key);
    } else {
      return String.format("(%s %s %s)", toString(root.left), root.key, toString(root.right));
    }
  }

  private <T> void assertIterationOrder(Iterable<T> actual, T... expected) {
    ArrayList<T> actualList = new ArrayList<>();
    for (T t : actual) {
      actualList.add(t);
    }
    assertEquals(Arrays.asList(expected), actualList);
  }
}
