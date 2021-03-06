/**
 * Copyright (c) 2011, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.force.sdk.jpa.beanvalidation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;

import com.force.sdk.jpa.beanvalidation.entities.BeanValidationEntity;
import com.force.sdk.qa.util.jpa.BaseTransactionalSpringContextJPAFTest;

/**
 * 
 * Bean validation tests in container managed persistence context.
 * Bean validation is currently not supported by the javasdk hence these tests are commented out.
 * @author Dirk Hain
 */
public class SpringBeanValidationTest extends BaseTransactionalSpringContextJPAFTest {

//    @Test
    @Transactional
    @Rollback(true)
    /**
     * Container bean validation for @NotNull.
     * Test @NotNull bean validation for container managed context.
     * @hierarchy javasdk
     * @userStory xyz
     * @expectedResults Correct ConstraintViolationException is thrown.
     */
    public void testNotNull() {
        try {
            BeanValidationEntity bve = new BeanValidationEntity();
            bve.setName(null);
            entityManager.persist(bve);
            entityManager.flush();
            Assert.fail("@NotNull constraint was violated.");
        } catch (ConstraintViolationException cve) {
            Assert.assertEquals(cve.getConstraintViolations().size(), 1, "Wrong number of constraint violations.");
            ConstraintViolation<?> cv = cve.getConstraintViolations().iterator().next();
            Assert.assertEquals(cv.getPropertyPath().toString(), "name", "Wrong constraint violation returned.");
        }
    }

//    @Test
    @Transactional
    @Rollback(true)
    /**
     * Container bean validation for @Size.
     * Test @Size bean validation for container managed context.
     * @hierarchy javasdk
     * @userStory xyz
     * @expectedResults Correct ConstraintViolationException is thrown.
     */
    public void testSize() {
        try {
            BeanValidationEntity bve = new BeanValidationEntity();
            bve.setName("ab");
            entityManager.persist(bve);
            entityManager.flush();
            Assert.fail("@Size constraint was violated.");
        } catch (ConstraintViolationException cve) {
            Assert.assertEquals(cve.getConstraintViolations().size(), 1, "Wrong number of constraint violations.");
            ConstraintViolation<?> cv = cve.getConstraintViolations().iterator().next();
            Assert.assertEquals(cv.getPropertyPath().toString(), "name", "Wrong constraint violation returned.");
        }
    }
}
