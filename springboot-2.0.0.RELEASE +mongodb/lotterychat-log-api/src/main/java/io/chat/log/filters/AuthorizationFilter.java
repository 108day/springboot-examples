package io.chat.log.filters;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

import io.chat.log.entity.AccessIpEntity;
import io.chat.common.service.IAccessIPService;
/**
 * 验证访问者的Ip 需要申请注册
 * 
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
@Configuration
public class AuthorizationFilter implements Filter{
	
	private Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	
	private List<Map<String,Object>> accessList;
	private  IAccessIPService accessIPService;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		try {
			 ServletContext sc = filterConfig.getServletContext(); 
			 AnnotationConfigServletWebServerApplicationContext cxt = (AnnotationConfigServletWebServerApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
		      if(cxt != null && cxt.getBean("accessIPService") != null && accessIPService == null) {
		    	  accessIPService = (IAccessIPService) cxt.getBean("accessIPService");  
		    	  accessList = (List<Map<String, Object>>) accessIPService.list();
		      }
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("查看权限列表："+JSON.toJSONString(accessList));
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req ;
		HttpServletResponse  response= (HttpServletResponse ) resp ;
		boolean allowAccess =  false;
		String ip = request.getRemoteHost();
		if(ip != null) {
			for( Map<String,Object> accessIp :accessList) {
				String scope = accessIp.get("scope").toString();
				String registerIp = accessIp.get("ip").toString();
				if(scope ==null && registerIp==null) {
					continue;
				}
				if(registerIp.endsWith("*") ) {
					ip = ip.substring(0, ip.length()-1) +"*"; //去掉最后的ip段加上 *
					allowAccess = ip.equals(accessIp) ? true:  false;
				}else {
					allowAccess = ip.equals(accessIp) ? true:  false;
				}
				if(allowAccess) {
					break;
				}
			}
			
			if(allowAccess) {
				chain.doFilter(req, resp);
			}else {
				Writer writer = null;
				try{
					writer = response.getWriter();
					writer.write("no access right !");
					writer.flush();
				} finally {
					if(writer!=null) {
						writer.close();
						writer = null;
					}
				}
			}
		}else {
			throw new ServletException("IP不能为空！");
		}
	}
	
    @Override
    public void destroy() {

    }

}