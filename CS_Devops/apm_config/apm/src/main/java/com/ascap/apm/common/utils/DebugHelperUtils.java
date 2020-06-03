package com.ascap.apm.common.utils;

/**
 * This is just for iterating thru collections etc and logging utility methods
 * 
 * @author shyam_narayana
 */
public class DebugHelperUtils {

    private DebugHelperUtils() {
    }

    private static final int MAX_RESULTS_TO_SHOW = 10;

    private static final String TOTAL_COLLECTION_SIZE = "Total Collection Size = ";

    private static final String DISPLAYS_ONLY = ", displays only ";

    private static final String ITEMS = " items {";

    /**
     * Method debugCollections iterates thru it members gets its toString and builds a string and returns it. the inColl
     * is not modified in any manner.
     * 
     * @param collName string name to be used
     * @param inColl collection to iterate for the dump
     * @return String output string dump generated
     */
    public static String debugCollections(String collName, java.util.List<Object> inColl) {
        StringBuilder outBuff;
        java.util.Iterator<?> itr;
        int itemNo = 1;
        outBuff = new StringBuilder("Collection ").append(collName).append(" = ");

        if (inColl == null) {
            outBuff.append("null");
            return outBuff.toString();
        } else {
            if (inColl.isEmpty()) {
                outBuff.append(" isEmpty ");
                return outBuff.toString();
            } else {
                outBuff.append(TOTAL_COLLECTION_SIZE).append(inColl.size()).append(DISPLAYS_ONLY)
                    .append(MAX_RESULTS_TO_SHOW).append(ITEMS);
                itr = inColl.iterator();
                while (itr.hasNext() && itemNo < DebugHelperUtils.MAX_RESULTS_TO_SHOW) {
                    itemNo++;
                    outBuff.append("\n");
                    outBuff.append(itr.next());
                    outBuff.append(",");
                }
                outBuff.append("\n}");
                return (outBuff.toString());
            }
        }
    }

    /**
     * Method debugIntegerArray iterates through its elements and builds a string and returns it. the integerArray is
     * not modified in any manner.
     * 
     * @param arrayName array name to be used
     * @param integerArray array to iterate for the dump
     * @return String output string dump generated
     */
    public static String debugIntegerArray(String arrayName, int[] integerArray) {
        StringBuilder outBuff;

        outBuff = new StringBuilder("Array ").append(arrayName).append(" = ");

        if (integerArray == null) {
            outBuff.append("null");
            return outBuff.toString();
        } else {
            if (integerArray.length == 0) {
                outBuff.append(" has no elements ");
                return outBuff.toString();
            } else {
                outBuff.append("{");
                int count = 0;
                outBuff.append(TOTAL_COLLECTION_SIZE).append(integerArray.length).append(DISPLAYS_ONLY)
                    .append(MAX_RESULTS_TO_SHOW).append(ITEMS);
                while (count < (integerArray.length) && count < DebugHelperUtils.MAX_RESULTS_TO_SHOW) {
                    outBuff.append(integerArray[count]);
                    outBuff.append(", ");
                    count++;
                }

                outBuff.append("\n}");
                return (outBuff.toString());
            }
        }
    }

    /**
     * Method debugStringArray iterates through its elements and builds a string and returns it. the stringArray is not
     * modified in any manner.
     * 
     * @param arrayName array name to be used
     * @param stringArray array to iterate for the dump
     * @return String output string dump generated
     */
    public static String debugStringArray(String arrayName, String[] stringArray) {
        StringBuilder outBuff;

        outBuff = new StringBuilder("Array ").append(arrayName).append(" = ");

        if (stringArray == null) {
            outBuff.append("null");
            return outBuff.toString();
        } else {
            if (stringArray.length == 0) {
                outBuff.append(" has no elements ");
                return outBuff.toString();
            } else {
                outBuff.append("{");
                int count = 0;
                outBuff.append(TOTAL_COLLECTION_SIZE).append(stringArray.length).append(DISPLAYS_ONLY)
                    .append(MAX_RESULTS_TO_SHOW).append(ITEMS);
                while (count < (stringArray.length) && count < DebugHelperUtils.MAX_RESULTS_TO_SHOW) {
                    outBuff.append(stringArray[count]);
                    outBuff.append(", ");
                    count++;
                }

                outBuff.append("}");
                return (outBuff.toString());
            }
        }
    }

}
