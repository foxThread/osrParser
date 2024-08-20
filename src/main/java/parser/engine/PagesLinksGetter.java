package parser.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import parser.exceptions.HtmlLoadException;
import parser.exceptions.LinkGetterException;

public  abstract class PagesLinksGetter implements LinksGetter {

 
    private PauseMaker pauseMaker;

  
    private HtmlGetter htmlGetter;

    private Iterator<String> currentPageLinks = null;
    private Iterator<Integer> pageNumbers = null;
    
    private int firstPage=-1;
    private int lastPage=-1;
    private String pagePrefix="gtreb";
    private String baseUrl="-2";
    private String cssExpression="-2";

    
    public PagesLinksGetter(PauseMaker pauseMaker,HtmlGetter htmlGetter){
        this.pauseMaker=pauseMaker;
        this.htmlGetter=htmlGetter;
        shufflePageNumbers();
        currentPageLinks = Collections.emptyIterator();
        

    }



    public  PagesLinksGetter setFirstPage(int firstPage){
        this.firstPage=firstPage;
        return this;
    }

    public PagesLinksGetter setLastPage(int lastPage){
        this.lastPage=lastPage;
        return this;

    }

    public PagesLinksGetter setPagePrefix(String pagePrefix){
        this.pagePrefix=pagePrefix;
        return this;
    }

    public PagesLinksGetter setBaseUrl(String baseUrl){
        this.baseUrl=baseUrl;
        return this;
    }

    
    public PagesLinksGetter setCssExpression(String cssExpression){
        this.cssExpression=cssExpression;
        return this;
    }


    private void shufflePageNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        int i;

        for (i = firstPage; i <= lastPage; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        pageNumbers = numbers.iterator();

    }


    private String getNextPageUrl() {
        
        if (!pageNumbers.hasNext() || pageNumbers==null) {
            shufflePageNumbers();
        }

        String urlOfPage = baseUrl + pagePrefix + Integer.toString(pageNumbers.next());
        System.out.println(urlOfPage);

        return urlOfPage;

    }


     private void getLinksOnPage(String url) throws HtmlLoadException {

        ArrayList<String> urls = new ArrayList<>();
        Element rootElement = null;
        pauseMaker.pause();
        htmlGetter.getHtml(url);
        rootElement = htmlGetter.getRootElement();

        Elements links = rootElement.select(cssExpression);

        urls = new ArrayList<String>();

        for (Element link : links) {

            String linkString = baseUrl + link.attr("href");
            urls.add(linkString);
        }

        Collections.shuffle(urls);
      
        currentPageLinks = urls.iterator();

    }

    
    
    
    @Override
    public String getNextLink() throws LinkGetterException {
       
        if (!currentPageLinks.hasNext() || currentPageLinks==null) {
            try {
                getLinksOnPage(getNextPageUrl());
            } catch (HtmlLoadException e) {
                throw new LinkGetterException("error getting Links on page");
            }
        }

        if (currentPageLinks.hasNext()) {
            return currentPageLinks.next();
        } else {
            throw new LinkGetterException("error getting Links on page");
        }

    }
    }





