package ma.octo.assignement.web;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;
    @GetMapping
    List<Utilisateur> loadAllUtilisateur() {
        return utilisateurService.getAll();
    }
}
