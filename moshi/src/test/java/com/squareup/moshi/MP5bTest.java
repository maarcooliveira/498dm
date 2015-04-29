package com.squareup.moshi;

import com.squareup.moshi.LinkedHashTreeMap.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marco Andre De Oliveira <mdeoliv2@illinois.edu>
 * Date: 4/29/15
 */
public final class MP5bTest {

    @Test
    public void testUppercaseTerminals() {
        LinkedHashTreeMap<Character, Character> map = new LinkedHashTreeMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            map.put(c, c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            assertEquals(c, (char) map.get(c));
        }
        assertEquals(26, map.size());
        for (char c = 'A'; c <= 'Z'; c++) {
            assertEquals(c, (char) map.remove(c));
        }
        assertEquals(0, map.size());
        for (char c = 'A'; c <= 'Z'; c++) {
            map.find(c, false);
            assertFalse(map.containsKey(c));
            Node n = map.find(c, true);
            n.setValue(c);
            assertEquals(c, n.getValue());
            assertTrue(map.containsKey(c));
            assertEquals(n, map.findByObject(c));
        }
        assertEquals(26, map.size());
    }

    @Test
    public void testLowercaseTerminals() {
        LinkedHashTreeMap<Character, Character> map = new LinkedHashTreeMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            map.put(c, c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(c, (char) map.get(c));
        }
        assertEquals(26, map.size());
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(c, (char) map.remove(c));
        }
        assertEquals(0, map.size());
        for (char c = 'a'; c <= 'z'; c++) {
            map.find(c, false);
            assertFalse(map.containsKey(c));
            Node n = map.find(c, true);
            n.setValue(c);
            assertEquals(c, n.getValue());
            assertTrue(map.containsKey(c));
            assertEquals(n, map.findByObject(c));
        }
        assertEquals(26, map.size());
    }

    @Test
    public void testNumberTerminals() {
        LinkedHashTreeMap<Integer, Integer> map = new LinkedHashTreeMap<>();
        for (int c = 0; c <= 9; c++) {
            map.put(c, c);
        }
        for (int c = 0; c <= 9; c++) {
            assertEquals(c, (int) map.get(c));
        }
        assertEquals(10, map.size());
        for (int c = 0; c <= 9; c++) {
            assertEquals(c, (int) map.remove(c));
        }
        assertEquals(0, map.size());
        for (int c = 0; c <= 9; c++) {
            map.find(c, false);
            assertFalse(map.containsKey(c));
            Node n = map.find(c, true);
            n.setValue(c);
            assertEquals(c, n.getValue());
            assertTrue(map.containsKey(c));
            assertEquals(n, map.findByObject(c));
        }
        assertEquals(10, map.size());
    }

}
