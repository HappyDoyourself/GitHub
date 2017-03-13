package cn.com.dubbo.base.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
//import org.json.simple.JSONObject;

import cn.com.dubbo.bean.page.Page;
import cn.com.dubbo.bean.page.Pagination;
import cn.com.jiuyao.pay.common.util.EntityReflectionUtils;
import cn.com.jiuyao.pay.common.util.StringUtil;

//import com.founder.ec.common.utils.EntityReflectionUtils;
//import com.founder.ec.common.utils.StringUtil;

public class BaseAction 
{
	protected final Log logger = LogFactory.getLog("Action");
	
	//分页对象
    protected Page page;

    // 需要跳转页
    protected Integer pageNo;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 
	 * @param page
	 * 格式:p=1
	 * @return
	 * @throws Exception
	 */
	protected Pagination getPagination(String page) throws Exception{
		Pagination pagination = new Pagination();
		if(StringUtil.isEmpty(page)){
			return pagination;
		}
		String ps[] = StringUtil.str2Array(page, "-");
		for(String p:ps){
			p = p.trim();
			if(p.indexOf("p=") != -1){
				p = p.replaceAll("p=", "");
				try{
					if (!"".equals(p)) {
						pagination.setPage(Integer.parseInt(p));
					}else {
						pagination.setPage(1);
					}
				}catch(Exception e){
					pagination.setPage(1);
				}
			}else {
				pagination.setPage(1);
			}
		}
		return pagination;
	}
	
	protected String returnErrorCode(String error){
		if("404".equals(error)){
			return "/jsp/common/404.jsp";
		}else if("403".equals(error)){
			return "/jsp/common/404.jsp";
		}
		return "/jsp/common/404.jsp";
	}
	
	public JSONObject ObjectChangJSONObject(Object o)
	{
		JSONObject json=new JSONObject();
		List<String> filedNameList=EntityReflectionUtils.getFiledNameList(o);		
		if(filedNameList!=null && filedNameList.size()>0)
		{
			for(int i=0;i<filedNameList.size();i++)
			{
				Object rObject=EntityReflectionUtils.getFieldValue(o, filedNameList.get(i));
			    json.put(filedNameList.get(i),rObject);
			}
		}
		return json;		
	}
	
	public void writeClient(HttpServletResponse response,String content,String type)
	{
		try{
			if("xml".equals(type))
			{
				if(content!=null && content.length()>0){
			    response.setContentType("text/xml;charset=UTF-8");} 
			}
			else
			{
				response.setContentType("text/html;charset=utf-8");
			}
			response.getWriter().write(content);
		}catch(Exception e){logger.error(e.getMessage(),e);}
	}
}
