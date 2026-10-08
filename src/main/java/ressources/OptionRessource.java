package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class OptionRessource {

    private OptionBusiness optBusiness = new OptionBusiness();

    // 1) Création d'une nouvelle option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOption(Option opt) {
        if (opt != null && this.optBusiness.addOption(opt)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de toutes les options & 3) d'un domaine spécifique
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        if (domaine != null && !domaine.trim().isEmpty()) {
            List<Option> options = this.optBusiness.getOptionsByDomaine(domaine);
            return Response.status(Response.Status.OK).entity(options).build();
        }
        return Response.status(Response.Status.OK).entity(this.optBusiness.getListeOptions()).build();
    }

    // 4) Suppression d'une option ayant un code spécifique
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (this.optBusiness.deleteOption(code)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d'une option ayant un code spécifique
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option opt) {
        if (opt == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (this.optBusiness.updateOption(code, opt)) {
            return Response.ok(opt).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupération d'une option ayant un code donné
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option opt = this.optBusiness.getOptionByCode(code);
        if (opt != null) {
            return Response.status(Response.Status.OK).entity(opt).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}



