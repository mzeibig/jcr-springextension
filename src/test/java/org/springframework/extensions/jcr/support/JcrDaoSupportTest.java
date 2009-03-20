/**
 * Copyright 2009 the original author or authors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.springframework.extensions.jcr.support;

import org.springframework.extensions.jcr.support.JcrDaoSupport;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.springframework.extensions.jcr.JcrTemplate;
import org.springframework.extensions.jcr.SessionFactory;

/**
 * @author Costin Leau
 * @author Sergio Bossa
 * @author Salvatore Incandela
 * 
 */
public class JcrDaoSupportTest extends TestCase {

	private MockControl sfCtrl, sessCtrl, repositoryCtrl;
	private SessionFactory sf;
	private Session sess;

	protected void setUp() throws Exception {
		super.setUp();
		sfCtrl = MockControl.createControl(SessionFactory.class);
		sf = (SessionFactory) sfCtrl.getMock();

		sessCtrl = MockControl.createControl(Session.class);
		sess = (Session) sessCtrl.getMock();
		repositoryCtrl = MockControl.createNiceControl(Repository.class);

	}

	protected void tearDown() throws Exception {
		super.tearDown();

		try {
			sessCtrl.verify();
			sfCtrl.verify();
			repositoryCtrl.verify();
		} catch (IllegalStateException ex) {
			// ignore: test method didn't call replay
		}
	}

	public void testJcrDaoSupportWithSessionFactory() throws Exception {

		// used for ServiceProvider
		/*
		 * sessCtrl.expectAndReturn(sess.getRepository(), repository,
		 * MockControl.ONE_OR_MORE); sfCtrl.expectAndReturn(sf.getSession(),
		 * sess);
		 */sfCtrl.replay();
		sessCtrl.replay();

		JcrDaoSupport dao = new JcrDaoSupport() {
			public void smth() {
			};
		};

		dao.setSessionFactory(sf);
		dao.afterPropertiesSet();
		assertEquals("Correct SessionFactory", sf, dao.getSessionFactory());
		// assertEquals("Correct JcrTemplate", sf,
		// dao.getJcrTemplate().getSessionFactory());
		sfCtrl.verify();
	}

	public void testJcrDaoSupportWithJcrTemplate() throws Exception {

		JcrTemplate template = new JcrTemplate();
		JcrDaoSupport dao = new JcrDaoSupport() {
			public void smth() {
			};
		};

		dao.setTemplate(template);
		dao.afterPropertiesSet();
		assertEquals("Correct JcrTemplate", template, dao.getTemplate());
	}

	public void testAfterPropertiesSet() {
		JcrDaoSupport dao = new JcrDaoSupport() {
		};

		try {
			dao.afterPropertiesSet();
			fail("expected exception");
		} catch (IllegalArgumentException e) {
			//
		}
	}

	public void testSetSessionFactory() throws RepositoryException {
		// sessCtrl.expectAndReturn(sess.getRepository(), repository,
		// MockControl.ONE_OR_MORE);
		// sfCtrl.expectAndReturn(sf.getSession(), sess);
		sfCtrl.replay();
		sessCtrl.replay();

		JcrDaoSupport dao = new JcrDaoSupport() {
		};

		dao.setSessionFactory(sf);

		assertEquals(dao.getSessionFactory(), sf);
	}

	public void testGetSession() throws RepositoryException {
		JcrDaoSupport dao = new JcrDaoSupport() {
		};
		// used for service provider

		sfCtrl.expectAndReturn(sf.getSession(), sess);
		sfCtrl.replay();
		sessCtrl.replay();

		dao.setSessionFactory(sf);
		dao.afterPropertiesSet();
		try {
			dao.getSession();
			fail("expected exception");
		} catch (IllegalStateException e) {
			// it's okay
		}
		assertEquals(dao.getSession(true), sess);
	}

	public void testReleaseSession() {
		JcrDaoSupport dao = new JcrDaoSupport() {
		};

		dao.releaseSession(null);

		sess.logout();

		sfCtrl.replay();
		sessCtrl.replay();

		dao.setSessionFactory(sf);
		dao.afterPropertiesSet();
		dao.releaseSession(sess);
	}

	public void testConvertException() {
		JcrDaoSupport dao = new JcrDaoSupport() {
		};
		MockControl tCtrl = MockClassControl.createControl(JcrTemplate.class);
		JcrTemplate t = (JcrTemplate) tCtrl.getMock();

		RepositoryException ex = new RepositoryException();

		tCtrl.expectAndReturn(t.convertJcrAccessException(ex), null);
		dao.setTemplate(t);
		dao.convertJcrAccessException(ex);

	}

}