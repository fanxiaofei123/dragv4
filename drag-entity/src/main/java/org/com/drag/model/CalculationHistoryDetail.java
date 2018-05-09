package org.com.drag.model;

public class CalculationHistoryDetail extends CalculationHistory {

	private static final long serialVersionUID = -5982805630863018949L;
	
	private User user;
	private FacadeModel model;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public FacadeModel getModel() {
		return model;
	}
	public void setModel(FacadeModel model) {
		this.model = model;
	}

}
