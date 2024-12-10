package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;

/**
 * Interface for validating phone numbers.
 * <p>
 * This interface defines methods for validating phone numbers during user registration or update.
 * It ensures that phone numbers follow the correct format and belong to the appropriate region.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #validatePhoneNumber(String, String)} validates the phone number format.</li>
 *     <li>{@link #validateUpdatePhoneNumber(String, String)} validates the phone number format during updates.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PhoneValidationServiceInterface {

    /**
     * Validates the provided phone number during an update.
     * <p>
     * This method ensures that the updated phone number is valid and follows the correct format
     * for the specified region.
     * </p>
     *
     * @param phoneNum the phone number to be validated
     * @param region   the region where the phone number is registered
     * @return a formatted phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validateUpdatePhoneNumber(String phoneNum, String region) throws NumberParseException;

    /**
     * Validates the provided phone number.
     * <p>
     * This method ensures that the phone number is valid and adheres to the correct format
     * for the specified region.
     * </p>
     *
     * @param phoneNum the phone number to be validated
     * @param region   the region where the phone number is registered
     * @return a formatted phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validatePhoneNumber(String phoneNum, String region) throws NumberParseException;
}
