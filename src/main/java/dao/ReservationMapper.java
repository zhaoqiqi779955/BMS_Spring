package dao;

import pojo.Reservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReservationMapper {
    List<Reservation>  queryByPage(@Param("id") int borrwer_id, @Param("start") int start, @Param("pageSize") int pageSize);

//    删除预约
    void delete(@Param("id") int reser_id);
}
