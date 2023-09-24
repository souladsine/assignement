package ma.octo.assignement;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootApplication
@Transactional
public class AssignementApplication implements CommandLineRunner {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private VirementRepository virementRepository;

    public static void main(String[] args) {
        SpringApplication.run(AssignementApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        Utilisateur utilisateurEmetteur = new Utilisateur.Builder()
                .setUsername("user1")
                .setLastname("last1")
                .setFirstname("first1")
                .setGender("Male")
                .build();

        utilisateurRepository.save(utilisateurEmetteur);
        //System.out.println(utilisateur1);


        Utilisateur utilisateurBeneficiaire = new Utilisateur.Builder()
				.setUsername("user2")
				.setLastname("last2")
				.setFirstname("first2")
				.setGender("Female")
				.build();

        utilisateurRepository.save(utilisateurBeneficiaire);

        Compte compteEmetteur = new Compte.Builder()
                .setNrCompte("010000A000001000")
                .setRib("RIB1")
                .setSolde(BigDecimal.valueOf(200000L))
                .setUtilisateur(utilisateurEmetteur)
                .build();

        compteRepository.save(compteEmetteur);

        Compte compteBeneficiaire = new Compte.Builder()
                .setNrCompte("010000B025001000")
                .setRib("RIB2")
                .setSolde(BigDecimal.valueOf(140000L))
                .setUtilisateur(utilisateurBeneficiaire)
                .build();

        compteRepository.save(compteBeneficiaire);

        Virement virement = new Virement.Builder()
                .setMontantVirement(BigDecimal.TEN)
                .setCompteBeneficiaire(compteBeneficiaire)
                .setCompteEmetteur(compteEmetteur)
                .setMotifVirement("Assignment 2021")
                .build();

        if(virement.getStatus().isValid()){
            virementRepository.save(virement);
            System.out.println("test saved");
        }
        else{
            System.out.println(virement.getStatus());
        }

    }
}
