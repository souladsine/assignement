package ma.octo.assignement.domain;

import ma.octo.assignement.domain.util.StatefulTransaction;
import ma.octo.assignement.domain.util.TransactionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "VIREMENT")
public class Virement implements StatefulTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montantVirement;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @ManyToOne
  private Compte compteEmetteur;

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motifVirement;

  public BigDecimal getMontantVirement() {
    return montantVirement;
  }

  public void setMontantVirement(BigDecimal montantVirement) {
    this.montantVirement = montantVirement;
  }

  public Date getDateExecution() {
    return dateExecution;
  }

  public void setDateExecution(Date dateExecution) {
    this.dateExecution = dateExecution;
  }

  public Compte getCompteEmetteur() {
    return compteEmetteur;
  }

  public void setCompteEmetteur(Compte compteEmetteur) {
    this.compteEmetteur = compteEmetteur;
  }

  public Compte getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Compte compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

  public String getMotifVirement() {
    return motifVirement;
  }

  public void setMotifVirement(String motifVirement) {
    this.motifVirement = motifVirement;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  //STATUS
  private TransactionStatus status = TransactionStatus.MISSING_DETAILS;

  @Override
  public TransactionStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public static class Builder {
    private long MAX_TRANSACTION_AMOUNT = 10000L;

    private BigDecimal montantVirement;
    private Compte compteEmetteur;
    private Compte compteBeneficiaire;
    private String motifVirement;

    public Builder setCompteEmetteur(Compte compteEmetteur) {
      this.compteEmetteur = compteEmetteur;
      return this;
    }

    public Builder setCompteBeneficiaire(Compte compteBeneficiaire) {
      this.compteBeneficiaire = compteBeneficiaire;
      return this;
    }

    public Builder setMontantVirement(BigDecimal montantVirement) {
      this.montantVirement = montantVirement;
      return this;
    }

    public Builder setMotifVirement(String motifVirement) {
      this.motifVirement = motifVirement.trim();
      return this;
    }

    public Virement build(){
      Virement virement = new Virement();
      virement.setDateExecution(new Date());

      if(compteBeneficiaire == null) {
        virement.setStatus(TransactionStatus.RECIPIENT_NOT_FOUND);
        return virement;
      }
      virement.setCompteBeneficiaire(compteBeneficiaire);

      if(compteEmetteur == null) {
        virement.setStatus(TransactionStatus.SENDER_NOT_FOUND);
        return virement;
      }
      virement.setCompteEmetteur(compteEmetteur);

      if(montantVirement == null){
        virement.setStatus(TransactionStatus.MIN_AMOUNT_NOT_REACHED);
        return virement;
      }
      if(montantVirement.compareTo(BigDecimal.TEN) < 0){
        virement.setStatus(TransactionStatus.MIN_AMOUNT_NOT_REACHED);
        return virement;
      }
      else if(montantVirement.compareTo(new BigDecimal(MAX_TRANSACTION_AMOUNT)) > 0){
        virement.setStatus(TransactionStatus.EXCEEDED_MAX_AMOUNT);
        return virement;
      }
      if(montantVirement.compareTo(compteEmetteur.getSolde()) > 0){
        virement.setStatus(TransactionStatus.NOT_ENOUGH_CREDIT);
        return virement;
      }
      virement.setMontantVirement(montantVirement);

      if(motifVirement == null || motifVirement.equals("")){
        virement.setStatus(TransactionStatus.MISSING_REASON);
        return virement;
      }
      virement.setMotifVirement(motifVirement);

      virement.setStatus(TransactionStatus.VALID);
      return virement;
    }
  }
}
