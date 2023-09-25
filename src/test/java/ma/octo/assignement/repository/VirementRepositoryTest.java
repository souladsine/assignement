package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VirementRepositoryTest {
    private Utilisateur utilisateurEmetteur;
    private Utilisateur utilisateurBeneficiaire;
    private Compte compteEmetteur;
    private Compte compteBeneficiaire;
    private Virement virementTest;


    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CompteRepository compteRepository;


    @BeforeEach
    public void setup() {
        utilisateurEmetteur = new Utilisateur.Builder()
                .setUsername("userTestE")
                .setLastname("lastTestE")
                .setFirstname("firstnameE")
                .setGender("Male")
                .build();
        utilisateurBeneficiaire = new Utilisateur.Builder()
                .setUsername("userTestB")
                .setLastname("lastTestB")
                .setFirstname("firstnameB")
                .setGender("Female")
                .build();
        compteEmetteur = new Compte.Builder()
                .setNrCompte("010000A000001002")
                .setRib("RIB3")
                .setSolde(BigDecimal.valueOf(200000L))
                .setUtilisateur(utilisateurEmetteur)
                .build();
        compteBeneficiaire = new Compte.Builder()
                .setNrCompte("010000B025001002")
                .setRib("RIB4")
                .setSolde(BigDecimal.valueOf(140000L))
                .setUtilisateur(utilisateurBeneficiaire)
                .build();
        virementTest = new Virement.Builder()
                .setMontantVirement(BigDecimal.TEN)
                .setCompteBeneficiaire(compteBeneficiaire)
                .setCompteEmetteur(compteEmetteur)
                .setMotifVirement("Assignment 2021")
                .build();

        utilisateurEmetteur = utilisateurRepository.save(utilisateurBeneficiaire);
        utilisateurBeneficiaire = utilisateurRepository.save(utilisateurEmetteur);
        compteEmetteur = compteRepository.save(compteBeneficiaire);
        compteBeneficiaire = compteRepository.save(compteEmetteur);
        virementTest = virementRepository.save(virementTest);
        virementRepository.flush();
    }


    private Virement virementExists(Virement virement) {
        Optional<Virement> test = virementRepository.findById(virement.getId());
        Assertions.assertTrue(test.isPresent());
        Assertions.assertNotNull(test.get());
        Assertions.assertEquals(test.get(), virement);
        return test.get();
    }


    @Test
    @Order(3)
    @DisplayName("find one virement")
    public void findOne() {
        virementExists(virementTest);
    }


    @Test
    @Order(4)
    @DisplayName("find all virements")
    public void findAll() {
        List<Virement> virements = virementRepository.findAll();
        Assertions.assertNotNull(virements);
        Assertions.assertFalse(virements.isEmpty());
        Assertions.assertEquals(virements.size(), 1);
    }

    @Test
    @Order(2)
    @DisplayName("save virement")
    public void save() {
        utilisateurEmetteur = utilisateurRepository.save(utilisateurBeneficiaire);
        utilisateurBeneficiaire = utilisateurRepository.save(utilisateurEmetteur);
        compteEmetteur = compteRepository.save(compteBeneficiaire);
        compteBeneficiaire = compteRepository.save(compteEmetteur);
        virementTest.setMontantVirement(new BigDecimal(99999));
        virementTest = virementRepository.save(virementTest);
        virementRepository.flush();
        Virement test = virementExists(virementTest);
        Assertions.assertEquals(new BigDecimal(99999), test.getMontantVirement());
    }

    @Test
    @Order(1)
    @DisplayName("delete virement and its subdependencies")
    public void delete() {
        virementRepository.delete(virementTest);
//        compteRepository.delete(virementTest.getCompteBeneficiaire());
//        compteRepository.delete(virementTest.getCompteEmetteur());
//        utilisateurRepository.delete(virementTest.getCompteEmetteur().getUtilisateur());
//        utilisateurRepository.delete(virementTest.getCompteBeneficiaire().getUtilisateur());
        Optional<Virement> virement = virementRepository.findById(virementTest.getId());
        Optional<Compte> compteEmetteur = compteRepository.findById(virementTest.getCompteEmetteur().getId());
        Optional<Compte> compteBeneficiaire = compteRepository.findById(virementTest.getCompteBeneficiaire().getId());
        Optional<Utilisateur> utilisateurEmetteur = utilisateurRepository.findById(virementTest.getCompteEmetteur().getUtilisateur().getId());
        Optional<Utilisateur> utilisateurBeneficiare = utilisateurRepository.findById(virementTest.getCompteBeneficiaire().getUtilisateur().getId());
        Assertions.assertFalse(virement.isPresent());
        Assertions.assertFalse(compteEmetteur.isPresent());
        Assertions.assertFalse(compteBeneficiaire.isPresent());
        Assertions.assertFalse(utilisateurEmetteur.isPresent());
        Assertions.assertFalse(utilisateurBeneficiare.isPresent());
    }
}