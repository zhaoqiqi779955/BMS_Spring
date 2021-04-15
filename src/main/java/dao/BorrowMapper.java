package dao;

import pojo.Borrow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BorrowMapper {
    @Select("select * from Borrow where id=#{id}")
    Borrow query(int id);
    List<Borrow> queryByPage(@Param(value = "borrower_id") int borrower_id,@Param("start") int start, @Param("pageSize") int pageSize);
}
