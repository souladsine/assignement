package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.dto.results.TransactionResult;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.VirementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/virements")
@RestController
public class VirementController {

    Logger LOGGER = LoggerFactory.getLogger(VirementController.class);

    @Autowired
    private VirementService virementService;
    @Autowired
    private CompteService compteService;
    @Autowired
    private AuditService auditService;

    @GetMapping
    List<Virement> loadAll() {
        return virementService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResult createTransaction(@RequestBody VirementDto virementDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        Compte compteEmetteur = compteService.getOne(virementDto.getNrCompteEmetteur());
        Compte compteBeneficiaire = compteService.getOne(virementDto.getNrCompteBeneficiaire());


        Virement virement = new Virement.Builder()
                .setCompteEmetteur(compteEmetteur)
                .setCompteBeneficiaire(compteBeneficiaire)
                .setMontantVirement(virementDto.getMontantVirement())
                .setMotifVirement(virementDto.getMotif())
                .build();

        TransactionResult result = virementService.createTransaction(virement);

        auditService.auditVirement(result.toString());

        return result;
    }
}
