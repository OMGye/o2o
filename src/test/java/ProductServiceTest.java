
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
        Date startDate = DateTimeUtil.strToDate("2018-12-01","yyyy-MM-dd");
        Date endDate = DateTimeUtil.strToDate("2018-12-06","yyyy-MM-dd");

        List<OrderInfo> orderInfoList = orderInfoMapper.selectByTime(startDate, endDate);
        System.out.println(orderInfoList.size());
    }


}
