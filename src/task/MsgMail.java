package task;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MsgMail{

		static Logger logger = LogManager.getLogger(MsgMail.class);
    static String text = "";
    static String to = "";
    static String from = "";
    static String host = "localhost"; 
    static String subject = "Task Request";
    static boolean debug = false;
    static String cc = null;
		/**
		 * for test purpose
		 */
    public static void main(String[] args) {

				if (args.length >= 0 && args.length < 5) {
						System.err.println("Not enough argument");
						System.exit(1);
				}
				else {
						to = args[0];
						cc = args[1];
						from = args[2];
						subject = args[3];
						text = args[4];
						MsgMail mm = new MsgMail(to, cc, from, subject, text, debug); 
				}
    }
    /**
     * The main constructor.
     *
     * @param to2 to email address
     * @param from2 from email address
     * @param msg2 the message
     * @param cc2 the cc email address
     * @param debug2 the debug flag true|false
     */
    public MsgMail( String to2, String cc2, String from2, String subject2, String msg2, boolean deb){

				//
				to = to2;
				cc = cc2;
				from = from2;
				subject = subject2;
				text = msg2;
				debug = deb;
				//
		}
		String doSend(){
				String back = "";
				Properties props = props = new Properties();
				props.put("mail.smtp.host", host);
				if (debug) props.put("mail.debug", "true");
				Session session = Session.getDefaultInstance(props, null);
				session.setDebug(debug);
				try {
						// create a message
						Message msg = new MimeMessage(session);
						msg.setFrom(new InternetAddress(from));
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						if(cc != null){
								InternetAddress ia = new InternetAddress();
								InternetAddress[] address2 = ia.parse(cc);
								msg.setRecipients(Message.RecipientType.CC, address2);
						}
						msg.setSubject(subject);
						msg.setSentDate(new Date());

						// If the desired charset is known, you can use
						// setText(text, charset)
						msg.setText(text);
						//
						Transport.send(msg);
				} catch (MessagingException nex){

						logger.error(nex);
						back += nex;						
						Exception ex = nex;
						do {
								if (ex instanceof SendFailedException) {
										SendFailedException sfex = (SendFailedException)ex;
										Address[] invalid = sfex.getInvalidAddresses();
										if (invalid != null) {
												logger.error("    ** Invalid Addresses");
												if (invalid != null) {
														for (int i = 0; i < invalid.length; i++) 
																logger.error("         " + invalid[i]);
												}
										}
										Address[] validUnsent = sfex.getValidUnsentAddresses();
										if (validUnsent != null) {
												logger.error("    ** ValidUnsent Addresses");
												if (validUnsent != null) {
														for (int i = 0; i < validUnsent.length; i++) 
																logger.error("         "+validUnsent[i]);
												}
										}
										Address[] validSent = sfex.getValidSentAddresses();
										if (validSent != null) {
												logger.error("    ** ValidSent Addresses");
												if (validSent != null) {
														for (int i = 0; i < validSent.length; i++) 
																logger.error("         "+validSent[i]);
												}
										}
								}
								if (ex instanceof MessagingException)
										ex = ((MessagingException)ex).getNextException();
								else
										ex = null;
						} while (ex != null);
				}
				return back;
    }
		//
		// reduced
		// no cc
		//
    public MsgMail( String to2, String from2, String subject2, String msg2, boolean deb){
				this(to2, null, from2, subject, msg2, deb);
		}
		//
		// even reduced more
		// no cc and debug
		//
    public MsgMail( String to2, String from2, String subject2, String msg2){
				this(to2, null, from2, subject, msg2, debug);
		}		
}
