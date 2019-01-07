package io.chat.log.filters;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import io.chat.log.entity.AccessIpEntity;
import io.chat.log.service.AccessIPServiceImpl;
import io.chat.log.service.IAccessIPService;
import io.chat.log.service.IAppLogService;
import io.chat.log.util.AccessRightUtil;
/**
 * 验证访问者的Ip 需要申请注册
 * 
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
public class AuthorizationFilter implements Filter{
	
	private Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	private List<AccessIpEntity> accessList;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		accessList = AccessRightUtil.getAccessRight();
		logger.info("查看权限列表："+JSON.toJSONString(accessList));
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req ;
		HttpServletResponse  response= (HttpServletResponse ) resp ;
		boolean allowAccess =  false;
		String ip = request.getRemoteAddr();
		if(ip != null) {
			for(AccessIpEntity accessIpEntity :accessList) {
				String scope = accessIpEntity.getScope();
				String accessIp = accessIpEntity.getIp();
				if(scope ==null && accessIp==null) {
					continue;
				}
				if(accessIp.endsWith("*") ) {
					ip = ip.substring(0, ip.length()-1) +"*"; //去掉最后的ip段加上 *
					allowAccess = ip.equals(accessIp) ? true:  false;
				}else {
					allowAccess = ip.equals(accessIp) ? true:  false;
				}
			}
			
			if(allowAccess) {
				chain.doFilter(req, resp);
			}else {
				Writer writer = null;
				try{
					writer = response.getWriter();
					writer.write("没有权限！");
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