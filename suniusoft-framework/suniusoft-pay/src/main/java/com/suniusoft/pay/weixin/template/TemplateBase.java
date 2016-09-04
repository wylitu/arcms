package com.suniusoft.pay.weixin.template;

import java.util.Map;

public class TemplateBase {
	
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Map<String,TemplateElement> data;
	public String getTouser() {
		return touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public String getUrl() {
		return url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public Map<String, TemplateElement> getData() {
		return data;
	}
	public void setData(Map<String, TemplateElement> data) {
		this.data = data;
	}
	

	


}
