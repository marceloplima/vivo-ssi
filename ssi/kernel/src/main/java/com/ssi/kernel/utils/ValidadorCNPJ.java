package com.ssi.kernel.utils;

public class ValidadorCNPJ {
	private static char[] aCnpj;

	public Boolean validar(String cnpj) {
		if (!validaCNPJ(String.valueOf(cnpj))) {
			return false;
		}
		return true;
	}

	/**
	 * Valida CNPJ da empresa
	 * 
	 * @param cnpj
	 *            String valor com 14 d�gitos
	 */
	private static boolean validaCNPJ(String cnpj) {
		if (cnpj == null || cnpj.length() != 14 || isCNPJPadrao(cnpj))
			return false;

		try {
			Long.parseLong(cnpj);
		} catch (NumberFormatException e) { // CNPJ n�o possui somente n�meros
			return false;
		}

		return verificacaoCNPJ(cnpj);
	}

	public static boolean verificacaoCNPJ(String cnpj) {
		int soma = 0;

		aCnpj = cnpj.toCharArray();

		soma += (parseCharToInt(aCnpj[0]) * 5);
		soma += (parseCharToInt(aCnpj[1]) * 4);
		soma += (parseCharToInt(aCnpj[2]) * 3);
		soma += (parseCharToInt(aCnpj[3]) * 2);
		soma += (parseCharToInt(aCnpj[4]) * 9);
		soma += (parseCharToInt(aCnpj[5]) * 8);
		soma += (parseCharToInt(aCnpj[6]) * 7);
		soma += (parseCharToInt(aCnpj[7]) * 6);
		soma += (parseCharToInt(aCnpj[8]) * 5);
		soma += (parseCharToInt(aCnpj[9]) * 4);
		soma += (parseCharToInt(aCnpj[10]) * 3);
		soma += (parseCharToInt(aCnpj[11]) * 2);

		int d1 = soma % 11;
		d1 = d1 < 2 ? 0 : 11 - d1;

		soma = 0;
		soma += (parseCharToInt(aCnpj[0]) * 6);
		soma += (parseCharToInt(aCnpj[1]) * 5);
		soma += (parseCharToInt(aCnpj[2]) * 4);
		soma += (parseCharToInt(aCnpj[3]) * 3);
		soma += (parseCharToInt(aCnpj[4]) * 2);
		soma += (parseCharToInt(aCnpj[5]) * 9);
		soma += (parseCharToInt(aCnpj[6]) * 8);
		soma += (parseCharToInt(aCnpj[7]) * 7);
		soma += (parseCharToInt(aCnpj[8]) * 6);
		soma += (parseCharToInt(aCnpj[9]) * 5);
		soma += (parseCharToInt(aCnpj[10]) * 4);
		soma += (parseCharToInt(aCnpj[11]) * 3);
		soma += (parseCharToInt(aCnpj[12]) * 2);

		int d2 = soma % 11;
		d2 = d2 < 2 ? 0 : 11 - d2;

		if (parseCharToInt(aCnpj[12]) == d1 && parseCharToInt(aCnpj[13]) == d2) {
			return true;
		} else {
			return false;
		}
	}

	private static int parseCharToInt(char c) {
		return Integer.parseInt(Character.toString(c));
	}

	private static boolean isCNPJPadrao(String cnpj) {
		if (cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333")
				|| cnpj.equals("44444444444444")
				|| cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666")
				|| cnpj.equals("77777777777777")
				|| cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999")) {

			return true;
		}

		return false;
	}
}
