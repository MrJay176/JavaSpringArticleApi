package com.article.articleapi.Services;

import com.article.articleapi.Models.FileDB;
import com.article.articleapi.Repository.FileDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileStorageServices {

    private final FileDbRepository fileDbRepository;

    @Autowired
    public FileStorageServices(FileDbRepository fileDbRepository) {
        this.fileDbRepository = fileDbRepository;
    }

    public FileDB storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDbRepository.save(FileDB);
    };

    public FileDB getFile(Long id){
      return  fileDbRepository.findFileDBById(id);
    };

    public Stream<FileDB> getAllFiles() {
        return fileDbRepository.findAll().stream();
    }

}
