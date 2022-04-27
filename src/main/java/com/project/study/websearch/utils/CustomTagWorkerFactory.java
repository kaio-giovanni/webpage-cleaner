package com.project.study.websearch.utils;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.styledxmlparser.node.IElementNode;

public class CustomTagWorkerFactory extends DefaultTagWorkerFactory {

    @Override
    public ITagWorker getCustomTagWorker (IElementNode tag, ProcessorContext context) {
        if (TagConstants.HTML.equals(tag.name())) {
            return new ZeroMarginHtmlTagWorker(tag, context);
        }
        return null;
    }
}

class ZeroMarginHtmlTagWorker extends HtmlTagWorker {
    public ZeroMarginHtmlTagWorker (IElementNode element, ProcessorContext context) {
        super(element, context);
        Document doc = (Document) getElementResult();
        doc.setMargins(0, 0, 0, 0);
    }
}