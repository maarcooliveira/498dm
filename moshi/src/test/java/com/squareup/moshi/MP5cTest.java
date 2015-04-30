package com.squareup.moshi;

import org.junit.Test;
import static org.junit.Assert.*;
import com.squareup.moshi.LinkedHashTreeMap.Node;

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



}
