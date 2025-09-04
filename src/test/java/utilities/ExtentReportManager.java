package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkreporter;   //UI of the report
	public ExtentReports extent;          //populate common info on the report
	public ExtentTest test;    
	String repName;
	
	//creating test case entries in the report and update status of the test methods

	
	
	
	public void onStart(ITestContext testContext)
	{
	
		
		
		//sparkreporter =new ExtentSparkReporter(File.separator + "reports" + File.separator + repName);
		
		String timestamp = new SimpleDateFormat("yy-MM-dd-HH-mm-ss").format(new Date());
	    repName = "Test-Report-" + timestamp + ".html";

	    String reportDirPath = System.getProperty("user.dir") + File.separator + "reports";
	    File reportDir = new File(reportDirPath);

	    if (!reportDir.exists()) {
	        reportDir.mkdirs();
	    }

	    String reportPath = reportDirPath + File.separator + repName;

	    sparkreporter = new ExtentSparkReporter(reportPath);

		//specify the location of report
		sparkreporter.config().setDocumentTitle("Opencart Automation Report");  //title of the report
		sparkreporter.config().setReportName("Opencart Functional Testing");    //name the report
		sparkreporter.config().setTheme(Theme.DARK);
		
		
		extent=new ExtentReports();
		extent.attachReporter(sparkreporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating Sytem", os);

		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		
		List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());    //creating a new entry in the report
		
		test.assignCategory(result.getMethod().getGroups());    //to display groups in report
		
		test.log(Status.PASS,"Test case passed is"+ result.getName());    //update status pass/fail/skipped
		
		
	}
	
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		
		test.assignCategory(result.getMethod().getGroups());
		
	    test.log(Status.FAIL,"Test case failed is"+ result.getName()); 
	    
	    test.log(Status.INFO,"Test case failed cause is"+ result.getThrowable().getMessage()); 
	    
	    try {
	    	
	    	String imgPath=new BaseClass().captureScreen(result.getName());
	    	test.addScreenCaptureFromPath(imgPath);
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	        test.log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
	    }
	    
	    
	}
	
	
	
	public void onTestSkipped(ITestResult result)
	{
     test=extent.createTest(result.getTestClass().getName());
		
		test.assignCategory(result.getMethod().getGroups());
		
	    test.log(Status.SKIP,"Test case skipped is"+ result.getName()); 
	    test.log(Status.INFO,"Test case Skipped cause is"+ result.getThrowable().getMessage()); 
	    
		
		
	}
	
	
	public void onFinish(ITestContext context)
	{
		
		extent.flush();
		
		String pathOfExtentReport=System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName;
		//String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport=new File(pathOfExtentReport);
		try {
			
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(IOException e)
		{
			
			e.printStackTrace();
		}
		
	}
	
}