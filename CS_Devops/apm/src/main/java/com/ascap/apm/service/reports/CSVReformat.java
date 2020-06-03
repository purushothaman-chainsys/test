package com.ascap.apm.service.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * Reformats a CSV file based on certain rules. These rules can be either in an XML file or
 * {@link com.goldencg.utils.csv.CSVColumn} array.<BR>
 * <BR>
 * Sample XML file <a href="columns.xml" target="_blank">columns.xml</a>
 * 
 * @author Garrett OBrien
 */
public class CSVReformat {

    protected static LogHelper log = new LogHelper(CSVReformat.class.getName());

    private static class SAXHandler extends DefaultHandler {

        private ArrayList<Object> columns = new ArrayList<>();

        private CSVColumn currentColumn = new CSVColumn();

        private static int toInt(String value, int defaultValue) {
            int returnResult = defaultValue;
            try {
                returnResult = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // don't care
            }
            return returnResult;
        }

        private static char toChar(String value, char defaultValue) {
            char returnResult = defaultValue;
            if (value.length() > 0) {
                returnResult = value.charAt(0);
            }
            return returnResult;
        }

        private static boolean toBoolean(String value) {
            return value != null && value.length() != 0
                && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || toInt(value, 0) != 0);
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("column")) {
                this.columns.add(this.currentColumn);
            }
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
            String value = "value";
            if (qName.equals("minimum-columns")) {
                CSVColumn.setMinimumNumberOfColumns(toInt(attributes.getValue(value), 0));
            } else if (qName.equals("delimiter")) {
                CSVColumn.setDelimiter(toChar(attributes.getValue(value), '"'));
            } else if (qName.equals("separator")) {
                CSVColumn.setSeparator(toChar(attributes.getValue(value), '"'));
                CSVColumn.setNewSeparator(CSVColumn.getSeparator());
            } else if (qName.equals("new-separator")) {
                CSVColumn.setNewSeparator(toChar(attributes.getValue(value), '"'));
            } else if (qName.equals("remove")) {
                this.currentColumn.setRemove(toBoolean(attributes.getValue(value)));
            } else if (qName.equals("remove-row-when-empty")) {
                this.currentColumn.setRemoveRowWhenEmpty(toBoolean(attributes.getValue(value)));
            } else if (qName.equals("column")) {
                this.currentColumn = new CSVColumn();
                this.currentColumn.setNumber(toInt(attributes.getValue("number"), 0));
            } else if (qName.equals("column-heading")) {
                String number = attributes.getValue("number");
                String name = attributes.getValue("name");
                if (number != null) {
                    this.currentColumn.setColumnHeadingNumber(toInt(number, 0));
                }
                if (name != null) {
                    this.currentColumn.setColumnHeadingName(name);
                }
            }
        }

        public CSVColumn[] getColumns() {
            return (CSVColumn[]) this.columns.toArray(new CSVColumn[this.columns.size()]);
        }

    }

    /**
     * @param in The string holding the entire csv file
     * @param xmlFile String specifying the XML file holding column information
     * @return StringBuffer containing the new CSV
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static StringBuffer reformat(String in, String xmlFile)
        throws IOException, ParserConfigurationException, SAXException {
        return null;
    }

    /**
     * @param in BufferedReader for reading the CSV file
     * @param xmlFile String specifying the XML file holding column information for the CSV
     * @return StringBuffer containing the new CSV
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static StringBuffer reformat(BufferedReader in, String xmlFile)
        throws IOException, ParserConfigurationException, SAXException {
        return null;
    }

    /**
     * @param in The string holding the entire csv file
     * @param columns Information about columns in the CSV
     * @return StringBuffer containing the new CSV
     */
    public static RandomAccessFile reformat(String in, CSVColumn[] columns) {
        RandomAccessFile out = null;
        try {
            String tempFileName = RandomStringUtils.randomNumeric(8);
            File temp = File.createTempFile(tempFileName, ".tmp");
            String path = temp.getAbsolutePath();
            log.debug("Temp file created: " + path);
            out = new RandomAccessFile(temp, "rw");
            reformat(new BufferedReader(new StringReader(in)), out, columns);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return out;
    }

    /**
     * @param in BufferedReader for reading the CSV file
     * @param columns Information about columns in the CSV
     * @return StringBuffer containing the new CSV
     * @throws IOException
     */
    public static RandomAccessFile reformat(BufferedReader in, CSVColumn[] columns) throws IOException {
        String tempFileName = RandomStringUtils.randomNumeric(8);
        File temp = File.createTempFile(tempFileName, ".tmp");
        String path = temp.getAbsolutePath();
        log.debug("Temp file created: " + path);
        RandomAccessFile out = new RandomAccessFile(temp, "rw");
        reformat(in, out, columns);
        return out;
    }

    /**
     * Reads an XML file in and converts it to a CSVColumn[]
     * 
     * @param xmlFile String specifying the XML file holding column information for the CSV
     * @return CSVColumn[] containing infor for each column. Used in various reformat methods
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static CSVColumn[] xmlToCSVColumnArray(InputStream xmlFile)
        throws IOException, ParserConfigurationException, SAXException {
        SAXHandler handler = new SAXHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFile, handler);
        return handler.getColumns();
    }

    /**
     * @param in BufferedReader for reading the CSV file
     * @param out PrintWriter for outputting new CSV file
     * @param columns Information about columns in the CSV
     * @throws IOException
     */
    public static void reformat(BufferedReader in, RandomAccessFile out, CSVColumn[] columns) throws IOException {
        boolean first = true;
        String line = "";
        int lineNumber = 0;
        ArrayList<Object> csv = null;
        int csvLength = 0;
        String headingSeparator = "";
        String recordSeparator = "";
        char newDelimiter = CSVColumn.getNewSeparator();
        if (newDelimiter == 0) {
            newDelimiter = CSVColumn.getSeparator();
        }
        while ((line = in.readLine()) != null) {
            lineNumber++;
            csv = split(line, CSVColumn.getSeparator(), CSVColumn.getDelimiter(), 10);// ,
            csvLength = csv.size();
            if (csvLength < CSVColumn.getMinimumNumberOfColumns() && csvLength != columns.length) {
                throw new IOException("Read line (line " + lineNumber + ") length (" + csvLength
                    + ") is not the same as defined column length (" + columns.length + ")");
            }
            if (first) {
                first = false;
                for (int i = 0; i < columns.length; i++) {
                    if (!columns[i].isRemove()) {
                        out.writeBytes(headingSeparator);
                        if (columns[i].getColumnHeadingName().length() != 0) {
                            out.writeBytes(columns[i].getColumnHeadingName());
                        } else if (columns[i].getColumnHeadingNumber() - 1 < csvLength
                            && columns[i].getColumnHeadingNumber() > 0) {
                            out.writeBytes((String) csv.get(columns[i].getColumnHeadingNumber() - 1));
                        }
                        headingSeparator = "" + newDelimiter;
                    }
                }
                out.writeBytes("\r\n");
            }
            StringBuffer sb = new StringBuffer();
            boolean remove = false;
            recordSeparator = "";
            for (int i = 0; i < csvLength; i++) {
                if (i < columns.length && columns[i] != null && !columns[i].isRemove()) {
                    sb.append(recordSeparator);
                    if (i < csvLength) {
                        sb.append(csv.get(i));
                        recordSeparator = "" + newDelimiter;
                    }
                }
                if (i < columns.length && columns[i] != null && columns[i].isRemoveRowWhenEmpty()
                    && (i >= csvLength || ((String) csv.get(i)).length() == 0)) {
                    remove = true;
                }
            }
            if (!remove) {
                sb.append("\r\n");
                out.writeBytes(sb.toString());
            }
        }
        out.seek(0);
    }

    private static ArrayList<Object> split(String line, char separator, char delimiter, int length)// , boolean
    {
        ArrayList<Object> returnResult = new ArrayList<>(length);
        boolean inDelimiter = false;
        char lastChar = 0;
        char currentChar = 0;
        char nextChar = 0;
        int beginningOfValue = 0;
        int endOfValue = 0;
        String value = "";
        for (int i = 0; i < line.length(); i++) {
            currentChar = line.charAt(i);
            if (i + 1 < line.length()) {
                nextChar = line.charAt(i + 1);
            } else {
                nextChar = 0;
            }
            if (currentChar == delimiter
                && ((!inDelimiter && (lastChar == separator || i == 0)) || (inDelimiter && nextChar == separator))) {
                inDelimiter = !inDelimiter;
            }
            if (!inDelimiter && currentChar == separator) {
                endOfValue = i;
                value = line.substring(beginningOfValue, endOfValue);
                returnResult.add(value);
                beginningOfValue = i + 1;
            }
            lastChar = currentChar;
        }
        value = line.substring(beginningOfValue);
        returnResult.add(value);
        return returnResult;
    }
}
