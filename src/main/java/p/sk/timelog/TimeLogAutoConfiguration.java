package p.sk.timelog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@Aspect
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "time.log", name = "enable", havingValue = "true", matchIfMissing = true)
public class TimeLogAutoConfiguration {
    @Around("@annotation(p.sk.timelog.TimeLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toLongString().split(" ")[2];
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("方法 {},耗时 {} ms", name, endTime - startTime);
        return object;
    }
}
