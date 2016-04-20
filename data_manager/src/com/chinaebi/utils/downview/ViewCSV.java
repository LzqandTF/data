package com.chinaebi.utils.downview;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinaebi.utils.StringUtils;

/**
 * 生成CSV视图，可用excel工具打开或者保存  
 * 
 * @author dongrui
 * @version $Id: ViewExcel.java, v 0.1 2012-8-2 下午2:42:33 Snow Exp $
 */
public class ViewCSV {

    public Logger         logger = LoggerFactory.getLogger(ViewCSV.class);

    public String         fileName;
    public String[]       headData;
    public List<String[]> dataList;
    public String[]       endData;

    public ViewCSV() {
    }

    public String buildCSVDocument(String fileName, List<String[]> dataList, String[] headData, String[] endData, HttpServletResponse response) {
        StringBuffer csvData = new StringBuffer();
        if (StringUtils.isBlank(fileName)) {
            logger.info("传入文件名称不能为空");
            return null;
        }
        if (headData.length <= 0) {
            logger.info("传入报表列数必须大于0");
            return null;
        }
        if (!fileName.endsWith(".csv")) {
            fileName = fileName + ".csv";
        }

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            response.setBufferSize(81920);

            // excel头部
            for (int i = 0; i < headData.length; i++) {
                csvData.append("\t" + headData[i] + ",");
            }
            csvData.append("\r\n");
            // excel数据   
            for (int i = 0; i < dataList.size(); i++) {
                String[] data = dataList.get(i);
                for (int j = 0; j < data.length; j++) {
                    csvData.append(data[j] + ","); //往一行添加数据
                }
                csvData.append("\r\n");
            }
            // excel尾部
            if (endData != null && endData.length > 0) {
                for (int i = 0; i < endData.length; i++) {
                    csvData.append(endData[i] + ",");
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("报表数据解析异常", e);
        }
        return csvData.toString();
    }

}
