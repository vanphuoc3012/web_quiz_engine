package engine.services;

import engine.models.UserDetailsImpl;
import engine.models.UserWebQuiz;
import engine.repository.UserWebQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserWebQuizRepository userWebQuizRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWebQuiz user = userWebQuizRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("Not found: "+username);
        }
        System.out.println("Email = " + user.getEmail());
        return new UserDetailsImpl(user);
    }
}
