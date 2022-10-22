package com.article.articleapi.Repository;
import com.article.articleapi.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findUserModelByEmail(String emailInput);
    Optional<UserModel> findUserModelByName (String userName);

}
