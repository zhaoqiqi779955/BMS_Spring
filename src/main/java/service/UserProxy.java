package service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserProxy {
   private Logger logger= LoggerFactory.getLogger(UserProxy.class);
  @Before(value = "execution(* service.BorrowerService.getBorrower(..))")
  public void pointCut(JoinPoint joinPoint)
  {

    String fun=joinPoint.getSignature().getDeclaringTypeName();
    String info=formatFunction(fun,joinPoint.getArgs());
    log(info);
  }
  String formatFunction(String fun,Object arg[])
  {
     StringBuilder builder=new StringBuilder("Function: "+fun);
     builder.append(" Paramaters: ");
    for (Object o : arg) {
      builder.append(o.toString()+" ");
    }
    return  builder.toString();
  }
  public void log(String msg)
  {
      logger.info(msg);
  }

}
