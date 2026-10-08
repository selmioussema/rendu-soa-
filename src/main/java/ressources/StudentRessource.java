package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class StudentRessource {

    private EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // 1) Création d'un nouvel étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEtudiant(Etudiant etudiant) {
        if (etudiant != null && this.etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de tous les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.status(Response.Status.OK).entity(this.etudiantBusiness.getAllEtudiants()).build();
    }

    // 6) Récupération de la liste des étudiants inscrits à une option donnée (codeOption) en XML
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option opt = this.optionBusiness.getOptionByCode(codeOption);
        if (opt != null) {
            List<Etudiant> etudiants = this.etudiantBusiness.getEtudiantsByOption(opt);
            return Response.status(Response.Status.OK).entity(new EtudiantList(etudiants)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 3) Récupération d'un étudiant ayant un identifiant spécifique
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = this.etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) Suppression d'un étudiant ayant un identifiant spécifique
    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (this.etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d'un étudiant ayant un identifiant spécifique
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (this.etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}