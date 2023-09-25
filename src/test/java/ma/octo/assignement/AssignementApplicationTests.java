package ma.octo.assignement;

import ma.octo.assignement.web.CompteController;
import ma.octo.assignement.web.UtilisateurController;
import ma.octo.assignement.web.VersementController;
import ma.octo.assignement.web.VirementController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssignementApplicationTests {

	@Autowired
	private CompteController compteController;

	@Autowired
	private UtilisateurController utlisateurController;

	@Autowired
	private VersementController versememtController;

	@Autowired
	private VirementController virementController;


	@DisplayName("context loading")
	@Test
	void contextLoads() {
		Assertions.assertNotNull(compteController);
		Assertions.assertNotNull(utlisateurController);
		Assertions.assertNotNull(versememtController);
		Assertions.assertNotNull(virementController);
	}
}
