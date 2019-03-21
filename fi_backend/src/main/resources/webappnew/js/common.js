//genralVars
var userName;
var password;
var role;
//finals
var AUTHORIZATION_COOKIES_TIMEOUT_MIN=30



function formToJson(form)
{
	 var formData = new FormData(form),
        result = {};

    for (var entry of formData.entries())
    {
        result[entry[0]] = entry[1];
    }
    result = JSON.stringify(result)
    return result;
}

///httpClient
function postWithoutAuthorization(url,data,methodSuccess,methodFail)
{
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    		handleResponse(this,methodSuccess,methodFail)
    }
  xhttp.open("POST", url, true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send(data);
}

function post(url,data,methodSuccess,methodFail)
{
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    	handleResponse(this,methodSuccess,methodFail)
    }
  xhttp.open("POST", url, true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send(data);
}

function get(url,methodSuccess,methodFail)
{
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
  		handleResponse(this,methodSuccess,methodFail)
    }
  xhttp.open("GET", url, true);
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send();
}

function synchronousGet(url)
{
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", url, false);
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send();
  return xhttp.responseText;
}

function update(url,data,methodSuccess,methodFail)
{
  var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
   	handleResponse(this,methodSuccess,methodFail)
  }
  xhttp.open("UPDATE", url, true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send(data);
}

function delete1(url,methodSuccess,methodFail)
{
  var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
   	handleResponse(this,methodSuccess,methodFail)
   }
  xhttp.open("DELETE", url, true);
  xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
  xhttp.send();

}

function uploadFile(url,file,method)
{
    var xhttp = new XMLHttpRequest();
    xhttp.addEventListener('progress', function(e) {
		var done = e.position || e.loaded, total = e.totalSize || e.total;
        console.log('xhttp progress: ' + (Math.floor(done/total*1000)/10) + '%');
		}, false);
    if ( xhttp.upload ) {
		xhttp.upload.onprogress = function(e) {
        var done = e.position || e.loaded, total = e.totalSize || e.total;
			console.log('xhttp.upload progress: ' + done + ' / ' + total + ' = ' + (Math.floor(done/total*1000)/10) + '%');
			};
        }
     xhttp.onreadystatechange = function(e) {
		if ( 4 == this.readyState ) {
		console.log(['xhttp upload complete', e]);
     method(e.target.response);
		}
    };
    xhttp.open('post', url, true);
    var formData = new FormData();
	formData.append("imageUpload", file);
	console.log(xhttp);
	xhttp.setRequestHeader("Authorization", "Basic " + btoa(userName+":"+password));
	xhttp.send(formData);
}

function handleResponse(response,methodSuccess,methodFail)
{
	if (response.readyState == 4 && response.status == 200){
		if (methodSuccess==null)
			methodSuccess=handleRequestSuccess;
		methodSuccess(response.responseText);
	}else if (response.readyState == 4){
		if (methodFail==null)
			methodFail=handleRequestFail;
			methodFail(response.responseText);
	}
}

function handleRequestFail(jsonResponse)
{
	alert(jsonResponse);
}
function handleRequestSuccess(jsonResponse)
{
	alert(jsonResponse);
}

//cookiesManager
//source https://www.w3schools.com/js/js_cookies.asp
function readAuthorizationCookies()
{
	role=getCookie('role');
	userName=getCookie('userName');
	password=getCookie('password');
	if (0 === userName.length || 0 === password.length || 0 === role.length)
		return false;
	else
		return true;

}

function setAuthorizationCookies(userName,password,role)
{
	setCookie('userName',userName,AUTHORIZATION_COOKIES_TIMEOUT_MIN);
	setCookie('password',password,AUTHORIZATION_COOKIES_TIMEOUT_MIN);
	setCookie('role',role,AUTHORIZATION_COOKIES_TIMEOUT_MIN);

}

function setCookie(cname, cvalue, exMin) {
    var d = new Date();
    d.setTime(d.getTime() + (exMin*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function logout()
{
	setCookie('userName',"",-100);
	setCookie('password',"",-100);
	setCookie('role',"",-100);
	userName='';
	password='';
	role='';
	window.location="/";
}

function openTab(tabName) {
    tab = document.getElementById("dashboard");
    tab.innerHtml=""
    tab.appendChild(newTab(tabName))
}

function popUp(message)
{
	alert(message);
}

