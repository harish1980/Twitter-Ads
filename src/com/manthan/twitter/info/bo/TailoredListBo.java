package com.manthan.twitter.info.bo;

import java.util.List;

/**
 * This class holds Twitter Tailored List Information.
 * 
 * @author rharish
 *
 */
public class TailoredListBo {

	private boolean targetable;
	private String name;
	private List<String> targetable_types;
	private String permission_level;
	private boolean is_owner;
	private String audience_type;
	private String id;
	private List<String> reasons_not_targetable;
	private String list_type;
	private String created_at;
	private String updated_at;
	private String partner_source;
	private boolean deleted;
	private String audience_size;
	/**
	 * @return the targetable
	 */
	public boolean isTargetable() {
		return targetable;
	}
	/**
	 * @param targetable the targetable to set
	 */
	public void setTargetable(boolean targetable) {
		this.targetable = targetable;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the targetable_types
	 */
	public List<String> getTargetable_types() {
		return targetable_types;
	}
	/**
	 * @param targetable_types the targetable_types to set
	 */
	public void setTargetable_types(List<String> targetable_types) {
		this.targetable_types = targetable_types;
	}
	/**
	 * @return the permission_level
	 */
	public String getPermission_level() {
		return permission_level;
	}
	/**
	 * @param permission_level the permission_level to set
	 */
	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}
	/**
	 * @return the is_owner
	 */
	public boolean isIs_owner() {
		return is_owner;
	}
	/**
	 * @param is_owner the is_owner to set
	 */
	public void setIs_owner(boolean is_owner) {
		this.is_owner = is_owner;
	}
	/**
	 * @return the audience_type
	 */
	public String getAudience_type() {
		return audience_type;
	}
	/**
	 * @param audience_type the audience_type to set
	 */
	public void setAudience_type(String audience_type) {
		this.audience_type = audience_type;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the reasons_not_targetable
	 */
	public List<String> getReasons_not_targetable() {
		return reasons_not_targetable;
	}
	/**
	 * @param reasons_not_targetable the reasons_not_targetable to set
	 */
	public void setReasons_not_targetable(List<String> reasons_not_targetable) {
		this.reasons_not_targetable = reasons_not_targetable;
	}
	/**
	 * @return the list_type
	 */
	public String getList_type() {
		return list_type;
	}
	/**
	 * @param list_type the list_type to set
	 */
	public void setList_type(String list_type) {
		this.list_type = list_type;
	}
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	/**
	 * @return the partner_source
	 */
	public String getPartner_source() {
		return partner_source;
	}
	/**
	 * @param partner_source the partner_source to set
	 */
	public void setPartner_source(String partner_source) {
		this.partner_source = partner_source;
	}
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the audience_size
	 */
	public String getAudience_size() {
		return audience_size;
	}
	/**
	 * @param audience_size the audience_size to set
	 */
	public void setAudience_size(String audience_size) {
		this.audience_size = audience_size;
	}

}
