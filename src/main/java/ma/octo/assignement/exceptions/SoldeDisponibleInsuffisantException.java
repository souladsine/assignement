package ma.octo.assignement.exceptions;

public class SoldeDisponibleInsuffisantException extends Exception {

  private static final long serialVersionUID = 1L;

  public SoldeDisponibleInsuffisantException() {
  }

  public SoldeDisponibleInsuffisantException(String message) {
    super(message);
  }
}
//try {
//        // Code qui vérifie si un compte existe
//        if (compteNexistePas) {
//        throw new CompteNonExistantException("Le compte n'existe pas.");
//        }
//        } catch (CompteNonExistantException e) {
//        // Gérer l'exception
//        System.out.println("Une exception s'est produite : " + e.getMessage());
//        // Autres opérations de gestion de l'exception
//        }
