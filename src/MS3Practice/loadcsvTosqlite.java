package MS3Practice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class loadcsvTosqlite {
	// The following UEL establishes a database connection with Java DB Embedded Driver
	public static String JDBC_CONNECTION_URL = "jdbc:sqlite:testdb; create=true";

	public static void main(String[] args){
		// input the csv file and name it filename
		String filename = "C:/Users/zsqlo/Desktop/MS3/ms3Interview.csv";
		boolean HasHeader = true;
		String line = "";
		String csvSeparator = ",";
		String[] employee = null;
		int totalNumberOfColumns = 0;
		int wrongRecords = 0;
		// create a string arraylist to hold employee objects called EmployeeList
		ArrayList<Employee> EmployeeList = new ArrayList<Employee>();
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// if column has header will read the line
			if (HasHeader) {
				String HeaderRow = bufferedReader.readLine();
				// if HeaderRow does not exist throw exception
				if (HeaderRow.isEmpty() || HeaderRow == null) {
					throw new FileNotFoundException("The columns in csv file correspond with column header!");
				}
			}

			while ((line = bufferedReader.readLine()) != null) {
				// csv split by comma
				employee = line.split(csvSeparator);
				// use length property to find out how many elements employee array has, if employee array length greater than 0 and equal to 10 create a new employeeInfo array
				// in employeeInfo[6] convert from String to Double,employeeInfo[7] and employeeInfo[8] convert from String to Boolean
				if (employee.length > 0 && employee.length == totalNumberOfColumns) {
					Employee employeeInfo = new Employee(employee[0], employee[1], employee[2], employee[3],
							employee[4], employee[5], Double.valueOf(employee[6]), Boolean.valueOf(employee[7]),
							Boolean.valueOf(employee[8]), employee[9]);
					EmployeeList.add(employeeInfo);
				} else {
					wrongRecords++;
				}
			} LoadCSVintoDatabase(EmployeeList);

		} 
		// throw FileNotFoundException and IOException to catch the error if it occurrs and print out the exception that occurred by using printStackTrace method
		catch (FileNotFoundException e) {
			// Print out the exception that occurred
			e.printStackTrace();
		} catch (IOException e) {
			// Print out the exception that occurred
			e.printStackTrace();
		}
		// print out the statistics about the number of records received, successful and failed in part four
		System.out.println(employee.length + " of records received " + "\n" + EmployeeList.size()
				+ " of records successfully " + "\n" + wrongRecords + " of records failed");
	}

	public static void LoadCSVintoDatabase(ArrayList<Employee> employeeList){
		Connection conn = null;
		boolean tableExist = false;
		boolean truncateTable = true;
		Statement stmt = null;
		try {
			// establishes a database connection with the database URL
			conn = DriverManager.getConnection(JDBC_CONNECTION_URL);
			// if table does not exist
			if (tableExist == false) {
				// create a statement and execute the query
				conn.createStatement().execute(
						"create table employee(firstName, lastName, email, gender, data, paymentMethod, fee, statusI, statusII, city)");
			}
			// TRUNCATE TABLE is treated as DROP TABLE followed by CREATE TABLE
			if (truncateTable == true) {
				// create a statement and execute the query
				conn.createStatement().execute("remove from employee table");
			}
			// create a PreparedStatement Object
			PreparedStatement prep = conn.prepareStatement(
					"insert into employee(firstName, lastName, email, gender, data, paymentMethod, fee, statusI, statusII, city) values"
							+ "(?,?,?,?,?,?,?,?,?,?)");
			// uses a for-each loop to repeatedly set values in PreparedStatement objects 
			// do this by calling one of the setter methods  
			for (Employee emp : employeeList) {
				//the first argument of each of these setter methods specifies the question mark placeholder
				// setString specifies the first to sixth and tenth placeholder, setDouble specifies the sixth placeholder and setBoolean specifies the eighth and ninth placeholder  
				prep.setString(1, emp.getFirstName());
				prep.setString(2, emp.getLastName());
				prep.setString(3, emp.getEmail());
				prep.setString(4, emp.getGender());
				prep.setString(5, emp.getData());
				prep.setString(6, emp.getPaymentMethod());
				prep.setDouble(7, emp.getFee());
				prep.setBoolean(8, emp.getStatusI());
				prep.setBoolean(9, emp.getStatusII());
				prep.setString(10, emp.getCity());
				// executeUpdate such as an UPDATE SQL statement
				prep.executeUpdate();
			}
			System.out.println("Result of SELECT\n");
			// create a statement object
			stmt = conn.createStatement();
			// execute statement object and generate ResultSet object
			ResultSet rs = stmt.executeQuery("select * from employee");
			// Processing the ResultSet Objects by using while loop
			// repeatedly calls the method rs.next() to move the cursor forward by one row, every time it calls next, 
			// the method outputs the data in the row where the cursor is currently positioned
			while (rs.next()) {
				String firstName = rs.getString(1);
				String lastName = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				String data = rs.getString(5);
				String paymentMethod = rs.getString(6);
				Double fee = rs.getDouble(7);
				Boolean statusI = rs.getBoolean(8);
				Boolean statusII = rs.getBoolean(9);
				String city = rs.getString(10);

				System.out.println(firstName + "\t" + lastName + "\t" + email + "\t" + gender + "\t" + data + "\t"
						+ paymentMethod + "\t" + fee + "\t" + statusI + "\t" + statusII + "\t" + city);
			}
			
		} 
		//JDBC throws an SQLException when it encounters an error during an interaction with a data source
		catch (SQLException e) {
			// Print out the exception that occurred
			e.printStackTrace();
		}
		// Closing Connections
		finally {
			if(stmt != null) {
				// call method stmt.close() to release the resources it is using 
				// when call this method, its ResultSet objects are closed 
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
