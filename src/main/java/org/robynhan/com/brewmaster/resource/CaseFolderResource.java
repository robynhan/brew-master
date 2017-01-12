package org.robynhan.com.brewmaster.resource;

import org.robynhan.com.brewmaster.coredomain.model.CaseFolder;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFolderRepository;
import org.robynhan.com.brewmaster.resource.request.AddCaseFolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;
import java.util.StringJoiner;

@Component
@Path("/casefolder")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CaseFolderResource {
    private static final String URI_PREFIX = "casefolder";
    private final CaseFolderRepository folderRepository;

    @Inject
    public CaseFolderResource(final CaseFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    private String getFolderUri(final String folderName) {
        return new StringJoiner("/").add(URI_PREFIX).add(folderName).toString();
    }

    @POST
    public Response addCaseFolder(@NotNull final AddCaseFolder addCaseFolder) {
        folderRepository.save(convertToCaseFolder(addCaseFolder));
        return Response.created(URI.create(getFolderUri(addCaseFolder.getFolderName()))).build();
    }

    private CaseFolder convertToCaseFolder(final AddCaseFolder addCaseFolder) {
        if (addCaseFolder.getParent() == null) {
            return new CaseFolder(addCaseFolder.getFolderName(), null);
        } else {
            CaseFolder parentFolder = getCaseFolder(addCaseFolder.getParent());
            CaseFolder childFolder = new CaseFolder(addCaseFolder.getFolderName(), parentFolder);
            parentFolder.addChild(childFolder);
            childFolder.setParent(parentFolder);

            folderRepository.save(parentFolder);
            return childFolder;
        }
    }

    @GET
    @Path("/{folderName}")
    public Response queryCaseFolder(@PathParam("folderName") final String folderName) {
        return Response.ok(getCaseFolder(folderName)).build();
    }

    @DELETE
    @Path("{folderName}")
    public Response deleteCaseFolder(@PathParam("folderName") final String folderName) {
        folderRepository.delete(getCaseFolder(folderName));
        return Response.ok().build();
    }

    private CaseFolder getCaseFolder(final String folderName) {
        Optional<CaseFolder> caseFolder = folderRepository.findOneByName(folderName);
        if (!caseFolder.isPresent()) {
            throw new NotFoundException();
        }
        return caseFolder.get();
    }
}
