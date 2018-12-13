
import com.dao.OrderInfoMapper;

import com.pojo.OrderInfo;
import com.util.DateTimeUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by geely
 */
public class ProductServiceTest extends TestBase {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Test
    public void testIProductService(){


        List<OrderInfo> orderInfoList = orderInfoMapper.selectByIdLike("103%");
        System.out.println(orderInfoList.size());
    }


}
