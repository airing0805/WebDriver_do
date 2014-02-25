package com.andy.yunpanDriver.entity;

import java.sql.Date;

public class QQWeibo {

	private int ID;
	private String URL;
	private Date START_TIME;
	private Date END_TIME;
	private String MESSAGE_STATE;
	private Date LAST_CRAWL_TIME;
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
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}
	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}
	/**
	 * @return the sTART_TIME
	 */
	public Date getSTART_TIME() {
		return START_TIME;
	}
	/**
	 * @param sTART_TIME the sTART_TIME to set
	 */
	public void setSTART_TIME(Date sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	/**
	 * @return the eND_TIME
	 */
	public Date getEND_TIME() {
		return END_TIME;
	}
	/**
	 * @param eND_TIME the eND_TIME to set
	 */
	public void setEND_TIME(Date eND_TIME) {
		END_TIME = eND_TIME;
	}
	/**
	 * @return the mESSAGE_STATE
	 */
	public String getMESSAGE_STATE() {
		return MESSAGE_STATE;
	}
	/**
	 * @param mESSAGE_STATE the mESSAGE_STATE to set
	 */
	public void setMESSAGE_STATE(String mESSAGE_STATE) {
		MESSAGE_STATE = mESSAGE_STATE;
	}
	/**
	 * @return the lAST_CRAWL_TIME
	 */
	public Date getLAST_CRAWL_TIME() {
		return LAST_CRAWL_TIME;
	}
	/**
	 * @param lAST_CRAWL_TIME the lAST_CRAWL_TIME to set
	 */
	public void setLAST_CRAWL_TIME(Date lAST_CRAWL_TIME) {
		LAST_CRAWL_TIME = lAST_CRAWL_TIME;
	}

}
