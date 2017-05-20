package com.example.demo.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SUB_CLASS_1")
@DiscriminatorValue("SC1")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubClass1 extends BaseClass {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "subClass1", fetch = FetchType.LAZY)
	private Set<SubClass2> subClass2s;

	public Set<SubClass2> getSubClass2s() {
		return subClass2s;
	}

	public void setSubClass2s(Set<SubClass2> subClass2s) {
		this.subClass2s = subClass2s;
	}

}
