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

import io.nats.jparse.node.RootNode;
import io.nats.jparse.parser.JsonEventParser;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.source.Sources;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.spi.TypeLiteral;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@State(value = Scope.Benchmark)
public class BenchMark {


    JsonParser fastParser = Json.builder().setStrict(false).build();

    JsonParser funcParser = Json.builder().setAllowComments(true)
            .setObjectsKeysCanBeEncoded(false)
            .setSupportNoQuoteKeys(false).build();

    JsonParser strictParser = Json.builder().setStrict(true).build();
    JsonEventParser fastEventParser =  Json.builder().setStrict(false).buildEventParser();
    JsonEventParser strictEventParser =  Json.builder().setStrict(true).buildEventParser();



    final static String jsonData;
    final static String doublesJsonData;
    final static String intsJsonData;
    final static String webXmlJsonData;
    final static String glossaryJsonData;




    final static TypeReference<HashMap<String, Object>> mapTypeRef
            = new TypeReference<>() {
    };

    final static TypeLiteral<Map<String, Object>> mapTypeRefJI = new TypeLiteral<>() {

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

    final static String glossaryObjectPath = "glossary.GlossDiv.GlossList.GlossEntry.GlossDef.para";
    final static String webXmlObjectPath = "['web-app'].servlet[0]['init-param'].useJSP";

    //final static String objectPath = "1/1";


    static {
        try {
            //final File file = new File("./src/test/resources/json/glossary.json");
            //final File file = new File("./src/test/resources/json/doubles.json");
            //final File file = new File("./src/test/resources/json/ints.json");

            final File file = new File("./src/test/resources/json/webxml.json");
            //final File file = new File("./src/test/resources/json/types.json");


            intsJsonData = Sources.fileSource(new File("./src/test/resources/json/ints.json")).toString().trim();
            doublesJsonData = Sources.fileSource(new File("./src/test/resources/json/doubles.json")).toString().trim();
            glossaryJsonData = Sources.fileSource(new File("./src/test/resources/json/glossary.json")).toString().trim();
            webXmlJsonData = Sources.fileSource(new File("./src/test/resources/json/webxml.json")).toString().trim();
            jsonData = webXmlJsonData;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void main(String... args) throws Exception {


        try {
            long startTime = System.currentTimeMillis();


            for (int i = 0; i < 10_500_000; i++) {

//                final RootNode root = new JsonParser().parse(webXmlJsonData);
//                final var result = Path.atPath(webXmlObjectPath, root);

                final RootNode result = Json.builder().setStrict(true).build().parse(webXmlJsonData);

                //PathNode pathElements = Path.toPath("foo.bar.baz[99][0][10][11]['hi mom']");


                if (i % 100_000 == 0) {
                    System.out.printf("Elapsed time %s %s \n", ((System.currentTimeMillis() - startTime) / 1000.0), result);
                }
            }
            System.out.println("Total Elapsed time " + ((System.currentTimeMillis() - startTime) / 1000.0));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//
//
//    @Benchmark
//    public void readWebJSONJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(jsonData, mapTypeRef));
//    }
//

//
//    @Benchmark
//    public void readWebJSONNoggitStax(Blackhole bh) throws Exception {
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
//    @Benchmark
//    public void readWebJSONNoggitObjectBuilder(Blackhole bh) throws Exception {
//
//        bh.consume(ObjectBuilder.fromJSON(jsonData));
//    }
//

//
//    @Benchmark
//    public void readWebJSONNoggitStax(Blackhole bh) throws Exception {
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
//    @Benchmark
//    public void readWebJSONJParse(Blackhole bh) {
//        bh.consume(new JsonParser().parse(jsonData));
//    }
//

//    @Benchmark
//    public void readWebJSONANoggitObjectBuilder(Blackhole bh) throws Exception {
//
//        bh.consume(ObjectBuilder.fromJSON(jsonData));
//    }

//
//    @Benchmark
//    public void readWebJSONJParseFast(Blackhole bh) {
//        bh.consume(fastParser.parse(webXmlJsonData));
//    }
//    @Benchmark
//    public void readWebJSONJParseFunc(Blackhole bh) {
//        bh.consume(funcParser.parse(webXmlJsonData));
//    }
//    @Benchmark
//    public void readWebJSONJParseStrict(Blackhole bh) {
//        bh.consume(strictParser.parse(webXmlJsonData));
//    }
//
//    @Benchmark
//    public void readWebJsonJsonIter(Blackhole bh) throws Exception{
//        JsonIterator iter = JsonIterator.parse(webXmlJsonData);
//        bh.consume(iter);
//        bh.consume(iter.read(mapTypeRefJI));
//    }
//
//    @Benchmark
//    public void readWebJsonJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(webXmlJsonData, mapTypeRef));
//    }
//        @Benchmark
//    public void readGlossaryJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(glossaryJsonData, mapTypeRef));
//    }
//
    @Benchmark
    public void readGlossaryJParseFast(Blackhole bh) {
        bh.consume(fastParser.parse(glossaryJsonData));
    }

    @Benchmark
    public void readGlossaryJParseFunc(Blackhole bh) {
        bh.consume(funcParser.parse(glossaryJsonData));
    }
//
//    @Benchmark
//    public void readGlossaryJParseStrict(Blackhole bh) {
//        bh.consume(strictParser.parse(glossaryJsonData));
//    }
//
//    @Benchmark
//    public void readGlossaryJsonIter(Blackhole bh) throws Exception{
//        JsonIterator iter = JsonIterator.parse(glossaryJsonData);
//        bh.consume(iter.read(mapTypeRefJI));
//    }
//
//    @Benchmark
//    public void readGlossaryJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(glossaryJsonData, mapTypeRef));
//    }

    //mapTypeRef

//    @Benchmark
//    public void readGlossaryStrictJParse(Blackhole bh) {
//        bh.consume(strictParser.parse(glossaryJsonData));
//    }
//
//
//    @Benchmark
//    public void readGlossaryEventStrictJParse(Blackhole bh) throws Exception {
//
//        final int [] token = new int[1];
//        final var events = new TokenEventListener() {
//            @Override
//            public void start(int tokenId, int index, CharSource source) {
//                token[0] = tokenId;
//            }
//
//            @Override
//            public void end(int tokenId, int index, CharSource source) {
//                token[0] = tokenId;
//            }
//        };
//
//        strictEventParser.parseWithEvents(glossaryJsonData, events);
//
//        bh.consume(token);
//    }
//
//
//    @Benchmark
//    public void readGlossaryEventFastJParse(Blackhole bh) throws Exception {
//
//        final int [] token = new int[1];
//        final var events = new TokenEventListener() {
//            @Override
//            public void start(int tokenId, int index, CharSource source) {
//                token[0] = tokenId;
//            }
//
//            @Override
//            public void end(int tokenId, int index, CharSource source) {
//                token[0] = tokenId;
//            }
//        };
//
//
//        fastEventParser.parseWithEvents(glossaryJsonData, events);
//
//        bh.consume(token);
//    }


//    @Benchmark
//    public void readWebJSONJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(webXmlJsonData, mapTypeRef));
//    }
//
//    @Benchmark public void readWebJsonNats(Blackhole bh) throws JsonParseException{
//        io.nats.client.support.JsonParser.parse(webXmlJsonData);
//    }

//    @Benchmark
//    public void readGlossaryJackson(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(glossaryJsonData, mapTypeRef));
//    }
//
//    @Benchmark public void readNatsJsonGlossary(Blackhole bh) throws JsonParseException{
//        io.nats.client.support.JsonParser.parse(glossaryJsonData);
//    }
//
//    @Benchmark
//    public void readGlossaryJParse(Blackhole bh) {
//        bh.consume(new JsonParser().parse(glossaryJsonData));
//    }

//    @Benchmark
//    public void readGlossaryJParseWithEvents(Blackhole bh) {
//        bh.consume(new JsonEventParser().parse(glossaryJsonData));
//    }

//    @Benchmark
//    public void readGlossaryNoggitEvent(Blackhole bh) throws Exception {
//
//        final var jsonParser =  new JSONParser(glossaryJsonData);
//
//        int event = -1;
//        while (event!=JSONParser.EOF) {
//            event = jsonParser.nextEvent();
//        }
//
//        bh.consume(event);
//    }




//    @Benchmark
//    public void readWebGlossaryNoggitObjectBuilder(Blackhole bh) throws Exception {
//
//        bh.consume(ObjectBuilder.fromJSON(glossaryJsonData));
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




//    @Benchmark
//    public void pathParse(Blackhole bh) {
//      bh.consume(Path.toPath("foo.bar.baz[99][0][10][11]['hi mom']"));
//    }
//


//    @Benchmark
//    public void jParseStrictLongArray(Blackhole bh) {
//        bh.consume(this.strictParser.parse(intsJsonData).asArray().getLongArray());
//    }

    @Benchmark
    public void jParseFuncLongArray(Blackhole bh) {
        bh.consume(this.funcParser.parse(intsJsonData).asArray().getLongArray());
    }

    @Benchmark
    public void jParseFastLongArray(Blackhole bh) {
        bh.consume(this.fastParser.parse(intsJsonData).asArray().getLongArray());
    }
//
//
//    @Benchmark
//    public void jacksonLongArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(intsJsonData, long[].class));
//    }
//
//    @Benchmark
//    public void jsonIteratorLongArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(JsonIterator.deserialize(intsJsonData, long[].class));
//    }
//




//    @Benchmark
//    public void jParseStrictFloatArray(Blackhole bh) {
//        bh.consume(this.strictParser.parse(doublesJsonData).asArray().getFloatArray());
//    }

//
    @Benchmark
    public void jParseFuncFloatArray(Blackhole bh) {
        bh.consume(this.funcParser.parse(doublesJsonData).asArray().getFloatArray());
    }

    @Benchmark
    public void jParseFastFloatArray(Blackhole bh) {
        bh.consume(this.fastParser.parse(doublesJsonData).asArray().getFloatArray());
    }
//
//
//
//    @Benchmark
//    public void jacksonFloatArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(doublesJsonData, float[].class));
//    }
//
//
//    @Benchmark
//    public void jsonIteratorFloatArray(Blackhole bh) {
//        bh.consume(JsonIterator.deserialize(doublesJsonData, float[].class));
//    }

//
//
//    @Benchmark
//    public void jParseStrictDoubleArray(Blackhole bh) {
//        bh.consume(this.strictParser.parse(doublesJsonData).asArray().getDoubleArray());
//    }

//    @Benchmark
//    public void jParseStrictDoubleArrayFast(Blackhole bh) {
//        bh.consume(this.strictParser.parse(doublesJsonData).asArray().getDoubleArrayFast());
//    }
//
//    @Benchmark
//    public void jParseFastDoubleArrayFast(Blackhole bh) {
//        bh.consume(this.fastParser.parse(doublesJsonData).asArray().getDoubleArrayFast());
//    }
//
//    @Benchmark
//    public void jParseFastDoubleArray(Blackhole bh) {
//        bh.consume(this.fastParser.parse(doublesJsonData).asArray().getDoubleArray());
//    }
//
//    @Benchmark
//    public void jacksonDoubleArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(doublesJsonData, double[].class));
//    }
//
//
//    @Benchmark
//    public void jParseStrictIntArray(Blackhole bh) {
//        bh.consume(this.strictParser.parse(intsJsonData).asArray().getIntArray());
//    }
//
//    @Benchmark
//    public void jParseFastIntArray(Blackhole bh) {
//        bh.consume(this.fastParser.parse(intsJsonData).asArray().getIntArray());
//    }
//
//
//
//    @Benchmark
//    public void jacksonIntArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(intsJsonData, int[].class));
//    }
//
//    @Benchmark
//    public void jParseStrictBigIntArray(Blackhole bh) {
//        bh.consume(this.strictParser.parse(intsJsonData).asArray().getBigIntegerArray());
//    }
//
//    @Benchmark
//    public void jParseFastBigIntArray(Blackhole bh) {
//        bh.consume(this.fastParser.parse(intsJsonData).asArray().getBigIntegerArray());
//    }
//
//    @Benchmark
//    public void jacksonBigIntArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(intsJsonData, BigInteger[].class));
//    }
//
//    @Benchmark
//    public void jParseStrictBigDecimalArray(Blackhole bh) {
//        bh.consume(strictParser.parse(doublesJsonData).asArray().getBigDecimalArray());
//    }
//
//    @Benchmark
//    public void jParseFastBigDecimalArray(Blackhole bh) {
//        bh.consume(fastParser.parse(doublesJsonData).asArray().getBigDecimalArray());
//    }
//
//
//    @Benchmark
//    public void jacksonBigDecimalArray(Blackhole bh) throws JsonProcessingException {
//        bh.consume(mapper.readValue(doublesJsonData, BigDecimal[].class));
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
//
//    @Benchmark
//    public void jaywayThenPathGrabGlossary(Blackhole bh) throws JsonProcessingException {
//
//        DocumentContext jsonContext = JsonPath.parse(glossaryJsonData);
//        String result = jsonContext.read(glossaryObjectPath);
//        bh.consume(result);
//    }
//    @Benchmark
//    public void jparsePathGrabGlossary(Blackhole bh) {
//        bh.consume(Path.atPath(glossaryObjectPath, new JsonParser().parse(glossaryJsonData)));
//    }
//
//    @Benchmark
//    public void jaywayPathGrabWebXML(Blackhole bh) throws JsonProcessingException {
//
//        DocumentContext jsonContext = JsonPath.parse(webXmlJsonData);
//        Boolean result = jsonContext.read(webXmlObjectPath);
//        bh.consume(result);
//    }
//
//    @Benchmark
//    public void jparsePathGrabWebXML(Blackhole bh) {
//        bh.consume(Path.atPath(webXmlObjectPath, new JsonParser().parse(webXmlJsonData)));
//    }
//
//
//    @Benchmark
//    public void jacksonPathGrabWebXML(Blackhole bh) throws JsonProcessingException {
//        bh.consume(PathUtils.getLastObject(webXmlObjectPath, mapper.readValue(webXmlJsonData, mapTypeRef)));
//    }
//
//    @Benchmark
//    public void jacksonPathGrabGlossary(Blackhole bh) throws JsonProcessingException {
//        bh.consume(PathUtils.getLastObject(glossaryObjectPath, mapper.readValue(glossaryJsonData, mapTypeRef)));
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
//        final var map = Json.toMap(glossaryJsonData);
//        final var map2 = map.get("glossary");
//        final var map3 = ((Map<String, Object>) map2).get("GlossDiv");
//        bh.consume(map);
//        bh.consume(Json.serialize(map3));
//    }
//
//    @Benchmark
//    public void deserializeIntoMapJacksonAndGetPathThenSerialize(Blackhole bh) throws JsonProcessingException {
//        final var map = mapper.readValue(glossaryJsonData, mapTypeRef);
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
