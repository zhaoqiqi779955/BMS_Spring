package dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.Admin;

public interface AdminMapper {
    @Select("select * from admin where work_id = #{id}")
    Admin query(@Param("id") int id);

    @Select("select work_id from admin where work_id = #{id}")
    Integer isExistent(@Param("id") int id);

    @Delete("delete from admin where work_id = #{id}")
    int delete(@Param("id") int work_id);

    @Insert("insert into admin values(#{work_id},#{name},#{sex},#{birth},#{adr},#{workTime},#{salary}" +
            ",#{level},#{pw},#{path},#{tel})")
    int add(Admin admin);

    int update(Admin admin);


}
