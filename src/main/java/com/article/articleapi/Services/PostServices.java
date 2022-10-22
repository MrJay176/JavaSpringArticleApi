package com.article.articleapi.Services;
import com.article.articleapi.Models.FileDB;
import com.article.articleapi.Models.PostModel;
import com.article.articleapi.Repository.PostRepository;
import com.article.articleapi.RestControllers.PostSent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class PostServices {
    private final PostRepository postRepository;
    private final FileStorageServices storageServices;

    private final AuthenticationService authenticationService;

    @Autowired
    public PostServices(PostRepository postRepository,
                        FileStorageServices storageServices,
                        AuthenticationService authenticationService) {
        this.postRepository = postRepository;
        this.storageServices = storageServices;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<List<PostModel>> getAllArticleService(){
      return ResponseEntity.ok().body(postRepository.findAll());
    }

    public PostSent createPost(
            List<MultipartFile> files,
            String title,
            String description , String posterName){

        Collection<FileDB> fileDBS = new ArrayList<>();
        PostSent postSent = new PostSent();
        PostModel postModel = new PostModel();
        for ( MultipartFile file : files){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if(fileName.contains("..")){
                System.out.println("Not Proper File");
            }else {
                FileDB fileDB = null;
                try {
                    fileDB = storageServices.storeFile(file);
                } catch (IOException e) {
                    System.out.println("Create post exception "+e.getMessage());
                    throw new RuntimeException(e);
                }
                fileDB.setData(null);
                fileDBS.add(fileDB);
            }
        }
        postModel.setTitle(title);
        postModel.setDescription(description);
        postModel.setImage(fileDBS);
        postModel.setPosterId(String.valueOf(authenticationService.userId(posterName)));
        postSent.setPostModel(postModel);
        postSent.setMessage("Successfully Uploaded Post");
        postRepository.save(postModel);
        return postSent;
    };

}
