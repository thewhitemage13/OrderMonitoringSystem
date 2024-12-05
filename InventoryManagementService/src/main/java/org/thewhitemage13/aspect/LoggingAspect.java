package org.thewhitemage13.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Represents a logging aspect for capturing method execution details across the application.
 * <p>
 * This class utilizes Spring AOP to intercept method executions within the `org.thewhitemage13` package
 * and its subpackages. It logs details such as method invocations, return values, and exceptions
 * to assist with debugging and monitoring application behavior.
 * <p>
 * The logging is powered by SLF4J, providing configurable log levels for different event types.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Logs method calls and their arguments before execution.</li>
 *     <li>Logs method return values upon successful completion.</li>
 *     <li>Captures and logs exceptions thrown by intercepted methods.</li>
 *     <li>Provides detailed logs around method execution lifecycle (start and completion).</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method invocation details before the method is executed.
     * <p>
     * This advice executes for any method within the `org.thewhitemage13` package and its subpackages.
     * Logs the method signature and arguments at the INFO level.
     * </p>
     *
     * @param joinPoint provides reflective access to the intercepted method, including its signature and arguments.
     */
    @Before("execution(* org.thewhitemage13..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method call: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Logs the result after a method successfully executes.
     * <p>
     * This advice executes after any method in the `org.thewhitemage13` package completes without throwing an exception.
     * Logs the method signature and its return value at the INFO level.
     * </p>
     *
     * @param joinPoint provides reflective access to the intercepted method.
     * @param result    the result returned by the method.
     */
    @AfterReturning(pointcut = "execution(* org.thewhitemage13..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("The {} method executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Captures and logs exceptions thrown by intercepted methods.
     * <p>
     * This advice executes after any method in the `org.thewhitemage13` package throws an exception.
     * Logs the method signature and exception details at the ERROR level.
     * </p>
     *
     * @param joinPoint provides reflective access to the intercepted method.
     * @param exception the exception that was thrown during method execution.
     */
    @AfterThrowing(pointcut = "execution(* org.thewhitemage13..*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in the {} method: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage(),
                exception);
    }

    /**
     * Logs details before, during, and after the execution of a method, including any exceptions thrown.
     * <p>
     * This advice wraps the execution of any method in the `org.thewhitemage13` package. It logs the
     * method start and completion at the DEBUG level and logs exceptions at the ERROR level.
     * </p>
     *
     * @param joinPoint provides reflective access to the intercepted method.
     * @return the result of the method execution.
     * @throws Throwable if the method being intercepted throws any exception.
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
