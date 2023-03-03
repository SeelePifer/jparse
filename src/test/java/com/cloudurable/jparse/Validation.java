package com.cloudurable.jparse;

import com.cloudurable.jparse.node.RootNode;
import com.cloudurable.jparse.parser.JsonIndexOverlayParser;
import com.cloudurable.jparse.source.CharSource;
import com.cloudurable.jparse.source.Sources;

import java.io.File;

public class Validation {

    public static void main(String... args) {
        try {
            final File file = new File("./src/test/resources/validation/");


            System.out.println("Event Strict Parser");
            final boolean showPass = false;
            final boolean showFail = false;
            validateParser(file, (JsonIndexOverlayParser) Json.builder().setStrict(true).buildEventParser(), showFail, showPass);

            System.out.println("Strict Parser");
            final var jsonParser = Json.builder().setStrict(true).build();
            validateParser(file, jsonParser, showFail, showPass);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static void validateParser(File file, JsonIndexOverlayParser jsonParser,
                                       boolean showFail, boolean showPass) {
        int[] result1 = validate(file, "y_", showFail, showPass, jsonParser);
        int[] result2 = validate(file, "i_", showFail, showPass, jsonParser);
        int[] result3 = validate(file, "n_", showFail, showPass, jsonParser);

        System.out.printf("Passed Mandatory %d Failed %d \n", result1[0], result1[1]);
        System.out.printf("Passed Optional %d Failed %d \n", result2[0], result2[1]);
        System.out.printf("Passed Garbage %d Failed %d \n", result3[0], result3[1]);
    }

    private static int[] validate(File file, String match, boolean showFail, boolean showPass,
                                  JsonIndexOverlayParser jsonParser) {


        try {
            int pass = 0;
            int error = 0;
            for (File listFile : file.listFiles()) {
                if (listFile.getName().startsWith(match)) {

                    final CharSource cs = Sources.fileSource(listFile);
                    final var jsonString = cs.toString();

                    //System.out.println("TESTING " + listFile);
                    try {

                        RootNode root = jsonParser.parse(jsonString);
                        if (showPass) {
                            System.out.println("PASS! " + listFile);
                            System.out.println(cs);
                            System.out.println();
                        }
                        pass++;
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                        if (showFail) {
                            System.out.println("FAIL! " + listFile);
                            System.out.println(cs);
                            System.out.println();
                        }
                        error++;
                    }
                }
            }

            return new int[]{pass, error};
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return new int[]{-1, -1};
    }
}
