package com.example.demo.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.pojo.User;

@Mapper
public interface AnnotationUserMapper {
	
	@Select("select * from user")
	List<User> selectAllUser();
	
	@Insert("insert into user ( id, name, password, age, birthday, intro ) values (#{id},#{name},#{password},#{age},#{birthday},#{intro})")
	void saveUser(User user);
	
	@Select("select * from user where id=#{id}")
	User selectUserById(String id);
	
	@Update("update user set name=#{name},password=#{password},age=#{age},birthday=#{birthday},intro=#{intro} where id=#{id}")
	void modify(User user);
	
	@Delete("delete from user where id=#{id}")
	void deleteUserById(String id);
	
}
