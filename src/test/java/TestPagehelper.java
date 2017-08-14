import com.bc.study.dao.UserDao;
import com.bc.study.entity.User;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by BlackCat.
 * Date 2017/8/14.
 * Time 9:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class TestPagehelper {

    @Resource
    private UserDao dao;

    @Test
    public void testPagehelper(){
        PageHelper.startPage(2,10);
        List<User> userList = dao.getList();
        for (User user : userList
             ) {
            System.out.println(user);
        }
    }
}
