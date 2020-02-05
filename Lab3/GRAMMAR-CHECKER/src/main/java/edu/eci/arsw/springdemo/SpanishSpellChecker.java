package edu.eci.arsw.springdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
@Service("spanishSpellChecker")
public class SpanishSpellChecker implements SpellChecker {

	@Override
	public String checkSpell(String text) {
		return "revisando ("+text+") con el verificador de sintaxis del espanol";
                
                
	}

}
