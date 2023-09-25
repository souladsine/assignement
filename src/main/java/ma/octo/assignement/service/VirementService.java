package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.domain.util.TransactionStatus;
import ma.octo.assignement.dto.results.TransactionResult;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VirementRepository;
import ma.octo.assignement.web.VirementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VirementService {
    Logger LOGGER = LoggerFactory.getLogger(VirementController.class);

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private VirementRepository virementRepository;


    public List<Virement> getAll() {
        List<Virement> all = virementRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public TransactionResult createTransaction(Virement virement)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {

        Compte compteBeneficiaire = virement.getCompteBeneficiaire();
        Compte compteEmetteur = virement.getCompteEmetteur();

        TransactionStatus transactionValid = virement.getStatus();

        if (transactionValid.getType() == TransactionStatus.Type.ACCOUNT) {
            LOGGER.error(transactionValid.getMessage());
            throw new CompteNonExistantException(transactionValid.getMessage());
        }
        if (transactionValid.getType() == TransactionStatus.Type.CREDIT) {
            LOGGER.error(transactionValid.getMessage());
            throw new SoldeDisponibleInsuffisantException(transactionValid.getMessage());
        }
        if (transactionValid.getType() == TransactionStatus.Type.TRANSACTION) {
            LOGGER.error(transactionValid.getMessage());
            throw new TransactionException(transactionValid.getMessage());
        }

        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virement.getMontantVirement()));
        compteRepository.save(compteEmetteur);

        compteBeneficiaire
                .setSolde(compteBeneficiaire.getSolde().add(virement.getMontantVirement()));
        compteRepository.save(compteBeneficiaire);

        virementRepository.save(virement);

        TransactionResult result = new TransactionResult();
        result.setAmount(virement.getMontantVirement());
        result.setFrom(compteEmetteur);
        result.setTo(compteBeneficiaire);
        result.setMotif(virement.getMotifVirement());
        result.setStatus(virement.getStatus());
        result.setType(EventType.VIREMENT);

        return result;
    }
}
