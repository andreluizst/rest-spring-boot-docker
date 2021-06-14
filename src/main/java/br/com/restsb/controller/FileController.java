package br.com.restsb.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.restsb.data.dto.UploadFileDTO;
import br.com.restsb.services.FileStorageService;
import io.swagger.annotations.Api;

@Api(tags = "FileEndpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public ResponseEntity<UploadFileDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		String filename = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/downloadFile/")
				.path(filename).toUriString();
		UploadFileDTO uploadFileDto = new UploadFileDTO(filename, fileDownloadUri, file.getContentType(),
				file.getSize());
		return new ResponseEntity<>(uploadFileDto, HttpStatus.CREATED);
	}

	@PostMapping("/uploadMultipleFiles")
	public List<ResponseEntity<UploadFileDTO>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/downloadFile/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename, HttpServletRequest request){
		Resource resource = this.fileStorageService.loadFileAsResource(filename);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch(Exception e) {
			logger.info("Could not determine file type!");
		}
		
		if (contentType == null)
			contentType = "application/octet-stream";
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
