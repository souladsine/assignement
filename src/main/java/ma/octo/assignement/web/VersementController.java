package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.domain.util.TransactionStatus;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.dto.results.TransactionResult;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VersementRepository;
import ma.octo.assignement.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/versements")
@RestController
class VersementController {
    Logger LOGGER = LoggerFactory.getLogger(VersementController.class);

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private VersementRepository versementRepository;
    @Autowired
    private AuditService auditService;

    @GetMapping
    List<Versement> loadAll() {
        List<Versement> all = versementRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResult createTransaction(@RequestBody VersementDto versementDto)
            throws CompteNonExistantException, TransactionException {
        String nomEmetteur = versementDto.getNomEmetteur();
        Compte compteBeneficiaire = compteRepository
                .findByNrCompte(versementDto.getNrCompteBeneficiaire());


        Versement v = new Versement.Builder()
                .setNomEmetteur(nomEmetteur)
                .setCompteBeneficiaire(compteBeneficiaire)
                .setmontantVersement(versementDto.getMontantVirement())
                .setMotifVersement(versementDto.getMotif())
                .build();

        TransactionStatus transactionValid = v.getStatus();

        if(transactionValid.getType() == TransactionStatus.Type.ACCOUNT){
            LOGGER.error(transactionValid.getMessage());
            throw new CompteNonExistantException(transactionValid.getMessage());
        }
        if(transactionValid.getType() == TransactionStatus.Type.TRANSACTION){
            LOGGER.error(transactionValid.getMessage());
            throw new TransactionException(transactionValid.getMessage());
        }

        compteBeneficiaire
                .setSolde(compteBeneficiaire.getSolde().add(versementDto.getMontantVirement()));
        compteRepository.save(compteBeneficiaire);

        versementRepository.save(v);

        TransactionResult result = new TransactionResult();
        result.setAmount(versementDto.getMontantVirement());
        result.setFrom(nomEmetteur);
        result.setTo(compteBeneficiaire);
        result.setMotif(versementDto.getMotif());
        result.setStatus(v.getStatus());
        result.setType(EventType.VERSEMENT);


        auditService.auditVersement(result.toString());

        return result;
    }
}
