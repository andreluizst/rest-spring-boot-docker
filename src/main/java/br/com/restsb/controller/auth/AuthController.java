package br.com.restsb.controller.auth;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restsb.data.model.User;
import br.com.restsb.security.dto.AccountCredentialsDTO;
import br.com.restsb.security.jwt.JwtTokenProvider;
import br.com.restsb.services.UserServices;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserServices userServices;
	
	@ApiOperation(value="Authenticade a user")
	@PostMapping(value = "/signin", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public ResponseEntity<Map> signin(@RequestBody AccountCredentialsDTO accountCredentialsDto) {
		Map<Object, Object> model = new HashMap<>();
		try {
			String username = accountCredentialsDto.getUsername();
			String password = accountCredentialsDto.getPassword();
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userServices.getUserByUsername(username);
			String token = this.jwtTokenProvider.createToken(username, user.getRoles());
			model.put("username", username);
			model.put("token", token);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
		return ok(model);
	}
}
