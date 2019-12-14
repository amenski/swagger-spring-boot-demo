package dzone.aman.restapidemo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dzone.aman.restapidemo.services.IUserService;
import dzone.aman.restapidemo.services.impl.UserServiceImpl;
import dzone.aman.swagger.api.UserApi;
import dzone.aman.swagger.model.ModelUser;
import dzone.aman.swagger.model.ResponseBase;
import dzone.aman.swagger.model.ResponseModelUser;
import io.swagger.annotations.ApiParam;

@RestController
public class UserController implements UserApi{

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserService userService;
	
	// PUT - /v1/user
	@Override
	public ResponseEntity<ResponseBase> saveUserInfo(@ApiParam(value = ""  )  @Valid @RequestBody ModelUser body) {
		String methodName = "saveUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		ResponseEntity<ResponseBase> responseEntity = null;
		ResponseBase response = new ResponseBase();
		try{
			userService.saveUserInfo(body);
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

	// GET - /v1/user/{user_id}
	@Override
	public ResponseEntity<ResponseModelUser> getUserInfo(@ApiParam(value = "",required=true) @PathVariable("user-id") Long userId) {
		String methodName = "getUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		ResponseEntity<ResponseModelUser> responseEntity = null;
		ResponseModelUser response =  new ResponseModelUser();;
		try{
			ModelUser modelUser = userService.getUserInfo(userId);
			
			response.userDetails(modelUser).success(Boolean.TRUE).resultCode(200);
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception ex) {
			logger.error("{} {}", methodName, ex.getMessage());
			logger.error("{} {}", methodName, ex);
			response.success(Boolean.FALSE).resultCode(500).message(ex.getMessage());
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("{} {}", methodName, "method end.");
		}
		
		return responseEntity;
	}

	// POST - /v1/user/{user_id}/updateAvatar
	/**
	 * File upload request requires HttpMethod = POST
	 */
	@Override
	public ResponseEntity<ResponseBase> updateUserAvatar(
			@ApiParam(value = "",required=true) @PathVariable("user-id") Long userId,
			@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile profileImage) 
	{
		String methodName = "updateUserAvatar()";
		ResponseEntity<ResponseBase> responseEntity = null;
		ResponseBase response = new ResponseBase();;
		try {
			userService.updateAvatar(userId, profileImage);
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
	
	
}
