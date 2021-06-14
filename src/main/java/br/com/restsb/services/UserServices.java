package br.com.restsb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.restsb.data.model.User;
import br.com.restsb.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServices implements UserDetailsService{

	@Autowired
	private UserRepository repository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("Username " + username + " not found!");
		return user;
	}

	public User getUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("Username " + username + " not found!");
		return user;
	}

}
