package com.digital.Student.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@org.aspectj.lang.annotation.Aspect
@Component
@Controller
@Order(value=0)
public class Aspect {

	private static final Logger logger = LoggerFactory.getLogger(Aspect.class);
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
	public void ControllerClass() {	
	}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void ControllerMethod() {	
	}
	
	@Around("ControllerClass() && ControllerMethod()")
	public Object studentAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		Object value = null;
		
		Object []obj = proceedingJoinPoint.getArgs();
		
		Object reqObj = null;
		
		if(obj.length>=1) {
			reqObj = obj[0];
		}
		Gson gson = new Gson();
		if (reqObj != null) {
		String apiRequest = gson.toJson(reqObj);
		MDC.put("apiRequest", apiRequest);
		}		
		logger.error("Request from controller");
		
		value = proceedingJoinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		String apiResponse = gson.toJson(value);
		MDC.put("apiResponse", apiResponse);
		MDC.put("total_time", executionTime);
		logger.error("Response from controller");
		MDC.clear();
		return value;	
	}
}
