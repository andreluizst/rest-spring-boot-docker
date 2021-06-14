package br.com.restsb.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String token= jwtTokenProvider.resolveToken((HttpServletRequest) request);
		if (token != null && jwtTokenProvider.validateToken(token)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			if (authentication != null)
				SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}

}
