package utilidades;

	import jakarta.mail.*;
	import jakarta.mail.internet.InternetAddress;
	import jakarta.mail.internet.MimeMessage;
	import java.util.Properties;
	
public class EmailUtilidad {

	    private static final String REMITENTE = "fuunnewsandtrends@gmail.com";
	    private static final String CONTRASENA = "tdpvfnntegbcyczh";
	    
	    //Metodo que envia el correo al destinatario
	    public void enviarCorreo(String correoDestinatario, String asunto, String mensaje) throws MessagingException {
	        Session session = configurarServidorSMTP();
	        MimeMessage mimeMessage = new MimeMessage(session);
	        
	        mimeMessage.setFrom(new InternetAddress(REMITENTE));
	        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
	        mimeMessage.setSubject(asunto);
	        mimeMessage.setText(mensaje, "utf-8");
	        
	        Transport.send(mimeMessage);
	        System.out.println("[Correo enviado a " + correoDestinatario + "]");
	    }

	    private Session configurarServidorSMTP() {
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.starttls.required", "true");

	        return Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(REMITENTE, CONTRASENA);
	            }
	        });
	    }
	    public void crearContrasena () {
	    	
	    }
	}
