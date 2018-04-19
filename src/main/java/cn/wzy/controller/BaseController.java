package cn.wzy.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wzy
 * @Date 2018/4/10 13:49
 */
public abstract class BaseController {
    public static int online_num = 0;
    protected static final ThreadLocal<HttpServletRequest> requests = new ThreadLocal();
    protected static final ThreadLocal<HttpServletResponse> responses = new ThreadLocal();
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.requests.set(request);
        this.responses.set(response);
    }

    protected HttpServletRequest getRequest() {
        return requests.get();
    }

    protected HttpServletResponse getResponse() {
        return responses.get();
    }
}
