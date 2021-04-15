package service;

import org.springframework.transaction.annotation.Transactional;
import pojo.Borrow;
import pojo.Borrower;
import pojo.Reservation;

import java.util.List;

public interface BorrowerService {
    /*
            根据id判断用户是否存在
             */
    boolean isExistent(int id);

    /*
        获取指定id的borrower对象
         */
    Borrower getBorrower(int id);

    /*
        项数据库添加一个borrower,返回为true表示添加成功
         */
    @Transactional(rollbackFor = {Throwable.class})
    boolean add(Borrower borrower);

    /*
        更新个人信息
         */
    void  update(Borrower borrower);

    /*
        根据id删除借阅者
         */
    void delete(int id);

    /*
        预约操作
        返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足,5表示已预约，6表示已经借阅
         */
    int reserveBook(int borrower_id, int book_id);

    /*
        取消预约
         */
    void removeReservation(int reservation_id);

    /*
        借书操作,返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足，6表示充分借阅
         */
    int borrowBook(int borrower_id, int book_id);

    /*
        还书操作
        -1表示服务器异常,1还书成功，0还书失败
         */
    int returnBook(int borrower_id, int book_id);

    /*
        获取指定借阅者id的预约记录,pageNumber为展示第几页，默认每页10条记录
         */
    List<Reservation> getReservation(int borrower_id, int pageNumber);

    /*
        根据借阅者id获取其借书记录，pageNumber为展示第几页
         */
    List<Borrow> getBorrow(int borrower_id, int pageNumber);

    List<Borrower> getAllBorrowers();
}
