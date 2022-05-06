package com.project.study.websearch.dto;

import java.util.List;

public class SerpResponseDto {

    private List<OrganicResults> organic_results;

    public SerpResponseDto() {
    }

    public List<OrganicResults> getOrganicResults() {
        return organic_results;
    }

    public void setOrganicResults(List<OrganicResults> organic_results) {
        this.organic_results = organic_results;
    }

    public static class OrganicResults {
        private int position;
        private String title;
        private String link;
        private String displayed_link;
        private String snippet;
        private String[] snippet_highlighted_words;

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

        public String getDisplayed_link() {
            return displayed_link;
        }

        public OrganicResults setDisplayedLink(String displayed_link) {
            this.displayed_link = displayed_link;
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
            return snippet_highlighted_words;
        }

        public OrganicResults setSnippetHighlightedWords(String[] snippet_highlighted_words) {
            this.snippet_highlighted_words = snippet_highlighted_words;
            return this;
        }
    }
}
