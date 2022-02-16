package com.edios.cdf.manager.impl;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.dao.SecurityDao;
import com.edios.cdf.entity.security.RoleEntity;
import com.edios.cdf.entity.security.UserEntity;
import com.edios.cdf.entity.security.UserLoginDetailsEntity;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleRightsEntityTO;
import com.edios.cdf.entity.to.UserDetailTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.manager.SecurityManager;
import com.edios.cdf.util.PayloadBean;

@Service("securityManager")
public class SecurityManagerImpl extends AbstractManagerImpl<UserBean, UserEntity> implements SecurityManager {

	@Autowired
	SecurityDao securityDao;

	@Autowired
	MessageSource messageSource;

	@Override
	@Transactional("db1Tx")
	public UserDetailTO fetchUserDetails(String userName, UsernamePasswordAuthenticationToken authenticationToken)
			throws AuthenticationException {
		try {
			UserDetailTO userDetailTO = securityDao.fetchUserDetails(userName);
			return userDetailTO;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional("db1Tx")
	public UserDetailTO fetchUserDetails(String userName) {
		return securityDao.fetchUserDetails(userName);
	}

	@Override
	@Transactional("db1Tx")
	public void updateLoginFailedTries(Long userID, int loginTries) {
		securityDao.updateLoginFailedTries(userID, loginTries);

	}

	@Override
	@Transactional("db1Tx")
	public void updateUserStatusLocked(Long userID, String string) {
		securityDao.updateUserStatusLocked(userID, string);
	}

	@Override
	@Transactional("db1Tx")
	public List<MenuEntityTO> fetchAccountMenus(PayloadBean payloadBean) {
		try {
			List<MenuEntityTO> menuEntityTOList = securityDao.fetchAccountMenus(payloadBean.getId());
			List<MenuEntityTO> parentMenuList = securityDao.fetchRoleRightsMenus(payloadBean.getAccountId());

			for (MenuEntityTO parentMenu : parentMenuList) {
				for (MenuEntityTO childrenMenu : menuEntityTOList) {
					if (parentMenu.getMenuID().equals(childrenMenu.getParentMenuID())) {
						MenuEntityTO menuEntityTO = new MenuEntityTO();
						menuEntityTO.setMenuID(childrenMenu.getMenuID());
						menuEntityTO.setMenuDesc(childrenMenu.getMenuDesc());
						menuEntityTO.setPageUrl(childrenMenu.getPageUrl());
						menuEntityTO.setMenuIcon(childrenMenu.getMenuIcon());
						parentMenu.getItems().add(menuEntityTO);
					}
				}
			}
			// Filter List
			parentMenuList = parentMenuList.stream().filter(element -> {
				if (element.getItems().size() != 0) {
					return true;
				} else {
					return false;
				}
			}).collect(Collectors.toList());

			return parentMenuList;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}

	}

	@Override
	@Transactional("db1Tx")
	public UserDetailTO getCurrentUser(String userName) {
		System.out.println("test");
		return securityDao.getCurrentUser(userName);
	}

	@Override
	@Transactional("db1Tx")
	public List<AccountUserEntityTO> fetchAccounts(Long id) {
		return securityDao.fetchAccounts(id);
	}

	@Override
	@Transactional("db1Tx")
	public RoleRightsEntityTO fetchRoleRights(RoleRightsEntityTO roleRightsEntityTO) {
		Integer menuId = securityDao.getMenuId(roleRightsEntityTO);
		if (menuId != null) {
			roleRightsEntityTO.setMenuID(menuId);
			return securityDao.fetchRoleRights(roleRightsEntityTO);
		} else {
			return null;
		}
	}

	@Override
	@Transactional("db1Tx")
	public String updateDefaultThemeForUser(PayloadBean payloadBean) {
		// TODO Auto-generated method stub
		if (securityDao.updateDefaultThemeForUser(payloadBean)) {
			return "UPDATED";
		}
		return null;
	}

	@Override
	@Transactional("db1Tx")
	public UserEntityTO insertUserLoginDetails(PayloadBean payloadBean, String referer, String clientIpAddr,
			String clientOS, String browserName, String browserVersion, String deviceType, HttpServletRequest request) {
		UserEntityTO userEntityTO = new UserEntityTO();
		Long pk = 0L;
		UserLoginDetailsEntity userLoginDetailsEntity = new UserLoginDetailsEntity();
		UserEntity userEntity = new UserEntity();
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleID(payloadBean.getId());
		userEntity.setUserID(payloadBean.getUserId());
		userLoginDetailsEntity.setUser(userEntity);
		userLoginDetailsEntity.setRole(roleEntity);
		userLoginDetailsEntity.setLoginDateTime(new Date());
		userLoginDetailsEntity.setIpAddress(clientIpAddr);
		userLoginDetailsEntity.setBrowserName(browserName);
		userLoginDetailsEntity.setBrowserVersion(browserVersion);
		userLoginDetailsEntity.setOsName(clientOS);
		userLoginDetailsEntity.setHostName(referer);
		userLoginDetailsEntity.setDeviceType(deviceType);

		pk = securityDao.insertUserLoginDetails(userLoginDetailsEntity);

		if (pk > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionID", session.getId());
			session.setAttribute("userId", payloadBean.getUserId());
			session.setAttribute("userLoginId", pk);
			session.setAttribute("sessionTimeoutMin", ((payloadBean.getSessionTimeoutMin().longValue()) * 60) + 120);

			if (payloadBean.getSessionTimeoutMin().longValue() == 999) {
				session.setMaxInactiveInterval(-1);
			} else {
				session.setMaxInactiveInterval(((int) (payloadBean.getSessionTimeoutMin().longValue()) * 60) + 120);
				// System.out.println(session.getMaxInactiveInterval() -
				// ((System.currentTimeMillis() - session.getLastAccessedTime())/1000));
			}

			securityDao.updateUserSessionId(payloadBean.getUserId(), session.getId());
			userEntityTO.setUserLoginId(pk);
			userEntityTO.setSessionID(session.getId());
		}

		return userEntityTO;
	}

	@Override
	@Transactional("db1Tx")
	public String updateUserLoginDetails(PayloadBean payloadBean) {
		String status = "";
		Long pk = securityDao.updateUserLoginDetails(payloadBean);
		if (pk > 0) {
			status = "UPDATED";
			securityDao.updateSessionToNull(payloadBean);
		} else {
			status = "ERROR";
		}
		return status;
	}

	@Override
	@Transactional("db1Tx")
	public String resetUserPassword(UserEntityTO userEntityTO, String ipAddress) {
		String status = "";
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, (int) userEntityTO.getPasswordExpiryDays().longValue());
		userEntityTO.setPasswordExpiryDate(c.getTime());

		Long pk = securityDao.resetUserPassword(userEntityTO, ipAddress);
		if (pk > 0) {
			status = "UPDATED";
		} else {
			status = "ERROR";
		}
		return status;
	}

	@Override
	@Transactional("db1Tx")
	public String sendPasswordMail(UserEntityTO userEntityTO, String ipAddress) throws MessagingException {
		// TODO Auto-generated method stub
		String resultString = "";
		UserEntityTO localUserEntityTO = securityDao.validateUsernameEmailAddress(userEntityTO);
		if (localUserEntityTO == null || localUserEntityTO.getUserID() == null) {
			resultString = "InvalidCredentials";
		} else {
			String password = generateRandomPassword(10);
			localUserEntityTO.setEmailPassword(password);
			if (securityDao.updatEmailPassword(localUserEntityTO, ipAddress) > 0) {
				if (sendEmail(password, userEntityTO)) {
					resultString = "MailSent";
					
				} else
					resultString = "MailNotSent";
			}
		}
		return resultString;
	}

	public boolean sendEmail(String password, UserEntityTO objUserEntityTO) throws MessagingException {

		boolean debug = false;
		Authenticator objAuthentication = null;
		Message mailMessage = null;
		Session objSession = null;
		InternetAddress addressFrom = null;
		InternetAddress[] addressTo = null;
		/*
		 * InternetAddress[] addressCC = null; InternetAddress[] addressBCC = null;
		 */
		Properties objProperties = null;

		// try {

		objProperties = System.getProperties();
		// System.out.println("_________" + messageSource.getMessage("SMTPHostName",
		// null, "", Locale.US));
		objProperties.put("mail.smtp.host", messageSource.getMessage("SMTPHostName", null, "", Locale.US));
		objProperties.put("mail.smtp.port", messageSource.getMessage("SMTPPortNo", null, "", Locale.US));
		objProperties.put("mail.smtp.starttls.enable", "true");
		objProperties.put("mail.smtp.auth", "true");
		/*
		 * objProperties.put("mail.smtp.host", gSMTPHostName);
		 * objProperties.put("mail.smtp.port", gSMTPPortNo); if
		 * (gSMTPProtocol.equalsIgnoreCase("smtp")) { if
		 * (!gSMTPUserName.equalsIgnoreCase("")) { objProperties.put("mail.smtp.auth",
		 * "true"); objProperties.put("mail.smtp.socketFactory.port", gSMTPPortNo);
		 * objProperties.put("mail.smtp.socketFactory.class",
		 * "javax.net.ssl.SSLSocketFactory"); } else {
		 * objProperties.put("mail.smtp.auth", "false"); } } else { if
		 * (!gSMTPUserName.equalsIgnoreCase("")) { objProperties.put("mail.smtp.auth",
		 * "true"); } else { objProperties.put("mail.smtp.auth", "false"); } }
		 */
		objAuthentication = new SMTPAuthenticator();
		objSession = Session.getInstance(objProperties, objAuthentication);
		objSession.setDebug(debug);
		mailMessage = new MimeMessage(objSession);
		addressFrom = new InternetAddress(messageSource.getMessage("SMPTUsername", null, "", Locale.US));
		mailMessage.setFrom(addressFrom);
		addressTo = new InternetAddress[1];
		// for (int addressToCounter = 0; addressToCounter < emailRecipients.length;
		// addressToCounter++) {
		addressTo[0] = new InternetAddress(objUserEntityTO.getEmailAddress());
		// }
		mailMessage.setRecipients(Message.RecipientType.TO, addressTo);
		/*
		 * if (CCAddress != null && !CCAddress[0].equals("")) { addressCC = new
		 * InternetAddress[CCAddress.length]; for (int addressCCcounter = 0;
		 * addressCCcounter < CCAddress.length; addressCCcounter++) {
		 * addressCC[addressCCcounter] = new
		 * InternetAddress(CCAddress[addressCCcounter]); }
		 * mailMessage.setRecipients(Message.RecipientType.CC, addressCC); } if
		 * (BCCAddress != null && !BCCAddress[0].equals("")) { addressBCC = new
		 * InternetAddress[BCCAddress.length]; for (int addressBCCcounter = 0;
		 * addressBCCcounter < BCCAddress.length; addressBCCcounter++) {
		 * addressBCC[addressBCCcounter] = new
		 * InternetAddress(BCCAddress[addressBCCcounter]); }
		 * mailMessage.setRecipients(Message.RecipientType.BCC, addressBCC); }
		 */
		mailMessage.setSubject("Reset Password");// mailMessage.setContent(message, "text/html");
		mailMessage.setSentDate(Calendar.getInstance().getTime());
		// mailMessage.setContent(emailMessage, "text/html");
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent("Dear Recipient,<br><br>We have received your request for reset password.<br>Please use below temporary password to login & reset the password."
				+ "<br>Password: <strong>"+password+"</strong><br><br>Regards<br>"
				+ "HMC Team<br><br>Note: This is a system generated e-mail, please do not reply to it.", "text/html;charset=UTF-8");
		// messageBodyPart.setText(emailMessage);
		// MimeBodyPart attachmentBodyPart = new MimeBodyPart();

		// attachmentBodyPart.attachFile(file);
		// System.out.println("The file name is =" + attachmentBodyPart.getFileName());

		Multipart multipart = new MimeMultipart();
		// multipart.addBodyPart(attachmentBodyPart);
		multipart.addBodyPart(messageBodyPart);
		mailMessage.setContent(multipart, "text/html;charset=UTF-8");
		Transport.send(mailMessage);
		return true;
		// } catch (Exception errorException) {
		// return false;
		// }

	}

	public String generateRandomPassword(int length) {

		final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
		final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		final String NUMBER = "0123456789";
		final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

		final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
		// optional, make it more random
		final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
		final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
			char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);

			// debug
			// System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

			sb.append(rndChar);

		}

		return sb.toString();

	}

	public String shuffleString(String string) {
		List<String> letters = Arrays.asList(string.split(""));
		Collections.shuffle(letters);
		return letters.stream().collect(Collectors.joining());
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = messageSource.getMessage("SMPTUsername", null, "", Locale.US);
			String password = messageSource.getMessage("SMTPPassword", null, "", Locale.US);
			return new PasswordAuthentication(username, password);
		}
	}

	@Override
	@Transactional("db1Tx")
	public String updateForgotPassword(UserEntityTO userEntityTO, String ipAddress) {
		// TODO Auto-generated method stub
		if(securityDao.updateForgotPassword(userEntityTO, ipAddress)>0) {
			return "UPDATED";
		}
		return null;
	}
	
	
	@Override
	@Transactional("db1Tx")
	public UserDetailTO getSiteName(Long id){
		try {
			UserDetailTO userDetailTO = securityDao.getSiteName(id);
			return userDetailTO;
		} catch (Exception e) {
			return null;
		}
		
	}
}
