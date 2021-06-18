package ua.com.alevel.data.exception;

public abstract class FinancialAccountingException  extends  Exception{

    public FinancialAccountingException(String message) {
        super(message);
    }

    public FinancialAccountingException(String message,Throwable cause) {
        super(message,cause);
    }
}
