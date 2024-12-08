package autoservice.repository;

import autoservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM U01_USER u WHERE u.U01_LOGIN = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM U01_USER u WHERE concat(u.U01_ID, ' ', u.U01_EMAIL, ' ', u.U01_LOGIN, ' ', u.U01_NAME, ' ', u.U01_PASS, '', u.U01_PHONE, ' ', u.role.R01_NAME) LIKE %?1%")
    List<User> search(String keyword);

    @Query("SELECT COUNT(u) > 0 FROM U01_USER u WHERE LOWER(u.U01_LOGIN) = LOWER(:u01Login)")
    boolean existsByU01Login(@Param("u01Login") String u01Login);


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM U01_USER u WHERE u.U01_EMAIL = :u01Email")
    boolean existsByU01Email(@Param("u01Email") String u01Email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM U01_USER u WHERE u.U01_PHONE = :u01Phone")
    boolean existsByU01Phone(@Param("u01Phone") String u01Phone);
}
