package com.manthan.twitter.info.bo;

/**
 * This class holds Twitter Account Information.
 * 
 * @author rharish
 *
 */
public class AccountBo {
	private String name;
	private String business_name;
	private String timezone;
	private String timezone_switch_at;
	private String id;
	private String created_at;
	private String salt;
	private String updated_at;
	private String business_id;
	private String approval_status;
	private boolean deleted;
	
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
	 * @return the business_name
	 */
	public String getBusiness_name() {
		return business_name;
	}
	/**
	 * @param business_name the business_name to set
	 */
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	/**
	 * @return the timezone_switch_at
	 */
	public String getTimezone_switch_at() {
		return timezone_switch_at;
	}
	/**
	 * @param timezone_switch_at the timezone_switch_at to set
	 */
	public void setTimezone_switch_at(String timezone_switch_at) {
		this.timezone_switch_at = timezone_switch_at;
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
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
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
	 * @return the business_id
	 */
	public String getBusiness_id() {
		return business_id;
	}
	/**
	 * @param business_id the business_id to set
	 */
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	/**
	 * @return the approval_status
	 */
	public String getApproval_status() {
		return approval_status;
	}
	/**
	 * @param approval_status the approval_status to set
	 */
	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
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

}
