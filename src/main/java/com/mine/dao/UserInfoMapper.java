package com.mine.dao;

import com.mine.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated
     */
    List<UserInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserInfo record);

    /**
     * 查找当前用户名存不存在
     */
    int checkUsername(String username);

    /**
     * 查找当前邮箱存不存在
     */
    int checkEmail(String email);

    /**
     * 根据用户名和密码查询用户信息
     */
    UserInfo selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找问题
     */
    String selectQuestionByUsername(String username);

    /**
     * 检查答案
     */
    int selectByUsernameAndQuestionAndAnswer(@Param("username") String username,
                                             @Param("question") String question,
                                             @Param("answer") String answer);

    /**
     * 修改密码
     */
    int updatePassword(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 更新用户信息
     */
    int updateUserBySelectActive(UserInfo userInfo);

}