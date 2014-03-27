package com.ssi.redes.common.utils;


import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dataMaiorValidator")
public class DataMaiorValidator implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle.
        }

//        UIInput startDateComponent = (UIInput) component.getAttributes().get("startDateComponent");
//
//        if (!startDateComponent.isValid()) {
//            return; // Already invalidated. Don't care about it then.
//        }

        //Date startDate = (Date) startDateComponent.getValue();

//        if (startDate == null) {
//            return; // Let required="true" handle.
//        }
        
        Calendar c = (Calendar)value;
        
        Date hoje = new Date();

        Date datainformada = c.getTime();

        if (!datainformada.after(hoje)) {
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data inv�lida", null));
        }
    }

}