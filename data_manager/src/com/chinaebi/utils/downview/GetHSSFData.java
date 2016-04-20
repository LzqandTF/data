/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.utils.downview;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel2003及以下版本文件读取操作类
 * 
 * @author dongrui
 * @version $Id: GetHSSFData.java, v 0.1 2012-12-5 上午1:15:54 Snow Exp $
 */
public class GetHSSFData extends MyHSSFListener {

    public static Logger logger      = LoggerFactory.getLogger(GetHSSFData.class);

    public XRow          row         = null;
    public List<XRow>    rowDataList = new ArrayList<XRow>();

    public GetHSSFData(POIFSFileSystem fs) {
        super(fs);
    }

    public GetHSSFData(String filename) throws FileNotFoundException, IOException {
        this(new POIFSFileSystem(new FileInputStream(filename)));
    }

    public void optRows(XRow row) throws SQLException {
        rowDataList.add(row);
    }

    public List<XRow> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(List<XRow> rowDataList) {
        this.rowDataList = rowDataList;
    }

}
