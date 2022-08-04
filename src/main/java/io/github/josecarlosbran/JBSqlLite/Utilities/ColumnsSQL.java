package io.github.josecarlosbran.JBSqlLite.Utilities;

public class ColumnsSQL {
    /**
     * TABLE_CAT String => table catalog (may be null)
     */
    private String TABLE_CAT = null;

    /**
     * TABLE_SCHEM String => table schema (may be null)
     */
    String TABLE_SCHEM = null;

    /**
     * TABLE_NAME String => table name
     */
    String TABLE_NAME = null;

    /**
     * COLUMN_NAME String => column name
     */
    String COLUMN_NAME = null;

    /**
     * DATA_TYPE int => SQL type from java.sql.Types
     */
    int DATA_TYPE = 0;

    /**
     * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     */
    String TYPE_NAME = null;


    /**
     * COLUMN_SIZE int => column size.
     */
    int COLUMN_SIZE = 0;

    /**
     * DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
     */
    int DECIMAL_DIGITS = 0;

    /**
     * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
     */
    int NUM_PREC_RADIX = 0;

    /**
     * NULLABLE int => is NULL allowed.
     * columnNoNulls - might not allow NULL values
     * columnNullable - definitely allows NULL values
     * columnNullableUnknown - nullability unknown
     */
    int NULLABLE = 0;

    /**
     * REMARKS String => comment describing column (may be null)
     */
    String REMARKS = null;

    /**
     * COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     */
    String COLUMN_DEF = null;



    /**
     * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     */
    int CHAR_OCTET_LENGTH = 0;

    /**
     * ORDINAL_POSITION int => index of column in table (starting at 1)
     *
     */
    int ORDINAL_POSITION = 0;

    /**
     * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
     * YES --- if the column can include NULLs
     * NO --- if the column cannot include NULLs
     * empty string --- if the nullability for the column is unknown
     *
     */
    String IS_NULLABLE = null;

    /**
     * SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
     *
     */
    String SCOPE_CATALOG = null;

    /**
     * SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    String SCOPE_SCHEMA = null;
    /**
     * SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     */
    String SCOPE_TABLE = null;

    /**
     * SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type,
     * SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
     */
    short SOURCE_DATA_TYPE=0;

    /**
     * IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
     * YES --- if the column is auto incremented
     * NO --- if the column is not auto incremented
     * empty string --- if it cannot be determined whether the column is auto incremented
     */
    String IS_AUTOINCREMENT = null;

    /**
     * IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
     * YES --- if this a generated column
     * NO --- if this not a generated column
     * empty string --- if it cannot be determined whether this is a generated column
     */
    String IS_GENERATEDCOLUMN = null;


}
