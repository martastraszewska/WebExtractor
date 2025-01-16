package pl.straszewska.auction.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import pl.straszewska.auction.model.Content;
import pl.straszewska.auction.service.ContentStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryContentStorage implements ContentStorage {

    private final Set<Content> storage;

    public InMemoryContentStorage() {
        this.storage = new HashSet<>();
        storage.addAll(getContentDtoFromJson());
    }

    public Set<Content> getAll() {
        return new HashSet<>(storage);
    }

    @Override
    public void saveAll(Set<Content> contents) {
        storage.clear();
        storage.addAll(contents);
    }

    private List<Content> getContentDtoFromJson() {
        ClassPathResource cpr = new ClassPathResource("listOfForrestsAdv.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Content> content = new ArrayList<>();
        try {
            byte[] jsonFile = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            content = mapper.readValue(jsonFile, new TypeReference<List<Content>>() {
            });
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
        return content;
    }

}
