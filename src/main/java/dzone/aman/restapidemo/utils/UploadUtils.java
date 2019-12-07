package dzone.aman.restapidemo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);

	
	private UploadUtils(){
		//
	}
	
	/**
	 * Check if the file is of permitted extension and return it
	 * 
	 * @param file
	 * @return file extension
	 * @throws RuntimeException
	 */
	public static String validateFileType(MultipartFile file, String[] permittedFileTypes) {
		String methodName = "getFileExtension()";
		logger.error("{} filename: [{}] and filetypes [{}]", methodName,file.getOriginalFilename(), permittedFileTypes);
		Assert.notNull(file, "Uploaded file can not be null");
		Set<String> fileTypeSet = new HashSet<>();
		String fileExtension = "";
		try {
			fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

			for (int i = 0; i < permittedFileTypes.length; i++) {
				fileTypeSet.add(permittedFileTypes[i]);
			}

			if (!fileTypeSet.contains(fileExtension) || file.isEmpty())
				throw new RuntimeException("File type or size not allowed.");

		}catch (Exception e) {
			logger.error("{}{}", methodName, e.getMessage());
			throw e;
		}
		return fileExtension;
	}
	
	/**
	 * Upload file to a path.
	 * File name will be as [filename + UserName + date] on disk
	 * 
	 * @param flag <b>[flag = true] tells file = image or otherwise </b>
	 * @return Path 
	 * @throws IOException 
	 */
	public static Path uploadFileToPath(String fullFileName, String uploadDir, byte[] filecontent) throws IOException {
		String methodName = "uploadFileToPath()";
		Assert.notNull(fullFileName, "Filename should not be null.");
		Assert.notNull(filecontent, "Filecontent can not be null.");
		
		Path fileAbsolutePath = null;
		Path fileOut = null;
		try{
			fileAbsolutePath = Paths.get(StringUtils.join(uploadDir, File.separatorChar, fullFileName));
			fileOut = Files.write(fileAbsolutePath, filecontent);
		}catch (Exception e) {
			logger.error("{}{}", methodName, e.getMessage());
			throw e;
		}
		return fileOut;
	}
	/**
	 * Resize image for profile picture
	 * 
	 * @param inputStream
	 * @param extension
	 * @return scaled image to be used as profile picture
	 * @throws IOException
	 */
	public static byte[] resizeImageAsThumbnails(InputStream inputStream, String extension, int[] widthHeight) throws IOException {
		Assert.notNull(inputStream, "Image data should not be null.");
		Assert.notNull(extension, "Extension can not be null.");
		String methodName = "resizeImageAsThumbnails()";
		byte[] imageInByte = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage img = ImageIO.read(inputStream);
			BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, widthHeight[0], widthHeight[1]);
	
			ImageIO.write(scaledImg, extension, baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		}catch(Exception e){
			logger.error("{}{}", methodName, e.getMessage());
			logger.error("{}{}", methodName, e);
			throw e;
		}
		return imageInByte;
	}
}
