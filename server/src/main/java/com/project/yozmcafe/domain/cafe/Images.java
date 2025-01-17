package com.project.yozmcafe.domain.cafe;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;

import java.util.List;

@Embeddable
public class Images {

    private static final int REPRESENTATIVE_INDEX = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "image")
    private List<String> urls;

    protected Images() {
    }

    public Images(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getRepresentativeImage() {
        return urls.get(REPRESENTATIVE_INDEX);
    }
}
