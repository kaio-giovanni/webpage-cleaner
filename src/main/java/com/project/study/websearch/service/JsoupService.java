package com.project.study.websearch.service;

import com.project.study.websearch.log.Log;
import com.project.study.websearch.utils.ConverterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupService {

    private static final Log LOG = new Log(JsoupService.class);
    private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

    private final Document document;
    private final String url;

    public JsoupService(String htmlCode, String url) {
        this.document = getDocumentByHtml(htmlCode);
        this.url = url;
        refactorDocument();
    }

    private Document getDocumentByHtml(String htmlCode) {
        return Jsoup.parse(htmlCode);
    }

    public File cleanPageFile() {
        String htmlCode = cleanPage();
        return makePageFile(htmlCode);
    }

    private void refactorDocument() {
        LOG.info("Refactoring document...");

        String protocol = url.split("/")[0];
        String domainName = protocol + "//" + getDomainName(url);
        Elements links = document.getElementsByAttribute("href");
        for (Element link : links) {
            String href = link.attr("href");
            if (!href.startsWith("http")) {
                String refHref = href.startsWith("//") ? protocol + href : domainName + href;
                link.attr("href", refHref);
                LOG.info(String.format("Refactoring tag <%s> from %s to %s", link.tagName(), href, refHref));
            }
        }

        LOG.info("Document successfully refactored!");
    }

    public String cleanPage() {
        Element headElement = document.head();
        Safelist safelist = Safelist.relaxed()
                .addTags("main", "section", "article", "style", "nav", "aside", "body", "time")
                .addAttributes("main", "style", "class", "id")
                .addAttributes("section", "style", "class", "id")
                .addAttributes("article", "style", "class", "id")
                .addAttributes("nav", "style", "class", "id")
                .addAttributes("div", "class", "style", "id")
                .addAttributes("aside", "class", "style", "id")
                .addAttributes("body", "class", "style", "id")
                .addAttributes("span", "style", "class", "id")
                .addAttributes("time", "class", "style", "id")
                .addAttributes("a", "style", "class", "id")
                .addAttributes("p", "style", "class", "id")
                .addAttributes("ul", "style", "class", "id")
                .addAttributes("li", "style", "class", "id")
                .removeTags("img");

        LOG.info("Clean page ...");
        Document cleanedDoc = new Cleaner(safelist).clean(document);
        removeElements(cleanedDoc);

        cleanedDoc.tagName("html").insertChildren(0, headElement);
        return cleanedDoc.outerHtml();
    }

    private void removeElements(Document pageDocument) {
        LOG.info("Removing elements...");
        pageDocument.getElementsByTag("header").remove();
        pageDocument.getElementsByTag("iframe").remove();

        Elements divWithoutChildren = pageDocument.getElementsByTag("div");
        for (Element div : divWithoutChildren) {
            if (div.childNodeSize() == 0) {
                div.remove();
            }
        }
    }

    private File makePageFile(String htmlCode) {
        LOG.info("Converting document to pdf...");
        String fileName = "WebPage_" + getDomainName(url) + System.currentTimeMillis();
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
