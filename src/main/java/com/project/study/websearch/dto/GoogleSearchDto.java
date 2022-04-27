package com.project.study.websearch.dto;

import java.util.List;

public class GoogleSearchDto {

    private List<OrganicResults> organic_results;

    public GoogleSearchDto() {
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

        public void setPosition(int position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDisplayed_link() {
            return displayed_link;
        }

        public void setDisplayed_link(String displayed_link) {
            this.displayed_link = displayed_link;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String[] getSnippet_highlighted_words() {
            return snippet_highlighted_words;
        }

        public void setSnippet_highlighted_words(String[] snippet_highlighted_words) {
            this.snippet_highlighted_words = snippet_highlighted_words;
        }
    }
}
