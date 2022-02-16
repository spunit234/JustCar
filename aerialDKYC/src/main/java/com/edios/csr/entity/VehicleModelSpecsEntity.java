package com.edios.csr.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.DynamicUpdate;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "vehicle_model_specs")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleModelSpecsEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLE_MODEL_SPEC_ID")
	private Long vehicleModelSpecId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODEL_ID")
	private VehicleModelEntity modelId;
	
	@Column(name = "PRICE_DATE")
	private Date priceDate;
	
	@Column(name = "BODY_TYPE_ID")
	private Long bodyType; //ApplicationParameterListEntity
	
	@Column(name = "FUEL_TYPE_ID")
	private Long fuelType; //ApplicationParameterListEntity
	
	@Column(name = "COLOUR_ID")
	private Long colour;//ApplicationParameterListEntity
	
	@Column(name = "TRANSMISSION_ID")
	private Long transmission;//ApplicationParameterListEntity
	
	@Column(name = "SUNROOF")
	private String sunroof;

	@Column(name = "MUSIC_SYSTEM")
	private String musicSystem;

	@Column(name = "ALLOYSWHEEL")
	private String alloysWheel;
	
	@Column(name = "ENGINE_CC")
	private String engineCC;

	@Column(name = "SEATING_CAPACITY")
	private Integer sittingCapacity;
	
	@Column(name = "VEHICLE_CYLINDERS")
	private Integer vehicleCylinders;
	
	
//	@Column(name = "PRICE_TYPE_STATUS")
//	private String priceTypeStatus;

	@Column(name = "USER_ACTIVITY_ID")
	private Long userActivityId;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "RECORD_TYPE")
	private Character recordType;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_MODIFIED_BY")
	private Long lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	


}
