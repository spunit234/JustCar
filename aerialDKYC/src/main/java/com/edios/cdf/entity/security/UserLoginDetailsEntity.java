package com.edios.cdf.entity.security;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.edios.cdf.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_login_details")
@Setter
@Getter
@NoArgsConstructor
public class UserLoginDetailsEntity extends AbstractEntity {
	 
	private static final long serialVersionUID = 8975386519985416321L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_LOGIN_ID")
	private Long userLoginId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private RoleEntity role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
	@Column(name = "LOGIN_DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDateTime;
	
	@Column(name = "LOGOUT_DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logoutDateTime;
	
	@Column(name = "LOGOUT_REASON")
	private String logoutReason;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "MAC_ADDRESS")
	private String macAddress;
	
	@Column(name = "HOST_NAME")
	private String hostName;
	
	@Column(name = "BROWSER_NAME")
	private String browserName;
	
	@Column(name = "BROWSER_VERSION")
	private String browserVersion;
	
	@Column(name = "OS_NAME")
	private String osName;
	
	@Column(name = "DEVICE_TYPE")
	private String deviceType;

	

}
