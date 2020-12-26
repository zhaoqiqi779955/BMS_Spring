package service;

import af.sql.c3p0.AfSimpleDB;
import data.*;
import utility.database.DateFormat;
import utility.database.SqlUpdate;

import java.util.List;

public class BorrowerService {
    /*
    根据id判断用户是否存在
     */
    public static boolean isExistent(int id){
        String query="select borrower_id from borrower where borrower_id="+id;
        try {
            String[] result=AfSimpleDB.get(query);
            if(result==null) return  false;
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /*
    获取指定id的borrower对象
     */
    public static Borrower getBorrower(int id){
        String sql="select * from borrower where borrower_id="+id;
        try {
            Borrower borrower=(Borrower) AfSimpleDB.get(sql,Borrower.class);
            return  borrower;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    /*
    项数据库添加一个borrower,返回为true表示添加成功
     */
    public static boolean add(Borrower borrower){
        try {
            AfSimpleDB.insert(borrower);
            return  true;
        } catch (Exception e) {
           e.printStackTrace();
            return  false;
        }
    }
    /*
    更新个人信息
     */
    public static void  update(Borrower borrower){
        SqlUpdate asu=new SqlUpdate("borrower");
        asu.add("name",borrower.getName());
        asu.add("adr",borrower.getAdr());
        asu.add3("sex",borrower.getSex());
        asu.add2("birth", DataUtil.isNull(borrower.getBirth()) ? null: DateFormat.DBDate(borrower.getBirth()));
        asu.add2("tel",borrower.getTel());
        asu.add2("level",borrower.getLevel());
        asu.add2("maxBook",borrower.getMaxBook());
        asu.add("badRecord",borrower.getBadRecord());
        asu.add2("borrowedNum",borrower.getBorrowedNum());
        String s1 = asu + " where borrower_id=" + borrower.getBorrower_id();
        try {
            AfSimpleDB.execute(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    根据id删除借阅者
     */
    public  static  void delete(int id){
        String sql="delete from borrower where borrower_id="+id;
        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    预约操作
    返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足,5表示已预约，6表示已经借阅
     */
    public static  int reserveBook(int borrower_id,int book_id)
    {

        String sql="select reserve("+borrower_id+","+book_id+","+ LibrarySystem.RESERVATION_TIME+")";
        try {
            String [] res=AfSimpleDB.get(sql);
            return Integer.parseInt(res[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }
    /*
    取消预约
     */
    public static void  removeReservation(int reservation_id,int book_id)
    {
        String sql="call removeReservation("+reservation_id+","+book_id+")";
        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    借书操作,返回-1表示服务器发生异常，0表示成功，1用户不存在，2存在不良记录，3借书已达最大限度，4图书余量不足，6表示充分借阅
     */
    public static  int borrowBook(int borrower_id,int book_id)
    {

        String sql="select borrowBook("+borrower_id+","+book_id+","+ LibrarySystem.RETURN_DAY+")";
        try {
            String [] res=AfSimpleDB.get(sql);
            return Integer.parseInt(res[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }
    /*
    还书操作
    -1表示服务器异常,1还书成功，0还书失败
     */
    public static  int returnBook(int borrower_id,int book_id){
        String sql="select returnBook("+borrower_id+","+book_id+")";
        try {
            String [] res=AfSimpleDB.get(sql);
            return Integer.parseInt(res[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }
    /*
    获取指定借阅者id的预约记录,pageNumber为展示第几页，默认每页10条记录
     */
    public static List<Reservation>   getReservation(int borrower_id,int pageNumber){
        int pageSize=LibrarySystem.recordPageSize;
        int start=(pageNumber-1)*pageSize;
        String sql="call getReservation("+borrower_id+ ","+start+","+pageSize+")";
        try {
            return  AfSimpleDB.query(sql,Reservation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    根据借阅者id获取其借书记录，pageNumber为展示第几页
     */
    public  static List<Borrow> getBorrow(int borrower_id,int pageNumber){
        int pageSize=LibrarySystem.recordPageSize;
        int start=(pageNumber-1)*pageSize;
        String sql="call getBorrow("+borrower_id+","+start+","+pageSize+")";
        try {
            return  AfSimpleDB.query(sql,Borrow.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
