/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.utils.downview;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * excel2007及以上版本文件读取操作类
 * 
 * @author dongrui
 * @version $Id: MyHander.java, v 0.1 2012-12-5 上午1:16:43 Snow Exp $
 */
public class MyHander extends DefaultHandler {

    public OPCPackage         pkg;
    public SharedStringsTable sst;
    public List<String>       rowlist     = new ArrayList<String>();
    public int                curRow      = 0;
    public int                curCol      = 0;

    public String             lastContents;
    public boolean            nextIsString;
    public boolean            closeV      = false;

    public XRow               row         = null;
    public List<XRow>         rowDataList = new ArrayList<XRow>();

    public MyHander() {

    }

    public MyHander(OPCPackage pkg, SharedStringsTable sst) {
        this.pkg = pkg;
        this.sst = sst;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        if (name.equals("v")) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowlist.add(curCol, value);
            curCol++;
            closeV = true;
        } else {
            if (name.equals("c")) {
                if (!closeV) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
            }
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                row = new XRow();
                for (int i = 0; i < rowlist.size(); i++) {
                    XCell cell = new XCell();
                    cell.setColumnIndex(i + 'A');
                    cell.setRowIndex(curRow + 1);
                    cell.setValue((String) rowlist.get(i));
                    row.setRowIndex(curRow + 1);
                    row.addCell(cell);
                }
                rowDataList.add(row);
                rowlist.clear();
                curRow++;
                curCol = 0;
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => 单元格
        if (name.equals("c")) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
            closeV = false;
        }
        // 置空
        lastContents = "";
    }

    public List<XRow> returnRowlist(MyHander hander, Iterator<InputStream> sheets) throws Exception {

        XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        parser.setContentHandler(hander);
        while (sheets.hasNext()) {
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        return rowDataList;
    }
}
