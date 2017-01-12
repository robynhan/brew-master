package org.robynhan.com.brewmaster.resource;


import org.robynhan.com.brewmaster.coredomain.model.CaseFile;
import org.robynhan.com.brewmaster.coredomain.model.CaseFolder;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFileRepository;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFolderRepository;
import org.robynhan.com.brewmaster.resource.request.AddCaseFile;
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
@Path("/casefile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CaseFileResource {
    private static final String URI_PREFIX = "casefile";
    private final CaseFolderRepository folderRepository;
    private final CaseFileRepository fileRepository;

    @Inject
    public CaseFileResource(final CaseFolderRepository folderRepository,
                            final CaseFileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    @POST
    public Response addCaseFile(@NotNull final AddCaseFile addCaseFile) {
        CaseFolder caseFolder = getCaseFolder(addCaseFile.getFolderId());
        CaseFile caseFile = addCaseFile.convertToCaseFile();

        caseFolder.addFile(caseFile);
        folderRepository.save(caseFolder);

        return Response.created(URI.create(getFileUri(caseFile.getName()))).build();
    }

    @GET
    @Path("/{fileName}")
    public Response queryCaseFile(@PathParam("fileName") final String fileName) {
        return Response.ok(getCaseFile(fileName)).build();
    }

    @POST
    @Path("/{fileName}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response patchFile(@PathParam("fileName") final String fileName,
                              final String content) {
        CaseFile caseFile = getCaseFile(fileName);
        caseFile.setContent(content);
        fileRepository.save(caseFile);
        return Response.ok().build();
    }

    @DELETE
    @Path("{fileName}")
    public Response deleteCaseFile(@PathParam("fileName") final String fileName) {
        fileRepository.delete(getCaseFile(fileName));
        return Response.ok().build();
    }

    private String getFileUri(final String fileName) {
        return new StringJoiner("/").add(URI_PREFIX).add(fileName).toString();
    }

    private CaseFolder getCaseFolder(final String folderName) {
        Optional<CaseFolder> caseFolder = folderRepository.findOneByName(folderName);
        if (!caseFolder.isPresent()) {
            throw new NotFoundException();
        }
        return caseFolder.get();
    }

    private CaseFile getCaseFile(final String fileName) {
        Optional<CaseFile> caseFile = fileRepository.findOneByName(fileName);
        if (!caseFile.isPresent()) {
            throw new NotFoundException();
        }
        return caseFile.get();
    }
}
