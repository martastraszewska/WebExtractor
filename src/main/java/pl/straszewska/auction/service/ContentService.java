package pl.straszewska.auction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.straszewska.auction.controller.IncorrectUrlException;
import pl.straszewska.auction.model.Content;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ContentService {
    @Autowired
    private ContentStorage contentStorage;

    public int updateContent() throws IncorrectUrlException {
        Set<Content> updatedContents = ConcurrentHashMap.newKeySet();
        Set<Content> contents = contentStorage.getAll();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (Content con : contents) {
            Runnable toRun = new ConcurrentComparator(con, updatedContents);
            executor.execute(toRun);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        updatedContents.forEach(c -> contents.removeIf(it -> it.getWebsiteUrl().equals(c.getWebsiteUrl())));
        contents.addAll(updatedContents);
        contentStorage.saveAll(contents);
        return updatedContents.size();
    }


    public Set<Content> getAll() {
        return contentStorage.getAll();
    }
}
