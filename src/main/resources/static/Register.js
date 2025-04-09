function CreateAccount()
{
	event.preventDefault();
	let passwd = document.getElementById("password").value
	let cpasswd = document.getElementById("cpassword").value
	let isValid = true
	if(passwd != cpasswd)
	{
		let passwordMatch = document.getElementById("pswid")
		passwordMatch.style.color = "red"
		passwordMatch.innerText = "Password not matched"
		isValid = false
	}
	
	if(passwd.length < 8)
	{
		let messageData = document.getElementById("msgid")
		messageData.style.color = "red"
		messageData.innerText = "Password length should be greater then 8"
		isValid = false
	}
	
	let email = document.getElementById("emailid").value
	
	if(!email.endsWith("@gmail.com"))
	{
		//alert("Email should end with @gmail.com")
		let message = document.getElementById("msg")
		message.style.color = "red"
		message.innerText = "Email should end with @gmail.com"
		isValid = false
	}
	
	if(isValid)
	{
		//alert("Details look good, you can proceed with Account Creation!!")
		let uName = document.getElementById("username").value
		
		fetch('/registerusers', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({ username: uName, password: passwd,email:email }),
	    })
	    .then(response => response.json())
	    .then(data => {
			//window.location.href = "/login";
			
			document.getElementById('createac').style.display='none';
			document.getElementById('qrcode').src = "data:image/png;base64,"+data.qrCodeUrl;
			document.getElementById('qrcode').style.display="block";
			
	    })
	    .catch(error => console.error('Error:', error));
	}
}