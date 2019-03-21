document.addEventListener('DOMContentLoaded', function() {
init()
}, false);

function init()
{

	openTab('Home')

	var imported = document.createElement('script');
	imported.src = 'common.js';
	document.head.appendChild(imported);
	var registerForm = document.getElementById('Register_Form');
	registerForm.addEventListener('submit', function(event)
	{
    	event.preventDefault();
    	register (registerForm)
	});

	var deleteForm = document.getElementById('Delete_Form');
    	deleteForm.addEventListener('submit', function(event)
    	{
        	event.preventDefault();
        	deleteUser (deleteForm)
    	});
       readAuthorizationCookies()
	  document.getElementsByClassName("helloUserName")[0].innerHTML='Hello ' + userName;

}

function register(registerForm)
{
	json=formToJson(registerForm); 
   console.log("registerForm:"+json);
   post("/api/user/register",json,registerSuccess,handleRequestFail);
}

function registerSuccess(jsonResponse)
{
		alert("Success to create user "+jsonResponse)
		console.log("Register success:"+jsonResponse)
}


function deleteUser(deleteForm){
    json=JSON.parse(formToJson(deleteForm));
       console.log("Go to delete user:"+json.userName);
          delete1(encodeURI("/api/user/delete/"+json.userName),deleteUserSuccess,handleRequestFail);


}

function deleteUserSuccess(generalResponse)
{
        console.log(generalResponse);
		alert(generalResponse)
}
init();