function newTabButton(name){
    	var tabButton=document.createElement('span');
        tabButton.onclick = function() {
            openTab(name)
        }
        tabButton.name=name
        tabButton.innerHTML=sufUperCase(name);
        return tabButton;
}

function newLogoutButton(){
    var tabButton=document.createElement('span');
    tabButton.onclick = function() {
                logout()
    }
    tabButton.name=name="logout"
    tabButton.className="logout"
    return tabButton;
}

function newAboutButton(){
    var tabButton=document.createElement('span');
    tabButton.className="about"
    tabButton.name="about"
    tabButton.onclick = function() {
        openTab("about")
    }
    return tabButton;
}


function newTab(tabName){
    if (tabName=="login"){
        return newLoginTab();
    }
}

function newLoginTab(){

   loginForm=document.createElement('form')
   loginForm.appendChild(newInput('userName'),'text',true)
   loginForm.appendChild(newBr())
   loginForm.appendChild(newInput('password','password'),true)
   loginForm.appendChild(newBr())
   loginForm.appendChild(newSubmit("login"))
    return loginForm
}

function newInput(name,type,required){
    p=document.createElement('p')
    input=document.createElement('input')
    input.name=name;
    input.className=name
    input.id=name
    input.type=type
    input.required = required
    p.appendChild(inputLabel(name))
    p.appendChild(input)
    return p
}

function sufUperCase(name){
    return name.charAt(0).toUpperCase()+name.slice(1)
}

function inputLabel(name){
        label=document.createElement('label');
        label.innerHTML=sufUperCase(name)
        label.className="inputLabel"
        id="inputLabel"+name
        label.for=name
        return label
}

function newSubmit(name){
   input=document.createElement('input')
   input.type="submit"
   input.value=sufUperCase(name)
   return input;
   }
 function newBr(){
    return document.createElement('br');
 }