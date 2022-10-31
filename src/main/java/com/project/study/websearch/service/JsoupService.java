package com.project.study.websearch.service;

import com.project.study.websearch.log.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;

public class JsoupService {

    private final Log logger = new Log(JsoupService.class);
    private final Document document;
    private final String url;

    public JsoupService(String htmlCode, String url) {
        this.document = getDocumentByHtml(htmlCode);
        this.url = url;
    }

    private Document getDocumentByHtml(String htmlCode) {
        return Jsoup.parse(htmlCode);
    }

    private void refactorDocument() throws URISyntaxException {
        logger.info("Refactoring document...");

        URI uri = new URI(url);
        String domain = uri.getHost();
        String protocol = uri.getScheme();
        String separator = "://";
        String domainName = protocol + separator + domain;
        logger.info("Protocol: " + protocol);
        logger.info("Domain: " + domainName);
        Elements links = document.getElementsByAttribute("href");
        for (Element link : links) {
            String href = link.attr("href");
            if (!href.startsWith("http")) {
                String refHref = href.replace("File:", "");
                if (href.startsWith("//")) {
                    refHref = protocol + ":" + href;
                } else if (href.startsWith("/")) {
                    refHref = domainName + href;
                }
                link.attr("href", refHref);
                logger.info(String.format("Refactoring tag <%s> from %s to %s", link.tagName(),
                        href, refHref));
            }
        }

        logger.info("Document successfully refactored!");
    }

    public String cleanPage() throws URISyntaxException {
        refactorDocument();
        Safelist safelist = Safelist.simpleText();

        logger.info("Removing elements...");
        document.getElementsByTag("header").remove();
        logger.info("The header tag has been removed");
        document.getElementsByTag("iframe").remove();
        logger.info("The iframe tag has been removed");
        document.getElementsByTag("footer").remove();
        logger.info("The footer tag has been removed");

        logger.info("Cleaning page ...");
        Document cleanedDoc = new Cleaner(safelist).clean(document);
        return cleanedDoc.outerHtml();
    }
}
