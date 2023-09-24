package ma.octo.assignement.web;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @GetMapping
    List<Utilisateur> loadAllUtilisateur() {
        List<Utilisateur> all = utilisateurRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }
}
