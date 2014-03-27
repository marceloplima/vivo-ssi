package com.ssi.admin.utils;

import com.ssi.admin.controller.IndexMB;

public class ResetadorPaineisPadrao {

	public static void resetarPaineis(IndexMB indexmb){
		indexmb.setPanelexibealerta(false);
	    indexmb.setPanelexibeerro(false);
	    indexmb.setPanelexibesucesso(false);
	    indexmb.setRenderizalistadeerros(false);
	    indexmb.setMsgpanel("");		
	}
}
