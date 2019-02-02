package com.lhchin.constant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author LHCHIN
 *
 */
public enum Mode {
	INSERT_ONLY("INSERT-ONLY"),
    UPDATE_ONLY("UPDATE-ONLY"),
    INSERT_UPDATE("INSERT-UPDATE"),
    TRUNCATE_ONLY("TRUNCATE-ONLY"),
    TRUNCATE_THEN_INSERT("TRUNCATE-INSERT"),
    TRUNCATE_THEN_INSERT_UPDATE("TRUNCATE-INSERT-UPDATE"),
    DELETE_ONLY("DELETE-ONLY"),
    DELETE_THEN_INSERT("DELETE-INSERT"),
    DELETE_THEN_INSERT_UPDATE("DELETE-INSERT-UPDAT");

	private String modeAliasName;

	Mode(String modeAliasName) {
	  this.modeAliasName = modeAliasName;
	}

	public String getModeAliasName() {
	  return this.modeAliasName;
	}

	public static Mode getEnumValue(String modeAliasName) {
	    for (Mode m : Mode.values()) {
	    	if (m.modeAliasName.equalsIgnoreCase(modeAliasName)) {
	    		return m;
	        }
	    }

	    return null;
	}

	public static Set<String> getAllModesAliasNameAsSet() {
		Set<String> modesSet = new HashSet<>();
		for (Mode m : Mode.values()) {
			modesSet.add(m.modeAliasName);
		}

		return modesSet;
	}
}
