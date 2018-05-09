package org.com.drag.web.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
/**
 *  切面
 * @author lzq
 *
 */
public class Aspect {
	
	Logger logger = Logger.getLogger(Aspect.class);
	String strLog = null ;
	
	/**
	 * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点前的执行
	 * @param jp 连接点：程序执行过程中的某一行为，例如，AServiceImpl.barA()的调用或者抛出的异常行为
	 */
	public void doBefore(JoinPoint jp) {
		strLog = "log Begining method:(日志开始的方法：) "
				+ jp.getTarget().getClass().getName() + "."
				+ jp.getSignature().getName();
		logger.warn(strLog);
	}
	/**
     * 环绕通知：包围一个连接点的通知，可以在方法的调用前后完成自定义的行为，也可以选择不执行
     * 类似Web中Servlet规范中的Filter的doFilter方法。
     * @param pjp 当前进程中的连接点
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process time:(过程的时间：) " + time + " ms");
        return retVal;
    }
    /**
     * 抛出异常后通知 ： 在方法抛出异常退出时执行的通知。
     * @param jp 连接点：程序执行过程中的某一行为，例如，AServiceImpl.barA()的调用或者抛出的异常行为
     */
    public void doAfter(JoinPoint jp) {
    	strLog ="doAfter:log Ending method:(日志结尾方法：) "
    			+ jp.getTarget().getClass().getName() + "."
    			+ jp.getSignature().getName(); 
    	logger.warn(strLog);
    }
}

//    public void doThrowing(JoinPoint jp, Throwable ex) {
//        System.out.println("method " + jp.getTarget().getClass().getName()
//                + "." + jp.getSignature().getName() + " throw exception");
//        System.out.println(ex.getMessage());
//    }	
