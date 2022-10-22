package com.article.articleapi.RestControllers;

import com.article.articleapi.Models.UserModel;
import com.article.articleapi.Services.AuthenticationService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/sign_up" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestParam("file") MultipartFile multipartFile ,
                                    @RequestParam("name") String userName,
                                    @RequestParam("email") String userEmail,
                                    @RequestParam("password") String userPassword
                                    ) throws JSONException {
        UserModel userModelFromInput = new UserModel(
                userName,userEmail,userPassword,null,null,false);
        UserModel userResult = null;
        try {
            userResult = authenticationService.signUpService(multipartFile,userModelFromInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(userResult==null){
          return ResponseEntity.status(501).body(new AlreadyExist("Email Already taken" , "false"));
      }
      return ResponseEntity.ok().body(userResult);
    }

}

class AlreadyExist {

    String message;
    String status;

    public AlreadyExist() {
    }

    public AlreadyExist(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
