package com.chinaebi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.chinaebi.utils.mybaits.Page;
/**
 * 
 * @author TangZhiQiang
 * @date 2013-5-14
 */
public class ParameterUtils {
	/**
	 * 拼接前台传递的参数,返回list
	 * list里面两个元素,list.get(0)保存的是Page信息
	 * list.get(1)保存的是参数
	 * @param session
	 * @param request
	 * @param model
	 * @param page
	 * @param objects
	 * @return list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List ParameterVoluation(HttpSession session, ServletRequest request,
			ModelMap model, Page page, Object... objects) {
		// 判断是否分页
		if(page != null){
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("numPerPage");
	
			if (StringUtils.isNotBlank(curPage)) {
				page.setPageNo(Integer.parseInt(curPage));
			}
			if (StringUtils.isNotBlank(pageSize)) {
				page.setPageSize(Integer.parseInt(pageSize));
			} else {
				page.setPageSize(10);
			}
		}
		List list = new ArrayList(2);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < objects.length; i++) {
			// 判断是否是数组
			if(objects[i] instanceof String[]){
				String[] str = (String[]) objects[i];
				for (int j = 0; j < str.length; j++) {
					map.put(str[j], request.getParameter(str[j]));
				}
			}
			map.put(objects[i].toString(),
					request.getParameter(objects[i].toString()));
		}
		list.add(0, page);
		list.add(1, map);
		return list;
	}
	
	public static String idByName(String type, String id) {
		if (type.equals("1")) {
			if (id.equals("110")) {
				return "北京";
			} else if (id.equals("120")) {
				return "天津";
			} else if (id.equals("130")) {
				return "河北";
			} else if (id.equals("140")) {
				return "山西";
			} else if (id.equals("150")) {
				return "内蒙古";
			} else if (id.equals("210")) {
				return "辽宁";
			} else if (id.equals("220")) {
				return "吉林";
			} else if (id.equals("230")) {
				return "黑龙江";
			} else if (id.equals("310")) {
				return "上海市";
			} else if (id.equals("320")) {
				return "江苏省";
			} else if (id.equals("330")) {
				return "浙江";
			} else if (id.equals("340")) {
				return "安徽";
			} else if (id.equals("350")) {
				return "福建";
			} else if (id.equals("360")) {
				return "江西";
			} else if (id.equals("370")) {
				return "山东";
			} else if (id.equals("410")) {
				return "河南";
			} else if (id.equals("420")) {
				return "湖北";
			} else if (id.equals("430")) {
				return "湖南";
			} else if (id.equals("440")) {
				return "广东";
			} else if (id.equals("450")) {
				return "广西";
			} else if (id.equals("460")) {
				return "海南";
			} else if (id.equals("500")) {
				return "重庆";
			} else if (id.equals("510")) {
				return "四川";
			} else if (id.equals("520")) {
				return "贵州";
			} else if (id.equals("530")) {
				return "云南";
			} else if (id.equals("540")) {
				return "西藏";
			} else if (id.equals("610")) {
				return "陕西";
			} else if (id.equals("620")) {
				return "甘肃";
			} else if (id.equals("630")) {
				return "青海";
			} else if (id.equals("640")) {
				return "宁夏";
			} else if (id.equals("650")) {
				return "新疆";
			} else if (id.equals("700")) {
				return "苏州";
			} else if (id.equals("710")) {
				return "台湾";
			} else if (id.equals("720")) {
				return "宁波";
			} else if (id.equals("810")) {
				return "香港";
			} else if (id.equals("820")) {
				return "澳门";
			} else if (id.equals("990")) {
				return "深圳";
			}
		} else if (type.equals("2")) {
			if (id.equals("1")) {
				return "组织机构代码证";
			} else if (id.equals("2")) {
				return "营业执照";
			} else if (id.equals("3")) {
				return "居民身份证";
			} else if (id.equals("4")) {
				return "临时身份证";
			} else if (id.equals("5")) {
				return "军人身份证";
			} else if (id.equals("6")) {
				return "户口簿";
			} else if (id.equals("7")) {
				return "护照";
			} else {
				return "其他";
			}
		}
		return null;
	}
}
