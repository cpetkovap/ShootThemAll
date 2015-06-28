<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	
	function checkEmptyRegistration() {
		if(document.getElementById("username").value == "" || document.getElementById("pass").value == "" || document.getElementById("email").value == ""){
			return false;
		}else{
			return true;
		}
	}
	
	function sendRegistration() {
		if(checkEmptyRegistration() ){
			var username = document.getElementById("username").value;
			var password = document.getElementById("pass").value;
			var email = document.getElementById("email").value;
			$.post("registration", JSON.stringify({username : username, password : password, email : email}), function(data) {
					alert(data);
			});
		}else{
			alert("Empty fields")
		}
	}
	
	function checkEmprtyLogin() {
		if(document.getElementById("usernameLogin").value == "" || document.getElementById("passLogin").value == "" ){
			alert("Empty fields")
			return false;
		
		}else{
			return true;
		}
		
	}

	function sendLogin() {
		if(checkEmprtyLogin()){
			var username = document.getElementById("usernameLogin").value;
			var password = document.getElementById("passLogin").value;
			$.post("login", JSON.stringify({username: username, password: password}),function(data){				
					alert(data);
			});
		}
	}
</script>
</head>
<body>
<h3>Registration</h3>
Name <input type = "text" name = "username" id = "username"/> 
Pass <input type = "password" id = "pass" name  = "pass" />
Email <input type = "text" id = "email" name  = "email" />
<button onclick="sendRegistration()">Register</button>
<hr/>

<h3>Login</h3>
Name <input type = "text" name = "usernameLogin" id = "usernameLogin"/> 
Pass <input type = "password" id = "passLogin" name  = "passLogin" />
<button onclick="sendLogin()">Login</button>
<hr/>

</body>
</html>