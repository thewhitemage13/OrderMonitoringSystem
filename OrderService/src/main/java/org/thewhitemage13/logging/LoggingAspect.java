package org.thewhitemage13.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method calls, results, and exceptions in the application.
 * <p>
 * This class uses Aspect-Oriented Programming (AOP) to log various aspects of method execution,
 * including method entry, successful execution, and exception handling. The logging is done using SLF4J
 * and logs method signatures, arguments, return values, and any exceptions thrown.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Logs method entry with method name and arguments.</li>
 *     <li>Logs successful execution with method name and result.</li>
 *     <li>Logs exceptions thrown during method execution with method name and error message.</li>
 *     <li>Uses SLF4J for logging, which can be configured for various logging frameworks (e.g., Logback, Log4j).</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This aspect is automatically applied to all methods in the package `org.thewhitemage13` and its sub-packages
 * using the {@link Before}, {@link AfterReturning}, {@link AfterThrowing}, and {@link Around} annotations.
 * It logs method executions at different stages to help trace method behavior, performance, and error tracking.
 * </p>
 *
 * @see Logger
 * @see LoggerFactory
 * @see JoinPoint
 * @see ProceedingJoinPoint
 * @see Before
 * @see AfterReturning
 * @see AfterThrowing
 * @see Around
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method entry before the actual method execution.
     * <p>
     * This method logs the method name and its arguments before the method body is executed.
     * </p>
     *
     * @param joinPoint the join point that provides details about the method execution
     */
    @Before("execution(* org.thewhitemage13..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method call: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Logs the method result after it successfully completes.
     * <p>
     * This method is called after the target method successfully returns. It logs the method signature
     * along with the returned result.
     * </p>
     *
     * @param joinPoint the join point that provides details about the method execution
     * @param result the result returned by the method
     */
    @AfterReturning(pointcut = "execution(* org.thewhitemage13..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("The {} method executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Logs exception details if an exception is thrown by the method.
     * <p>
     * This method is called after the target method throws an exception. It logs the method signature
     * and the exception details including the message and stack trace.
     * </p>
     *
     * @param joinPoint the join point that provides details about the method execution
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
     * Logs the method execution flow, including entry, execution, and result.
     * <p>
     * This method wraps the target method execution, logging method entry, successful execution, and any
     * exceptions thrown. It provides detailed logging for tracing the method flow.
     * </p>
     *
     * @param joinPoint the join point that provides details about the method execution
     * @return the result returned by the target method
     * @throws Throwable if an exception occurs during method execution
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
