package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;

public interface PhoneValidationServiceInterface {
    String validateUpdatePhoneNumber(String phoneNum, String region) throws NumberParseException;
    String validatePhoneNumber(String phoneNum, String region) throws NumberParseException;
}
