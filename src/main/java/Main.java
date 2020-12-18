import data.Admin;
import service.AdminService;

public class Main {
    public static void main(String[] args) throws Exception {

        Admin admin= AdminService.getAdmin(10000);
        admin.setBirth(null);
        AdminService.update(admin);
    }

}
