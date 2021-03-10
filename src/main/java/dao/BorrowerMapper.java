package dao;

import data.Borrower;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BorrowerMapper {
    @Select("select * from borrower where borrower_id=#{id}")
    Borrower queryById(Integer id);
    @Insert("insert into borrower values(#{borrower_id},#{name},#{sex},#{birth},#{adr},#{path},#{tel}," +
            "#{pw},#{badRecord},#{level},#{maxBook},#{borrowedNum})")
    int add(Borrower borrower);
    @Delete("delete from borrower where borrower_id = #{id}")
    int delete(int id);
    @Update("update borrower set name=#{name},sex=#{sex},birth=#{birth},adr=#{adr},tel=#{tel}")
    int update(Borrower borrower);
    

}
