package br.com.restsb.services;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.restsb.config.FileStorageConfig;
import br.com.restsb.exception.FileStorageException;
import br.com.restsb.exception.ResourceNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) throws FileStorageException {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
		try {
			if (!Files.isDirectory(this.fileStorageLocation)) {
				Files.createDirectory(fileStorageLocation);
			}
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory to upload file.", e);
		}
	}

	public String storeFile(MultipartFile file) throws FileStorageException {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (filename.contains(".."))
				throw new FileStorageException("Filename contains invalid path sequence");
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return filename;
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory to upload file.", e);
		}
	}
	
	public Resource loadFileAsResource(String filename) throws ResourceNotFoundException {
		Path filePath = this.fileStorageLocation.resolve(filename).normalize();
		Resource resource = null;
		try {
			resource = new UrlResource(filePath.toUri());
			if (!resource.exists()) {
				throw new ResourceNotFoundException("File not found: " + filename);
			}
		} catch (Exception e) {
			throw new ResourceNotFoundException("File not found: " + filename, e);
			//e.printStackTrace();
		}
		return resource;
	}
}
