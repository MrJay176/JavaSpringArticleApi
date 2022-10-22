package com.article.articleapi.RestControllers;

import com.article.articleapi.Models.FileDB;
import com.article.articleapi.Models.ResponseFile;
import com.article.articleapi.Services.FileStorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/file")
public class FileController {

    private final FileStorageServices storageServices;

    @Autowired
    public FileController(FileStorageServices storageServices) {
        this.storageServices = storageServices;
    }

    @GetMapping("/first/{idd}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        FileDB fileDB = storageServices.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/{id}")
    @ResponseBody()
    public ResponseEntity<InputStreamResource> showImage(@PathVariable Long id ) throws IOException {
           MediaType contentType = MediaType.IMAGE_JPEG;
           FileDB fileDB =  storageServices.getFile(id);
           InputStream targetStream = new ByteArrayInputStream(fileDB.getData());
           InputStreamResource in = new InputStreamResource(targetStream);
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(in);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageServices.getAllFiles().map(dbFile -> {

            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "showImage", String.valueOf(dbFile.getId())).build().toString();

            return new ResponseFile(
                    dbFile.getName(),
                    url,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
