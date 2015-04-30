package com.squareup.moshi;

import org.junit.Test;

import static com.squareup.moshi.TestUtil.newReader;
import static org.junit.Assert.*;
import com.squareup.moshi.LinkedHashTreeMap.Node;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marco Andre De Oliveira <mdeoliv2@illinois.edu>
 * Date: 4/29/15
 */
public final class MP5cTest {

    // Test for mutant m1
    @Test
    public void setValueTest() {
        LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
        map.put("k1", "oldValue");
        map.put("k2", "oldValue");
        Node<String, String> n1 = map.findByObject("k1");
        Node<String, String> n2 = map.findByObject("k2");

        assertNotEquals(n1.setValue("newValue"), n2.setValueMutant("newValue"));
    }

    // Test for mutant m2
    @Test
    public void findByObjectTest() {
        LinkedHashTreeMap<String, String> m1 = new LinkedHashTreeMap<>();
        LinkedHashTreeMap<String, String> m2 = new LinkedHashTreeMap<>();
        m1.findByObject("k");
        m2.findByObjectMutant("k");

        assertNotEquals(m1.size(), m2.size());
    }


    // Test m3
    @Test
    public void findByEntry(){
        LinkedHashTreeMap<String, String> map = new LinkedHashTreeMap<>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");

        Set<Map.Entry<String, String>> entries = map.entrySet();

        Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<>("a", "differentValue");
        assertNotEquals(map.findByEntry(entry), map.findByEntryMutant(entry));
    }

    // Test m4
    @Test public void readBooleanArray() throws IOException {
        JsonReader reader = newReader("[true, false]");
        reader.beginArray();
        assertEquals(reader.nextBoolean(), reader.nextBooleanMutant());
        reader.endArray();
    }

    // Test m5
    @Test public void readNextInt() throws IOException {
        JsonReader reader = newReader("{\"a\":123,\"b\":-123}");
        reader.beginObject();
        reader.nextName();
        int value = reader.nextInt();
        reader.nextName();
        int mutantValue = reader.nextIntMutant();

        reader.endObject();
        assertEquals(value, mutantValue);
    }
}
