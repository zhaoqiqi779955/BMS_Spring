package service;

import dao.BookMapper;
import dao.BorrowMapper;
import dao.BorrowerMapper;
import dao.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Borrow;
import pojo.Borrower;
import pojo.LibrarySystem;
import pojo.Reservation;

import java.util.List;
@Service("borrowerService")
public class BorrowerServiceImpl  implements BorrowerService{

    @Autowired
    BorrowerMapper borrowerMapper;
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    BookMapper bookMapper;

    /*
        根据id判断用户是否存在
         */
    @Override
    public  boolean isExistent(int id){
        Integer res=borrowerMapper.isExistent(id);
        if(res==null) return false;
        return true;
    }
    /*
    获取指定id的borrower对象
     */
    @Override
    public  Borrower getBorrower(int id){
        return  borrowerMapper.queryById(id);
    }

    /*
    项数据库添加一个borrower,返回为true表示添加成功
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public  boolean add(Borrower borrower){
         int res=borrowerMapper.add(borrower);
         if(res!=0) return true;
         else return  false;
    }
    /*
    更新个人信息
     */
    @Override
    public  void  update(Borrower borrower){
        borrowerMapper.update(borrower);
    }
    /*
    根据id删除借阅者
     */
    @Override
    public   void delete(int id){
         borrowerMapper.delete(id);
    }
    /*
    预约操作
    返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足,5表示已预约，6表示已经借阅
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public  int reserveBook(int borrower_id, int book_id)
    {
        String sql="select reserve("+borrower_id+","+book_id+","+ LibrarySystem.RESERVATION_VALIDITY_TIME +")";
        borrowerMapper.reserve(borrower_id,book_id,LibrarySystem.RESERVATION_VALIDITY_TIME);
        return -1;

    }
    /*
    取消预约
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public void removeReservation(int reservation_id)
    {
        reservationMapper.delete(reservation_id);
    }

    /*
    借书操作,返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足，6表示充分借阅
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public   int borrowBook(int borrower_id, int book_id)
    {

        String sql="select borrowBook("+borrower_id+","+book_id+","+ LibrarySystem.RETURN_DAY+")";
        return borrowerMapper.borrowBook(borrower_id,book_id,LibrarySystem.RETURN_DAY);

    }
    /*
    还书操作
    -1表示服务器异常,1还书成功，0还书失败
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public  int returnBook(int borrower_id, int book_id){

        return  borrowerMapper.returnBook(borrower_id,book_id);

    }
    /*
    获取指定借阅者id的预约记录,pageNumber为展示第几页，默认每页10条记录
     */

    @Override
    public  List<Reservation>  getReservation(int borrower_id, int pageNumber){
        int pageSize=LibrarySystem.recordPageSize;
        int start=(pageNumber-1)*pageSize;
        return  reservationMapper.queryByPage(borrower_id,start,pageSize);
    }
    /*
    根据借阅者id获取其借书记录，pageNumber为展示第几页
     */
    @Override
    public  List<Borrow> getBorrow(int borrower_id, int pageNumber){
        int pageSize=LibrarySystem.recordPageSize;
        int start=(pageNumber-1)*pageSize;
       return borrowMapper.queryByPage(borrower_id,start,pageSize);
    }

    @Override
    public List<Borrower> getAllBorrowers() {

        return  borrowerMapper.queryAll();
    }
}
