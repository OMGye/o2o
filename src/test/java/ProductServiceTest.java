
import com.pojo.CustomerInfo;
import com.service.OrderInfoService;
import com.vo.EngineerRankVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geely
 */
public class ProductServiceTest extends TestBase {

    @Autowired
    private OrderInfoService orderInfoService;

    @Test
    public void testIProductService(){
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(1001);
        orderInfoService.customerList(100,1,customerInfo,null);
    }


}
