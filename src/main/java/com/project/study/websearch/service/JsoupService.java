package com.project.study.websearch.service;

import com.project.study.websearch.log.Log;
import com.project.study.websearch.utils.ConverterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupService {

    private static final Log LOG = new Log(JsoupService.class);
    private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

    private final Document document;

    public JsoupService(String url) {
        this.document = getDocument(url);
    }

    private Document getDocument(String url) {
        LOG.info("Trying connect to " + url);

        try {
            Document doc = Jsoup.connect(url).get();
            LOG.info("Successfully connected to the url: " + url);
            return refactorDocument(doc);
        } catch (IOException ex) {
            LOG.error("Error trying connect to url. Cause: " + ex.getMessage());
            return null;
        }
    }

    public File cleanPageFile() {
        String htmlCode = removeElements();
        return makePageFile(htmlCode);
    }

    private Document refactorDocument(Document pageDocument) {
        LOG.info("Refactoring document...");

        String baseUri = pageDocument.baseUri();
        String protocol = baseUri.split("/")[0];
        String domainName = protocol + "//" + getDomainName(baseUri);
        Elements links = pageDocument.getElementsByAttribute("href");
        for (Element link : links) {
            String href = link.attr("href");
            if (!href.startsWith("http")) {
                String refHref = href.startsWith("//") ? protocol + href : domainName + href;
                link.attr("href", refHref);
                LOG.info(String.format("Refactoring tag <%s> from %s to %s", link.tagName(), href, refHref));
            }
        }

        LOG.info("Document successfully refactored!");
        return pageDocument;
    }

    public String removeElements() {
        /*
         * Allowed list:
         * a, b, blockquote, br, caption,
         * cite, code, col, colgroup, dd,
         * div, dl, dt, em, h1, h2, h3, h4, h5, h6,
         * i, img, li, ol, p, pre, q, small, span,
         * strike, strong, sub, sup, table, tbody,
         * td, tfoot, th, thead, tr, u, ul
         *
         * */
        Element headElement = document.head();
        headElement.getElementsByTag("script").remove();
        Safelist safelist = Safelist.relaxed()
                .addTags("main", "section", "article", "style")
                .addAttributes("main", "style", "class", "id")
                .addAttributes("section", "style", "class", "id")
                .addAttributes("article", "style", "class", "id")
                .addAttributes("div", "class", "id")
                .addAttributes("a", "style", "class", "id")
                .addAttributes("p", "style", "class", "id")
                .removeTags("img");

        Document cleanedDoc = new Cleaner(safelist).clean(document);
        cleanedDoc.tagName("html").insertChildren(0, headElement);
        return cleanedDoc.outerHtml();
    }

    private File makePageFile(String htmlCode) {
        LOG.info("Converting document to pdf...");
        String baseUri = getDomainName(document.baseUri());
        String fileName = "WebPage_" + baseUri + System.currentTimeMillis();
        File file = ConverterUtils.convertHtmlToPdf(fileName, htmlCode);
        assert file != null;
        LOG.info("Document successfully created in path: " + file.getAbsolutePath());
        return file;
    }

    private String getDomainName(String url) {
        Pattern patternDomain = Pattern.compile(DOMAIN_NAME_PATTERN);
        Matcher matcher = patternDomain.matcher(url);
        if (matcher.find()) {
            return matcher.group(0).toLowerCase().trim();
        }

        return null;
    }
}
