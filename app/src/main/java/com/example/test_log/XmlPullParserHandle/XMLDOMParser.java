package com.example.test_log.XmlPullParserHandle;

public class XMLDOMParser {
    public Document getDocument(String xml)
    {
        Document document = null;

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            is.setEncoding("UTF-8");
            document = db.parse(is);
        }catch(ParserConfigurationException e)
        {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        catch (SAXException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        catch(IOException e){
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        return document;
    }
    public String getValue(Element item, String name)
    {
        NodeList nodes = item.getElementsByTagName(name);
        String sss = getTextNodeValue(nodes.item(0));
        return this.getTextNodeValue(nodes.item(0));
    }
    public String getValueDesc(Element item, String name)
    {
        String desc =
                item.getElementsByTagName(name).item(0).getTextContent();
        return desc;
    }
    public String getImageLink(String htmlContent)
    {
// Parse the html description to get the image url
        String html = htmlContent;
        org.jsoup.nodes.Document docHtml = Jsoup.parse(html);
        Elements imgEle = docHtml.select("img");
        return imgEle.attr("src");
    }
    public String getDescContent(String htmlContent)
    {
// Parse the html description to get the image url
        String html = htmlContent;
        org.jsoup.nodes.Document docHtml = Jsoup.parse(html);
        return docHtml.text();
    }
    public String getTextNodeValue(Node elem) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child =
                        child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE ){

                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}