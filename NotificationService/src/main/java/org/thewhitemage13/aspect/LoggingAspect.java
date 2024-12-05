package org.thewhitemage13.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for centralized logging of method executions throughout the application.
 * <p>
 * This aspect uses Spring AOP to log method calls, return values, exceptions,
 * and execution times. It ensures better traceability and debugging by
 * capturing detailed information about the application's runtime behavior.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Logs method calls with their arguments before execution.</li>
 *     <li>Logs method execution results upon successful completion.</li>
 *     <li>Logs any exceptions thrown by methods for better error tracking.</li>
 *     <li>Wraps method execution to log start, completion, and potential errors.</li>
 * </ul>
 *
 * @see org.aspectj.lang.annotation.Aspect
 * @see org.slf4j.Logger
 * @see org.aspectj.lang.JoinPoint
 * @see org.aspectj.lang.ProceedingJoinPoint
 * @see org.springframework.stereotype.Component
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method calls and their arguments before execution.
     * <p>
     * This advice runs before the method execution and logs the method signature
     * along with its provided arguments.
     * </p>
     *
     * @param joinPoint the join point providing details about the intercepted method
     */
    @Before("execution(* org.thewhitemage13..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method call: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Logs method execution results after successful completion.
     * <p>
     * This advice runs after the method has executed successfully and logs
     * the method signature along with its return value.
     * </p>
     *
     * @param joinPoint the join point providing details about the intercepted method
     * @param result    the return value of the method
     */
    @AfterReturning(pointcut = "execution(* org.thewhitemage13..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("The {} method executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Logs exceptions thrown by intercepted methods.
     * <p>
     * This advice runs when the intercepted method throws an exception and logs
     * the method signature along with details about the exception.
     * </p>
     *
     * @param joinPoint the join point providing details about the intercepted method
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
     * Logs the execution of a method, including its arguments, result, and exceptions.
     * <p>
     * This advice wraps the execution of a method, logging its start, result,
     * and any exceptions encountered during execution.
     * </p>
     *
     * @param joinPoint the join point providing details about the intercepted method
     * @return the result of the intercepted method execution
     * @throws Throwable if the intercepted method throws an exception
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