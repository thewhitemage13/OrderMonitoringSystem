package org.thewhitemage13.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method execution details, including method arguments and object fields.
 * <p>
 * This aspect intercepts method calls in the {@code org.thewhitemage13} package
 * and logs comprehensive details about their execution, such as method arguments, results, exceptions,
 * and fields of the class where the method is executed.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Logs method calls before execution with method arguments and fields of the target object.</li>
 *     <li>Logs method results after successful execution.</li>
 *     <li>Logs exceptions if the method throws an error.</li>
 *     <li>Logs the execution flow and performance details of the intercepted methods.</li>
 * </ul>
 *
 * @see JoinPoint
 * @see ProceedingJoinPoint
 * @see Logger
 * @see Aspect
 * @see Component
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method execution details before the method is invoked,
     * including the method's arguments and the fields of the target object.
     *
     * @param joinPoint the join point representing the intercepted method
     */
    @Before("execution(* org.thewhitemage13..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method call: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Logs the result of method execution after successful completion.
     *
     * @param joinPoint the join point representing the intercepted method
     * @param result    the result returned by the method
     */
    @AfterReturning(pointcut = "execution(* org.thewhitemage13..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("The {} method executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Logs an exception thrown during method execution.
     *
     * @param joinPoint the join point representing the intercepted method
     * @param exception the exception thrown by the method
     */
    @AfterThrowing(pointcut = "execution(* org.thewhitemage13..*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in the {} method: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage(),
                exception);
    }

    /**
     * Logs the execution flow and performance of a method, including its result or thrown exception.
     *
     * @param joinPoint the proceeding join point representing the intercepted method
     * @return the result of the method execution
     * @throws Throwable if the method throws an error
     */
    @Around("execution(* org.thewhitemage13..*(..))")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.debug("Start executing method {} with arguments: {}", methodName, args);

        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("The {} method executed successfully. Result: {}", methodName, result);
        } catch (Throwable ex) {
            logger.error("Error in method {}: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }

        logger.debug("Completion of method {} with the result: {}", methodName, result);
        return result;
    }
}
