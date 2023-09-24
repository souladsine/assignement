package ma.octo.assignement.domain.util;

public interface StatefulTransaction {
    TransactionStatus getStatus();
    void setStatus(TransactionStatus status);
}
