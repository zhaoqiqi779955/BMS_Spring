package service;

import dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Admin;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public boolean isExistent(int id){
        Integer res=adminMapper.isExistent(id);
        if(res==null) return false;
        return true;
    }
    @Override
    public  boolean add(Admin admin)
    {
        int res=adminMapper.add(admin);
        if(res != 0) return true;
        else return false;
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public  void update(Admin admin){
       adminMapper.update(admin);
    }
    @Override
    public  Admin getAdmin(int id){
       return adminMapper.query(id);
    }

    @Override
    public boolean delete(int id) {
        int res=adminMapper.delete(id);
        if(res!=0) return true;
        else return false;
    }

}
