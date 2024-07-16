package cn.bugstack.springframework.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 *
 *
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 * @description 资源处理接口
 * @date 2022/3/9
 *
 *
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
