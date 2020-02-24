package com.lucasmahl.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	//para decodificar nome, qndo tiver espaço entre as palavras
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	// static pq o metodo não será instanciado
	// metodo q quebra a string em arraylist
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}

		return list;
	}
}
