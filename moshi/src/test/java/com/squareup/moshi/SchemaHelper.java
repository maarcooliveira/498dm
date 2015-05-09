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


    static class TestString {
        final String description;
        final String data;
        final boolean valid;

        TestString(String description, String data, boolean valid) {
            this.description = description;
            this.data = data;
            this.valid = valid;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestString)
                return ((TestString) o).data == data;
            else if (o instanceof String)
                return ((String) o).equals(data);
            return false;
        }


    }

    static class TestStringAdapter extends JsonAdapter<TestString> {
        @Override
        public TestString fromJson(JsonReader reader) throws IOException {
            String description = "placeholder";
            boolean valid = false;
            String data = "placeholder";

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("description")) {
                    description = reader.nextString();
                } else if (name.equals("data")) {
                    data = reader.nextString();
                } else if (name.equals("valid")) {
                    valid = reader.nextBoolean();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new TestString(description, data, valid);
        }

        @Override
        public void toJson(JsonWriter writer, TestString value) throws IOException {
            writer.beginObject();
            writer.name("description").value(value.description);
            writer.name("data").value(value.data);
            writer.name("valid").value(value.valid);
            writer.endObject();
        }
    }


    static class TestObject {
        final String description;
        final Object data;
        final boolean valid;

        TestObject(String description, Object data, boolean valid) {
            this.description = description;
            this.data = data;
            this.valid = valid;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestObject)
                return ((TestString) o).data == data;
            else if (o instanceof String)
                return o.equals(data);
            return false;
        }


    }

//    static class TestObjectAdapter extends JsonAdapter<TestObject> {
//        @Override
//        public TestObject fromJson(JsonReader reader) throws IOException {
//            String description = "placeholder";
//            boolean valid = false;
//            Object data = null;
//
//            reader.beginObject();
//            while (reader.hasNext()) {
//                String name = reader.nextName();
//                if (name.equals("description")) {
//                    description = reader.nextString();
//                } else if (name.equals("data")) {
//                    try{
//                        data = reader.nextInt();
//                        continue;
//                    }catch(Exception e){}
//                    try{
//                        data = reader.nextBoolean();
//                        continue;
//                    }catch(Exception e){}
//                    try{
//                        data = reader.nextDouble();
//                        continue;
//                    }catch(Exception e){}
//                    try{
//                        data = reader.nextLong();
//                        continue;
//                    }catch(Exception e){}
//                    try{
//                        data = reader.nextString();
//                        continue;
//                    }catch(Exception e){}
//
//                    try{
//                        reader.beginObject();
//                        data = new
//                        while (reader.hasNext()) {
//                            String innerName = reader.nextName();
//                            if (innerName.equals("description")) {
//                                description = reader.nextString();
//                            } else if (innerName.equals("data")) {
//                                try{
//                                    data = reader.nextInt();
//                                    continue;
//                                }catch(Exception e){}
//                                try{
//                                    data = reader.nextBoolean();
//                                    continue;
//                                }catch(Exception e){}
//                                try{
//                                    data = reader.nextDouble();
//                                    continue;
//                                }catch(Exception e){}
//                                try{
//                                    data = reader.nextLong();
//                                    continue;
//                                }catch(Exception e){}
//                                try{
//                                    data = reader.nextString();
//                                    continue;
//                                }catch(Exception e){}
//
//                                try{
//                                    reader.beginObject();
//                                }
//
//                            } else if (name.equals("valid")) {
//                                valid = reader.nextBoolean();
//                            } else {
//                                reader.skipValue();
//                            }
//
//
//
//
//
//                    }
//
//                } else if (name.equals("valid")) {
//                    valid = reader.nextBoolean();
//                } else {
//                    reader.skipValue();
//                }
//            }
//            reader.endObject();
//            return new TestObject(description, data, valid);
//        }
//
//        @Override
//        public void toJson(JsonWriter writer, TestObject value) throws IOException {
//            writer.beginObject();
//            writer.name("description").value(value.description);
//            writer.name("data").value(value.data.toString()); // Check
//            writer.name("valid").value(value.valid);
//            writer.endObject();
//        }
//    }

}