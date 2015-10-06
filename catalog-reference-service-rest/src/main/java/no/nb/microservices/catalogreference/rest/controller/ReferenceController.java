package no.nb.microservices.catalogreference.rest.controller;

import no.nb.microservices.catalogreference.core.reference.service.IReferenceService;
import no.nb.microservices.catalogreference.model.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class ReferenceController {
    private static final Logger LOG = LoggerFactory.getLogger(ReferenceController.class);
    private final IReferenceService referenceService;

    @Autowired
    public ReferenceController(IReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @RequestMapping(value = "/reference/{id}/wiki", method = RequestMethod.GET)
    public ResponseEntity<String> createWikiReference(@PathVariable("id") String id) {
        Reference reference = referenceService.getWikipediaReference(id);
        return new ResponseEntity<String>(reference.generateReferenceString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/reference/{id}/ris", method = RequestMethod.GET)
    public void createRISReference(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/ris");
        response.setHeader("Content-Disposition", "attachment; filename=" + id + ".ris");
        OutputStream outputStream = response.getOutputStream();

        Reference reference = referenceService.getRISReference(id);
        String refString = reference.generateReferenceString();
        outputStream.write(refString.getBytes());
        outputStream.close();
    }

    @RequestMapping(value = "/reference/{id}/enw", method = RequestMethod.GET)
    public void createENWReference(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/enw");
        response.setHeader("Content-Disposition", "attachment; filename=" + id + ".enw");
        OutputStream outputStream = response.getOutputStream();

        Reference reference = referenceService.getRISReference(id);
        String refString = reference.generateReferenceString();
        outputStream.write(refString.getBytes());
        outputStream.close();
    }


}