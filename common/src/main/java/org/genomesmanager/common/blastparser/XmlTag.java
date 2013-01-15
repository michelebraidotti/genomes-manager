/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomesmanager.common.blastparser;

/**
 *
 * @author Kristofer
 */
public abstract class XmlTag {

    protected String openTag;
    protected String closeTag;
    protected String data;
    protected int fromIndex;

    protected abstract void setTagInfo();

    protected abstract void fillFields();

    public XmlTag() {
        setTagInfo();
    }
    /*public XmlTag(String tagContent) {
    data = tagContent;
    setTagInfo();
    }*/ //Doesn't do anything when I have it :(

    public String getOpenTag() {
        return openTag;
    }

    public String getCloseTag() {
        return closeTag;
    }

    public String getData() {
        return data;
    }

    public void setData(String Data) {
        data = Data;
    }

    private String parseDataBetweenTags(String findOpenTag, String findCloseTag) {
        if (data == null) {
            //complain
            return null;
        }
        if (fromIndex >= data.length()) {
            //complain
            return null;
        }

        int foundIndex = data.indexOf(findOpenTag, fromIndex);
        if (foundIndex < 0) {
            //System.err.println("Broken XML File! fromIndex="+fromIndex);
            //System.err.println("findOpenTag="+findOpenTag+" findCloseTag="+findCloseTag);
            return null;
        }
        int contentStart = foundIndex + findOpenTag.length();
        int contentEnd = data.indexOf(findCloseTag, contentStart);

        if ((contentStart >= contentEnd) || (contentStart < 0)) {
            System.err.println("Broken XML File!");
            //System.err.println("contentStart="+contentStart+" contentEnd="+contentEnd);
            //System.err.println("findOpenTag="+findOpenTag+" findCloseTag="+findCloseTag);
            return null;
        }
        fromIndex = contentEnd + findCloseTag.length();

        return data.substring(contentStart, contentEnd);
    }

    protected String createOpenTag(String tagName) {
        return new String("<" + tagName + ">");
    }

    protected String createCloseTag(String tagName) {
        return new String("</" + tagName + ">");
    }

    protected String parseDataBetweenTagName(String tagName) {
        return parseDataBetweenTags(
                createOpenTag(tagName),
                createCloseTag(tagName));
    }

    protected void setTagInfoHelper(String tagName) {
        openTag = new String(createOpenTag(tagName));
        closeTag = new String(createCloseTag(tagName));
    }
}
