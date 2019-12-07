package dzone.aman.restapidemo.services;

import org.springframework.web.multipart.MultipartFile;

import dzone.aman.swagger.model.ModelUser;

public interface IUserService {

	public boolean saveUserInfo(ModelUser userInfo) throws Exception;
	
	public boolean updateAvatar(Long userId, MultipartFile file) throws Exception;
	
	public ModelUser getUserInfo(Long userId) throws Exception;
}
