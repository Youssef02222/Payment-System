package com.axis.task.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.axis.task..*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing method: {}", methodName);

        if (args.length > 0) {
            logger.info("Method arguments: {}", arrayToString(args));
        }
    }

    @AfterReturning(pointcut = "execution(* com.axis.task..*.*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();

        if (result != null) {
            logger.info("Method returned: {}", result);
        } else {
            logger.info("Method completed: {}", methodName);
        }
    }

    private String arrayToString(Object[] array) {
        StringBuilder result = new StringBuilder("[");
        for (Object element : array) {
            result.append(element).append(", ");
        }
        if (array.length > 0) {
            result.setLength(result.length() - 2); // Remove the trailing comma and space
        }
        result.append("]");
        return result.toString();
    }
}
