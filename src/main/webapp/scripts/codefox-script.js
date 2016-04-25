/*
 * JQuery for Code fox
 * 
 */

$(document).ready(function() {
	//Content pages to be displayed
	var content = $('.content');
	var homeContent = $(".main-content");
	var about = $(".aboutus-content");
	var warning = $('.signinwarning-page');
	var loginOptions = $('.login-options');
	var contactUsContent = $('.contactus-content');
	var createBlog = $('.create-blog');
	var allBlogs = $('.view-blogs');
	var searchBlog = $('.search-blog');
	
	var loggedIn = false;
	
	var logout = function() {
		$.ajax('blog/techspace/user/logout', {
			success: function(response) {
				$('.header-list #login-button').text("Login");
				username='';
				password='';
				loggedIn = false;
				userId = -1;
				hideAllAndDisplay(loginOptions);
				$('#login-button').unbind();
				$('#login-button').on('click', function() {
					hideAllAndDisplay(loginOptions);
				});
			},
			error: function() {
				
			}
		});
	};
	
	$.ajax('blog/techspace/user/login', {
		success: function(response) {
			$('.header-list #login-button').text("Logout");
			loggedIn = true;
			$('#login-button').unbind();
			$('#login-button').on('click', function() {
				logout();
			});
			console.log(response);
			userId = response.userId;
			username = response.emailId;
			password = response.password;
			console.log("success")
		},
		error: function(request, errorType, errorMessage) {
			console.log(errorType);
			console.log(errorMessage);
			$('.header-list #login-button').text("Login");
			loggedIn = false;
			$('#login-button').unbind();
			$('#login-button').on('click', function() {
				hideAllAndDisplay(loginOptions);
			});
		}
	});
	
	var viewAllBlogs = function() {
		$("#view-blog-table").find("tr:gt(0)").remove();
		$.get("blog/techspace/question", function(data) {
			console.log(data);
			var rowTemplate = $("#templates table").html();
			var tableIndex = 1;
			console.log(rowTemplate);
			for(index in data) {
				var row = rowTemplate.replace("id", tableIndex++).replace("title", "<em>Title</em> : "+data[index].title+"<br><em>Description</em> : "+data[index].description).replace("answers", 0);
				$("#view-blog-table").append(row);
			}
		});
	};
	
	
	var hideAllAndDisplay = function (toDisplay) {
		console.log(toDisplay);
		hideAllContents();
		toDisplay.fadeIn(1000);
	}
	
	// Clicked buttons
	$('#home-button').on('click', function() {
		hideAllAndDisplay(homeContent);
	});
	
	$('#aboutus-button').on('click', function() {
		hideAllAndDisplay(about);
	});
	
	$('#contactus-button').on('click', function() {
		hideAllAndDisplay(contactUsContent);
	});
	
	$('#create-blog-button').on('click', function() {
		
		$.ajax('blog/techspace/user/login', {
			success: function(response) {
				hideAllAndDisplay(createBlog);
			},
			error: function(request, errorType, errorMessage) {
				console.log(errorType);
				console.log(errorMessage);
				console.log(request);
				if (request.status == 401) {
					hideAllAndDisplay(warning);
				}
			}
		});
		
	});
	
	$('#view-blog-button').on('click', function() {
		
		$.ajax('blog/techspace/user/login', {
			success: function(response) {
				hideAllAndDisplay(allBlogs);
				viewAllBlogs();
			},
			error: function(request, errorType, errorMessage) {
				console.log(errorType);
				console.log(errorMessage);
				console.log(request);
				if (request.status == 401) {
					hideAllAndDisplay(warning);
				}
			}
		});
		
	});
	
	$('#search-blog-button').on('click', function() {
		
		$.ajax('blog/techspace/user/login', {
			success: function(response) {
				hideAllAndDisplay(searchBlog);
			},
			error: function(request, errorType, errorMessage) {
				console.log(errorType);
				console.log(errorMessage);
				console.log(request);
				if (request.status == 401) {
					hideAllAndDisplay(warning);
				}
			}
		});
		
	});
	
	
	
	function hideAllContents() {
		homeContent.hide();
		about.hide();
		warning.hide();
		loginOptions.hide();
		contactUsContent.hide();
		createBlog.hide();
		allBlogs.hide();
		searchBlog.hide();
	}
	
	$('#register-button').click(function() {
		var user = {};
		user.firstName = $('#firstName').val();
		user.lastName = $('#lastName').val();
		user.email = $('#email').val();
		user.phoneNumber = $('#phone').val();
		user.password = $('#password').val();
		console.log(user);
		var conf = confirm(JSON.stringify(user));
		if (conf) {
			var userJson = JSON.stringify(user);
			
			$.ajax({
				url: 'blog/techspace/user',
				method: 'put',
				data: userJson,
				headers: { 'Accept': 'application/json', 'Content-type': 'application/json' }, 
				success: function(data) {
					console.log("Add done");
					alert("Registration succesgful... Login to access the portal");
					$('.login-form #id').focus();
					regFormReset();
				},
				error: function(request, errorType, errorMessage) {
					console.log(errorType);
					console.log(errorMessage);
					console.log(request);
					alert(errorMessage);
					/*if (request.status == 401) {
						hideAllAndDisplay(warning);
					}*/
				}
			});
		} else {
			regFormReset();
		}
	});
	
	$('.login-form #form-login-button').click(function() {
		username = $('.login-form #id').val();
		password = $('.login-form #pWord').val();
		loginUrl = "blog/techspace/user/login";
		
		console.log(username);
		console.log(password);
		login();
	});
	
	//define vars
	var username = '';
	var password = '';
	var url = '';
	var userId = -1;

	// ajax call
	var login = function() {
		$.ajax({
		    url: loginUrl,
		    dataType : 'json',
		    headers: { 'Accept': 'application/json', 'Content-type': 'application/json' },
		    beforeSend : function(xhr) {
		      // generate base 64 string from username + password
//		      var bytes = Crypto.charenc.Binary.stringToBytes(username + ":" + password);
//		      var base64 = Crypto.util.bytesToBase64(bytes);
		      // set header
		    	xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
		    },
		    success: function(data) {
		        // success handler
		    	console.log('success : ' + JSON.stringify(data));
		    	alert("Login Successful");
	    		loginFormReset();
	    		$('.header-list #login-button').text("Logout");
	    		$('#login-button').unbind();
				$('#login-button').on('click', function() {
					logout();
				});
	    		hideAllAndDisplay(homeContent);
	    		loggedIn = true;
		    	userId = data.userId;
		    	console.log(userId);
		    },
		    error : function(request, errorType, errorMessage, response) {
		    	console.log(response);
		      // error handler
		    	console.log("error");
		    	console.log(request.status);
		    	console.log(errorType);
		    	console.log(errorMessage);
		    	console.log(request);
		    	alert("Login Failure... Try again");
		    	loginFormReset();
		    	
		    }
		    
		});
	};


	var loginFormReset = function() {
		$('.login-form #id').val("");
		$('.login-form #pWord').val("");
	}

	var regFormReset = function() {
		$('#firstName').val("");
		$('#lastName').val("");
		$('#email').val("");
		$('#phone').val("");
		$('#password').val("");
	};
	
	$('.create-blog-form #description #submit-button').on('click', function() {
		var title = $('.create-blog-form #title').val();
		var description = $('.create-blog-form #des-text').val();
		var question = {};
		question.title = title;
		question.description = description;
		question.userId = userId;
		
		console.log(title);
		console.log(description);
		var questionJson = JSON.stringify(question);
		
		$.ajax({
			url: 'blog/techspace/question',
			method: 'put',
			data: questionJson,
			headers: { 'Accept': 'application/json', 'Content-type': 'application/json' }, 
			success: function(data) {
				console.log("Question added successfully");
				alert("Question added successfully");
				regQuestionFormReset();
			},
			error: function(request, errorType, errorMessage) {
				console.log(errorType);
				console.log(errorMessage);
				console.log(request);
				alert(errorMessage);
				/*if (request.status == 401) {
					hideAllAndDisplay(warning);
				}*/
			}
		});
		
	});
	
	var regQuestionFormReset = function() {
		$('.create-blog-form #title').val("");
		$('.create-blog-form #des-text').val("");
	};
	
	
	var searchBlogs = function(searchString) {
		$("#search-blog-table").find("tr:gt(0)").remove();
		$.get("blog/techspace/question/"+searchString, function(data) {
			console.log(data);
			var rowTemplate = $("#templates table").html();
			console.log(rowTemplate);
			var tableIndex = 1;
			for(index in data) {
				var row = rowTemplate.replace("id", tableIndex++).replace("title", "<em>Title</em> : "+data[index].title+"<br><em>Description</em> : "+data[index].description).replace("answers", 0);
				$("#search-blog-table").append(row);
			}
		});
	};
	
	$('#search-action #submit-button').on('click', function() {
		var searchString = $('#search-action #search').val();
		console.log(searchString);
		searchBlogs(searchString);
	});
	
});


