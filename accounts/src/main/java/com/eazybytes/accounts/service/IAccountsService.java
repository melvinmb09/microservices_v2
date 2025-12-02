package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * @param customerDto - CustomerDto Object
     */
    void updateAccount(CustomerDto customerDto);

   /**
    *
    * @param mobileNumber - Input Mobile Number
    * @return boolean indicating if the delete of Account details is successful or not
    */
    void deleteAccount(String mobileNumber);
}
