package br.ufc.qxd.util;

public class ValidDate {	
	public ValidDate() {}
	
	public boolean compareTo(String date) throws Exception{
		String aux[] = date.split("/");
		if(Integer.parseInt(aux[0]) <= 31 && Integer.parseInt(aux[0]) >= 1 && Integer.parseInt(aux[1]) <= 12 && Integer.parseInt(aux[1]) >= 1 && Integer.parseInt(aux[2]) >= 1500 && Integer.parseInt(aux[2]) <= 2050)
			return true;
		return false;
	}
}
