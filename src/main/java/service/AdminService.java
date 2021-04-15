package service;

import pojo.Admin;

public interface AdminService {
    boolean isExistent(int id);

    boolean add(Admin admin);

    void update(Admin admin);

    Admin getAdmin(int id);
    boolean delete(int id);

}
