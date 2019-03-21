
function buildPostDiv(postJson,type)
{
	console.log("called buildPost function")
	console.log(postJson)
	var post=document.createElement('div');
	post.className="post";
	post.id="post"+postJson.id;
	
	//userName
	var userNameDiv=document.createElement('div');
	userNameDiv.className="userName";
	userNameDiv.innerHTML=postJson.userName;
	userNameDiv.id="userNameDiv"+postJson.userName;
	post.appendChild(userNameDiv);
	
	//body
	var body=document.createElement('div');
	body.className="body";
	body.innerHTML=postJson.body;
	post.appendChild(body);
	
	//pic
	if (postJson.picId!=null){
		var pic=document.createElement('img');
		pic.src="api/pic/"+postJson.picId;
		//pic.addEventListener("click", openPicLargeListener);
		pic.className="pic";
		pic.id=postJson.picId;
		var modal = document.getElementById('myModal');

	// Get the image and insert it inside the modal - use its "alt" text as a caption
		var modalImg = document.getElementById("img01");
		var captionText = document.getElementById("caption");
		pic.onclick = function(){
			modal.style.display = "block";
			modalImg.src = this.src;
			//document.getElementById('board').style.visibility = 'hidden'; 
			captionText.innerHTML = this.alt;
			}
	// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];
		// When the user clicks on <span> (x), close the modal
		span.onclick = function() { 
			modal.style.display = "none";
			}
		
		post.appendChild(pic);
		}
	
	//comments
	var comments=document.createElement('div');
	comments.className="comments";
	comments.id='comments'+postJson.id;
	for (var i=0;i<postJson.comments.length;i++)
	{
			var comment=buildCommentDiv( postJson.comments[i].userName,postJson.comments[i].comment,postJson.comments[i].picId);
			comments.appendChild(comment);
	}
	
	//newComment
	var newComment=document.createElement('div');
	newComment.name='newComment';
	var html=document.createElement('t');
	if (type == "wall"){
		newComment.className='newComment';
		var newCommentBody=document.createElement('textArea');
		newCommentBody.id='newComment'+postJson.id;
		newCommentBody.value='Write a comment';
		newCommentBody.addEventListener("focus", cleanText);
		newCommentBody.addEventListener("focusout", cleanText);
		newCommentBody.className='insertBody';
		newCommentBody.name='newCommentBody';
		newComment.appendChild(newCommentBody);
	
		var subbmit=document.createElement('button');
		subbmit.id=postJson.id;
		subbmit.addEventListener("click", uploadCommentListener)
		subbmit.className='commentUploadButton'
		subbmit.innerHTML='Upload';
		newComment.appendChild(subbmit);
		
		var numberLikes=document.createElement('div');
		numberLikes.className='numberLikes';
		if (postJson.likes==null)
			numberLikes.innerHTML=0;
		else
			numberLikes.innerHTML=postJson.likes;
		numberLikes.id='numberLikes'+postJson.id;
		newComment.appendChild(numberLikes);
		
		var like=document.createElement('div');
		like.addEventListener("click", doLikeListener)
		like.className='like';
		like.id=postJson.id;
		newComment.appendChild(like);
		
		comments.appendChild(newComment);
	}
	else{
		var updatePost=document.createElement('div');
		updatePost.className='updatePost';
		
		var numberLikes=document.createElement('div');
		numberLikes.className='numberLikes';
		numberLikes.innerHTML=postJson.likes;
		numberLikes.id='numberLikes'+postJson.id;
		updatePost.appendChild(numberLikes);
		
		var like=document.createElement('div');
		like.className='like';
		like.id=postJson.id;
		updatePost.appendChild(like);
		
		var delete1=document.createElement('div');
		delete1.className='delete';
		
		var deleteCheckBox=document.createElement('input');
		deleteCheckBox.name='delete';
		deleteCheckBox.type='checkbox'
		deleteCheckBox.id='delete'+postJson.id;
		delete1.appendChild(deleteCheckBox);
		delete1.appendChild(document.createTextNode("Delete"));
		updatePost.appendChild(delete1);

		var selectPerm=document.createElement('select');	
		selectPerm.className='permission';
		selectPerm.id='permission'+postJson.id;
		selectPerm.name='permission';
		
		var option1=document.createElement('option');	
		option1.className='permission';
		option1.id=postJson.id;
		option1.name='permission';
		option1.value='public';
		option1.innerHTML='public';

		var option2=document.createElement('option');	
		option2.className='permission';
		option2.id=postJson.id;
		option2.name='permission';
		option2.value='private';
		option2.innerHTML='private';	
		if (postJson.permission=="public")
			option1.selected='selected'
		else
			option2.selected='selected'
		
		selectPerm.appendChild(option1);
		selectPerm.appendChild(option2);
		updatePost.appendChild(selectPerm);

		var update=document.createElement('button');
		update.id=postJson.id;
		update.addEventListener("click", updatePostListener)
		update.className='upload'
		update.innerHTML='Update';
		updatePost.appendChild(update);
		

		comments.appendChild(updatePost);
	}


	post.appendChild(comments);
	return post;
}

function buildNewPostDiv()
{
	var newPost=document.createElement('div');
	newPost.className="newPost"
	
	var newPostBody=document.createElement('textArea');
	newPostBody.value='Whats new?';
	newPostBody.addEventListener("focus", cleanText);
	newPostBody.addEventListener("focusout", cleanText);
	newPostBody.className='insertBody';
	newPostBody.name='newPostBody';
	newPost.appendChild(newPostBody);
	
	var selectPerm=document.createElement('select');	
	selectPerm.className='newPermission';
	selectPerm.id='newPermission';
	selectPerm.name='newPermission';

	var option1=document.createElement('option');	
	option1.name='permission';
	option1.value='public';
	option1.innerHTML='public';
	selectPerm.appendChild(option1);

	var option2=document.createElement('option');	
	option2.name='permission';
	option2.value='private';
	option2.innerHTML='private';	
	selectPerm.appendChild(option2);
	newPost.appendChild(selectPerm);

	
	var imageUpload=document.createElement('input');
	imageUpload.className='imageUpload';
	imageUpload.type='file';
	imageUpload.name='imageUpload';
	newPost.appendChild(imageUpload);

	var update=document.createElement('button');
	update.addEventListener("click", uploadNewPostListener)
	update.className='upload'
	update.innerHTML='Upload';
	newPost.appendChild(update);
	return newPost;

}

function buildCommentDiv(userName,body,picId)
{
	var comment=document.createElement('div');
	comment.className="comment";
		
	var userNameDiv=document.createElement('div');
	userNameDiv.className="userName";
	userNameDiv.innerHTML=userName;
    comment.appendChild(userNameDiv);
		
	var commentBody=document.createElement('div');
	commentBody.className="commentBody";
	commentBody.innerHTML=body;
	comment.appendChild(commentBody);

	if (picId!=null){
		var pic=document.createElement('img');
		pic.src="api/pic/"+picId;
		pic.className="picComment";
		comment.appendChild(pic);
	}	
	
	return comment;
}

function updateViewNewPost(post)
{
	post.userName=userName;
	if (post.comments==null)
    post.comments=[];
	newPost=buildPostDiv(post,"wall");
	wall=document.getElementsByClassName('wall')[0];
	newPostDiv=wall.firstChild;
	wall.removeChild(wall.firstChild);
	wall.insertBefore(newPost, wall.firstChild);
	wall.insertBefore(newPostDiv, wall.firstChild);

}

function updateViewNewComment(id,body)
{
	var comments=document.getElementById('comments'+id)
	comment=buildCommentDiv(userName,body);
	var newComment=comments.lastElementChild;
	newComment.firstChild.value="Write a comment"
  comments.removeChild(newComment);
	comments.appendChild(comment);
	comments.appendChild(newComment);
}

//type profile or wall 
function drawPosts(jsonPosts,type)
{
	posts=JSON.parse(jsonPosts).posts;
	if (posts==null) 
		posts=JSON.parse(jsonPosts)
	posts.sort(function(a, b)
	{
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
