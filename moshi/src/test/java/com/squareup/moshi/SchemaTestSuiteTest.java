package com.squareup.moshi;


import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import org.junit.Test;

import static com.squareup.moshi.TestUtil.newReader;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by mattlodes on 5/8/15.
 */
public final class SchemaTestSuiteTest {

    final String integerTest1 = "{\"description\":\"an integer is an integer\",\"data\":1,\"valid\":true}";
    final String integerTest2 = "{\"description\":\"a float is not an integer\",\"data\":1.1,\"valid\":false}";
    final String integerTest3 = "{\"description\":\"a string is not an integer\",\"data\":\"foo\",\"valid\":false}";
    final String integerTest4 = "{\"description\":\"an object is not an integer\",\"data\":{},\"valid\":false}";
    final String integerTest5 = "{\"description\":\"an array is not an integer\",\"data\":[],\"valid\":false}";
    final String integerTest6 = "{\"description\":\"a boolean is not an integer\",\"data\":true,\"valid\":false}";
    final String integerTest7 = "{\"description\":\"null is not an integer\",\"data\":null,\"valid\":false}";

    final String booleanTest1 = "{\"description\":\"an integer is not a boolean\",\"data\":1,\"valid\":false}";
    final String booleanTest2 = "{\"description\":\"a float is not a boolean\",\"data\":1.1,\"valid\":false}";
    final String booleanTest3 = "{\"description\":\"a string is not a boolean\",\"data\":\"foo\",\"valid\":false}";
    final String booleanTest4 = "{\"description\":\"an object is not a boolean\",\"data\":{},\"valid\":false}";
    final String booleanTest5 = "{\"description\":\"an array is not a boolean\",\"data\":[],\"valid\":false}";
    final String booleanTest6 = "{\"description\":\"a boolean is not a boolean\",\"data\":true,\"valid\":true}";
    final String booleanTest7 = "{\"description\":\"null is not a boolean\",\"data\":null,\"valid\":false}";

    @Test
    public void test1() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(SchemaHelper.TestInteger.class, new SchemaHelper.TestIntegerAdapter())
                .build();

        JsonAdapter<SchemaHelper.TestInteger> jsonAdapter = moshi.adapter(SchemaHelper.TestInteger.class);
        assertThat(jsonAdapter.toJson(new SchemaHelper.TestInteger("an integer is an integer", 1, true)))
                .isEqualTo(integerTest1);

    }

    @Test
    public void test2() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(SchemaHelper.TestInteger.class, new SchemaHelper.TestIntegerAdapter())
                .build();

        JsonAdapter<SchemaHelper.TestInteger> jsonAdapter = moshi.adapter(SchemaHelper.TestInteger.class);
        assertThat(jsonAdapter.fromJson(integerTest1))
                .isEqualTo(1);

        try {
            jsonAdapter.fromJson(integerTest2);
            fail();
        } catch (NumberFormatException expected){}

        try {
            jsonAdapter.fromJson(integerTest3);
            fail();
        } catch (NumberFormatException expected){}

        try {
            jsonAdapter.fromJson(integerTest4);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(integerTest5);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(integerTest6);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(integerTest7);
            fail();
        } catch (IllegalStateException expected){}

    }


    @Test
    public void fromJsonBooleanTest() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(SchemaHelper.TestBoolean.class, new SchemaHelper.TestBooleanAdapter())
                .build();

        JsonAdapter<SchemaHelper.TestBoolean> jsonAdapter = moshi.adapter(SchemaHelper.TestBoolean.class);
//        assertThat(jsonAdapter.fromJson(booleanTest1))
//                .isEqualTo(1);

        try {
            jsonAdapter.fromJson(booleanTest1);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(booleanTest2);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(booleanTest3);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(booleanTest4);
            fail();
        } catch (IllegalStateException expected){}

        try {
            jsonAdapter.fromJson(booleanTest5);
            fail();
        } catch (IllegalStateException expected){}


        assertThat(jsonAdapter.fromJson(booleanTest6))
                .isEqualTo(true);

        try {
            jsonAdapter.fromJson(booleanTest7);
            fail();
        } catch (IllegalStateException expected){}


    }


    @Retention(RUNTIME)
    public @interface Uppercase {
    }

    static class UppercaseAdapterFactory implements JsonAdapter.Factory {
        @Override
        public JsonAdapter<?> create(
                Type type, AnnotatedElement annotations, Moshi moshi) {
            if (!type.equals(String.class)) return null;
            if (!annotations.isAnnotationPresent(Uppercase.class)) return null;

            final JsonAdapter<String> stringAdapter = moshi.nextAdapter(this, String.class, annotations);
            return new JsonAdapter<String>() {
                @Override
                public String fromJson(JsonReader reader) throws IOException {
                    String s = stringAdapter.fromJson(reader);
                    return s.toUpperCase(Locale.US);
                }

                @Override
                public void toJson(JsonWriter writer, String value) throws IOException {
                    stringAdapter.toJson(writer, value.toUpperCase());
                }
            };
        }
    }


}

