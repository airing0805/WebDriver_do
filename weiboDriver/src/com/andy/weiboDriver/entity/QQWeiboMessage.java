package com.andy.weiboDriver.entity;

import java.util.Date;

public class QQWeiboMessage {

	private int ID; 
	private String FORM_URL;
	private String MESSAGE_CONTENT;
	private String PIC_HREF; 
	private Date MESSAGE_TIME;
	private String STATE;
	
	
	public Date getMESSAGE_TIME() {
		return MESSAGE_TIME;
	}
	public void setMESSAGE_TIME(Date mESSAGE_TIME) {
		MESSAGE_TIME = mESSAGE_TIME;
	}
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * @return the fORM_URL
	 */
	public String getFORM_URL() {
		return FORM_URL;
	}
	/**
	 * @param fORM_URL the fORM_URL to set
	 */
	public void setFORM_URL(String fORM_URL) {
		FORM_URL = fORM_URL;
	}
	/**
	 * @return the mESSAGE_CONTENT
	 */
	public String getMESSAGE_CONTENT() {
		return MESSAGE_CONTENT;
	}
	/**
	 * @param mESSAGE_CONTENT the mESSAGE_CONTENT to set
	 */
	public void setMESSAGE_CONTENT(String mESSAGE_CONTENT) {
		MESSAGE_CONTENT = mESSAGE_CONTENT;
	}
	/**
	 * @return the pIC_HREF
	 */
	public String getPIC_HREF() {
		return PIC_HREF;
	}
	/**
	 * @param pIC_HREF the pIC_HREF to set
	 */
	public void setPIC_HREF(String pIC_HREF) {
		PIC_HREF = pIC_HREF;
	}
	/**
	 * @return the sTATE
	 */
	public String getSTATE() {
		return STATE;
	}
	/**
	 * @param sTATE the sTATE to set
	 */
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	
	
}
