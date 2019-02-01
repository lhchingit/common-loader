package com.lhchin.constant;

/**
 * 
 * @author LHCHIN
 *
 */
public enum Mode {
    TRUNCATE_ONLY("TRUNCATE-ONLY"),
    TRUNCATE_THEN_INSERT("TRUNCATE-INSERT"),
    TRUNCATE_THEN_INSERT_UPDATE("TRUNCATE-INSERT-UPDATE"),
    DELETE_ONLY("DELETE-ONLY"),
    DELETE_THEN_INSERT("DELETE-INSERT"),
    DELETE_THEN_INSERT_UPDATE("DELETE-INSERT-UPDATE"),
    INSERT_ONLY("INSERT-ONLY"),
    UPDATE_ONLY("UPDATE-ONLY"),
    INSERT_UPDATE("INSERT-UPDATE");

    private String modeName;

	Mode(String modeName) {
		this.modeName = modeName;
	}

	public String modeName() {
		return modeName;
	}
}
