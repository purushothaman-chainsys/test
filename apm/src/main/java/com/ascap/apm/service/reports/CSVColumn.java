package com.ascap.apm.service.reports;

/**
 * This class holds column information. It is used in the {@link com.goldencg.utils.csv.CSVReformat} class.
 * 
 * @author Garrett OBrien
 */
public class CSVColumn {

    private static int minimumNumberOfColumns = 0;

    private static char delimiter = '"';

    private static char separator = ',';

    private static char newSeparator = 0;

    private int number = 0;

    private int columnHeadingNumber = 0;

    private boolean remove = true;

    private boolean removeRowWhenEmpty = false;

    private String columnHeadingName = "";

    public CSVColumn() {
        // Nothing here
    }

    public CSVColumn(int number, int columnHeadingNumber, boolean remove, boolean removeRowWhenEmpty,
        String columnHeadingName) {
        this.number = number;
        this.columnHeadingNumber = columnHeadingNumber;
        this.remove = remove;
        this.removeRowWhenEmpty = removeRowWhenEmpty;
        this.columnHeadingName = columnHeadingName;
    }

    public CSVColumn(int number, int columnHeadingNumber, boolean remove, boolean removeRowWhenEmpty) {
        this.number = number;
        this.columnHeadingNumber = columnHeadingNumber;
        this.remove = remove;
        this.removeRowWhenEmpty = removeRowWhenEmpty;
    }

    /**
     * The delimiter is usually double quotes. This is usually surrounding each field and prevents commas in the fields
     * from interfering with the separator
     * 
     * @return the delimiter
     */
    public static char getDelimiter() {
        return delimiter;
    }

    /**
     * The delimiter is usually double quotes. This is usually surrounding each field and prevents commas in the fields
     * from interfering with the separator
     * 
     * @param delimiter the delimiter to set
     */
    public static void setDelimiter(char delimiter) {
        CSVColumn.delimiter = delimiter;
    }

    /**
     * Minimum number of columns for each row. An error will be thrown if there are fewer than this. If no error is to
     * be thrown, set this to 0.
     * 
     * @return the minimumNumberOfColumns
     */
    public static int getMinimumNumberOfColumns() {
        return minimumNumberOfColumns;
    }

    /**
     * Minimum number of columns for each row. An error will be thrown if there are fewer than this. If no error is to
     * be thrown, set this to 0.
     * 
     * @param minimumNumberOfColumns the minimumNumberOfColumns to set
     */
    public static void setMinimumNumberOfColumns(int minimumNumberOfColumns) {
        CSVColumn.minimumNumberOfColumns = minimumNumberOfColumns;
    }

    /**
     * The separator to be used on the output. If this is not specified, the separator value will be used.
     * 
     * @return the newSeparator
     */
    public static char getNewSeparator() {
        return newSeparator;
    }

    /**
     * The separator to be used on the output. If this is not specified, the separator value will be used.
     * 
     * @param newSeparator the newSeparator to set
     */
    public static void setNewSeparator(char newSeparator) {
        CSVColumn.newSeparator = newSeparator;
    }

    /**
     * The separator for the input.
     * 
     * @return the separator
     */
    public static char getSeparator() {
        return separator;
    }

    /**
     * The separator for the input.
     * 
     * @param separator the separator to set
     */
    public static void setSeparator(char separator) {
        CSVColumn.separator = separator;
    }

    /**
     * The specific heading name of this column. This is the literal heading name, not from any other field.
     * 
     * @return the columnHeadingName
     */
    public String getColumnHeadingName() {
        return this.columnHeadingName;
    }

    /**
     * The specific heading name of this column. This is the literal heading name, not from any other field.
     * 
     * @param columnHeadingName the columnHeadingName to set
     */
    public void setColumnHeadingName(String columnHeadingName) {
        this.columnHeadingName = columnHeadingName;
    }

    /**
     * The column to be used for the heading of this column.
     * 
     * @return the columnHeadingNumber
     */
    public int getColumnHeadingNumber() {
        return this.columnHeadingNumber;
    }

    /**
     * The column to be used for the heading of this column.
     * 
     * @param columnHeadingNumber the columnHeadingNumber to set
     */
    public void setColumnHeadingNumber(int columnHeadingNumber) {
        this.columnHeadingNumber = columnHeadingNumber;
    }

    /**
     * The column number.
     * 
     * @return the number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * The column number.
     * 
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Specifies if this column should be removed.
     * 
     * @return the remove
     */
    public boolean isRemove() {
        return this.remove;
    }

    /**
     * Specifies if this column should be removed.
     * 
     * @param remove the remove to set
     */
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    /**
     * Specifies if the current row should be removed if this field is empty.
     * 
     * @return the removeRowWhenEmpty
     */
    public boolean isRemoveRowWhenEmpty() {
        return this.removeRowWhenEmpty;
    }

    /**
     * Specifies if the current row should be removed if this field is empty.
     * 
     * @param removeRowWhenEmpty the removeRowWhenEmpty to set
     */
    public void setRemoveRowWhenEmpty(boolean removeRowWhenEmpty) {
        this.removeRowWhenEmpty = removeRowWhenEmpty;
    }
}
