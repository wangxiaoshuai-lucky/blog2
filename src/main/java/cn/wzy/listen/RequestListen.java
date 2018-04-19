package cn.wzy.listen;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @author wzy
 * @Date 2018/4/10 16:59
 */
@Log4j
public class RequestListen implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    }
}
