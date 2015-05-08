package com.squareup.moshi;

import java.io.IOException;


public class SchemaHelper {

    static class TestInteger {
        final String description;
        final int data;
        final boolean valid;

        TestInteger(String description, int data, boolean valid) {
            this.description = description;
            this.data = data;
            this.valid = valid;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestInteger)
                return ((TestInteger) o).data == data;
            else if (o instanceof Integer)
                return ((int) o) == data;
            return false;
        }


    }

    static class TestIntegerAdapter extends JsonAdapter<TestInteger> {
        @Override
        public TestInteger fromJson(JsonReader reader) throws IOException {
            String description = "placeholder";
            boolean valid = false;
            int data = -1;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("description")) {
                    description = reader.nextString();
                } else if (name.equals("data")) {
                    data = reader.nextInt();
                } else if (name.equals("valid")) {
                    valid = reader.nextBoolean();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new TestInteger(description, data, valid);
        }

        @Override
        public void toJson(JsonWriter writer, TestInteger value) throws IOException {
            writer.beginObject();
            writer.name("description").value(value.description);
            writer.name("data").value(value.data);
            writer.name("valid").value(value.valid);
            writer.endObject();
        }
    }


    static class TestBoolean {
        final String description;
        final boolean data;
        final boolean valid;

        TestBoolean(String description, boolean data, boolean valid) {
            this.description = description;
            this.data = data;
            this.valid = valid;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestBoolean)
                return ((TestBoolean) o).data == data;
            else if (o instanceof Boolean)
                return ((boolean) o) && data;
            return false;
        }


    }

    static class TestBooleanAdapter extends JsonAdapter<TestBoolean> {
        @Override
        public TestBoolean fromJson(JsonReader reader) throws IOException {
            String description = "placeholder";
            boolean valid = false;
            boolean data = false;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("description")) {
                    description = reader.nextString();
                } else if (name.equals("data")) {
                    data = reader.nextBoolean();
                } else if (name.equals("valid")) {
                    valid = reader.nextBoolean();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new TestBoolean(description, data, valid);
        }

        @Override
        public void toJson(JsonWriter writer, TestBoolean value) throws IOException {
            writer.beginObject();
            writer.name("description").value(value.description);
            writer.name("data").value(value.data);
            writer.name("valid").value(value.valid);
            writer.endObject();
        }
    }
}