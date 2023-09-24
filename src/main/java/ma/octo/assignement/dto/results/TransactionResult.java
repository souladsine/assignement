package ma.octo.assignement.dto.results;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.domain.util.TransactionStatus;

import java.math.BigDecimal;

public class TransactionResult {
    private String from;
    private Compte to;
    private BigDecimal amount;
    private String motif;
    private TransactionStatus status;
    private EventType type;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFrom(Compte from) {
        this.from = from.getUtilisateur().getFullName() +"'s account " + from.getNrCompte();
    }

    public Compte getTo() {
        return to;
    }

    public void setTo(Compte to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s from %s to %s with amount %f and reason %s", type, from, to, amount, motif);
    }
}
