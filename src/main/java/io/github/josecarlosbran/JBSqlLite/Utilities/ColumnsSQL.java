package io.github.josecarlosbran.JBSqlLite.Utilities;

public class ColumnsSQL {
    /**
     * TABLE_CAT String => table catalog (may be null)
     */
    private String TABLE_CAT = null;

    /**
     * TABLE_SCHEM String => table schema (may be null)
     */
    private String TABLE_SCHEM = null;

    /**
     * TABLE_NAME String => table name
     */
    private String TABLE_NAME = null;

    /**
     * COLUMN_NAME String => column name
     */
    private String COLUMN_NAME = null;

    /**
     * DATA_TYPE int => SQL type from java.sql.Types
     */
    private int DATA_TYPE = 0;

    /**
     * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     */
    private String TYPE_NAME = null;


    /**
     * COLUMN_SIZE int => column size.
     */
    private int COLUMN_SIZE = 0;

    /**
     * DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
     */
    private int DECIMAL_DIGITS = 0;

    /**
     * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
     */
    private int NUM_PREC_RADIX = 0;

    /**
     * NULLABLE int => is NULL allowed.
     * columnNoNulls - might not allow NULL values
     * columnNullable - definitely allows NULL values
     * columnNullableUnknown - nullability unknown
     */
    private int NULLABLE = 0;

    /**
     * REMARKS String => comment describing column (may be null)
     */
    private String REMARKS = null;

    /**
     * COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     */
    private String COLUMN_DEF = null;



    /**
     * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     */
    private int CHAR_OCTET_LENGTH = 0;

    /**
     * ORDINAL_POSITION int => index of column in table (starting at 1)
     *
     */
    private int ORDINAL_POSITION = 0;

    /**
     * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
     * YES --- if the column can include NULLs
     * NO --- if the column cannot include NULLs
     * empty string --- if the nullability for the column is unknown
     *
     */
    private String IS_NULLABLE = null;

    /**
     * SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
     *
     */
    private String SCOPE_CATALOG = null;

    /**
     * SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    private String SCOPE_SCHEMA = null;
    /**
     * SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    private String SCOPE_TABLE = null;

    /**
     * SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type,
     * SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
     */
    private short SOURCE_DATA_TYPE=0;

    /**
     * IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
     * YES --- if the column is auto incremented
     * NO --- if the column is not auto incremented
     * empty string --- if it cannot be determined whether the column is auto incremented
     */
    private String IS_AUTOINCREMENT = null;

    /**
     * IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
     * YES --- if this a generated column
     * NO --- if this not a generated column
     * empty string --- if it cannot be determined whether this is a generated column
     */
    private String IS_GENERATEDCOLUMN = null;


    /**
     * TABLE_CAT String => table catalog (may be null)
     */
    public String getTABLE_CAT() {
        return TABLE_CAT;
    }

    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    /**
     * TABLE_SCHEM String => table schema (may be null)
     */
    public String getTABLE_SCHEM() {
        return TABLE_SCHEM;
    }

    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    /**
     * TABLE_NAME String => table name
     */
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    /**
     * COLUMN_NAME String => column name
     */
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    /**
     * DATA_TYPE int => SQL type from java.sql.Types
     */
    public int getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(int DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    /**
     * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     */
    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    /**
     * COLUMN_SIZE int => column size.
     */
    public int getCOLUMN_SIZE() {
        return COLUMN_SIZE;
    }

    public void setCOLUMN_SIZE(int COLUMN_SIZE) {
        this.COLUMN_SIZE = COLUMN_SIZE;
    }

    /**
     * DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
     */
    public int getDECIMAL_DIGITS() {
        return DECIMAL_DIGITS;
    }

    public void setDECIMAL_DIGITS(int DECIMAL_DIGITS) {
        this.DECIMAL_DIGITS = DECIMAL_DIGITS;
    }

    /**
     * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
     */
    public int getNUM_PREC_RADIX() {
        return NUM_PREC_RADIX;
    }

    public void setNUM_PREC_RADIX(int NUM_PREC_RADIX) {
        this.NUM_PREC_RADIX = NUM_PREC_RADIX;
    }

    /**
     * NULLABLE int => is NULL allowed.
     * columnNoNulls - might not allow NULL values
     * columnNullable - definitely allows NULL values
     * columnNullableUnknown - nullability unknown
     */
    public int getNULLABLE() {
        return NULLABLE;
    }

    public void setNULLABLE(int NULLABLE) {
        this.NULLABLE = NULLABLE;
    }

    /**
     * REMARKS String => comment describing column (may be null)
     */
    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    /**
     * COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     */
    public String getCOLUMN_DEF() {
        return COLUMN_DEF;
    }

    public void setCOLUMN_DEF(String COLUMN_DEF) {
        this.COLUMN_DEF = COLUMN_DEF;
    }

    /**
     * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     */
    public int getCHAR_OCTET_LENGTH() {
        return CHAR_OCTET_LENGTH;
    }

    public void setCHAR_OCTET_LENGTH(int CHAR_OCTET_LENGTH) {
        this.CHAR_OCTET_LENGTH = CHAR_OCTET_LENGTH;
    }

    /**
     * ORDINAL_POSITION int => index of column in table (starting at 1)
     *
     */
    public int getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }

    public void setORDINAL_POSITION(int ORDINAL_POSITION) {
        this.ORDINAL_POSITION = ORDINAL_POSITION;
    }

    /**
     * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
     * YES --- if the column can include NULLs
     * NO --- if the column cannot include NULLs
     * empty string --- if the nullability for the column is unknown
     *
     */
    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    /**
     * SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
     *
     */
    public String getSCOPE_CATALOG() {
        return SCOPE_CATALOG;
    }

    public void setSCOPE_CATALOG(String SCOPE_CATALOG) {
        this.SCOPE_CATALOG = SCOPE_CATALOG;
    }

    /**
     * SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    public String getSCOPE_SCHEMA() {
        return SCOPE_SCHEMA;
    }

    public void setSCOPE_SCHEMA(String SCOPE_SCHEMA) {
        this.SCOPE_SCHEMA = SCOPE_SCHEMA;
    }

    /**
     * SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    public String getSCOPE_TABLE() {
        return SCOPE_TABLE;
    }

    public void setSCOPE_TABLE(String SCOPE_TABLE) {
        this.SCOPE_TABLE = SCOPE_TABLE;
    }

    /**
     * SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type,
     * SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
     */
    public short getSOURCE_DATA_TYPE() {
        return SOURCE_DATA_TYPE;
    }

    public void setSOURCE_DATA_TYPE(short SOURCE_DATA_TYPE) {
        this.SOURCE_DATA_TYPE = SOURCE_DATA_TYPE;
    }

    /**
     * IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
     * YES --- if the column is auto incremented
     * NO --- if the column is not auto incremented
     * empty string --- if it cannot be determined whether the column is auto incremented
     */
    public String getIS_AUTOINCREMENT() {
        return IS_AUTOINCREMENT;
    }

    public void setIS_AUTOINCREMENT(String IS_AUTOINCREMENT) {
        this.IS_AUTOINCREMENT = IS_AUTOINCREMENT;
    }

    /**
     * IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
     * YES --- if this a generated column
     * NO --- if this not a generated column
     * empty string --- if it cannot be determined whether this is a generated column
     */
    public String getIS_GENERATEDCOLUMN() {
        return IS_GENERATEDCOLUMN;
    }

    public void setIS_GENERATEDCOLUMN(String IS_GENERATEDCOLUMN) {
        this.IS_GENERATEDCOLUMN = IS_GENERATEDCOLUMN;
    }
}
