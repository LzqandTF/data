package com.chinaebi.utils.downview;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.chinaebi.utils.StringUtils;

/**
 * 生成excel视图，可用excel工具打开或者保存 由ViewController的return new ModelAndView(viewExcel,
 * model)生成
 * 
 * @author dongrui
 * @version $Id: ViewExcel.java, v 0.1 2012-8-2 下午2:42:33 Snow Exp $
 */
@SuppressWarnings("deprecation")
public class ViewExcel extends AbstractExcelView {

	public Logger logger = LoggerFactory.getLogger(ViewExcel.class);

	public String fileName;
	public Integer orderType;
	public String[] headData;
	public List<String[]> dataList;
	public String[] endData;

	public ViewExcel() {
	}

	/**
	 * 传递的参数
	 * 
	 * @param orderType
	 *            交易订单类型
	 * @param fileName
	 *            文件名称
	 * @param dataList
	 *            文件数据
	 * @param headData
	 *            文件头数据
	 * @param endData
	 *            文件尾数据
	 */
	public ViewExcel(String fileName, String[] headData,
			List<String[]> dataList, String[] endData) {
		this.fileName = fileName;
		this.headData = headData;
		this.dataList = dataList;
		this.endData = endData;
	}

	/**
	 * 组装excel格式数据
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map,
	 *      org.apache.poi.hssf.usermodel.HSSFWorkbook,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(fileName)) {
			logger.info("传入文件名称不能为空");
			return;
		}
		if (headData.length <= 0) {
			logger.info("传入报表列数必须大于0");
			return;
		}
		if (!fileName.endsWith(".xls")) {
			fileName = fileName + ".xls";
		}
		try {
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			if (request.getHeader("User-Agent").toLowerCase()
					.indexOf("firefox") > 0) // firefox浏览器
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ new String(fileName.getBytes("UTF-8"),
										"ISO8859-1"));
			else if (request.getHeader("User-Agent").toUpperCase()
					.indexOf("MSIE") > 0) // IE浏览器
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ URLEncoder.encode(fileName, "UTF-8"));
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 10);
			style.setAlignment((short) 10);
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBottomBorderColor(HSSFColor.AQUA.index); // 设置单元格的边框颜色
			// 产生Excel表头
			// HSSFSheet sheet = workbook.createSheet(createSheet(orderType));
			HSSFSheet sheet = workbook.createSheet(fileName);
			
			HSSFRow header = sheet.createRow(0); // 第0行
			// excel头部
			for (int i = 0; i < headData.length; i++) {
				header.createCell(i).setCellValue(headData[i]);
			}
			// excel数据
			int rowNum = 1;
			for (int i = 0; i < dataList.size(); i++) {
				sheet.setColumnWidth(i, 3500);
				String[] data = dataList.get(i);
				HSSFRow row = sheet.createRow(rowNum + i); // 新增一行
				for (int j = 0; j < data.length; j++) {
					HSSFCell dataCell = row.createCell(j);
					dataCell.setCellStyle(style);
					if (data[j].indexOf(".") != -1) {
						dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						dataCell.setCellValue(Double.parseDouble(data[j])); // 往一行添加数据
					} else {
						dataCell.setCellValue(data[j]); // 往一行添加数据
					}
				}

			}
			// excel尾部
			
			if (endData != null && endData.length > 0) {
				HSSFRow end = sheet.createRow(dataList.size() + 1);
				for (int i = 0; i < endData.length; i++) {
					// end.createCell(i).setCellValue(endData[i]);
					HSSFCell endCell = end.createCell(i);
					endCell.setCellStyle(style);
					if (endData[i].indexOf(".") != -1) {// 如果单元格是小数
						endCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						endCell.setCellValue(Double.parseDouble(endData[i])); // 往一行添加数据
					} else {
						endCell.setCellValue(endData[i]); // 往一行添加数据
					}
				}
			}
		} catch (Exception e) {
			logger.error("报表数据解析异常", e);
		}
	}

//	public void buildExcelDocument(Profitlogs pro, String fileName,
//			HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
//		if (StringUtils.isBlank(fileName)) {
//			logger.info("传入文件名称不能为空");
//			return;
//		}
//		if (!fileName.endsWith(".xls")) {
//			fileName = fileName + ".xls";
//		}
//		// 产生Excel表头
//		// HSSFSheet sheet = workbook.createSheet(createSheet(orderType));
//		HSSFWorkbook wb = new HSSFWorkbook();
//		NumberFormat numberFormat = new DecimalFormat("#.0");
//		HSSFSheet sheet = wb.createSheet(fileName);
//		for (int index = 0; index < 12; index++) {
//			// 在索引0的位置创建行（第一行：sheet.createRow((short) 0);）:从index判断行数
//			HSSFRow row = sheet.createRow((short) index);
//			row.setHeightInPoints((short) 30);// 设置ex单元格的高度
//			// 列
//			for (int i = 0; i < 7; i++) {
//				// 在索引0的位置创建单元格（第一列从i中片段列数多少或者说应用格式
//				HSSFCell cell = row.createCell((short) i);
//				HSSFCellStyle style = wb.createCellStyle();
//				sheet.setColumnWidth(i, 3000);
//				String strValue = "";
//				if (index == 0) {
//					HSSFFont font = wb.createFont();
//					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//					font.setFontHeightInPoints((short) 16);
//					style.setAlignment((short) 16);
//					style.setBorderBottom((short) 16);
//					style.setFont(font);
//					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//					style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//					style.setBorderLeft((short) 1);   
//					style.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
//					style.setBorderRight((short) 1);
//					style.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
//					style.setBorderTop((short) 1);
//					style.setTopBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
//					style.setBorderBottom((short) 1);
//					style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
//					cell.setCellStyle(style);
//					strValue = "机构服务费表";
//					cell.setCellValue(strValue);
//					Region region = new Region(0, (short) 0, 0, (short) 6);
//					sheet.addMergedRegion(region);
//					break;
//				} else {
//					HSSFFont font = wb.createFont();
//					font.setFontHeightInPoints((short) 10);
//					style.setAlignment((short) 10);
//					style.setFont(font);
//					if(index == 11){
//						style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//					}else {
//						style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//					}
//					style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//					style.setBorderLeft((short) 1);   
//					style.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
//					style.setBorderRight((short) 1);
//					style.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
//					style.setBorderTop((short) 1);
//					style.setTopBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
//					style.setBorderBottom((short) 1);
//					style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
//					cell.setCellStyle(style);
//					switch (index) {
//					case 1:
//						if (i == 0) {
//							strValue = "机构号：";
//						} else if (i == 2) {
//							strValue = pro.getOrgId();
//						} else if (i == 3)
//							strValue = "生成报表日期：";
//						else if (i == 4)
//							strValue = String.valueOf("第"+pro.getSepCycle()+(pro.getSepType() == 1?"天":"月"));
//						else if (i == 5)
//							strValue = "批次号：";
//						else if (i == 6)
//							strValue = pro.getSeqNo()+"";
//						break;
//					case 2:
//						if (i == 0) {
//							strValue = "初始日期：";
//						} else if (i == 2) {
//							strValue = sd.format(pro.getStartDate());
//						} else if (i == 3)
//							strValue = "截止日期：";
//						else if (i == 4)
//							strValue = sd.format(pro.getEndDate());
//						else if (i == 5)
//							strValue = "制表日期：";
//						else if (i == 6)
//							strValue = sd.format(pro.getTabDate());
//						break;
//					case 3:
//						if (i == 0) {
//							strValue = "机构名称：";
//						} else if (i == 2)
//							strValue = pro.getOrgName();
//						break;
//					case 4:
//						if (i == 0) {
//							strValue = "开户名：";
//						} else if (i == 2)
//							strValue = pro.getUserName();
//						break;
//					case 5:
//						if (i == 0) {
//							strValue = "开户行：";
//						} else if (i == 2)
//							strValue = pro.getBankName();
//						break;
//					case 6:
//						if (i == 0) {
//							strValue = "账户：";
//						} else if (i == 2)
//							strValue = pro.getCardNo();
//						break;
//					case 7:
//						if (i == 0) {
//							strValue = "交易情况：";
//						} else if (i == 2) {
//							strValue = "笔数";
//						} else if (i == 3)
//							strValue = pro.getCount()+"";
//						else if (i == 4)
//							strValue = "金额";
//						else if (i == 5)
//							strValue = pro.getAmount()+"";
//						break;
//					case 8:
//						if (i == 0) {
//							strValue = "退款情况：";
//						} else if (i == 2) {
//							strValue = "笔数";
//						} else if (i == 3)
//							strValue = pro.getRefundCount()+"";
//						else if (i == 4)
//							strValue = "金额";
//						else if (i == 5)
//							strValue = pro.getRefundAmount()+"";
//						break;
//					case 9:
//						if (i == 0) {
//							strValue = "相应服务费交易额：";
//						} else if (i == 2) {
//							String min = "0.0";
//							if(pro.getMinAmount() != 0) {
//								min = numberFormat.format(pro.getMinAmount());
//							}
//							strValue = min + "~" + numberFormat.format(pro.getMaxAmount());
//						} else if (i == 4)
//							strValue = "服务费比例：";
//						else if (i == 5)
//							strValue = pro.getRate() + "%";
//						break;
//					case 10:
//						if (i == 0) {
//							strValue = "应服务费金额：";
//						} else if (i == 3) {
//							strValue = pro.getProfitAmount() + "";
//						}
//						break;
//					case 11:
//						if (i == 0) {
//							strValue = "划款制单：";
//						} else if (i == 3) {
//							strValue = "划款复核：";
//						} else if (i == 5) {
//							strValue = "划款时间：";
//						}
//						break;
//					}
//
//					cell.setCellValue(strValue);
//					// 列的格式
//					if (index == 1 || index == 2) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 1);
//							sheet.addMergedRegion(region);
//						} else {
//							Region region = new Region(index, (short) i, index,
//									(short) i);
//							sheet.addMergedRegion(region);
//						}
//					} else if (index > 2 && index < 7) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 1);
//							sheet.addMergedRegion(region);
//						} else {
//							Region region = new Region(index, (short) i, index,
//									(short) 6);
//							sheet.addMergedRegion(region);
//						}
//					} else if (index == 7 || index == 8) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 1);
//							sheet.addMergedRegion(region);
//						} else if (i > 1 && i < 5) {
//							Region region = new Region(index, (short) i, index,
//									(short) i);
//							sheet.addMergedRegion(region);
//						} else {
//							Region region = new Region(index, (short) i, index,
//									(short) 6);
//							sheet.addMergedRegion(region);
//						}
//					} else if (index == 9) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 1);
//							sheet.addMergedRegion(region);
//						} else if (i == 2 || i == 5) {
//							Region region = new Region(index, (short) i, index,
//									(short) (i + 1));
//							sheet.addMergedRegion(region);
//						} else if (i == 4) {
//							Region region = new Region(index, (short) i, index,
//									(short) i);
//							sheet.addMergedRegion(region);
//						}
//					} else if (index == 10) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 2);
//							sheet.addMergedRegion(region);
//						} else if (i == 3) {
//							Region region = new Region(index, (short) i, index,
//									(short) 6);
//							sheet.addMergedRegion(region);
//						}
//					} else if(index == 11) {
//						if (i == 0) {
//							Region region = new Region(index, (short) i, index,
//									(short) 2);
//							sheet.addMergedRegion(region);
//						} else if (i == 3 || i == 5) {
//							Region region = new Region(index, (short) i, index,
//									(short) (i+1));
//							sheet.addMergedRegion(region);
//						} 
//					}
//				}
//			}
//		}
//		fileName = ViewExcel.encodeFilename(fileName, request);
//		response.setContentType("application/vnd.ms-excel");
//		response.setHeader("Content-disposition", "attachment;filename="
//				+ fileName);
//		OutputStream ouputStream = response.getOutputStream();
//		wb.write(ouputStream);
//		ouputStream.flush();
//		ouputStream.close();
//	}
	
	public static String encodeFilename(String filename, HttpServletRequest request) {  
	    String agent = request.getHeader("USER-AGENT");  
	    try {  
	      if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {  
	        String newFileName = URLEncoder.encode(filename, "UTF-8");  
	        if (newFileName.length() > 150) {  
	          newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");  
	        }  
	        return newFileName;  
	      }  
	      return filename;  
	    } catch (Exception ex) {  
	      return filename;  
	    }  
	  }  
}
