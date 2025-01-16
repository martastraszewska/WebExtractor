package pl.straszewska.auction.service;

import pl.straszewska.auction.model.Content;

import java.util.Set;

public interface ContentStorage {

    public Set<Content> getAll();

    void saveAll(Set<Content> contents);
}
