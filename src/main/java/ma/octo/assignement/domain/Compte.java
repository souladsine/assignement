package ma.octo.assignement.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

  @ManyToOne(cascade = CascadeType.ALL)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Compte compte = (Compte) o;

    if (!Objects.equals(id, compte.id)) return false;
    if (!Objects.equals(nrCompte, compte.nrCompte)) return false;
    if (!Objects.equals(rib, compte.rib)) return false;
    if (!Objects.equals(solde, compte.solde)) return false;
    return Objects.equals(utilisateur, compte.utilisateur);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (nrCompte != null ? nrCompte.hashCode() : 0);
    result = 31 * result + (rib != null ? rib.hashCode() : 0);
    result = 31 * result + (solde != null ? solde.hashCode() : 0);
    result = 31 * result + (utilisateur != null ? utilisateur.hashCode() : 0);
    return result;
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
