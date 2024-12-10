package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.IncorrectPhoneNumberException;
import org.thewhitemage13.interfaces.PhoneValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

/**
 * Service implementation for validating phone numbers.
 * <p>
 * This service uses Google's libphonenumber library to validate and format phone numbers
 * according to the region and ensures that no duplicate phone numbers exist in the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates phone numbers using regional formats.</li>
 *     <li>Formats phone numbers to international standards.</li>
 *     <li>Checks for phone number duplication in the database.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link UserRepository} - For checking phone number uniqueness in the database.</li>
 *     <li>{@link PhoneNumberUtil} - For parsing, validating, and formatting phone numbers.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * Throws {@link IncorrectPhoneNumberException} for invalid or duplicate phone numbers.
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class PhoneValidationServiceImpl implements PhoneValidationServiceInterface {
    private final UserRepository userRepository;
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * Constructor for dependency injection.
     *
     * @param userRepository the repository for user data access
     */
    @Autowired
    public PhoneValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates and formats a phone number for updating user information.
     *
     * @param phoneNum the phone number to validate
     * @param region   the region code for the phone number (e.g., "US", "GB")
     * @return the validated and formatted phone number in international format
     * @throws NumberParseException           if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException  if the phone number is invalid
     */
    @Override
    public String validateUpdatePhoneNumber(String phoneNum, String region) throws NumberParseException {
        return getString(phoneNum, region);
    }

    /**
     * Validates and formats a new phone number, ensuring it is not already in use.
     *
     * @param phoneNum the phone number to validate
     * @param region   the region code for the phone number (e.g., "US", "GB")
     * @return the validated and formatted phone number in international format
     * @throws NumberParseException           if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException  if the phone number is invalid or already in use
     */
    @Override
    public String validatePhoneNumber(String phoneNum, String region) throws NumberParseException {
        if (userRepository.existsUserByPhoneNumber(phoneNum)){
            throw new IncorrectPhoneNumberException("Phone number is already in use");
        }else {
            return getString(phoneNum, region);
        }
    }

    /**
     * Helper method for parsing, validating, and formatting a phone number.
     *
     * @param phoneNum the raw phone number to process
     * @param region   the region code for the phone number (e.g., "US", "GB")
     * @return the formatted phone number in international format
     * @throws NumberParseException           if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException  if the phone number is invalid
     */
    private String getString(String phoneNum, String region) throws NumberParseException {
        String formattedNumber;

        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNum, region);

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);

        if (!isValid) {
            throw new IncorrectPhoneNumberException("Incorrect phone number");
        }else {
            formattedNumber = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        }
        return formattedNumber;
    }
}
