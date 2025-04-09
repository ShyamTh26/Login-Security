function handleforgotun(event)
{
	event.preventDefault();
	let email = document.getElementById("email").value
	
	//if(passwd == confirmpass)
	//{
		fetch('/forgotusernameincontroller', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({ email: email }),
	    })
	    .then(response => response.json())
	    .then(data => {
			document.getElementById('formid').style.display='none';
			document.getElementById('responseMessage').style.color='red';
			document.getElementById('responseMessage').innerText = data.message;
	    })
	    .catch(error => console.error('Error:', error));	
	//}
	//else
	//{
		//alert("try again, password not matched")
	//}
}