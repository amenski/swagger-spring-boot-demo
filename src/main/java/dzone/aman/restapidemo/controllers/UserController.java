package dzone.aman.restapidemo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dzone.aman.restapidemo.services.IUserService;
import dzone.aman.restapidemo.services.impl.UserServiceImpl;
import io.swagger.annotations.ApiParam;
import it.aman.ethauthserver.swagger.api.UserApi;
import it.aman.ethauthserver.swagger.model.ModelUser;
import it.aman.ethauthserver.swagger.model.ResponseBase;
import it.aman.ethauthserver.swagger.model.ResponseModelUser;

@RestController
public class UserController implements UserApi{

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserService userService;

	@Override
	public ResponseEntity<ResponseBase> saveUserInfo(@ApiParam(value = ""  )  @Valid @RequestBody ModelUser body) {
		String methodName = "saveUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		ResponseEntity<ResponseBase> responseEntity = null;
		ResponseBase response = null;
		try{
			userService.saveUserInfo(body);
			response = new ResponseBase();
			response.success(Boolean.TRUE).resultCode(200);
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception ex) {
			logger.error("{} {}", methodName, ex.getMessage());
			logger.error("{} {}", methodName, ex);
			response.success(Boolean.FALSE).resultCode(500).addErrorsItem(ex.getMessage());
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("{} {}", methodName, "method end.");
		}
		
		return responseEntity;
	}

	@Override
	public ResponseEntity<ResponseModelUser> getUserInfo(@ApiParam(value = "",required=true) @PathVariable("user-id") Long userId) {
		String methodName = "getUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		ResponseEntity<ResponseModelUser> responseEntity = null;
		ResponseModelUser response = null;
		try{
			ModelUser modelUser = userService.getUserInfo(userId);
			
			response = new ResponseModelUser();
			response.userDetails(modelUser).success(Boolean.TRUE).resultCode(200);
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception ex) {
			logger.error("{} {}", methodName, ex.getMessage());
			logger.error("{} {}", methodName, ex);
			response.success(Boolean.FALSE).resultCode(500).addErrorsItem(ex.getMessage());
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("{} {}", methodName, "method end.");
		}
		
		return responseEntity;
	}
	
	
}
