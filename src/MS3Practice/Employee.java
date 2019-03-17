package MS3Practice;

public class Employee {
	String firstName;
	String lastName;
	String email;
	String gender;
	String data;
	String paymentMethod;
	double fee;
	boolean statusI;
	boolean statusII;
	String city;
	
	public Employee(String firstName, String lastName, String email, String gender, String data,
			String paymentMethod, double fee, boolean statusI, boolean statusII, String city) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.data = data;
		this.paymentMethod = paymentMethod;
		this.fee = fee;
		this.statusI = statusI;
		this.statusII = statusII;
		this.city = city;
	}
	public String getFirstName() {
		return firstName;
	}
	public void SetFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data=data;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMedthod) {
		this.paymentMethod = paymentMethod;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee=fee;
	}
	public boolean getStatusI() {
		return statusI;
	}
	public void setStatusI(boolean statusI) {
		this.statusI = statusI;
	}
	public boolean getStatusII() {
		return statusII;
	}
	public void setStatusII(boolean statusII) {
		this.statusII = statusII;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
