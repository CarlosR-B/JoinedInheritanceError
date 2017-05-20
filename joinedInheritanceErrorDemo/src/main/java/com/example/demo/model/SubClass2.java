package com.example.demo.model;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SUB_CLASS_2")
@DiscriminatorValue("SC2")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubClass2 extends BaseClass {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "SUB_CLASS_1_ID", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private SubClass1 subClass1;

	public SubClass1 getSubClass1() {
		return subClass1;
	}

	public void setSubClass1(SubClass1 subClass1) {
		this.subClass1 = subClass1;
	}

}
