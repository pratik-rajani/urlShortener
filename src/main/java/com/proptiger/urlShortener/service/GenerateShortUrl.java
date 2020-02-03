package com.proptiger.urlShortener.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

@Service
public class GenerateShortUrl {

	public String generateShortUrlFromId(long id) {
		
		ArrayList<Long> urlDigits = new ArrayList();
		
		while(id > 0) {
			long reminder = id%62;
			urlDigits.add(reminder);
			id /= 62;
		}
		Collections.reverse(urlDigits);
		
		return getMappingDigitsToUrl(urlDigits);
	}

	private String getMappingDigitsToUrl(ArrayList<Long> urlDigits) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for(long digit : urlDigits) {
			char newChar;
			if(digit >= 0 && digit < 26) {
				newChar = (char)('a' + digit);
			}
			else if(digit >= 26 && digit < 52) {
				newChar = (char)('A' + digit - 26);
			}
			else {
				newChar = (char)(digit + '0' - 52);
			}
			stringBuilder.append(newChar);
		}
		
		return stringBuilder.toString();
	}
}
