package com.moifi.spring.quartz.cluster.bridge;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.ExceptionDepthComparator;
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Mock
    PropertyResolver resolver;

    PropertyUtil propertyUtil = new PropertyUtil();

    @Test
    public void test_getInteger_ok() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn("10");
        // Act
        Integer value = propertyUtil.getInteger(resolver, "fake.property");
        // Assert
        assertNotNull(value);
        assertTrue(value.equals(10));
    }


    @Test
    public void test_getInteger_ko_NoNumeric() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn("A");
        expectedException.expect(ValidationException.class);
        // Act
        propertyUtil.getInteger(resolver, "fake.property");
        // Assert
    }


    @Test
    public void test_getInteger_ko_Null() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn(null);
        expectedException.expect(ValidationException.class);
        // Act
        propertyUtil.getInteger(resolver, "fake.property");
        // Assert
    }

    @Test
    public void test_getInteger_ko_NotInteger() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn("45.56");
        expectedException.expect(ValidationException.class);
        // Act
        propertyUtil.getInteger(resolver, "fake.property");
        // Assert
    }

    @Test
    public void test_getInteger_ko_NumbersAndChars() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn("45.56klsjdflkdsj");
        expectedException.expect(ValidationException.class);
        // Act
        propertyUtil.getInteger(resolver, "fake.property");
        // Assert
    }

    @Test
    public void test_getInteger_ko_LargerThanInt() throws Exception {
        // Arrange
        when(resolver.getProperty(anyString())).thenReturn(String.valueOf(Long.MAX_VALUE));
        expectedException.expect(ValidationException.class);
        // Act
        propertyUtil.getInteger(resolver, "fake.property");
        // Assert
    }

}