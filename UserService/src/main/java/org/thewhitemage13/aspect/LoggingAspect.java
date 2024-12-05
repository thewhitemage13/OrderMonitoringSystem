package org.thewhitemage13.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for centralized logging of application methods.
 * <p>
 * This class uses Aspect-Oriented Programming (AOP) to intercept method executions
 * and log important information such as method calls, their results, and exceptions.
 * The `LoggingAspect` ensures consistent and detailed logging across the application,
 * improving the ability to monitor and debug the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Logs method invocations with input arguments.</li>
 *     <li>Logs method execution results and durations.</li>
 *     <li>Captures and logs exceptions thrown by methods.</li>
 * </ul>
 *
 * <h3>How It Works:</h3>
 * <p>
 * The `LoggingAspect` uses annotations such as {@code @Before}, {@code @AfterReturning},
 * {@code @AfterThrowing}, and {@code @Around} to intercept methods in the application
 * and log details about their execution.
 * </p>
 *
 * @see JoinPoint
 * @see ProceedingJoinPoint
 * @see org.slf4j.Logger
 * @see org.aspectj.lang.annotation.Aspect
 * @see org.springframework.stereotype.Component
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {
    /**
     * Logger instance for logging messages in this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs the method call details before its execution.
     * <p>
     * This method logs the name of the method being called along with its arguments.
     * </p>
     *
     * @param joinPoint provides reflective access to the target method being called
     */
    @Before("execution(* org.thewhitemage13..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method call: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Logs the result of a method after its successful execution.
     * <p>
     * This method captures the return value of the method and logs it for tracking purposes.
     * </p>
     *
     * @param joinPoint provides reflective access to the target method being called
     * @param result    the return value of the method
     */
    @AfterReturning(pointcut = "execution(* org.thewhitemage13..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("The {} method executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Logs any exception thrown during the execution of a method.
     * <p>
     * This method captures the exception details, including its message,
     * and logs them to help diagnose issues in the application.
     * </p>
     *
     * @param joinPoint provides reflective access to the target method being called
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
     * Logs details around a method execution, including start, end, and exceptions.
     * <p>
     * This method wraps the execution of the target method, providing a detailed
     * overview of its behavior, including execution time and any exceptions encountered.
     * </p>
     *
     * @param joinPoint provides reflective access to the target method being called
     * @return the result of the target method execution
     * @throws Throwable if the target method throws an exception
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
