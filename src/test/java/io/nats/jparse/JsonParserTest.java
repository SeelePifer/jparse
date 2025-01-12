/*
 * Copyright 2013-2023 Richard M. Hightower
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.nats.jparse;


import io.nats.jparse.node.*;
import io.nats.jparse.parser.JsonEventParser;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.source.Sources;
import io.nats.jparse.token.Token;
import io.nats.jparse.token.TokenTypes;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.nats.jparse.node.JsonTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    public JsonParser jsonParser() {
        final JsonParser parser = Json.builder().setStrict(true).build();

        System.out.println("             " + parser.getClass().getName());
        return parser;
    }

    public ArrayNode toArrayNode(final String json) {
        final JsonParser parser = jsonParser();
        return parser.parse(json).getArrayNode();
    }

    public  String niceJson(String json) {
        return Json.niceJson(json);
    }

    public  RootNode toRootNode(final String json) {
        final JsonParser parser = jsonParser();
        return parser.parse(json);
    }

    public  Map<String, Object> toMap(final String json) {
        final JsonParser parser = jsonParser();
        return (Map<String, Object>) (Object) parser.parse(json).getObjectNode();
    }

    public  List<Object> toList(final String json) {
        final JsonParser parser = jsonParser();
        return (List<Object>) (Object) parser.parse(json).getArrayNode();
    }

    @Test
    public void testDoubleArray() {
        //................012345678901234567890123
        final String json = "[1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9]";
        final double[] array = {1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9};
        double[] readDoubles = toArrayNode(niceJson(json)).getDoubleArray();
        assertArrayEquals(array, readDoubles);
    }



    @Test
    public void testDoubleArrayFast() {
        //................012345678901234567890123
        final String json = "[1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9]";
        final double[] array = {1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9};
        //double[] readDoubles = toArrayNode(niceJson(json)).getDoubleArrayFast(); //TODO
        double[] readDoubles = toArrayNode(niceJson(json)).getDoubleArray();
        assertArrayEquals(array, readDoubles);
    }

    @Test
    public void testFloatArray() {
        //................012345678901234567890123
        final String json = "[1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9]";
        final float[] array = {1, 1.1f, 1.2f, 1.3f, 1e+9f, 1e9f, 1e-9f};
        //float[] values = toArrayNode(niceJson(json)).getFloatArray(); //TODO
        float[] values = toArrayNode(niceJson(json)).getFloatArray(); //TODO
        assertArrayEquals(array, values);
    }

    @Test
    public void testSimpleArray() {
        //................012345678901234567890123
        final String json = "[1]";
        final float[] array = {1};
        float[] values = toArrayNode(niceJson(json)).getFloatArray();
        assertArrayEquals(array, values);
    }

    @Test
    public void testSimpleArray2() {
        //................012345678901234567890123
        final String json = "[1,2]";
        final float[] array = {1,2};
        float[] values = toArrayNode(niceJson(json)).getFloatArray();
        assertArrayEquals(array, values);
    }


    @Test
    public void testFloatArrayFast() {
        //................012345678901234567890123
        final String json = "[1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9]";
        final float[] array = {1, 1.1f, 1.2f, 1.3f, 1e+9f, 1e9f, 1e-9f};
        //float[] values = toArrayNode(niceJson(json)).getFloatArrayFast(); //TODO
        float[] values = toArrayNode(niceJson(json)).getFloatArray();
        assertArrayEquals(array, values);
    }

    private BigDecimal bg(String s) {
        return new BigDecimal(s);
    }

    @Test
    public void testBigDecimalArray() {
        //................012345678901234567890123
        final String json = "[1, 1.1, 1.2, 1.3, 1e+9, 1e9, 1e-9]";
        final BigDecimal[] array = new BigDecimal[]{bg("1"), bg("1.1"), bg("1.2"), bg("1.3"), bg("1e+9"), bg("1e9"), bg("1e-9")};
        final BigDecimal[] values = toArrayNode(niceJson(json)).getBigDecimalArray();
        assertArrayEquals(array, values);
    }

    private BigInteger bi(int s) {
        return new BigInteger("" + s);
    }

    @Test
    public void testBigIntArray() {
        //................012345678901234567890123
        final String json = "[1, 2, 3, 4, 5, 6, 7, 8, -9]";
        final BigInteger[] array = new BigInteger[]{bi(1), bi(2), bi(3), bi(4), bi(5), bi(6), bi(7), bi(8), bi(-9)};
        final BigInteger[] values = toArrayNode(niceJson(json)).getBigIntegerArray();
        assertArrayEquals(array, values);
    }

    @Test
    public void testIntArray() {
        //................012345678901234567890123
        final String json = "[1, 2, 3, 4, 5, 6, 7, 8, -9]";
        final int[] array = {1, 2, 3, 4, 5, 6, 7, 8, -9};
        int[] values = toArrayNode(niceJson(json)).getIntArray();
        assertArrayEquals(array, values);
    }

    @Test
    public void testLongArray() {
        //................012345678901234567890123
        final String json = "[1, 2, 3, 4, 5, 6, 7, 8, 9]";
        final long[] array = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L};
        long [] values = toArrayNode(niceJson(json)).getLongArray();
        assertArrayEquals(array, values);
    }

    @Test
    public void testIntArrayFast() {
        //................012345678901234567890123
        final String json = "[1, 2, 3, 4, 5, 6, 7, 8, -9]";
        final int[] array = {1, 2, 3, 4, 5, 6, 7, 8, -9};
        int[] values =       toArrayNode(niceJson(json)).getIntArray();
        assertArrayEquals(array, values);
    }

    @Test
    public void testLongArrayFast() {
        //................012345678901234567890123
        final String json = "[1, 2, 3, 4, 5, 6, 7, 8, 9]";
        final long[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        long[] values = toArrayNode(niceJson(json)).getLongArray();
        assertArrayEquals(array, values);
    }

    //-3.689349E18
    @Test
    public void testDoubleArrayExponent2() {

        final double[] array = {3.689349E18};
        //...................01234567890
        final String json = "[3.689349E18]";
        double[] values = toArrayNode(niceJson(json)).getDoubleArray();
        assertArrayEquals(array, values);
    }


    @Test
    public void testComplexMap() {
        //................012345678901234567890123
        final String json = "{'1':2,'2':7,'3':[1,2,3]}";
        final RootNode root = jsonParser().parse(Json.niceJson(json));

        final Map<String,Object> jsonObject = root.getMap();
        assertEquals(2, asInt(jsonObject, "1"));
        assertEquals(7, asInt(jsonObject, "2"));

        ArrayNode arrayNode = root.asObject().getArrayNode("3");
        showTokens(arrayNode.tokens());

        assertEquals(1L,arrayNode.getNodeAt(0).asScalar().longValue());
        //assertEquals(1L, );
    }

    @Test
    void testParseNumberJsonElement() {
        final JsonParser parser = jsonParser();
        final String json = "1";
        RootNode jsonRoot = parser.parse(Sources.stringSource(json));
        assertEquals(1, jsonRoot.getInt());
        assertEquals(1, jsonRoot.getLong());
        assertEquals(new BigInteger("1"), jsonRoot.getBigIntegerValue());
        assertTrue(jsonRoot.getNode().isScalar());
        assertFalse(jsonRoot.getNode().isCollection());
    }

    @Test
    void testParseFloatJsonElement() {
        final JsonParser parser = jsonParser();
        final String json = "1.1";
        RootNode jsonRoot = parser.parse(Sources.stringSource(json));
        assertEquals(1.1, jsonRoot.getFloat(), 0.001);
        assertEquals(1.1, jsonRoot.getDouble(), 0.001);
        assertEquals(new BigDecimal("1.1"), jsonRoot.getBigDecimal());
    }


    @Test
    public void testComplexMapWithMixedKeys() {
        //................012345678901234567890123
        final String json = "{'1':2,'2':7,'abc':[1,2,3,true,'hi'],'4':true}";

        final JsonParser parser = jsonParser();

        final RootNode root = parser.parse(Json.niceJson(json));
        final Map<String, Object> jsonObject = toMap(niceJson(json));
        assertTrue(asBoolean(jsonObject, "4"));
        assertEquals(2, asInt(jsonObject, "1"));
        assertEquals(7, asInt(jsonObject, "2"));
        assertEquals(7, asShort(jsonObject, "2"));
        assertEquals(7L, asLong(jsonObject, "2"));
        assertEquals(7.0, asDouble(jsonObject, "2"));
        assertEquals(7.0f, asFloat(jsonObject, "2"));
        assertEquals(new BigInteger("7"), asBigInteger(jsonObject, "2"));
        assertEquals(new BigDecimal("7"), asBigDecimal(jsonObject, "2"));

        assertEquals(Arrays.asList(1, 2, 3, true, "hi"), asArray(jsonObject, "abc").stream().map(n->n.asScalar().value()).collect(Collectors.toList()));

        assertEquals(1, asInt(asList(jsonObject, "abc"), 0));
        assertEquals("hi", asString(asList(jsonObject, "abc"), 4));
        assertEquals("hi", asString(asList(jsonObject, "abc"), 4));
        assertEquals(1, asShort(asList(jsonObject, "abc"), 0));
        assertEquals(1.0, asDouble(asList(jsonObject, "abc"), 0));
        assertEquals(1.0f, asFloat(asList(jsonObject, "abc"), 0));
        assertEquals(1L, asLong(asList(jsonObject, "abc"), 0));
        assertEquals(new BigInteger("1"), asBigInteger(asList(jsonObject, "abc"), 0));
        assertEquals(new BigDecimal("1"), asBigDecimal(asList(jsonObject, "abc"), 0));

        assertTrue(asBoolean(asList(jsonObject, "abc"), 3));
        assertTrue(asArray(jsonObject, "abc").getBooleanNode(3).booleanValue());
        assertTrue(asArray(jsonObject, "abc").getBoolean(3));

        assertFalse(root.getNode().isScalar());
        assertTrue(root.getNode().isCollection());

    }


    @Test
    void testSimpleBooleanTrue() {
        final JsonParser parser = jsonParser();
        final String json = "true";

        final RootNode jsonRoot = parser.parse(Sources.charSeqSource(json));
        assertTrue(jsonRoot.getBooleanNode().booleanValue());
        assertTrue(jsonRoot.getBoolean());

        assertEquals(json, jsonRoot.getBooleanNode().toString());
        assertEquals(json, jsonRoot.getBooleanNode().charSource().toString());

        assertEquals(json.charAt(0), jsonRoot.getBooleanNode().charAt(0));
        assertEquals(json.charAt(1), jsonRoot.getBooleanNode().charAt(1));
        assertEquals(json.charAt(2), jsonRoot.getBooleanNode().charAt(2));
        assertEquals(json.charAt(3), jsonRoot.getBooleanNode().charAt(3));

        assertEquals(4, jsonRoot.getBooleanNode().length());
        assertEquals(NodeType.BOOLEAN, jsonRoot.getBooleanNode().type());
        assertEquals(TokenTypes.BOOLEAN_TOKEN, jsonRoot.getBooleanNode().rootElementToken().type);
        assertEquals(TokenTypes.BOOLEAN_TOKEN, jsonRoot.getBooleanNode().tokens().get(0).type);
    }


    @Test
    void testSimpleNull() {
        final JsonParser parser = jsonParser();
        final String json = "null";

        final RootNode jsonRoot = parser.parse(Sources.charSeqSource(json));

        assertEquals(json, jsonRoot.getNullNode().toString());
        assertEquals(json, jsonRoot.getNullNode().charSource().toString());

        assertEquals(json.charAt(0), jsonRoot.getNullNode().charAt(0));
        assertEquals(json.charAt(1), jsonRoot.getNullNode().charAt(1));
        assertEquals(json.charAt(2), jsonRoot.getNullNode().charAt(2));
        assertEquals(json.charAt(3), jsonRoot.getNullNode().charAt(3));

        assertEquals(4, jsonRoot.getNullNode().length());
        assertEquals(NodeType.NULL, jsonRoot.getNullNode().type());
        assertEquals(TokenTypes.NULL_TOKEN, jsonRoot.getNullNode().rootElementToken().type);
        assertEquals(TokenTypes.NULL_TOKEN, jsonRoot.getNullNode().tokens().get(0).type);
    }

    @Test
    void testSimpleBooleanFalse() {
        final JsonParser parser = jsonParser();
        final String json = "false";

        final RootNode jsonRoot = parser.parse(Sources.stringSource(json));
        assertFalse(jsonRoot.getBooleanNode().booleanValue());

        assertEquals(json, jsonRoot.getBooleanNode().toString());
        assertEquals(json, jsonRoot.getBooleanNode().charSource().toString());

        assertEquals(json.charAt(0), jsonRoot.getBooleanNode().charAt(0));
        assertEquals(json.charAt(1), jsonRoot.getBooleanNode().charAt(1));
        assertEquals(json.charAt(2), jsonRoot.getBooleanNode().charAt(2));
        assertEquals(json.charAt(3), jsonRoot.getBooleanNode().charAt(3));
        assertEquals(json.charAt(4), jsonRoot.getBooleanNode().charAt(4));

        assertEquals(5, jsonRoot.getBooleanNode().length());
        assertEquals(NodeType.BOOLEAN, jsonRoot.getBooleanNode().type());
        assertEquals(TokenTypes.BOOLEAN_TOKEN, jsonRoot.getBooleanNode().rootElementToken().type);
        assertEquals(TokenTypes.BOOLEAN_TOKEN, jsonRoot.getBooleanNode().tokens().get(0).type);
    }


    @Test
    void testSimpleString() {
        final JsonParser parser = jsonParser();
        final String json = "\"abc\"";

        final RootNode jsonRoot = parser.parse(Sources.charSeqSource(json));
        assertEquals("abc", jsonRoot.getStringNode().toString());
        assertEquals(NodeType.STRING, jsonRoot.getType());
        assertEquals("bc", jsonRoot.getStringNode().subSequence(1, 3).toString());
    }


    @Test
    void testSimpleEncodedString() {
        final JsonParser parser = jsonParser();
        //....................012 3 4 5 6
        final String json = "'abc`n`b`r`u1234'";

        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"").replace('`', '\\')));

        final String encodedString = jsonRoot.getStringNode().toString();
        final String unencodedString = jsonRoot.getStringNode().toUnencodedString();

        assertEquals("abc\\n\\b\\r\\u1234", unencodedString);
        assertEquals("abc\n\b\r\u1234", encodedString);
        assertEquals("abc\n\b\r\u1234", jsonRoot.getStringNode().toString());
        assertEquals(7, jsonRoot.getStringNode().toEncodedString().length());
        assertEquals(NodeType.STRING, jsonRoot.getType());
        assertEquals("bc", jsonRoot.getStringNode().subSequence(1, 3).toString());

        assertEquals('\n', jsonRoot.getStringNode().toEncodedString().charAt(3));
    }

    @Test
    void testGetNumFloat() {
        final JsonParser parser = jsonParser();
        final String json = "1.1";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1.1, jsonRoot.getNumberNode().floatValue(), 0.001);
        assertEquals(NodeType.FLOAT, jsonRoot.getType());
    }


    @Test
    void test_empty_object() {

        final JsonParser parser = jsonParser();

        if (parser instanceof JsonEventParser) {
            //TODO see why this fails for event parser
            return;
        }
        final String json = "{}";
        final RootNode jsonRoot = parser.parse(niceJson(json));

        System.out.println(jsonRoot.tokens());
        assertEquals(0, jsonRoot.asObject().size());

    }

    @Test
    void test_n_object_with_single_string() {
        final JsonParser parser = jsonParser();
        //...................0123456789012345678901234
        final String json = "{'foo' :  'bar',  'a' }";
        try {
            final RootNode jsonRoot = parser.parse(niceJson(json));
            System.out.println(jsonRoot.tokens());
            assertTrue(false);
        } catch (Exception ex) {

        }

    }


    @Test
    void testBadChar() {
        final JsonParser parser = jsonParser();

        final String json = "@";
        try {
            final List<Token> tokens = parser.scan(Sources.stringSource(json));
            assertTrue(false);
        } catch (Exception ex) {

        }

    }

    @Test
    void testEmptyArray() {
        final JsonParser parser = jsonParser();

        final String json = "[]";
        try {
            final List<Token> tokens = parser.scan(Sources.stringSource(json));

        } catch (Exception ex) {
            ex.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    void objectSimpleWithEmptyArrayValue() {
        final JsonParser parser = jsonParser();

        final String json = "{'a':[]}";
        try {
            final List<Token> tokens = parser.scan(Json.niceJson(json));

        } catch (Exception ex) {
            ex.printStackTrace();
            assertTrue(false);

        }

    }




    @Test
    void testBadCharInNumber() {
        final JsonParser parser = jsonParser();

        final String json = "123@";
        try {
            final List<Token> tokens = parser.scan(Sources.stringSource(json));
            assertTrue(false);
        } catch (Exception ex) {

        }

    }



    @Test
    void testBadCharInFloat() {
        final JsonParser parser = jsonParser();

        final String json = "123.1@";
        try {
            final List<Token> tokens = parser.scan(Sources.stringSource(json));
            assertTrue(false);
        } catch (Exception ex) {

        }

    }




    @Test
    void testGetNumInt() {
        final JsonParser parser = jsonParser();
        final String json = "1";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1.0, jsonRoot.getNumberNode().floatValue(), 0.001);
        assertEquals(NodeType.INT, jsonRoot.getType());
    }


    @Test
    void testSimpleStringFromMap() {
        final JsonParser parser = jsonParser();
        final String json = "{'a':'abc'}";
        final RootNode jsonRoot = parser.parse(json.replace("'", "\""));
        StringNode aStringValue = jsonRoot.getObjectNode().getStringNode("a");
        assertEquals("abc", aStringValue.toString());
        assertEquals("abc", jsonRoot.getObjectNode().getString("a"));
        assertEquals(NodeType.OBJECT, jsonRoot.getType());
        assertEquals(1, jsonRoot.getObjectNode().length());
        assertEquals(niceJson("abc"), jsonRoot.getObjectNode().getString("a"));
        assertEquals(niceJson(json), jsonRoot.originalString());
    }

    @Test
    void testSimpleStringFromMap2() {
        final String json = "{'a':'abc'}";
        Map<String, Object> map = toMap(niceJson(json));
        assertEquals("abc", asString(map, "a"));
    }

    @Test
    void testSimpleStringFromMap3() {
        final String json = "{'a':null}";
        Map<String, Object> map = toMap(niceJson(json));
        final RootNode root = toRootNode(niceJson(json));
        assertEquals("null", "" + map.get("a"));

        final Node nullNode = root.getNode("a");
        assertEquals("null", nullNode.toString());
        assertEquals(4, nullNode.length());
        assertEquals('n', nullNode.charAt(0));
        assertEquals('u', nullNode.charAt(1));
        assertEquals('l', nullNode.charAt(2));
        assertEquals('l', nullNode.charAt(3));
        assertEquals(nullNode, nullNode);
        assertEquals(nullNode, toRootNode(niceJson(json)).getNode("a"));
        assertEquals(nullNode, toRootNode(niceJson(json)).getObjectNode().getNullNode("a"));
    }



    @Test
    void y_object_empty_key() {
        final JsonParser parser = jsonParser();
        final String json = "{\"\":0}";
        final RootNode jsonRoot = parser.parse(niceJson(json));
    }

    @Test
    void test3ItemMap() {
        final JsonParser parser = jsonParser();
        //...................012345678901234567890
        final String json = "{'a':'abc','b':'def', 'c': true }";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(3, jsonRoot.getObjectNode().length());
        assertEquals(NodeType.OBJECT, jsonRoot.getType());
        assertEquals("abc", jsonRoot.getObjectNode().getNode("a").toString());
        assertEquals("def", jsonRoot.getObjectNode().getStringNode("b").toString());
        assertEquals(true, jsonRoot.getObjectNode().getBooleanNode("c").booleanValue());
        assertEquals(true, jsonRoot.getObjectNode().getBoolean("c"));
    }

    @Test
    void test2ItemIntKeyMap() {
        final JsonParser parser = jsonParser();
        //...................0123456789
        final String json = "{'1':2,'2':3}";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(2, jsonRoot.getObjectNode().length());
        assertEquals(3, jsonRoot.getObjectNode().getLong("2"));

        assertEquals(new BigInteger("3"), jsonRoot.getObjectNode().getBigInteger("2"));
        assertEquals(new BigDecimal("3"), jsonRoot.getObjectNode().getBigDecimal("2"));

        assertEquals(2, jsonRoot.getObjectNode().getLong("1"));
        assertEquals(3, jsonRoot.getObjectNode().getInt("2"));
        assertEquals(2, jsonRoot.getObjectNode().getInt("1"));
        assertEquals(NodeType.OBJECT, jsonRoot.getType());
    }

    @Test
    void testSimpleMapFromMap() {
        final JsonParser parser = jsonParser();
        //...................01234567890123456789
        final String json = "{'a':{'a':1}}";
        final RootNode jsonRoot = parser.parse(niceJson(json));
        jsonRoot.tokens().forEach(System.out::println);
        assertEquals(1, jsonRoot.getObjectNode().size());

        assertEquals(NodeType.OBJECT, jsonRoot.getType());
        assertEquals(NodeType.OBJECT, jsonRoot.getObjectNode().type());


        assertEquals(niceJson("{'a':1}"), jsonRoot.getObjectNode().getObjectNode("a").originalString());
        assertEquals(niceJson("{'a':1}"), jsonRoot.getObjectNode().getObjectNode("a").originalCharSequence().toString());

    }

    @Test
    void testSimpleMapFromMap2() {

        final String json = "{'a':{'b':'abc'}}";

        final Map<String, Object> mapOuter = toMap(niceJson(json));
        final Map<String, Object> mapInner = asMap(mapOuter, "a");

        assertEquals("abc", mapInner.get("b").toString());


    }

    @Test
    void testSimpleArrayFromMap() {
        final String json = "{'a':[1,2,3]}";
        final ObjectNode jsonObject = jsonParser().parse(Json.niceJson(json)).asObject();
        final Map<String, Object> map = toMap(niceJson(json));
        final int hash = jsonObject.hashCode();
        final ArrayNode jsonArray = asArray(map, "a");
        assertEquals(1L, jsonArray.getLong(0));
        assertEquals(1, jsonArray.getInt(0));
        assertEquals(hash, jsonObject.hashCode());
        final ObjectNode j2 = jsonParser().parse(Json.niceJson(json)).asObject();
        assertEquals(j2, jsonObject);

        jsonObject.entrySet().forEach(objectObjectEntry -> assertTrue(jsonObject.containsKey(objectObjectEntry.getKey())));
    }

    @Test
    void testSimpleArrayFromMa2p() {
        final String json = "{'a':[1,2,3]}";
        final ObjectNode jsonObject = jsonParser().parse(Json.niceJson(json)).asObject();
        final Map<String, Object> map =  toMap(niceJson(json));
        final int hash = jsonObject.hashCode();
        final List<Object> list = asList(map, "a");
        assertEquals(1L, asLong(list, 0));
        assertEquals(1, asInt(list, 0));
    }

    @Test
    void testSimpleIntFromArray() {
        final String json = "[1,2,3]";
        final ArrayNode jsonArray = toArrayNode(json);
        assertEquals(1L, jsonArray.getLong(0));
        assertEquals(1, jsonArray.getNumberNode(0).intValue());
        assertEquals(1, jsonArray.getNumberNode(0).longValue());
    }

    @Test
    void testSimpleFloatFromArray() {
        final JsonParser parser = jsonParser();
        final String json = "[1.1,2,3]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1.1, jsonRoot.getArrayNode().getDouble(0), 0.001);
        assertEquals(1.1, jsonRoot.getArrayNode().getFloat(0), 0.001);
        assertEquals(new BigDecimal("1.1"), jsonRoot.getArrayNode().getBigDecimal(0));
        assertEquals(new BigInteger("2"), jsonRoot.getArrayNode().getBigInteger(1));

        assertEquals(1.1, jsonRoot.getArrayNode().getNumberNode(0).floatValue(), 0.001);
        assertEquals(1.1, jsonRoot.getArrayNode().getNumberNode(0).doubleValue(), 0.001);
    }

    @Test
    void testSimpleNullFromArray() {
        final JsonParser parser = jsonParser();
        final String json = "[null,2,null]";
        final RootNode jsonRoot = toRootNode(json);
        NullNode nullNode = jsonRoot.getArrayNode().getNullNode(0);
        assertEquals("null", nullNode.toString());

        assertNull(jsonRoot.getArrayNode().get(0));

    }

    @Test
    void testSimpleArrayFromArray() {
        final JsonParser parser = jsonParser();
        final String json = "[[1,2,3],2,3]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1L, jsonRoot.getArrayNode().getArray(0).getLong(0));
        assertEquals(1, jsonRoot.getArrayNode().getArray(0).getNumberNode(0).intValue());
        assertEquals(3, jsonRoot.getArrayNode().getArray(0).length());
    }

    @Test
    void testSimpleArrayFromArray_easy() {
        final JsonParser parser = jsonParser();
        final String json = "[[1],2]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1L, jsonRoot.getArrayNode().getArray(0).getLong(0));
        assertEquals(1, jsonRoot.getArrayNode().getArray(0).getNumberNode(0).intValue());
        assertEquals(1, jsonRoot.getArrayNode().getArray(0).length());
    }
    @Test
    void testSimpleArrayFromArray2() {

        final String json = "[[1,2,3],7,13]";
        final List<Object> listOuter =  toList(niceJson(json));
        final List<Object> listInner = asList(listOuter, 0);

        final ArrayNode arrayInner = asArray(listOuter, 0);

        assertEquals(1, asInt(listInner, 0));
        assertEquals(2, asInt(listInner, 1));
        assertEquals(3, asInt(listInner, 2));

        assertEquals(1, arrayInner.getInt(0));
        assertEquals(2, arrayInner.getInt(1));
        assertEquals(3, arrayInner.getInt(2));
        assertEquals(7, asInt(listOuter, 1));
        assertEquals(13, asInt(listOuter, 2));

    }


    @Test
    void testGeFloatFromMap() {
        final JsonParser parser = jsonParser();
        final String json = "{'a':1.1}";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1.1, jsonRoot.getObjectNode().getDouble("a"), 0.001);
        assertEquals(1.1, jsonRoot.getObjectNode().getFloat("a"), 0.001);

        assertEquals(NodeType.OBJECT, jsonRoot.getType());
    }

    @Test
    void testGetIntFromMap() {
        final JsonParser parser = jsonParser();
        //...................0123
        final String json = "{'a':1}";
        final RootNode jsonRoot = jsonParser().parse(Json.niceJson(json));
        System.out.println(jsonRoot.tokens());
        assertEquals(1, jsonRoot.getObjectNode().getLong("a"));
        assertEquals(1, jsonRoot.getObjectNode().getNumberNode("a").intValue());
        assertEquals(NodeType.OBJECT, jsonRoot.getType());
    }

    @Test
    void testGetItemFromMap() {
        final JsonParser parser = jsonParser();
        final String json = "{'a':1}";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertNotNull(jsonRoot.getObjectNode().getNumberNode("a"));
        assertNull(jsonRoot.getObjectNode().getNumberNode("b"));
        assertNull(jsonRoot.getObjectNode().getNumberNode("abc"));

    }

    @Test
    void testSimpleList() {
        final JsonParser parser = jsonParser();

        System.out.println(parser.getClass().getName());
        final String json = "['h','a',true,false]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        String s = jsonRoot.getArrayNode().getStringNode(0).toString();
        assertEquals("h", "h");
        assertEquals("a", jsonRoot.getArrayNode().getStringNode(1).toString());
        assertEquals("a", jsonRoot.getArrayNode().getString(1));
        assertTrue(jsonRoot.getArrayNode().getBooleanNode(2).booleanValue());
        assertFalse(jsonRoot.getArrayNode().getBooleanNode(3).booleanValue());
    }

    @Test
    void testSimpleListWithInts() {
        final JsonParser parser = jsonParser();
        final String json = "[1,3]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));
        assertEquals(1L, jsonRoot.getArrayNode().getLong(0));
        assertEquals(3L, jsonRoot.getArrayNode().getLong(1));
    }

    @Test
    void testObjectGetOperation() {

        final JsonParser parser = jsonParser();
        //...................012345678
        final String json = "{'h':'a'}";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));

        String hVal = jsonRoot.getObjectNode().getStringNode("h").toString();
        assertEquals("a", hVal);

    }

    @Test
    void testSimpleObjectWithNumber() {

        final JsonParser parser = jsonParser();
        //...................012345678
        final String json = "{'h' : -1 }";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));

        final int hVal = jsonRoot.getObjectNode().getNumberNode("h").intValue();
        assertEquals(-1, hVal);

    }

    @Test
    void testSingletonListWithOneObject() {

        final JsonParser parser = jsonParser();
        final String json = "[ { 'h' : 'a' } ]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));

        String hVal = jsonRoot.getArrayNode().getObject(0).getStringNode("h").toString();
        assertEquals("a", "a");

    }

    @Test
    void testSingletonListWithOneObjectNoSpaces() {

        final JsonParser parser = jsonParser();
        final String json = "[{'h':'a'}]";
        final RootNode jsonRoot = parser.parse(Sources.stringSource(json.replace("'", "\"")));

        final ObjectNode object = jsonRoot.getArrayNode().getObject(0);

        final StringNode h = object.getStringNode("h");

        final String hVal = h.toString();
        assertEquals("a", hVal);

    }

    @Test
    void testSingletonListWithOneObjectNoSpaces2() {
        final String json = "[{'h':'a'}]";
        final List<Object> list =  toList(niceJson(json));
        final Map<String, Object> map = asMap(list, 0);
        assertEquals("a", asString(map, "h"));
    }



    @Test
    void allowedEscapes() {
        final JsonParser parser = jsonParser();
        final String json =   "['`b`f`n`r`t`/']";
        final RootNode root = parser.parse(Json.niceJson(json));
        assertEquals("\b\f\n\r\t/", root.asArray().get(0).asScalar().toString());
    }

    @Test
    void allowedEscapes2() {
        final JsonParser parser = jsonParser();
        final String json =   "['``']";
        final RootNode root = parser.parse(Json.niceJson(json));
        assertEquals("\\", root.asArray().get(0).asScalar().toString());
    }


    @Test
    void testTooManySignOperators() {
        final JsonParser parser = jsonParser();

        final String json = "123.1e-+1";
        try {
            final List<Token> tokens = parser.scan(Sources.stringSource(json));
            assertTrue(false);
        } catch (Exception ex) {

        }

    }

}
