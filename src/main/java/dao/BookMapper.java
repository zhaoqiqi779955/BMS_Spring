package dao;

import pojo.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BookMapper {
//按照id查询
     @Select("select * from book where book_id = #{id}")
     Book query(@Param("id") int book_id);
//     按照任意一个字段匹配查询
     List<Book> queryAnyField(@Param("filter") String value,@Param("start") int start,@Param("pageSize") int pageSize);
     /*
     按照多字段查询
     map中应该包含字段可以为ISBN,title,author,category
     * */
     List<Book> queryAll(Map<String,Object> map);
     int add(Book book);
     int update(Book book);
     @Delete("delete from book where book_id = #{id}")
     int delete(int id);


}
