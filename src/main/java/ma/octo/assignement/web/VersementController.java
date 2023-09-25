package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.dto.results.TransactionResult;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.VersementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/versements")
@RestController
public class VersementController {
    Logger LOGGER = LoggerFactory.getLogger(VersementController.class);

    @Autowired
    private CompteService compteService;
    @Autowired
    private VersementService versementService;
    @Autowired
    private AuditService auditService;

    @GetMapping
    List<Versement> loadAll() {
        return versementService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResult createTransaction(@RequestBody VersementDto versementDto)
            throws CompteNonExistantException, TransactionException {
        String nomEmetteur = versementDto.getNomEmetteur();
        Compte compteBeneficiaire = compteService.getOne(versementDto.getNrCompteBeneficiaire());


        Versement versement = new Versement.Builder()
                .setNomEmetteur(nomEmetteur)
                .setCompteBeneficiaire(compteBeneficiaire)
                .setmontantVersement(versementDto.getMontantVirement())
                .setMotifVersement(versementDto.getMotif())
                .build();

        TransactionResult result = versementService.createTransaction(versement);

        auditService.auditVersement(result.toString());

        return result;
    }
}
