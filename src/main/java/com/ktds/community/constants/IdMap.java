package com.ktds.community.constants;

import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class IdMap {
	
	public static Map<String, Integer> idExist(Map<String, Integer> idBlock, String id) {
		
				
		if( idBlock.get(id) == null ) {
			idBlock.put(id, 1);
			return idBlock;
		}
		else {
			idBlock.put(id, idBlock.get(id)+1);
			return idBlock;
			
		}
		
		
	}
	

}
