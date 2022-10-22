package com.article.articleapi.Services;

import com.article.articleapi.Models.FileDB;
import com.article.articleapi.Models.UserModel;
import com.article.articleapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final FileStorageServices fileStorageServices;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, FileStorageServices fileStorageServices) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageServices = fileStorageServices;
    }

    public Long userId(String userName){
        System.out.println("This is the username "+userName);
        return userRepository.findUserModelByName(userName).get().getId();
    };

    public UserModel signUpService(MultipartFile file , UserModel userModel) throws IOException {

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if(fileName.contains("..")){
                System.out.println("Not Proper File");
            }
            System.out.println("This is user email "+userModel.getEmail() +userModel.getAdmin());
            Optional<UserModel> userModelRepository;
            userModelRepository = userRepository.findUserModelByEmail(userModel.getEmail());
            if(userModelRepository.isPresent()){
                System.out.println("This model is present in the database");
                return null;
            }else{
                 System.out.println("This model is absent in the database");
                 FileDB fileDB = fileStorageServices.storeFile(file);
                 userModel.setProfilePicture(fileDB.getId());
                 userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                 userRepository.save(userModel);
                return userModel;
            }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String AdminOrUser;
        Optional<UserModel> userModel;
        System.out.println("This is email user "+email);
        try {
            userModel = userRepository.findUserModelByEmail(email);
            System.out.println("This is user model "+userModel.get().getAdmin());
            Boolean userAdmin = userModel.get().getAdmin();
            if (userAdmin) {
                AdminOrUser = "Admin";
            } else {
                AdminOrUser = "User";
            }
        }catch (Exception e){
            System.out.println("This is message "+e.getMessage());
            throw new UsernameNotFoundException(e.getMessage(),null);
        }
        System.out.println("GOTTEENNN ROLE "+AdminOrUser);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AdminOrUser);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(userModel.get().getName(),userModel.get().getPassword(),authorities);
    }

}
