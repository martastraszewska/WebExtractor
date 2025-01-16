package pl.straszewska.auction.service;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.straszewska.auction.controller.IncorrectUrlException;
import pl.straszewska.auction.model.Content;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class ConcurrentComparator implements Runnable {

    private Content content;
    private Set<Content> globalUpdatedContent;


    public ConcurrentComparator(Content content, Set<Content> globalUpdatedContent) {
        this.content = content;
        this.globalUpdatedContent = globalUpdatedContent;
    }

    @Override
    public void run() {
        String currentContent = "";
        try {
            currentContent = fetchContentFromSite(content);
        } catch (IncorrectUrlException e) {
            log.info("Error while retrieving content from url: " + content.getWebsiteUrl(), e.getMessage());
        }
        String currentContentHash = Hashing.sha256()
                .hashString(currentContent, StandardCharsets.UTF_8)
                .toString();

        boolean differentResult = !Objects.equals(currentContentHash, content.getContentHash());
        if (differentResult) {
            Content updatedContent = new Content(content.getName(), currentContentHash, Instant.now().toEpochMilli(), content.getWebsiteUrl(), content.getDataSelector());
            globalUpdatedContent.add(updatedContent);
        }
        log.info("Compared URL: " + content.getWebsiteUrl() + " result: " + differentResult);
    }

    private String fetchContentFromSite(Content content) throws IncorrectUrlException {
        Document html;
        Elements element;
        try {
            html = Jsoup.connect(content.getWebsiteUrl()).get();
            element = html.select(content.getDataSelector());
        } catch (Exception e) {
            log.info("Error while fetching URL: " + content.getWebsiteUrl(), e);
            throw new IncorrectUrlException("Error while fetching URL: " + content.getWebsiteUrl());
        }
        return element.html();
    }
}
