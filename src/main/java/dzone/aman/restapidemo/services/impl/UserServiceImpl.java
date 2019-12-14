package dzone.aman.restapidemo.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dzone.aman.restapidemo.models.User;
import dzone.aman.restapidemo.repositories.UserRepository;
import dzone.aman.restapidemo.services.IUserService;
import dzone.aman.restapidemo.utils.UploadUtils;
import dzone.aman.swagger.model.ModelUser;

@Service
public class UserServiceImpl implements IUserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Value("${app.profile-image.upload-dir}")
	private String imageUploadDir;

	@Value("${app.profile-image.file-types}")
	private String[] fileTypes;
	
	@Value("${app.profile-image.width-height}")
	private int[] widthHeight;

	@Value("${app.profile-image.max-size}")
	private int uploadMaxSize;
		
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
	public boolean updateAvatar(Long userId, MultipartFile file) throws Exception {
		boolean success = false;
		User user;
		try {
			user = userRepository.findById(userId).orElse(null);
			if(user == null)
				throw new RuntimeException("Invalid input value.");;
			
			//filename = [filename + username + date]
			String filename = FilenameUtils.getBaseName(file.getOriginalFilename());
			String validateType = UploadUtils.validateFileType(file, fileTypes);
			String trimedFileName = StringUtils.deleteWhitespace(StringUtils.join(filename, user.getFirstName(), LocalDate.now(), ".", validateType));
			
			byte[] imageByte = UploadUtils.resizeImageAsThumbnails(file.getInputStream(), validateType, widthHeight);
			
			if(imageByte.length > 0){
				Path fileOut = UploadUtils.uploadFileToPath(trimedFileName, imageUploadDir, imageByte);
				
				user.setAvatar(fileOut.toString());
				userRepository.save(user);
				success = true;
			}
		} catch (Exception e) {
			throw e;
		}
		return success;
	}

	@Override
	public ModelUser getUserInfo(Long userId) throws Exception {
		String methodName = "getUserInfo()";
		logger.info("{} {}", methodName, "method start.");
		
		try{
			if(userId == null)
				throw new RuntimeException("User Not found.");
			
			User userInfo = userRepository.findById(userId).orElse(null);
			if(userInfo == null)
				throw new RuntimeException("User Not found.");
			
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
