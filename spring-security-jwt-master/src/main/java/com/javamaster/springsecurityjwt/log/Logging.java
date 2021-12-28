package com.javamaster.springsecurityjwt.log;
import org.aspectj.lang.annotation.*;


    @Aspect
    public class Logging {

        @Pointcut("execution(* com.javamaster.springsecurityjwt*.*(..))")
        public void selectAllMethodsAvaliable() {
        }
        @Before("selectAllMethodsAvaliable()")
        public void beforeAdvice() {
            System.out.println("Now we are going to initiate user's profile.");
        }

        @After("selectAllMethodsAvaliable()")
        public void afterAdvice() {
            System.out.println("User's profile has been initiated.");
        }

        @AfterReturning(pointcut = "selectAllMethodsAvaliable()", returning = "someValue")
        public void afterReturningAdvice(Object someValue) {
            System.out.println("Value: " + someValue.toString());
        }

        @AfterThrowing(pointcut = "selectAllMethodsAvaliable()", throwing = "e")
        public void inCaseOfExceptionThrowAdvice(ClassCastException e) {
            System.out.println("We have an exception here: " + e.toString());
        }

    }


