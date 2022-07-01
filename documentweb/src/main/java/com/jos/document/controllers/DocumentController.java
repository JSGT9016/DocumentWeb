package com.jos.document.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.jos.document.entities.Document;
import com.jos.document.repos.DocumentRepository;

@Controller
public class DocumentController {
	
	@Autowired
	DocumentRepository documentRepository;
	
	@RequestMapping("/displayUpload")
	public String displayUpload(ModelMap modelMap){
		List<Document> documents = documentRepository.findAll();
		System.out.println(documents.size());
		modelMap.addAttribute("documents", documents);
		return "documentUpload";
	}
	
	@RequestMapping(value="/upload", method =RequestMethod.POST)
	public String uploadDocument(@RequestParam("document") MultipartFile multiparFile, @RequestParam("id") long id, ModelMap modelMap){
		Document  document =new Document();
		document.setId(id);
		document.setName(multiparFile.getOriginalFilename());
		try {
			document.setData(multiparFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		documentRepository.save(document);
		
		List<Document> documents = documentRepository.findAll();
		System.out.println(documents.size());
		modelMap.addAttribute("documents", documents);
		return "documentUpload";
	}
	
	@RequestMapping("/download")
	public StreamingResponseBody downloadDocument(@RequestParam("id") Long id, HttpServletResponse response){
		Document document = documentRepository.findById(id).get();
		byte[] data = document.getData();
		
		response.setHeader("Content-Disposition", "attachment;filename=download.jpeg");
		return outputStream ->{
			outputStream.write(data);
		};
	}
	
	

}
