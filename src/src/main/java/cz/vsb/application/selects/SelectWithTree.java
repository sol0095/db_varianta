package cz.vsb.application.selects;

import org.w3c.dom.Element;

public class SelectWithTree {

    private String query;
    private Element element;

    public SelectWithTree(Element element, String query) {
        this.element = element;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Element getElement() {
        return element;
    }

}
