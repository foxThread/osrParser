package parser.engine;

import parser.exceptions.LinkGetterException;

public interface LinksGetter {
    public String getNextLink() throws LinkGetterException;

}
