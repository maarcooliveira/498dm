package com.squareup.moshi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Marco Andre De Oliveira <mdeoliv2@illinois.edu>
 * Date: 4/21/15
 */

/* Project tests - ParameterizedNodeCheckTest - beginning */

// t10
@RunWith(Parameterized.class)
public class ParameterizedNodeCheckTest {
    private String key;
    private String value;
    LinkedHashTreeMap<String, String> map;

    @Before
    public void initialize() {
        map = new LinkedHashTreeMap<>();
        map.put("string", "lower");
        map.put("String", "firstCap");
        map.put("STRING", "upper");
        map.put("", "empty");
        map.put(" ", "space");
    }

    public ParameterizedNodeCheckTest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "string", "lower" },
                { "String", "firstCap" },
                { "STRING", "upper" },
                { "", "empty" },
                { " ", "space" }
        });
    }

    @Test
    public void checkDangerousKeys() {
        assertEquals(value, map.get(key));
    }
}
