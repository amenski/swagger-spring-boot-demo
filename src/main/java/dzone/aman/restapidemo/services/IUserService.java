package dzone.aman.restapidemo.services;

import org.springframework.web.multipart.MultipartFile;

import it.aman.ethauthserver.swagger.model.ModelUser;

public interface IUserService {

	public boolean saveUserInfo(ModelUser userInfo) throws Exception;
	
	public boolean updateAvatar(MultipartFile file) throws Exception;
	
	public ModelUser getUserInfo(Long userId) throws Exception;
}
