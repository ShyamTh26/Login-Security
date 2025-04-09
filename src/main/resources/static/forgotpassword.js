function handleuserpassworddetails(event)
{
	event.preventDefault();
	let email = document.getElementById("email").value
	let passwd = document.getElementById("newpsd").value
	let confirmpass = document.getElementById("confirmpwd").value
	
	if(passwd == confirmpass)
	{
		fetch('/forgotpasswordincontroller', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({ email: email, password: passwd }),
	    })
	    .then(response => response.json())
	    .then(data => {
			window.location.href = "/login";
	    })
	    .catch(error => console.error('Error:', error));	
	}
	else
	{
		alert("try again, password not matched")
	}
}