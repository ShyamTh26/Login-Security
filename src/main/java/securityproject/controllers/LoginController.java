package securityproject.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

@Controller
public class LoginController {
	
	@GetMapping("/forgotusername")
	public String ForgotUsername()
	{
		return "ForgotUN";
	}
	
	@GetMapping("/verifyOtp")
	public String GetOtpPage()
	{
		return "VerifyOtpPage";
	}
	
	@GetMapping("/forgotpassword")
	public String ForgotPassword()
	{
		return "ForgotPwd";
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@PostMapping("/loginsubmit")
	@ResponseBody
	public Map<String,String> OnLogin(@RequestBody Map<String,String> logindata)
	{
		Map<String,String> response = new HashMap<>();
		String msg = "";
		
		String sql = "Select count(*) from users where username= ? AND password= ?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
				logindata.get("username"), logindata.get("password"));
		
		if(count > 0 && count != null)
		{
			msg = "Sucessfully Login";
		}
		else
		{
			msg = "Incorrect username and password";
		}
		
		response.put("message", msg);
		
		return response;
	}
	
	
	@PostMapping("/registerusers")
	@ResponseBody
	public Map<String,String> OnRegister(@RequestBody Map<String,String> userdata)
	{
		Map<String,String> response = new HashMap<>();
		String msg = "";
		
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		GoogleAuthenticatorKey key = gAuth.createCredentials();
		
		String secretkey = key.getKey();
		
		String sql = "insert into users(username, password, emailid,secretkey) values (? ,? ,?,?)";
		jdbcTemplate.update(sql,
				userdata.get("username"), userdata.get("password"), userdata.get("email"),secretkey);
		
		// Generate QR Code URL
		
		String url = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("", userdata.get("username"), key);
		String base64Image="";
		try
		{
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			var bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(qrImage, "png",baos);
			base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
		}
		catch(WriterException | IOException e)
		{
			e.printStackTrace();
		}
		
		msg = "Account created Sucessfully";
		
		
		response.put("message", msg);
		response.put("qrCodeUrl", base64Image);
		
		return response;
	}
	
	
	@PostMapping("/forgotpasswordincontroller")
	@ResponseBody
	public Map<String,String> OnForgotPassword(@RequestBody Map<String,String> forgotpsddata)
	{
		Map<String,String> response = new HashMap<>();
		String msg = "";
		
		String sql = "update users set password = ? where emailid = ?";
		jdbcTemplate.update(sql,
				forgotpsddata.get("password"), forgotpsddata.get("email"));
		
		msg = "Password change successfully";
		
		response.put("message", msg);
		
		return response;
	}
	
	
	@PostMapping("/forgotusernameincontroller")
	@ResponseBody
	public Map<String,String> OnForgotUsername(@RequestBody Map<String,String> forgotunddata)
	{
		Map<String,String> response = new HashMap<>();
		String msg = "";
		
		String sql = "select username from users where emailid = ?";
		String username = jdbcTemplate.queryForObject(sql, String.class,
				forgotunddata.get("email"));
		
		msg = "Your username is   "   + username;
		
		response.put("message", msg);
		
		return response;
	}
	
	
}
