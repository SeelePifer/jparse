package com.cloudurable.jparse;

import com.cloudurable.jparse.node.RootNode;
import com.cloudurable.jparse.path.PathElement;
import com.cloudurable.jparse.path.PathNode;
import com.cloudurable.jparse.source.CharSource;
import com.cloudurable.jparse.source.Sources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import org.noggit.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class BenchMark {


    final static String jsonData;

    final static TypeReference<HashMap<String, Object>> mapTypeRef
            = new TypeReference<>() {
    };


    final static TypeReference<List<Object>> listObjects
            = new TypeReference<>() {
    };

    final static TypeReference<List<Double>> listDoubleTypeRef
            = new TypeReference<>() {
    };

    final static TypeReference<List<BigDecimal>> listBigDTypeRef
            = new TypeReference<>() {
    };

    final static TypeReference<List<Float>> listFloatTypeRef
            = new TypeReference<>() {
    };


    final static ObjectMapper mapper = new ObjectMapper();

    //final static String objectPath = "glossary.GlossDiv.GlossList.GlossEntry.GlossDef.para";

    final static String objectPath = "['web-app'].servlet[0]['init-param'].useJSP";
    //final static String objectPath = "1/1";


    static {
        try {
            //final File file = new File("./src/test/resources/json/glossary.json");
            //final File file = new File("./src/test/resources/json/doubles.json");
            //final File file = new File("./src/test/resources/json/ints.json");

            final File file = new File("./src/test/resources/json/webxml.json");
            //final File file = new File("./src/test/resources/json/types.json");
            final CharSource charSource = Sources.fileSource(file);
            jsonData = charSource.toString().trim();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
//
//    @Benchmark
//    public void readWebJSONJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//
    @Benchmark
    public void readWebJSONJParse(Blackhole bh) {
        bh.consume(new JsonParser().parse(jsonData));
    }

    @Benchmark
    public void readWebJSONNoggitStax(Blackhole bh) throws Exception {

        final var jsonParser =  new JSONParser(jsonData);

        int event = -1;
        while (event!=JSONParser.EOF) {
            event = jsonParser.nextEvent();
        }

        bh.consume(event);
    }
    @Benchmark
    public void readWebJSONNoggitObjectBuilder(Blackhole bh) throws Exception {

        bh.consume(ObjectBuilder.fromJSON(jsonData));
    }

//
//    @Benchmark
//    public void readGlossaryJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//
//    @Benchmark
//    public void readGlossaryJParse(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData));
//    }
//
//    @Benchmark
//    public void readGlossaryNoggit(Blackhole bh) throws Exception {
//
//        final var jsonParser =  new JSONParser(jsonData);
//
//        int event = -1;
//        while (event!=JSONParser.EOF) {
//            event = jsonParser.nextEvent();
//        }
//
//        bh.consume(event);
//    }
//
//    @Benchmark
//    public void readWebGlossaryNoggitObjectBuilder(Blackhole bh) throws Exception {
//
//        bh.consume(ObjectBuilder.fromJSON(jsonData));
//    }

//    @Benchmark
//    public void simpleParseJJson(Blackhole bh) {
//        bh.consume(new JsonParser().scan(jsonData));
//    }
//
//    @Benchmark
//    public void simpleDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData));
//    }


//    @Benchmark
//    public void simpleFullBigDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObjectBig());
//    }
//
//    @Benchmark
//    public void simpleFullMediumDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObject());
//    }

//    @Benchmark
//    public void simpleFullSmallDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObjectSmall());
//    }

//    @Benchmark
//    public void simpleFullSmallDeserializeJJsonThenWalk(Blackhole bh) {
//        bh.consume(TestUtils.walkFull(new JsonParser().parse(jsonData).asCompleteObjectSmall()));
//    }
//
//    @Benchmark
//    public void simpleDeserializeJacksonThenWalk(Blackhole bh) throws JsonProcessingException {
//        bh.consume(TestUtils.walkFull(mapper.readValue(jsonData, mapTypeRef)));
//    }


    public static void main(String... args) throws Exception {

        try {
//            System.out.println(Path.atPath(objectPath, new JsonParser().parse(jsonData)));
//            DocumentContext jsonContext = JsonPath.parse(jsonData);
//            Boolean result = jsonContext.read(objectPath);
//            System.out.println(result);


           final var jsonParser =  new JSONParser(jsonData);

           while (jsonParser.nextEvent()!=JSONParser.EOF) {

           }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//
//        try {
//            long startTime = System.currentTimeMillis();
//
//            for (int i = 0; i < 1_500_000; i++) {
//
//                final RootNode root = new JsonParser().parse(jsonData);
//                final var result = Path.atPath(objectPath, root);
//
//                //PathNode pathElements = Path.toPath("foo.bar.baz[99][0][10][11]['hi mom']");
//
//
//                if (i % 100_000 == 0) {
//                    System.out.printf("Elapsed time %s %s \n", ((System.currentTimeMillis() - startTime) / 1000.0), result);
//                }
//            }
//            System.out.println("Total Elapsed time " + ((System.currentTimeMillis() - startTime) / 1000.0));
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

//
//    @Benchmark
//    public void pathParse(Blackhole bh) {
//      bh.consume(Path.toPath("foo.bar.baz[99][0][10][11]['hi mom']"));
//    }
//
//    @Benchmark
//    public void jParseLongArrayFast(Blackhole bh) {
//        bh.consume(Json.toLongArrayFast(jsonData));
//    }
//
//
//    @Benchmark
//    public void jParseLongArray(Blackhole bh) {
//        bh.consume(Json.toLongArray(jsonData));
//    }
//
//    @Benchmark
//    public void jacksonLongArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, long[].class));
//    }
//
//    @Benchmark
//    public void jParseIntArray(Blackhole bh) {
//        bh.consume(Json.toIntArray(jsonData));
//    }
//
//    @Benchmark
//    public void jParseIntArrayFast(Blackhole bh) {
//        bh.consume(Json.toIntArrayFast(jsonData));
//    }
//
//    @Benchmark
//    public void jacksonIntArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, int[].class));
//    }
//
//
//    @Benchmark
//    public void jParseBigIntArray(Blackhole bh) {
//        bh.consume(Json.toBigIntegerArray(jsonData));
//    }
//
//    @Benchmark
//    public void jacksonBigIntArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, BigInteger[].class));
//    }


//
//    @Benchmark
//    public void jParseBigDecimalArray(Blackhole bh) {
//        bh.consume(Json.toBigDecimalArray(jsonData));
//    }
//
//    @Benchmark
//    public void jacksonBigDecimalArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, BigDecimal[].class));
//    }
//
//
//    @Benchmark
//    public void jParseFloatArray(Blackhole bh) {
//        bh.consume(Json.toFloatArray(jsonData));
//    }
//
//    @Benchmark
//    public void jParseFloatArrayFast(Blackhole bh) {
//        bh.consume(Json.toFloatArrayFast(jsonData));
//    }
//
//
//    @Benchmark
//    public void jacksonFloatArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, float[].class));
//    }
//
//
//    @Benchmark
//    public void jParseDoubleArray(Blackhole bh) {
//        bh.consume(Json.toDoubleArray(jsonData));
//    }
//
//    @Benchmark
//    public void jParseDoubleArrayFast(Blackhole bh) {
//        bh.consume(Json.toDoubleArrayFast(jsonData));
//    }
//
//
//    @Benchmark
//    public void jacksonDoubleArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, double[].class));
//    }
//
//
//    @Benchmark
//    public void jParseBigDecimalArrayFast(Blackhole bh) {
//        bh.consume(Json.toBigDecimalArray(jsonData));
//    }
//
//
//    @Benchmark
//    public void jacksonBigDecimalArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, BigDecimal[].class));
//    }


//
//    @Benchmark
//    public void simpleDeserializeJacksonWebXML(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//
//
//    @Benchmark
//    public void simpleDeserializeJJsonWebXML(Blackhole bh) {
//
//        final JsonParser jsonParser = new JsonParser();
//
//        bh.consume(jsonParser.parse(jsonData));
//    }

//        @Benchmark
//    public void simpleDeserializeJacksonWebXMLTenTimes(Blackhole bh) throws JsonProcessingException {
//        for (int i = 0; i < 100; i++)
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//
//
//    @Benchmark
//    public void simpleDeserializeJJsonWebXMLTenTimes(Blackhole bh) {
//
//        final JsonParser jsonParser = new JsonParser();
//
//        for (int i = 0; i < 100; i++)
//        bh.consume(jsonParser.parse(jsonData));
//    }

//    @Benchmark
//    public void simpleDeserializeJaywayThenPathGrab(Blackhole bh) throws JsonProcessingException {
//
//        DocumentContext jsonContext = JsonPath.parse(jsonData);
//        String result = jsonContext.read(objectPath);
//        bh.consume(result);
//    }
//    @Benchmark
//    public void simpleDeserializeJParseThenPathGrab(Blackhole bh) {
//        bh.consume(Path.atPath(objectPath, new JsonParser().parse(jsonData)));
//    }

//    @Benchmark
//    public void simpleDeserializeJaywayThenPathGrabWEBXML(Blackhole bh) throws JsonProcessingException {
//
//        DocumentContext jsonContext = JsonPath.parse(jsonData);
//        Boolean result = jsonContext.read(objectPath);
//        bh.consume(result);
//    }
//
//    static final PathNode path =      Path.toPath(objectPath);
//    @Benchmark
//    public void simpleDeserializeJParseThenPathGrabWEBXML(Blackhole bh) {
//        bh.consume(Path.atPath(path, new JsonParser().parse(jsonData)));
//    }


//    @Benchmark
//    public void simpleDeserializeJParseThenPathGrabOld(Blackhole bh) {
//        bh.consume(PathUtils.getLastObject(objectPath, new JsonParser().parse(jsonData)));
//    }

//    @Benchmark
//    public void simpleDeserializeJacksonThenPathGrab(Blackhole bh) throws JsonProcessingException {
//        bh.consume(PathUtils.getLastObject(objectPath, mapper.readValue(jsonData, mapTypeRef)));
//    }


//    @Benchmark
//    public void deserializeIntoMapJParse(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData));
//    }
//
//    @Benchmark
//    public void deserializeIntoMapJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//
//    @Benchmark
//    public void deserializeIntoMapJParseAndGetPathThenSerialize(Blackhole bh) {
//        final var map = Json.toMap(jsonData);
//        final var map2 = map.get("glossary");
//        final var map3 = ((Map<String, Object>) map2).get("GlossDiv");
//        bh.consume(map);
//        bh.consume(Json.serialize(map3));
//    }
//
//    @Benchmark
//    public void deserializeIntoMapJacksonAndGetPathThenSerialize(Blackhole bh) throws JsonProcessingException {
//        final var map = mapper.readValue(jsonData, mapTypeRef);
//        final var map2 = map.get("glossary");
//        final var map3 = ((Map<String, Object>) map2).get("GlossDiv");
//        bh.consume(map);
//        bh.consume(mapper.writeValueAsString(map3));
//    }


//    @Benchmark
//    public void deserializeIntoMapJParseAndSerialize(Blackhole bh) {
//        final var map = Json.toMap(jsonData);
//        bh.consume(Json.serialize(map));
//    }
//
//    @Benchmark
//    public void deserializeIntoMapJacksonAndSerialize(Blackhole bh) throws JsonProcessingException {
//        final var map = mapper.readValue(jsonData, mapTypeRef);
//        bh.consume(mapper.writeValueAsString(map));
//    }

//    @Benchmark
//    public void simpleParseJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readTree(jsonData));
//    }


//    @Benchmark
//    public void simpleDeserializeJJsonFull(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).getString().toString());
//    }
//
//    @Benchmark
//    public void simpleDeserializeJJsonFullEncoded(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).getString().toEncodedString());
//    }


//    @Benchmark
//    public void simpleDeserializeJacksonString(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, String.class));
//    }
//    @Benchmark
//    public void simpleDeserializeJacksonDoubleList(Blackhole bh) throws JsonProcessingException {
//        //bh.consume(mapper.readValue(jsonData, mapTypeRef));
//        bh.consume(mapper.readValue(jsonData, listDoubleTypeRef));
//    }
//
//    @Benchmark
//    public void simpleDeserializeJacksonFloatList(Blackhole bh) throws JsonProcessingException {
//        //bh.consume(mapper.readValue(jsonData, mapTypeRef));
//        bh.consume(mapper.readValue(jsonData, listFloatTypeRef));
//    }
//    @Benchmark
//    public void simpleDeserializeJacksonBigDecimalList(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, listBigDTypeRef));
//    }
//
//
//    @Benchmark
//    public void simpleFullBigDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObjectBig());
//    }
//
//    @Benchmark
//    public void simpleFullMediumDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObject());
//    }
//
//    @Benchmark
//    public void simpleFullSmallDeserializeJJson(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData).asCompleteObjectSmall());
//    }
}
