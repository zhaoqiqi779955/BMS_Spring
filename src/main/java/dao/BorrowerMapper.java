package dao;

import data.Borrower;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface BorrowerMapper {
    @Select("select * from borrower where borrower_id=#{id}")
    Borrower queryById(Integer id);
    @Insert("insert into borrower values(#{borrower_id},#{name},#{sex},#{birth},#{adr},#{path},#{tel}," +
            "#{pw},#{badRecord},#{level},#{maxBook},#{borrowedNum})")
    int add(Borrower borrower);

}
