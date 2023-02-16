package com.cloudurable.jparse;

import com.cloudurable.jparse.node.RootNode;
import com.cloudurable.jparse.source.CharSource;
import com.cloudurable.jparse.source.Sources;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    final static String webXmlJson;

    static {
        try {
            final File file = new File("./src/test/resources/json/webxml.json");
            final CharSource charSource = Sources.fileSource(file);
            webXmlJson = charSource.toString().trim();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

    }

    @Test
    void testBasicListIndex() {
        final String json = "[1,2,3]";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        assertEquals(1, Path.atPath("[0]", rootNode.getNode()).asScalar().value());
        assertEquals(3, Path.atPath("[2]", rootNode.getNode()).asScalar().value());
        assertEquals(2, Path.atPath("[1]", rootNode.getNode()).asScalar().value());
    }

    @Test
    void testBasicObjectKeyLookup() {
        final String json = "{'a':'b', 'b':'c', 'c':'d'}";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        assertEquals("b", Path.atPath("a", rootNode.getNode()).asScalar().value());
        assertEquals("c", Path.atPath("b", rootNode.getNode()).asScalar().value());
        assertEquals("d", Path.atPath("c", rootNode.getNode()).asScalar().value());
    }

    @Test
    void nestedArray() {
        final String json = "[1,2,[1,2,3]]";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        assertEquals(2, Path.atPath("[2][1]", rootNode.getNode()).asScalar().value());
    }

    @Test
    void nestedMap() {
        final String json = "{'a':'b', 'b':'c', 'c': {'a':'b', 'b':'c', 'c':'d'}}";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        assertEquals("b", Path.atPath("c.a", rootNode.getNode()).asScalar().value());
    }

    @Test
    void nestedMapList() {
        final String json = "{'a':'b', 'b':'c', 'c': [1,2,3]}";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        final var node = Path.atPath("c[0]", rootNode.getNode()).asScalar();
        Object value = node.value();
        assertEquals(1, value);
    }

    @Test
    void nestedMapList2() {
        final String json = "{'a':'b', 'b':'c', 'c': [1,2,3]}";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        final var node = Path.atPath("['c'][0]", rootNode.getNode()).asScalar();
        Object value = node.value();
        assertEquals(1, value);
    }

    @Test
    void nestListMap() {
        final String json = "[1,2,{'a':'b', 'b':'c', 'c':'d'}]";
        RootNode rootNode = Json.toRootNode(Json.niceJson(json));
        assertEquals("b", Path.atPath("[2].a", rootNode.getNode()).asScalar().value());
    }


    @Test
    void webXMLBug() {

        final  String objectPath = "['web-app'].servlet[0]['init-param'].useJSP";


        RootNode rootNode = Json.toRootNode(webXmlJson);
        Object value = Path.atPath(objectPath, rootNode.getNode()).asScalar().value();


        System.out.println(value);
    }
}
