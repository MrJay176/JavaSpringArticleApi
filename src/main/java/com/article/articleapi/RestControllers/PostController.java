package com.article.articleapi.RestControllers;
import com.article.articleapi.Models.FileDB;
import com.article.articleapi.Models.PostModel;
import com.article.articleapi.Services.PostServices;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostController {

    private final PostServices postServices;


    @Autowired
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    @GetMapping
    public ResponseEntity<List<PostModel>> getAllArticles(){
      return postServices.getAllArticleService();
    }

    @PostMapping("/create-post")
    public ResponseEntity<PostSent> uploadPost(
            @RequestParam("file[]") List<MultipartFile> files,
            @RequestParam("title") String title,
            @RequestParam("description") String description , HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String userName = null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring("Bearer Bearer ".length());
            Algorithm algorithm  = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            userName = decodedJWT.getSubject();
        }
       return ResponseEntity.ok().body(postServices.createPost(files,title,description,userName));
    }

}

