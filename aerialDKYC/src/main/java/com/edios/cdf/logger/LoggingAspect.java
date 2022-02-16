package com.edios.cdf.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
@Aspect 
public class LoggingAspect {
	
	    private Log log = LogFactory.getLog("Logging");
	  
	    @Pointcut("execution(* com.edios.cdf.controller.*.*(..))")
	    protected void loggingOperation() {}
	  
	    @Before("loggingOperation()")
	    @Order(1) 
	    public void logJoinPoint(JoinPoint joinPoint)
	    {
	       /* log.info("Join point kind : " + joinPoint.getKind());
	        log.info("Signature declaring type : "+ joinPoint.getSignature().getDeclaringTypeName());*/
	        log.info("In " + joinPoint.getSignature().getName()+" method of "+joinPoint.getTarget().getClass().getName());
	   /*     log.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	        log.info("Target class : "+ joinPoint.getTarget().getClass().getName());
	        log.info("This class : " + joinPoint.getThis().getClass().getName());*/
	    }
	      
	    @AfterReturning(pointcut="loggingOperation()", returning = "result")
	    @Order(2)
	    public void logAfter(JoinPoint joinPoint, Object result)
	    {
//	        log.info("Exiting from Method :"+joinPoint.getSignature().getName());
	        log.info("Return value :"+result);
	    }
	  
//	    @AfterThrowing(pointcut="execution(* com.edios.cdf.controller.LoginController.fetchUserDetails(..))", throwing = "e")
//	    @Order(3)
//	    public void logAfterThrowing(JoinPoint joinPoint, Throwable e)
//	    {
//	        log.error("An exception has been thrown in "+ joinPoint.getSignature().getName() + "()");
//	        log.error("Cause :"+e.getCause());
//	    }
//	  
//	    @Around("execution(* com.edios.cdf.controller.LoginController.fetchUserDetails(..))")
//	    @Order(4)
//	    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable
//	    {
//	        log.info("The method " + joinPoint.getSignature().getName()+ "() begins with " + Arrays.toString(joinPoint.getArgs()));
//	        try
//	        {
//	            Object result = joinPoint.proceed();
//	            log.info("The method " + joinPoint.getSignature().getName()+ "() ends with " + result);
//	            return result;
//	        } catch (IllegalArgumentException e)
//	        {
//	            log.error("Illegal argument "+ Arrays.toString(joinPoint.getArgs()) + " in "+ joinPoint.getSignature().getName() + "()");
//	            throw e;
//	        }      
//	    }

}