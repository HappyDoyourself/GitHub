package com.xlly.repository;

import com.xlly.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fanhongtao
 * Date 2017-03-22 11:42
 */
@Repository
public interface  UserRepository extends JpaRepository<UserEntity,Integer>{

    @Modifying
    @Transactional
    @Query("update UserEntity   us set us.nickname=:qNickname,us.firstName=:qFirstName,us.lastName=:qLastName,us.password=:qPassword where us.id=:qId")
    public void updateUser(@Param("qNickname")String nickName,@Param("qFirstName")String qFirstName,
                           @Param("qLastName")String lastName,@Param("qPassword")String password, @Param("qId")Integer id);
}
