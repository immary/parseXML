package com.chenmr.parseXML.SAX;

import com.chenmr.parseXML.entity.Book;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Chenmr on 2018/4/21/021.
 */
public class SAXParserHandler extends DefaultHandler {

    String value = null;
    Book book = null;

    private ArrayList<Book> bookList = new ArrayList<Book>();
    public ArrayList<Book> getBookList(){
        return bookList;
    }

    int bookIndex = 0;

    /**
     * 用来标识解析开始
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException{
        super.startDocument();
        System.out.println("SAX解析开始");
    }

    /**
     * 用来标识解析结束
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("SAX解析结束");
    }

    /**
     * 解析xml元素
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement方法
        super.startElement(uri, localName, qName, attributes);
        if("book".equals(qName)){
            book = new Book();
            //开始解析bool元素的属性
            System.out.println("==============开始遍历某一本书的内容================");
            int num = attributes.getLength();
            for(int i=0; i<num; i++){
                System.out.println(
                        "book元素的第" + (i+1) + "个属性名是：" + attributes.getQName(i) +
                                ",值是：" + attributes.getValue(i));
                if(attributes.getQName(i).equals("id"))
                    book.setId(attributes.getValue(i));
            }
        }else if (!"bookstore".equals(qName)){
            System.out.println("节点名是：" + qName);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //调用DefaultHandler类的endElement方法
        super.endElement(uri, localName, qName);
        //判断是否针对一本书已经遍历结束
        if("book".equals(qName)){
            bookList.add(book);
            book = null;
            System.out.println("==============结束遍历某一本书的内容================");
        }else if ("name".equals(qName)){
            book.setName(value);
        }else if ("author".equals(qName)){
            book.setAuthor(value);
        }else if ("year".equals(qName)){
            book.setYear(value);
        }else if ("price".equals(qName)){
            book.setPrice(value);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if(!value.trim().equals("")){
            System.out.println("节点值是：" + value);
        }
    }
}
