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
        Element headElement = document.head();
        final String style = "style";
        final String _class = "class";
        final String id = "id";
        Safelist safelist = Safelist.relaxed()
                .addTags("main", "section", "article", "style", "img", "picture", "source", "span", "nav", "aside",
                        "body", "time", "table", "thead", "tbody", "tfoot", "td", "th", "tr")
                .addAttributes("main", style, _class, id)
                .addAttributes("section", style, _class, id)
                .addAttributes("article", style, _class, id)
                .addAttributes("nav", style, _class, id)
                .addAttributes("div", _class, style, id)
                .addAttributes("aside", _class, style, id)
                .addAttributes("body", _class, style, id)
                .addAttributes("span", style, _class, id)
                .addAttributes("time", _class, style, id)
                .addAttributes("table", style, _class, id)
                .addAttributes("a", style, _class, id)
                .addAttributes("p", style, _class, id)
                .addAttributes("ul", style, _class, id)
                .addAttributes("ol", style, _class, id)
                .addAttributes("li", style, _class, id);

        logger.info("Removing elements...");
        document.getElementsByTag("header").remove();
        logger.info("The header tag has been removed");
        document.getElementsByTag("iframe").remove();
        logger.info("The iframe tag has been removed");
        document.getElementsByTag("footer").remove();
        logger.info("The footer tag has been removed");

        logger.info("Cleaning page ...");
        Document cleanedDoc = new Cleaner(safelist).clean(document);

        cleanedDoc.tagName("html").insertChildren(0, headElement);
        return cleanedDoc.outerHtml().replace("\u00a0", "");
    }
}
