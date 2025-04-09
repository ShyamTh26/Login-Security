function handlelogin(event)
{
	event.preventDefault();
	let uName = document.getElementById("username").value
	let passwd = document.getElementById("password").value
	
	fetch('/loginsubmit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: uName, password: passwd }),
    })
    .then(response => response.json())
    .then(data => {
    	//document.getElementById('logid').style.display='none';
    	//document.getElementById('formid').style.display='none';
    	//document.getElementById('responseMessage').style.color='red';
        //document.getElementById('responseMessage').innerText = data.message;
		window.location.href = "/verifyOtp";
    })
    .catch(error => console.error('Error:', error));	
}