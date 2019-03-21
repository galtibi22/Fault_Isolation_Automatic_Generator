var posts;
document.addEventListener('DOMContentLoaded', function() {
init()
}, false);

function init()
{
	var imported = document.createElement('script');
	imported.src = 'common.js';
	document.head.appendChild(imported);
	var authorization=readAuthorizationCookies();
	if (!authorization) 
	{
		window.location="/";
	}
  document.getElementsByClassName("helloUserName")[0].innerHTML='Hello ' + userName.charAt(0).toUpperCase()+userName.slice(1);
	refreshWall();
 
}
function updateUserList()
{
 	get("api/users",updateUsersListRequestSuccess,updateUsersListRequestFail)
}



function updateUsersListRequestSuccess(jsonResponse)
{	var userList=document.getElementById("userList");
	userList.innerHTML ='';
	usersJson=JSON.parse(jsonResponse);
 	var users = usersJson.users;
	users.forEach(function(item){
   var option = document.createElement('option');
   option.value = item;
   userList.appendChild(option);
});
}

function updateUsersListRequestFail(jsonResponse)
{
	console.log("updateUsersListRequest fail:"+Json.prase(jsonResponse));
}

function addFriend()
{
	var username=document.getElementById('addFriend').value;
	document.getElementById('addFriend').value='';
	post("/api/friend/"+username,null,addFriendSuccess)
}

function addFriendSuccess(jsonResponse)
{
		alert(jsonResponse);
		refreshWall();
}

function refreshWall()
{
	console.log("refreshWall function called")
	getPosts("wall");
}

function uploadNewPost(newPostForm)
{
		console.log("called uploadNewPost function")
}


function openProfile()
{
	console.log("called openProfile function")
	getPosts("profile")
}

function getPosts(type)
{
if(type == "wall")
	get("/api/post/",getWallPostsRequestSuccess);
else{
		get("/api/post/",getProfilePostsRequestSuccess);
	}
}

function getWallPostsRequestSuccess(jsonPosts){
	drawPosts(jsonPosts,"wall")
}

function getProfilePostsRequestSuccess(jsonPosts){
	drawPosts(jsonPosts,"profile")
}
//type profile or wall 
function drawPosts(jsonPosts,type)
{
		posts=JSON.parse(jsonPosts).posts;
		if (posts==null) 
			posts=JSON.parse(jsonPosts)
		posts.sort(function(a, b){
  				return a.date < b.date;
		});
		var wall = document.createElement('div');
		wall.className="wall";
		if (type == "wall" )
		{
			wall.appendChild(buildNewPostDiv())
		}
		for (var i=0;i<posts.length;i++)
		{
			
			if (type == "wall" && posts[i].permission == "public" )
			{
					wall.appendChild(buildPostDiv(posts[i],type))
			}else if (type == "profile" && posts[i].userName == userName )
			{
				wall.appendChild(buildPostDiv(posts[i],type))
			}
	
		}

document.getElementById('board').innerHTML='';
document.getElementById('board').appendChild(wall);
}

function addWallListeners()
{
	var newPost = document.getElementsByClassName ('newPost')[0];
	newPost.addEventListener('submit', function(event)
	{
    	event.preventDefault();
    	uploadNewPost (newPost)
	});
}

function updatePostListener()
{
	
	console.log("updatePost called")
	id=this.id;
	var deleteValue=document.getElementById("delete"+id).checked;
	var perm=	document.getElementById("permission"+id);
	var permVal= perm.options[perm.selectedIndex].value;	
	if (deleteValue==true)
	{
	   postDeleteId=id;
		delete1("/api/post/"+id,successDeletePostServer);
	}else{
		postObj=findPost(id);
		if(permVal!=postObj.permission)
		{
			post("/api/post/"+id+"/"+permVal,null,successUpdatePostServer);
		}
	}
}

function uploadCommentListener()
{
	id=this.id
	var commentBody=document.getElementById('newComment'+id);
	var comment=new Object();
	comment.comment=commentBody.value;
    var jsonString= JSON.stringify(comment);
	updateViewNewComment(id,comment.comment);
	post('/api/post/'+id+'/comment',jsonString,uploadCommentSuccess);
}

function doLikeListener()
{
	id=this.id
	var numberLikes=document.getElementById('numberLikes'+id);
	likes=numberLikes.innerHTML;
	likes=parseInt(likes)+1;
	numberLikes.innerHTML=likes;
	post('/api/post/'+id+'/like',null,doLikeSuccess);
	
}

var postToSend;
function uploadNewPostListener()
{
	var postObj=new Object();
	var perm=document.getElementsByName('newPermission')[0];
	postObj.permission=perm.options[perm.selectedIndex].value;
	
	postObj.body=document.getElementsByName('newPostBody')[0].value;
	if (postObj.body== "Whats new?")
		popUp("You must insert text for create new post");
	var list =document.getElementsByName('imageUpload')[0];
 var file = list.files[0];
	postToSend=postObj;

if (file !=null)
	{
	  postObj.picId=document.getElementsByName('imageUpload')[0].value;
	  uploadFile('/api/pic/',file,uploadPost);
  }else
  {
    uploadPost(null);
	}	
 
}

function uploadPost(response)
{
  if (response != null)
  {
    id=JSON.parse(response).id;
  }else
  {
    id=null;
  }
	if (typeof id !== 'undefined' && id !=null)
  {
    postToSend.picId=id;
  }
	obj=JSON.stringify(postToSend);
	post('/api/post/',obj,uploadNewPostSuccess);
}

function uploadNewPostSuccess(jsonResponse)
{
  console.log("uploadNewPostSuccess called");
  postToUpdate=JSON.parse(jsonResponse);
 	if (postToSend.permission == "public")
    {
      updateViewNewPost(postToUpdate);
    }
  //clean newPost div
  document.getElementsByName('imageUpload')[0].value='';
  document.getElementsByName('newPostBody')[0].value='Whats new?';
  var perm=document.getElementsByName('newPermission')[0];
  perm.options[0].selected="selected";
}

function uploadCommentSuccess()
{
  console.log("uploadCommentSuccess called")
}

function doLikeSuccess(response)
{
		console.log("Success do like"+response);
		getPostsFromServer();
}

function uploadNewCommentSuccess(response)
{
	console.log("uploadNewCommentSuccess called"+response)
	getPostsFromServer();
}

function successUpdatePostServer()
{
	console.log("successUpdatePostServer called")
	popUp("The permission of the post updated")
}

var postDeleteId;

function successDeletePostServer()
{
	console.log("called successDeletePostServer");
	success=deletePost(postDeleteId);
	if (success)
	{
		drawPosts(JSON.stringify(posts),"profile")	
	}else{
		popUp("Cannot delete the post.\nThe page will refreshed");
		reload();
	}
	
}


function findPost(id)
{
	for (var i=0;i<posts.length;i++)
	{
		if (posts[i].id==id)
			return post;
	}
}

function deletePost(id)
{
	for (var i=0;i<posts.length;i++)
	{
		if (posts[i].id==id)
		{
			posts.splice(i, 1);
			return true;
		}
	}
	return false;
}

function updatePost(id,perm)
{
	for (var i=0;i<posts.length;i++)
	{
		if (posts[i].id==id)
		{
			posts[i].permission=perm;
			return true;
		}
			
	}
	return false;
}


function getPostsFromServer()
{
	get("/api/post/",getPostsFromServerSuccess);
	
}

function getPostsFromServerSuccess(response)
{
	posts=JSON.parse(response).posts;	
}

