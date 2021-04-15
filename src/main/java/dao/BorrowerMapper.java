package dao;

import org.apache.ibatis.annotations.*;
import pojo.Borrower;

import java.util.List;

public interface BorrowerMapper {
    @Select("select * from borrower where borrower_id=#{id}")
    Borrower queryById(Integer id);

    @Select("select borrower_id from borrower where borrower_id = #{id}")
    Integer isExistent(@Param("id") int borrower_id);

    @Select("select * from borrower")
    List<Borrower> queryAll();

    @Insert("insert into borrower values(#{borrower_id},#{name},#{sex},#{birth},#{adr},#{path},#{tel}," +
            "#{pw},#{badRecord},#{level},#{maxBook},#{borrowedNum})")

    int add(Borrower borrower);

    @Delete("delete from borrower where borrower_id = #{id}")
    int delete(int id);

    @Update("update borrower set name=#{name},sex=#{sex},birth=#{birth},adr=#{adr},tel=#{tel}")
    int update(Borrower borrower);
//    订书
    int reserve(@Param("borrower_id") int borrower_id, @Param("book_id") int book_id, @Param("duration") int duration);
    @Select("select borrowBook(#{borrower_id},#{book_id},#{duration})")
//借书
    int borrowBook(@Param("borrower_id") int borrower_id,@Param("book_id") int book_id,@Param("duration") int duration);
    @Select("select returnBook(#{borrower_id},#{book_id})")
    int returnBook(@Param("borrower_id") int borrower_id,@Param("book_id") int book_id);
}
