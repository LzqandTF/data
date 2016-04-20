/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.utils.downview;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel行对象封装
 * 
 * @author dongrui
 * @version $Id: XRow.java, v 0.1 2012-12-4 下午5:03:20 Snow Exp $
 */
public class XRow {
    public int         rowIndex;
    public List<XCell> cells = new ArrayList<XCell>();

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCellsSize() {
        return cells.size();
    }

    public XRow addCell(XCell cell) {
        this.cells.add(cell);
        return this;
    }

    public XCell getCell(int cellIndex) {
        return cells.get(cellIndex);
    }
}
