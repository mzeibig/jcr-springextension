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
package org.springframework.extensions.jcr;

/**
 * Interface used for delimiting Jcr operations based on what the underlying
 * repository supports (in this case optional operations).. Normally not used
 * but useful for casting to restrict access in some situations.
 * 
 * @author Costin Leau
 * @author Sergio Bossa
 * @author Salvatore Incandela
 * 
 */
public interface JcrOptionalOperations extends JcrModel2Operations {

	/**
	 * @see javax.jcr.Session#addLockToken(java.lang.String)
	 */
	public void addLockToken(String lock);

	/**
	 * @see javax.jcr.Session#getLockTokens()
	 */
	public String[] getLockTokens();

	/**
	 * @see javax.jcr.Session#removeLockToken(java.lang.String)
	 */
	public void removeLockToken(String lt);

}