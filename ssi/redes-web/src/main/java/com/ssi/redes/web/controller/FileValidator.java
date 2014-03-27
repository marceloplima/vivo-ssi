package com.ssi.redes.web.controller;


import java.io.File;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "fileValidator")
public class FileValidator implements Validator {

    private static final long MAX_FILE_SIZE = 31457280; // 30MB.
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        File file = (File) value;
        if (file != null && file.length() > MAX_FILE_SIZE) {
            file.delete();
            throw new ValidatorException(new FacesMessage(String.format(
                "Arquivo excede o m�ximo de tamanho de %d bytes.", MAX_FILE_SIZE)));
        }
    }

}