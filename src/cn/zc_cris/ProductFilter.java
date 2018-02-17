package cn.zc_cris;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter("*.action")
public class ProductFilter extends HttpFilter {

	
	/**
	 * @Field Name：serialVersionUID
	 * @Description：TODO (就是一个序列化版本号) 
	 */
	
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//1.首先对请求的路径进行判断，然后进行相关的处理
		HttpServletRequest req = (HttpServletRequest)request;
		String servletPath = req.getServletPath();
		System.out.println(servletPath);
		
		String path = null;
		if("/product-add.action".equals(servletPath)) {
			path = "/WEB-INF/pages/productAdd.jsp";
		}
		
		if("/product-save.action".equals(servletPath)) {
			path = "/WEB-INF/pages/showDetails.jsp";
			String name = request.getParameter("name");
			String desc = request.getParameter("desc");
			String price = request.getParameter("price");
			//把请求信息封装成一个Product对象
			Product product = new Product(null, name, desc, BigDecimal.valueOf(Double.parseDouble(price)));
			product.setId(1001);
			//再把Product对象封装到request的请求域中（最好是${requestScope.product.name},而不是{param.name}）
			request.setAttribute("product", product);
			System.out.println(product);
		}
		//如果path不为空，就进行转发
		if(path != null) {
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
