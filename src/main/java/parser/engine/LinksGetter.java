package parser.engine;

import org.springframework.stereotype.Service;

import parser.exceptions.LinkGetterException;

@Service
public interface LinksGetter {
    public String getNextLink() throws LinkGetterException;

}
