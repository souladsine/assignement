package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.domain.util.TransactionStatus;
import ma.octo.assignement.dto.results.TransactionResult;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VersementRepository;
import ma.octo.assignement.web.VersementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VersementService {
    Logger LOGGER = LoggerFactory.getLogger(VersementController.class);

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private VersementRepository versementRepository;


    public List<Versement> getAll() {
        List<Versement> all = versementRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public TransactionResult createTransaction(Versement versement)
            throws CompteNonExistantException, TransactionException {
        Compte compteBeneficiaire = versement.getCompteBeneficiaire();
        String nomEmetteur = versement.getNomEmetteur();


        TransactionStatus transactionValid = versement.getStatus();

        if (transactionValid.getType() == TransactionStatus.Type.ACCOUNT) {
            LOGGER.error(transactionValid.getMessage());
            throw new CompteNonExistantException(transactionValid.getMessage());
        }
        if (transactionValid.getType() == TransactionStatus.Type.TRANSACTION) {
            LOGGER.error(transactionValid.getMessage());
            throw new TransactionException(transactionValid.getMessage());
        }

        compteBeneficiaire
                .setSolde(compteBeneficiaire.getSolde().add(versement.getMontantVirement()));
        compteRepository.save(compteBeneficiaire);

        versementRepository.save(versement);

        TransactionResult result = new TransactionResult();
        result.setAmount(versement.getMontantVirement());
        result.setFrom(nomEmetteur);
        result.setTo(compteBeneficiaire);
        result.setMotif(versement.getMotifVersement());
        result.setStatus(versement.getStatus());
        result.setType(EventType.VERSEMENT);

        return result;
    }
}
