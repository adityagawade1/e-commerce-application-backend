package com.entity;

import java.util.Date;
import java.util.List;

import com.mysql.cj.util.LazyString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
	private int id;
	
	@Column(name="category_name")
	private String name;
	
	@Column(name="category_type")
	private String type;
	
	@Column(name="category_description" , length = 2000)
	private String description;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated-at")
	private String updateAt;

	@OneToMany(mappedBy = "category")
	private List<Product> product;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = String.valueOf(createdAt.getTime());
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = String.valueOf(updateAt.getTime());
		;
	}

}
