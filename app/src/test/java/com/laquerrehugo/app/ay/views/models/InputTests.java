package com.laquerrehugo.app.ay.views.models;

import org.junit.Assert;
import org.junit.Test;


public class InputTests {
    //Data
     protected final TestData Emails = new TestData(new String[]{
            "email@domain.org",
            "firstname.lastname@domain.com",
            "email@subdomain.domain.net",
            "email@domain.name",
            "email@domain.co.jp",

            //Strange characters
            "\"email\"@domain.com",
            "firstname+lastname@domain.com",
            "1234567890@domain.com",
            "email@domain-one.com",
            "_______@domain.com",
            "firstname-lastname@domain.com"
    }, Input.Type.Email);

    protected final TestData PhoneNumbers = new TestData(new String[]{
            "(438) 555-7132",
            "+1 450 555-5555",
    }, Input.Type.PhoneNumber);

    protected final TestData Names = new TestData(new String[] {
            "Hugo Laquerre",
            "Effy",

            //Strange characters
            "Álvarez"
    }, Input.Type.Name);

    protected final TestData[] Data = {
            Emails,
            //Ignore phone number because android msut mock Patterns
            Names
    };

    //Tests
    @Test
    public void getTypeTest() throws Exception {
        for (TestData dataSet: Data)
        for (String value: dataSet.getValues()) {
            Input input = new Input(value);
            Input.Type inputType = input.getType();

            final String error =
                    input.getValue()
                    + " is considered to be "
                    + inputType.name()
                    + " instead of "
                    + dataSet.getExpectedType().name();
            Assert.assertSame(error, dataSet.ExpectedType, inputType);
        }
    }

    @Test
    public void isTest() throws Exception {
        for (TestData dataSet: Data)
        for (String value: dataSet.getValues()) {
            Input input = new Input(value);
            boolean inputIsExpectedType = input.is(dataSet.getExpectedType());

            final String error =
                    input.getValue()
                    + " is not considered to be an "
                    + dataSet.ExpectedType.name();
            Assert.assertTrue(error, inputIsExpectedType);
        }
    }

    //Classes
    protected class TestInput {
        //Properties
        private String Value;
        public String getValue() {
            return Value;
        }

        private Input.Type ExpectedType;
        public Input.Type getExpectedType() {
            return ExpectedType;
        }

        //Initialize
        public TestInput(String value, Input.Type expectedType) {
            this.Value = value;
            this.ExpectedType = expectedType;
        }
    }
    protected class TestData {
        //Properties
        private String[] Values;
        public String[] getValues() {
            return Values;
        }

        private Input.Type ExpectedType;
        public Input.Type getExpectedType() {
            return ExpectedType;
        }

        //Initialize
        public TestData(String[] values, Input.Type expectedType) {
            this.Values = values;
            this.ExpectedType = expectedType;
        }
    }
}