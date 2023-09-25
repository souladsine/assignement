package ma.octo.assignement.service;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @GetMapping
    public List<Utilisateur> getAll() {
        List<Utilisateur> all = utilisateurRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public Utilisateur getOne(long id){
        return utilisateurRepository.getById(id);
    }
}
