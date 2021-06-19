package ua.com.alevel.data.exception;

public class FinancialAccountingExceptionDataNotFound extends FinancialAccountingException {

    public FinancialAccountingExceptionDataNotFound(String massage) {
        super(massage);
    }

    public FinancialAccountingExceptionDataNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
