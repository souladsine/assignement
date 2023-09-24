package ma.octo.assignement.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "COMPTE")
public class Compte {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 16, unique = true)
  private String nrCompte;

  private String rib;

  @Column(precision = 16, scale = 2)
  private BigDecimal solde;

  @ManyToOne()
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

  public String getNrCompte() {
    return nrCompte;
  }

  public void setNrCompte(String nrCompte) {
    this.nrCompte = nrCompte;
  }

  public String getRib() {
    return rib;
  }

  public void setRib(String rib) {
    this.rib = rib;
  }

  public BigDecimal getSolde() {
    return solde;
  }

  public void setSolde(BigDecimal solde) {
    this.solde = solde;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public static class Builder {
    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private Utilisateur utilisateur;

    public Builder() {
    }

    public Compte.Builder setNrCompte(String nrCompte) {
      this.nrCompte = nrCompte;
      return this;
    }

    public Compte.Builder setRib(String rib) {
      this.rib = rib;
      return this;
    }

    public Compte.Builder setSolde(BigDecimal solde) {
      this.solde = solde;
      return this;
    }

    public Compte.Builder setSolde(int solde) {
      this.solde = new BigDecimal(solde);
      return this;
    }

    public Compte.Builder setSolde(double solde) {
      this.solde = new BigDecimal(solde);
      return this;
    }

    public Compte.Builder setUtilisateur(Utilisateur utilisateur) {
      this.utilisateur = utilisateur;
      return this;
    }

    public Compte build() {
      if (this.nrCompte == null) {
        this.nrCompte = "";
      }
      if (this.rib == null) {
        this.rib = "";
      }
      if (this.solde == null || this.solde.compareTo(BigDecimal.ZERO) < 0) {
        this.solde = BigDecimal.ZERO;
      }
      Compte compte = new Compte();
      compte.nrCompte = this.nrCompte;
      compte.rib = this.rib;
      compte.solde = this.solde;
      compte.utilisateur = this.utilisateur;
      return compte;
    }
  }
}
