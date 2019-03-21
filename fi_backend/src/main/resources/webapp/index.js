document.addEventListener('DOMContentLoaded', function() {
init()
}, false);

function init()
{

	var imported = document.createElement('script');
	imported.src = 'common.js';
	document.head.appendChild(imported);
	var loginForm = document.getElementById('Login_Form');
	loginForm.addEventListener('submit', function(event)
	{
    	event.preventDefault();
    	login (loginForm)
	});
    var authorization=readAuthorizationCookies();
	if (authorization)
	{
		openApp()
	}else
	{
	  openTab("Login")
	}
	/*var registerForm = document.getElementById('Register_Form');
	registerForm.addEventListener('submit', function(event)
	{
    	event.preventDefault();
    	register (registerForm)
	});*/



}
function login(loginForm)
{

	json=formToJson(loginForm); 
   console.log("loginJson:"+json);
   parsedJson = JSON.parse(json);
   userName=parsedJson['userName'];
   password=parsedJson['password'];
   post("/api/user/login","",loginSuccess,loginFail);
   
}

function loginSuccess(role1)
{
	console.log("Login success. Role user "+role1);
	role=role1;
	setAuthorizationCookies(userName,password,role1);
	openApp(role)

}

function loginFail(jsonResponse)
{
	userName="";
	password="";
	role="";
	console.log("Login fail");
	alert("Login fail:"+ jsonResponse)
}

/*
function register(registerForm)
{
	json=formToJson(registerForm); 
   console.log("registerForm:"+json);
   postWithoutAuthorization("/api/register",json,registerSuccess,handleRequestFail);
}

function registerSuccess(jsonResponse)
{
		alert("Register Success. Please login")
		console.log("Register success:"+jsonResponse)
		openTab('Login')
}
*/
