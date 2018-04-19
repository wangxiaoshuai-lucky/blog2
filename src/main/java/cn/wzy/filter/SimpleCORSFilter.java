package cn.wzy.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理跨域请求
 */
public class SimpleCORSFilter implements Filter {

		@Override
		public void destroy() {

		}

		@Override
		public void doFilter(ServletRequest req, ServletResponse res,
							 FilterChain chain) throws IOException, ServletException {
			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			chain.doFilter(req, res);

		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {

		}
}


