package com.urlrest.service;

import com.urlrest.model.Url;
import com.urlrest.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

	@Autowired
	private EntityManager em;

	@Autowired
	private UrlRepository rep;

	public List<Url> findAll() {
		return rep.findAll();
	}

	public Optional<Url> findById(Long id) {
		return rep.findById(id);
	}

	public Url findByFriendlyName(String name) {
		return rep.findByFriendlyName(name);
	}

	public List<Url> findByAttribute(Url url) {
		List<Url> list = new ArrayList<>();
		if(url != null) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Url> cq = cb.createQuery(Url.class);

			Root<Url> company = cq.from(Url.class);
			List<Predicate> predicates = new ArrayList<>();

			if(url.getId() != null) {
				predicates.add(cb.equal(company.get("id"), url.getId()));
			}

			if(url.getFriendlyName() != null && !url.getFriendlyName().equals("")) {
				predicates.add(cb.like(cb.upper(company.get("friendlyName")),"%" + url.getFriendlyName().toUpperCase() + "%"));
			}

			cq.where(predicates.toArray(new Predicate[predicates.size()]));
			cq.orderBy(cb.asc(company.get("id")));

			TypedQuery<Url> query = em.createQuery(cq);
			list = query.getResultList();
		}

		return list;
	}

	public Url save(Url url, Long id) {
		Optional<Url> u = findById(id);
		if(u.isPresent()) {
			Url tmp = u.get();
			tmp.setUrl(url.getUrl());
			tmp.setFriendlyName(url.getFriendlyName());

			return rep.save(url);
		} else {
			return url;
		}
	}

	public Url save(Url url) {
		return rep.save(url);
	}

	public boolean delete(Long id) {
		Optional<Url> url = findById(id);
		if(url.isPresent()) {
			rep.delete(url.get());

			url = findById(id);
			if(!url.isPresent()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
