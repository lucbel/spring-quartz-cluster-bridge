package com.moifi.spring.quartz.cluster.bridge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.env.PropertyResolver;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by rodrigo on 15/12/15.
 * Test for Property util
 */
@RunWith(PowerMockRunner.class)
public class PropertyUtilTest {

    @Mock
    PropertyResolver resolver;

    PropertyUtil propertyUtil = new PropertyUtil();

    @Test
    public void testGetInteger() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn("10");
        // Act
        Integer value = propertyUtil.getInteger(resolver, "fake.property");
        // Assert
        assertNotNull(value);
        assertTrue(value.equals(10));
    }
}