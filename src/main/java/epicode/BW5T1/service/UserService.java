package epicode.BW5T1.service;

import com.cloudinary.Cloudinary;
import epicode.BW5T1.dto.UserDto;
import epicode.BW5T1.enumeration.Ruolo;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.User;
import epicode.BW5T1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailService mailService;


    public User saveUser(UserDto userDto){
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRuolo(Ruolo.USER);

        return userRepository.save(user);
    }

    public Page<User> getAllUser(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setUsername(userDto.getUsername());
        userDaAggiornare.setEmail(userDto.getEmail());
        if(!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(userDaAggiornare);
    }

    public String patchUser(int id, MultipartFile file) throws NotFoundException, IOException {
        User userDaPatchare = getUser(id);
        String avatar = (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        userDaPatchare.setAvatar(avatar);
        userRepository.save(userDaPatchare);
        return avatar;
    }

    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);

        userRepository.delete(userDaCancellare);
    }


    public void send(String mittente, String destinatario, String oggetto, String testo) {
        mailService.send(mittente, destinatario, oggetto, testo);
    }



    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // oppure getPrincipal() se hai un oggetto custom

        return userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }
}

