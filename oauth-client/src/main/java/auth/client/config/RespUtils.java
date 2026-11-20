package auth.client.config;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: RespUtils
 * @description:
 * @author: yang pan
 */
public class RespUtils {

    public static void responseJson(HttpServletResponse response, Object value) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);  //设置响应头的编码
        response.getWriter().write(JSON.toJSONString(value));

    }
}
