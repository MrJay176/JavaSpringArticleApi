package com.article.articleapi.Repository;

import com.article.articleapi.Models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDbRepository extends JpaRepository<FileDB , Long> {

    FileDB findFileDBById(Long id);

}
