package travel_book.service.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {   // 예외 상황에서 통과
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID,uuid);

        // @RequestMapping(어노테이션)   : HandlerMethod 사용
        // 정적 리소스(resources/static) : ResourceHttpRequestHandler 사용
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출 할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            log.info("hm.getBean 호출 {}",hm.getBean());
        }
        log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {  // 예외 상황 통과
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  // 무조건 찍힘
        String requestURI = request.getRequestURI();
        String uuid =(String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]",uuid,requestURI,handler);

        if (ex != null) {
            log.error("afterCompletion Error!!", ex);
        }

    }
}
