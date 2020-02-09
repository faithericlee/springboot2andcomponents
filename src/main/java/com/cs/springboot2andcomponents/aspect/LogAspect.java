package com.cs.springboot2andcomponents.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * 日志aspect整合
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.cs.springboot2andcomponents.aop.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        showLog(joinPoint);
        log.info(null!=result?result.toString():"");
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        showLog((ProceedingJoinPoint)joinPoint);
        log.error(e.getMessage());
    }

    private void showLog(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.cs.springboot2andcomponents.aop.Log aopLog = method.getAnnotation(com.cs.springboot2andcomponents.aop.Log.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        log.info("{}-耗时{}ms",aopLog.value(),System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.info(methodName);
        log.info(params.toString() + " }");
    }
}
