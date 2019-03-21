//document.addEventListener('DOMContentLoaded', function() {
//init()
//}, false);
init(loadPage);

function init(callback){
   var imported = document.createElement('script');
           imported.src = 'js/elementBuilder.js';
           document.head.appendChild(imported);
     var imported = document.createElement('script');
          	imported.src = 'js/common.js';
          	imported.onreadystatechange = callback;
            imported.onload = callback;
          	document.head.appendChild(imported);
}


function loadPage(){
  var authorization=readAuthorizationCookies();
	if (authorization)
	{
	    loadApp()
	}else
	{
	    loadLogin()
	}

}

function loadLogin(){
    loadBar("login")
    openTab("login")
}

function loadApp(){
    loadBar(role)
    openTab(role+"home")

}


function loadBar(name){
    bar=document.getElementById("bar")

    if (name=="login"){
        bar.appendChild(newTabButton(name))
        bar.appendChild(newAboutButton())
    }
    if (name=="admin"){
        bar.appendChild(newTabButton("adminHome"))
        bar.appendChild(newLogoutButton())
    }
     if (name=="user"){
            bar.appendChild(newTabButton("userHome"))
            bar.appendChild(newLogoutButton())
        }
}



