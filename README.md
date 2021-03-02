# Sogeti Challange
Installing dependencies:<br />
The project used Gradle to install the dependencies, Please run the below commands
1. Run command to install the project dependencies: Gradle build
2. Install the Gradle if Gradle is not installed: brew install Gradle ( for mac users)

Running the test:<br />
precondition: dependencies should be installed in the project.
Test can be executed using testNG xml:<br/>
* Using TestNG.xml: Right click on "TestNG.xml" which is available in sogetiChallange->src folder and right click on the file and press run.<br />

Project description:<br />
<b>UI test:</b><br />
sogetiChallange project is a selenium project, Containing the tests for URL "https://www.sogeti.com"<br />
It follows the Page object modal design pattern. i.e. Webpages are in package 'Pages' and Test cases are in package" Tests. with a helper class 'TestHelper' to initialize the driver and webpages.<br /><br />
<b>API test :</b><br/>
Test used glassfish and simple JSON parser. 'API Test' class in 'Tests' contains the API test's and APIBase class contains setup and verification method implementation.<br /><br />
Both the test cases implements interface 'Property', which contains web links and timeout and variable.<br /><br />
<b>NOTE:</b><br> I am not able to automate the UI test 2 (Contact form") As it contains reCaptcha in the website. To automate the functionality I am expecting a website with disabled captcha. I Managed to click on checkbox on reCaptcha, however google identifies me as not human and throws picture enable reCaptcha.
