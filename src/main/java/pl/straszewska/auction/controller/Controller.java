package pl.straszewska.auction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.straszewska.auction.model.Content;
import pl.straszewska.auction.service.ContentService;


import java.util.*;

@Slf4j
@RestController
@RequestMapping()
public class Controller {

    @Autowired
    private ContentService contentService;

    @GetMapping(value = "/getAll", produces = "application/json")
    public Set<Content> getAllContent()
    {
        System.out.println("jsdnjdfksa");
        return contentService.getAll();
    }


    @GetMapping(value = "/update")
    public Map<String, Object> update() {
        Map<String, Object> response = new HashMap<>();
        int status = HttpStatus.OK.value();
        int numberOfUpdated = 0;
        try {
            numberOfUpdated = contentService.updateContent();
        } catch (IncorrectUrlException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            response.put("status", status);
        }
        response.put("status", status);
        response.put("NumberOfUpdated", numberOfUpdated);
        return response;
    }
}
