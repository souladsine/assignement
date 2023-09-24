package ma.octo.assignement.domain;

import ma.octo.assignement.domain.util.StatefulTransaction;
import ma.octo.assignement.domain.util.TransactionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VERSEMENT")
public class Versement implements StatefulTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montantVirement;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @Column(name = "nom_prenom_emetteur")
  private String nomEmetteur;

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motifVersement;

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

  public Compte getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Compte compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

  public String getMotifVersement() {
    return motifVersement;
  }

  public void setMotifVersement(String motifVersement) {
    this.motifVersement = motifVersement;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomEmetteur() {
    return nomEmetteur;
  }

  public void setNomEmetteur(String nomEmetteur) {
    this.nomEmetteur = nomEmetteur;
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

  //BUILDER PATTERN
  public static class Builder {
    private long MAX_TRANSACTION_AMOUNT = 100000L; // to be determined

    private BigDecimal montantVersement;
    private String nomEmetteur;
    private Compte compteBeneficiaire;
    private String motifVersement;

    public Versement.Builder setNomEmetteur(String nomEmetteur) {
      this.nomEmetteur = nomEmetteur.trim();
      return this;
    }

    public Versement.Builder setCompteBeneficiaire(Compte compteBeneficiaire) {
      this.compteBeneficiaire = compteBeneficiaire;
      return this;
    }

    public Versement.Builder setmontantVersement(BigDecimal montantVersement) {
      this.montantVersement = montantVersement;
      return this;
    }

    public Versement.Builder setMotifVersement(String motifVersement) {
      this.motifVersement = motifVersement.trim();
      return this;
    }

    public Versement build(){
      Versement versement = new Versement();
      versement.setDateExecution(new Date());

      if(compteBeneficiaire == null) {
        versement.setStatus(TransactionStatus.RECIPIENT_NOT_FOUND);
        return versement;
      }
      versement.setCompteBeneficiaire(compteBeneficiaire);

      if(nomEmetteur == null || nomEmetteur.length() < 3) {
        versement.setStatus(TransactionStatus.MISSING_DETAILS);
        return versement;
      }
      versement.setNomEmetteur(nomEmetteur);

      if(montantVersement == null || montantVersement.compareTo(BigDecimal.TEN) < 0){
        versement.setStatus(TransactionStatus.MIN_AMOUNT_NOT_REACHED);
        return versement;
      }
      else if(montantVersement.compareTo(new BigDecimal(MAX_TRANSACTION_AMOUNT)) > 0){
        versement.setStatus(TransactionStatus.EXCEEDED_MAX_AMOUNT);
        return versement;
      }
      versement.setMontantVirement(montantVersement);

      if(motifVersement == null || motifVersement.equals("")){
        versement.setStatus(TransactionStatus.MISSING_REASON);
        return versement;
      }
      versement.setMotifVersement(motifVersement);

      versement.setStatus(TransactionStatus.VALID);
      return versement;
    }
  }
}
