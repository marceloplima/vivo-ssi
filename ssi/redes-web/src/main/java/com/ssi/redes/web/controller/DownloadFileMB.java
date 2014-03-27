package com.ssi.redes.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.redes.common.utils.ParametrosSistema;

@ManagedBean
@RequestScoped
public class DownloadFileMB {
	
	private ParametrosSistema parametrosistema = new ParametrosSistema();
	
	public void downloadFile(AnexosRedes anexo) throws IOException
	{
		File file = new File(parametrosistema.recuperaCaminhoUploads()+anexo.getCnmcaminhoanexo());
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

		response.setHeader("Content-Disposition", "attachment;filename="+anexo.getCnmcaminhoanexo());  
		response.setContentLength((int) file.length());  
		ServletOutputStream out = null;  
		try {  
			FileInputStream input = new FileInputStream(file);  
			byte[] buffer = new byte[1024];  
			out = response.getOutputStream();  
	           
			while ((input.read(buffer)) != -1) {  
				out.write(buffer);  
				out.flush();  
			}  
			FacesContext.getCurrentInstance().getResponseComplete(); 
			input.close();
		} catch (IOException err) {  
			err.printStackTrace();  
		} finally {  
			try {  
				if (out != null) {  
					out.close();  
				}  
			} catch (IOException err) {  
				err.printStackTrace();  
			}  
		}      
	}
}
