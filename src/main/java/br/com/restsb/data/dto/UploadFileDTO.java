package br.com.restsb.data.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDTO implements Serializable {

	private static final long serialVersionUID = 3502013846757876745L;

	
	private String filename;
	private String uri;
	private String type;
	private Long size;
}
