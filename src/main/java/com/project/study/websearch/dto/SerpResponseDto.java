package com.project.study.websearch.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerpResponseDto {

    @SerializedName("organic_results")
    private List<OrganicResults> organicResults;

    public SerpResponseDto() {
        // do nothing
    }

    public List<OrganicResults> getOrganicResults() {
        return organicResults;
    }

    public void setOrganicResults(List<OrganicResults> organicResults) {
        this.organicResults = organicResults;
    }

    public static class OrganicResults {
        @SerializedName("position")
        private int position;
        @SerializedName("title")
        private String title;
        @SerializedName("link")
        private String link;
        @SerializedName("displayed_link")
        private String displayedLink;
        @SerializedName("snippet")
        private String snippet;
        @SerializedName("snippet_highlighted_words")
        private String[] snippetHighlightedWords;

        public OrganicResults() {
        }

        public int getPosition() {
            return position;
        }

        public OrganicResults setPosition(int position) {
            this.position = position;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public OrganicResults setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getLink() {
            return link;
        }

        public OrganicResults setLink(String link) {
            this.link = link;
            return this;
        }

        public String getDisplayedLink() {
            return displayedLink;
        }

        public OrganicResults setDisplayedLink(String displayedLink) {
            this.displayedLink = displayedLink;
            return this;
        }

        public String getSnippet() {
            return snippet;
        }

        public OrganicResults setSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public String[] getSnippetHighlightedWords() {
            return snippetHighlightedWords;
        }

        public OrganicResults setSnippetHighlightedWords(String[] snippetHighlightedWords) {
            this.snippetHighlightedWords = snippetHighlightedWords;
            return this;
        }
    }
}
