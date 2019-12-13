package com.digital.employeegateway.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.google.gson.Gson;

@Aspect
@Order(value = 0)
@Component
public class AuditAspect {

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
	public void ControllerClass() {
	}

	@Pointcut("@annotation(com.digital.employeegateway.config.Auditable)")
	public void externalSystemAudit() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void ControllerMethod() {
	}

	@Around("ControllerClass() && ControllerMethod()")
	public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		long start = System.currentTimeMillis();
		Object value = null;
		Object[] obj = proceedingJoinPoint.getArgs();

		Object requestOb = null;
		if (obj.length > 1) {
			requestOb = obj[0];
		}

		Gson gson = new Gson();
		if (requestOb != null) {
			String apiRequest = gson.toJson(requestOb);
			MDC.put("apiRequest", apiRequest);
		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		MDC.put("service", env.getProperty("spring.application.name"));
		MDC.put("request_path", path);

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

	@Around("externalSystemAudit()")
	public Object externalAudit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		long start = System.currentTimeMillis();
		Object value = null;
		Object[] obj = proceedingJoinPoint.getArgs();
		//Class<? extends Object> className = proceedingJoinPoint.getTarget().getClass();
		Object requestOb = null;
		if (obj.length == 1) {
			requestOb = obj[0];
		}
		Gson gson = new Gson();
		if (requestOb != null) {
			String apiRequest = gson.toJson(requestOb);
			MDC.put("ext_apiRequest", apiRequest);
		}
		
		value = proceedingJoinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		
		String apiResponse = gson.toJson(value);
		MDC.put("ext_apiResponse", apiResponse);
		MDC.put("ext_total_time", executionTime);
		
		MDC.clear();
		return value;
	}
}
