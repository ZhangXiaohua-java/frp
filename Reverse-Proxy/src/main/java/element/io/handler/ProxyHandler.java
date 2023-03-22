package element.io.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
@Slf4j
@WebFilter(filterName = "proxyFilter", urlPatterns = {"/*"})
public class ProxyHandler implements Filter {


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String val = request.getHeader(name);
			log.info("key: {},val:  {}", name, val);
		}
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(200);
		PrintWriter writer = response.getWriter();
		writer.write("ok");
		writer.flush();
		writer.close();
	}


}
