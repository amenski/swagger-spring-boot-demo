package dzone.aman.restapidemo.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dzone.aman.restapidemo.models.User;
import dzone.aman.restapidemo.repositories.UserRepository;
import dzone.aman.restapidemo.services.IUserService;
import it.aman.ethauthserver.swagger.model.ModelUser;

@Service
public class UserServiceImpl implements IUserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean saveUserInfo(ModelUser modelUser) {
		String methodName = "saveUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		try{
			if(modelUser == null)
				throw new RuntimeException();
			
			User userInfo = new User();
			userInfo.setFirstName(modelUser.getFirstname());
			userInfo.setLastName(modelUser.getLastName());
			userInfo.setAvatar(modelUser.getAvatar());
			
			userRepository.save(userInfo);
			return true;
		}catch (Exception ex) {
			logger.error("{} {}", methodName, ex.getMessage());
			logger.error("{} {}", methodName, ex);
			throw ex;
		} finally {
			logger.info("{} {}", methodName, "method end.");
		}
	}

	@Override
	public boolean updateAvatar(MultipartFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ModelUser getUserInfo(Long userId) throws Exception {
		String methodName = "getUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		try{
			if(userId == null)
				throw new RuntimeException();
			
			User userInfo = userRepository.findById(userId).orElse(null);
			if(userInfo == null)
				throw new RuntimeException();
			
			ModelUser modelUser = new ModelUser();
			modelUser.userId(userInfo.getId())
						.firstname(userInfo.getFirstName())
						.lastName(userInfo.getLastName());
			
			/**
			 * Encoding to base64, you can use any method of your choice
			 */
			if(userInfo.getAvatar() != null) {
				Path fileOut = Paths.get(userInfo.getAvatar());
				String content = Base64.getEncoder().encodeToString(Files.readAllBytes(fileOut));
				modelUser.avatar(content);
			}
			
			return modelUser;
		}catch (Exception ex) {
			logger.error("{} {}", methodName, ex.getMessage());
			logger.error("{} {}", methodName, ex);
			throw ex;
		} finally {
			logger.info("{} {}", methodName, "method end.");
		}
	}

}
