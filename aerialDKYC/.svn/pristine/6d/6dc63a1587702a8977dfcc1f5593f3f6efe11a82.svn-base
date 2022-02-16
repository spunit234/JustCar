package com.edios.cdf.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edios.cdf.entity.to.UserDetailTO;
import com.edios.cdf.manager.SecurityManager;
import com.edios.cdf.util.WebAppied;

@Service
public class AccountUserDetailsService implements UserDetailsService {

	@Autowired
	MessageSource messageSource;

	@Autowired
	private SecurityManager securityManager;

	public UserDetails fetchUserByUsername(String username, UsernamePasswordAuthenticationToken authenticationToken)
			throws AuthenticationException {
		String password = authenticationToken.getCredentials().toString();
		UserDetailTO userDetailTO = securityManager.fetchUserDetails(username, authenticationToken);
		if (userDetailTO != null) {
			/*
			 * WebLogging logging =null; try { logging= new WebLogging("AL",username,
			 * "app/AppConfiguration"); logging.
			 * logDebugInfo("*****Inside fetchUserByUsername() of AccountUserDetailsService to authenticate user*****"
			 * , "INFO"); } catch (Exception e) {}
			 */
			if (userDetailTO.getRecordType().equals('D')) {
				throw new BadCredentialsException(
						messageSource.getMessage("AppUserDeleted", null, "AppUserDeleted", Locale.US));
			}
			if (userDetailTO.getUserStatus().equalsIgnoreCase("Locked")) {
				throw new LockedException(messageSource.getMessage("AppUserLocked", null, "AppUserLocked", Locale.US));
			}
			if (userDetailTO.getUserStatus().equalsIgnoreCase("Inactive")) {
				throw new BadCredentialsException(
						messageSource.getMessage("AppUserInActive", null, "AppUserInActive", Locale.US));
			}
			if (!userDetailTO.getUserType().equalsIgnoreCase("System")) {
				throw new BadCredentialsException(
						messageSource.getMessage("NonSystemAppUser", null, "NonSystemAppUser", Locale.US));
			}
			WebAppied appied = new WebAppied();

			if (appied.decrypt(userDetailTO.getPassword()).equals(password)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				Date licenseStartDate = null;
				Date licenseExpiryDate = null;
				String licStDate, licExpDate;
				try {
					licStDate = appied.decrypt(userDetailTO.getLicenseStartDate());
					if (licStDate != null && !licStDate.isEmpty())
						licenseStartDate = dateFormat.parse(licStDate);
					licExpDate = appied.decrypt(userDetailTO.getLicenseExpiryDate());
					if (licExpDate != null && !licExpDate.isEmpty())
						licenseExpiryDate = dateFormat.parse(licExpDate);
				} catch (java.text.ParseException e) {
					System.out.println("Licence Start Date end date Parsing Check");
				}

				if (licenseStartDate != null && licenseStartDate.after(userDetailTO.getCurrentDate())) {
					throw new BadCredentialsException(messageSource.getMessage("AppAccountLicenseNotActivated", null,
							"AppAccountLicenseNotActivated", Locale.US));
				}

				if (licenseStartDate != null && licenseExpiryDate != null
						&& (userDetailTO.getCurrentDate().before(licenseStartDate)
								|| licenseExpiryDate.after(userDetailTO.getCurrentDate()))) {
					throw new BadCredentialsException(messageSource.getMessage("AppAccountLicenseNotActivated", null,
							"AppAccountLicenseNotActivated", Locale.US));
				}
				if (userDetailTO.getLoginFailedTries() != 0) {
					securityManager.updateLoginFailedTries(userDetailTO.getUserID(), 0);
				}

			} else {
				userDetailTO.setMaxLoginTries(
						Integer.parseInt(messageSource.getMessage("maxLoginTries", null, "maxLoginTries", Locale.US)));
				if (userDetailTO.getLoginFailedTries() + 1 < userDetailTO.getMaxLoginTries()) {
					securityManager.updateLoginFailedTries(userDetailTO.getUserID(),
							userDetailTO.getLoginFailedTries() + 1);
					throw new BadCredentialsException(messageSource.getMessage("AppLoginNamePasswordIncorrect", null,
							"AppLoginNamePasswordIncorrect", Locale.US));
				} else {
					securityManager.updateLoginFailedTries(userDetailTO.getUserID(),
							userDetailTO.getLoginFailedTries() + 1);
					securityManager.updateUserStatusLocked(userDetailTO.getUserID(), "Locked");
					throw new LockedException(
							messageSource.getMessage("AppUserLocked", null, "AppUserLocked", Locale.US));
				}
			}
		} else {
			String userNotExistID = "User_Not_Found";
			/*
			 * try { WebLogging logging = new WebLogging("AL",userNotExistID,
			 * "app/AppConfiguration");
			 * logging.logDebugInfo("*****User name '"+username+"' does not exist*****",
			 * "INFO"); } catch (Exception e) {}
			 */
			throw new BadCredentialsException(
					messageSource.getMessage("AppLoginNameIncorrect", null, "AppLoginNameIncorrect", Locale.US));
		}
		User userDetails = new User(userDetailTO.getLoginName(), userDetailTO.getPassword(), getAuthorities(1));
		// User userDetails = new User(userDetailTO.getLoginName(),
		// userDetailTO.getPassword(), getAuthorities(1));
		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailTO userDetailTO = securityManager.fetchUserDetails(username);
		if (userDetailTO == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		User userDetails = new User(userDetailTO.getLoginName(), userDetailTO.getPassword(), getAuthorities(1));
		return userDetails;
	}

	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (access.compareTo(1) == 0) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authList;
	}

}
