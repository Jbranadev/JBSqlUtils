package io.github.josecarlosbran.JBSqlUtils.DataBase;

/***
 * @author Jose Bran
 * @param <T> Define el tipo de resultado que se espera de la ejecución asincrona de una operación en BD's
 */
class ResultAsync<T> {
    private final T result;
    private final Exception exception;

    /**
     * Constructor por default del ResultAsync por medio del cual se recopilara la información de la operación
     * asyncrona
     *
     * @param result    Resultado esperado de la operación
     * @param exception Exception de la operación en caso sucediera, de lo contrario NULL
     */
    protected ResultAsync(T result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    /**
     * @return Objeto resultado de la ejecución asyncrona de una operación en BD's
     */
    public T getResult() {
        return result;
    }

    /**
     * @return Exception que se lanzara en caso haya ocurrido un problema en la ejecución de la operación asyncrona
     */
    public Exception getException() {
        return exception;
    }
}
