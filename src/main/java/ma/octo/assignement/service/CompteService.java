package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> getAll() {
        List<Compte> all = compteRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public Compte getOne(String nrCompte){
        if(nrCompte.trim().equals("")) return null;
        return compteRepository.findByNrCompte(nrCompte);
    }

    public void deductFromAccount(Compte account, BigDecimal amount){
        account.setSolde(account.getSolde().subtract(amount));
        compteRepository.save(account);
    }

    public void addToAccount(Compte account, BigDecimal amount){
        account.setSolde(account.getSolde().add(amount));
        compteRepository.save(account);
    }
}
