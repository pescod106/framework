package util;

import com.ltar.framework.web.dto.HttpResponseEntity;
import com.ltar.framework.web.util.HttpUtils;
import org.apache.http.Header;
import org.junit.Test;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/16
 * @version: 1.0.0
 */
public class TestHttpUtil {

    @Test
    public void testGet() {
        String url = "https://www.baidu.com/";
        System.out.println();
        HttpResponseEntity responseEntity = HttpUtils.sendGetRequest(url);
        System.out.println(responseEntity.getStatusCode());
        System.out.println("headers:");
        for (Header header : responseEntity.getHeaders()) {
            System.out.println(header.getName() + ":" + header.getValue());
        }
        System.out.println("body:");
        System.out.println(responseEntity.getBody());
        System.out.println();
    }
}
