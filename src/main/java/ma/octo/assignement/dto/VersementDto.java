package ma.octo.assignement.dto;

import java.math.BigDecimal;

public class VersementDto {
    private String nomEmetteur;
    private String nrCompteBeneficiaire;
    private String motif;
    private BigDecimal montantVirement;

    public String getNomEmetteur() {
        return nomEmetteur;
    }

    public void setNomEmetteur(String nomEmetteur) {
        this.nomEmetteur = nomEmetteur;
    }

    public String getNrCompteBeneficiaire() {
        return nrCompteBeneficiaire;
    }

    public void setNrCompteBeneficiaire(String nrCompteBeneficiaire) {
        this.nrCompteBeneficiaire = nrCompteBeneficiaire;
    }

    public BigDecimal getMontantVirement() {
        return montantVirement;
    }

    public void setMontantVirement(BigDecimal montantVirement) {
        this.montantVirement = montantVirement;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
