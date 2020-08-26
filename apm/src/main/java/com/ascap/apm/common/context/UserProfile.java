package com.ascap.apm.common.context;

import java.io.Serializable;
import java.util.List;

import com.ascap.apm.common.utils.constants.MembershipConstants;
import com.ascap.apm.vob.BaseVOB;
import com.ascap.apm.vob.admin.UserLoginHistory;
import com.ascap.apm.vob.common.PaymentWaiverInformation;

/**
 * @author Sudhar_Krishnamachar User Profile Value Object Class REMEMBER TO UPDATE THE toString METHOD EVERYTIME AN
 *         ATTRIBUTE IS ADDED and MAKE SURE THAT SENSITIVE INFORMATION like passwords etc are NOT PRESENT IN THE
 *         toString()
 */
public class UserProfile extends BaseVOB {

    private static final long serialVersionUID = 8212535631636157378L;

    private String userName;

    private String userId;

    private String salutation;

    private String suffix;

    private String suffixOther;

    private String firstName;

    private String lastName;

    private String fullName;

    private String description;

    private String userType;

    private String status;

    private String password;

    private String confirmPassword;

    private String userStatus;

    private String passwordStatus;

    private String secretQuestion1;

    private String secretAnswer1;

    private String secretQuestion2;

    private String secretAnswer2;

    private String emailId;

    private String phoneCountryCode;

    private String phoneAreaCode;

    private String phoneNumber;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String designatedUserId;

    private String province;

    // added for admin
    private String middleName;

    private String newPassword;

    private String userStatusId;

    private String userStatusDescription;

    private String departmentId;

    private String departmentDescription;

    private String inquiryOwnerFlag;

    private String inquiryAssignToFlag;

    private String delFlagInd;
    // end added for admin

    private List<Serializable> childUserProfiles;

    private String duParty;

    private String prepOrEwaUser;

    private String userRole;

    private String userOrg;

    private List<Serializable> groups;

    private List<Serializable> availableGroups;

    private String[] userRoles;

    private String copyrightRole;

    private long partyId;

    private long superParentId;

    private String partyName;

    private String partyFirstName;

    private String partyLastNameOrCompanyName;

    private String userLoginStatusId;

    // Super Parent Login Publisher Name
    private String superParentLoginPublisher;

    private UserPreference userPreference;

    // Temp variable to turn on/off the security (Call to TAM). Set to false in the
    // "LogonAction" if needs turn off.
    private boolean isSecurityEnabled = true;

    // http session key to be used by httpsession.setAttribute methods
    public static final String HTTP_SESSION_KEY = "PREP.UserProfile";

    // add new attributes
    // REMEMBER TO UPDATE THE toString METHOD EVERYTIME AN ATTRIBUTE IS ADDED

    /** <!-- Start ascapdevel changes --> **/
    private String partyTypeCode;

    private String partyRoleTypeCode;

    private String[] designatedUserIdArr;

    private PaymentWaiverInformation paymentWaiverInformation;

    private boolean isTriggerUpdateRequired = false;

    private boolean maAccountActivated = false;

    private String promoEmailOpt = null;

    private String profileId;

    private String profileName;

    private List<Serializable> profileGroups;

    private String partyStatus;

    private String deceasedInd;

    private String userAccessPermissionCode;

    private String isPartyMerged;

    private String userIdForAudit;

    private String accountLockedFlag = "N";

    private String parentPartyId;

    private String parentPartyName;

    private String parentPartyFirstName;

    private String parentPartyLastNameOrCompanyName;

    private String leAllocationPartyId;

    private String leAllocationPartyName;

    private String leAllocationPartyFirstName;

    private String leAllocationPartyLastNameOrCompanyName;

    private String parentPartyStatus;

    private String successorActivationFlag;

    private String partyHasAllocRecGreaterThanZeroIndicator;

    private String parentPartyUserAccessPermissionCode;

    private boolean isParentPartyMerged = false;

    private UserLoginHistory loginSessionInfo;

    private String finalPartyStatus;

    private String firstTimeLoginInd = "N";

    private String activationSource = null;

    private int numberOfAccountsFound;

    private String ssnTaxId;

    private String payDirectIndicator;

    private String childPublisherPartyId;

    private String superParentPartyId;

    /**
     * Returns the userId.
     * 
     * @return String
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the userName.
     * 
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the userOrg.
     * 
     * @return String
     */
    public String getUserOrg() {
        return userOrg;
    }

    /**
     * Returns the userRole.
     * 
     * @return String
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Sets the userId.
     * 
     * @param userId The userId to set
     */
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the userName.
     * 
     * @param userName The userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the userOrg.
     * 
     * @param userOrg The userOrg to set
     */
    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }

    /**
     * Sets the userRole.
     * 
     * @param userRole The userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * String representation of this object REMEMBER TO UPDATE THIS METHOD EVERYTIME AN ATTRIBUTE IS ADDED
     */
    public String toString() {

        try {
            StringBuilder outStrBuff;
            outStrBuff = new StringBuilder();
            final String APPEND_BREAK = "', <br>";

            outStrBuff.append("com.ascap.apm.common.context.UserProfile {").append("\naccountLockedFlag= '")
                .append(accountLockedFlag).append(APPEND_BREAK).append("\nisPartyMerged= '").append(isPartyMerged)
                .append(APPEND_BREAK).append("\nuserAccessPermissionCode= '").append(userAccessPermissionCode)
                .append(APPEND_BREAK).append("\ndeceasedInd= '").append(deceasedInd).append(APPEND_BREAK)
                .append("\npartyStatus= '").append(partyStatus).append(APPEND_BREAK).append("\nfinalPartyStatus= '")
                .append(finalPartyStatus).append(APPEND_BREAK).append("\nprofileGroups= '").append(profileGroups)
                .append(APPEND_BREAK).append("\nprofileName= '").append(profileName).append(APPEND_BREAK)
                .append("\nprofileId= '").append(profileId).append(APPEND_BREAK).append("\npromoEmailOpt= '")
                .append(promoEmailOpt).append(APPEND_BREAK).append("\nmaAccountActivated= '").append(maAccountActivated)
                .append(APPEND_BREAK).append("\nisTriggerUpdateRequired= '").append(isTriggerUpdateRequired)
                .append(APPEND_BREAK)
                // .append("\npaymentWaiverInformation=
                // '").append(paymentWaiverInformation).append("', <br>")
                .append("\ndesignatedUserIdArr= '").append(designatedUserIdArr).append(APPEND_BREAK)
                .append("\npartyRoleTypeCode= '").append(partyRoleTypeCode).append(APPEND_BREAK)
                .append("\npartyTypeCode= '").append(partyTypeCode).append(APPEND_BREAK)
                // .append("\nHTTP_SESSION_KEY= '").append(HTTP_SESSION_KEY).append("', <br>")
                .append("\nisSecurityEnabled= '").append(isSecurityEnabled).append(APPEND_BREAK)
                .append("\nuserPreference= '").append(userPreference).append(APPEND_BREAK)
                .append("\nsuperParentLoginPublisher= '").append(superParentLoginPublisher).append(APPEND_BREAK)
                .append("\nuserLoginStatusId= '").append(userLoginStatusId).append(APPEND_BREAK)
                .append("\npartyLastNameOrCompanyName= '").append(partyLastNameOrCompanyName).append(APPEND_BREAK)
                .append("\npartyFirstName= '").append(partyFirstName).append(APPEND_BREAK).append("\npartyName= '")
                .append(partyName).append(APPEND_BREAK).append("\nsuperParentId= '").append(superParentId)
                .append(APPEND_BREAK).append("\npartyId= '").append(partyId).append(APPEND_BREAK)
                .append("\ncopyrightRole= '").append(copyrightRole).append(APPEND_BREAK)
                // .append("\nuserRoles= '").append(userRoles).append("', <br>")
                // .append("\navailableGroups= '").append(availableGroups).append("', <br>")
                // .append("\ngroups= '").append(groups).append("', <br>")
                // .append("\nuserOrg= '").append(userOrg).append("', <br>")
                .append("\nuserRole= '").append(userRole).append(APPEND_BREAK).append("\nprepOrEwaUser= '")
                .append(prepOrEwaUser).append(APPEND_BREAK).append("\nduParty= '").append(duParty).append(APPEND_BREAK)
                .append("\nchildUserProfiles= '").append(childUserProfiles).append(APPEND_BREAK)
                .append("\ndelFlagInd= '").append(delFlagInd).append(APPEND_BREAK).append("\ninquiryAssignToFlag= '")
                .append(inquiryAssignToFlag).append(APPEND_BREAK).append("\ninquiryOwnerFlag= '")
                .append(inquiryOwnerFlag).append(APPEND_BREAK).append("\ndepartmentDescription= '")
                .append(departmentDescription).append(APPEND_BREAK).append("\ndepartmentId= '").append(departmentId)
                .append(APPEND_BREAK).append("\nuserStatusDescription= '").append(userStatusDescription)
                .append(APPEND_BREAK).append("\nuserStatusId= '").append(userStatusId).append(APPEND_BREAK)
                // .append("\nnewPassword= '").append(newPassword).append("', <br>")
                .append("\nmiddleName= '").append(middleName).append(APPEND_BREAK).append("\nprovince= '")
                .append(province).append(APPEND_BREAK).append("\ndesignatedUserId= '").append(designatedUserId)
                .append(APPEND_BREAK).append("\ncountry= '").append(country).append(APPEND_BREAK)
                .append("\npostalCode= '").append(postalCode).append(APPEND_BREAK).append("\nstate= '").append(state)
                .append(APPEND_BREAK).append("\ncity= '").append(city).append(APPEND_BREAK).append("\naddress2= '")
                .append(address2).append(APPEND_BREAK).append("\naddress1= '").append(address1).append(APPEND_BREAK)
                .append("\nphoneNumber= '").append(phoneNumber).append(APPEND_BREAK).append("\nphoneAreaCode= '")
                .append(phoneAreaCode).append(APPEND_BREAK).append("\nphoneCountryCode= '").append(phoneCountryCode)
                .append(APPEND_BREAK).append("\nemailId= '").append(emailId).append(APPEND_BREAK)
                // .append("\nsecretAnswer2= '").append(secretAnswer2).append("', <br>")
                // .append("\nsecretQuestion2= '").append(secretQuestion2).append("', <br>")
                // .append("\nsecretAnswer1= '").append(secretAnswer1).append("', <br>")
                // .append("\nsecretQuestion1= '").append(secretQuestion1).append("', <br>")
                // .append("\npasswordStatus= '").append(passwordStatus).append("', <br>")
                .append("\nuserStatus= '").append(userStatus).append(APPEND_BREAK)
                // .append("\nconfirmPassword= '").append(confirmPassword).append("', <br>")
                // .append("\npassword= '").append(password).append("', <br>")
                .append("\nstatus= '").append(status).append(APPEND_BREAK).append("\nuserType= '").append(userType)
                .append(APPEND_BREAK).append("\ndescription= '").append(description).append(APPEND_BREAK)
                .append("\nfullName= '").append(fullName).append(APPEND_BREAK).append("\nlastName= '").append(lastName)
                .append(APPEND_BREAK).append("\nfirstName= '").append(firstName).append(APPEND_BREAK)
                .append("\nsuffixOther= '").append(suffixOther).append(APPEND_BREAK).append("\nsuffix= '")
                .append(suffix).append(APPEND_BREAK).append("\nsalutation= '").append(salutation).append(APPEND_BREAK)
                .append("\nuserId= '").append(userId).append(APPEND_BREAK).append("\nuserName= '").append(userName)
                .append(APPEND_BREAK).append("\nparentPartyId= '").append(parentPartyId).append(APPEND_BREAK)
                .append("\nparentPartyName= '").append(parentPartyName).append(APPEND_BREAK)
                .append("\nleAllocationPartyId= '").append(leAllocationPartyId).append(APPEND_BREAK)
                .append("\nleAllocationPartyName= '").append(leAllocationPartyName).append(APPEND_BREAK)
                .append("\nparentPartyStatus= '").append(parentPartyStatus).append(APPEND_BREAK)
                .append("\nsuccessorActivationFlag= '").append(successorActivationFlag).append(APPEND_BREAK)
                .append("\npartyHasAllocRecGreaterThanZeroIndicator= '")
                .append(partyHasAllocRecGreaterThanZeroIndicator).append(APPEND_BREAK).append("\nfirstTimeLoginInd= '")
                .append(firstTimeLoginInd).append(APPEND_BREAK).append("\nactivationSource= '").append(activationSource)
                .append(APPEND_BREAK).append("\nnumberOfAccountsFound= '").append(numberOfAccountsFound)
                .append(APPEND_BREAK)

                .append("'}");
            return (outStrBuff.toString());

        } catch (Exception e) {
            return "Error Unable to Construct UserProfile Object String";
        }

    }

    /**
     * Returns the address1.
     * 
     * @return String
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Returns the address2.
     * 
     * @return String
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Returns the city.
     * 
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the country.
     * 
     * @return String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the emailId.
     * 
     * @return String
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Returns the firstName.
     * 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastName.
     * 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the phoneNumber.
     * 
     * @return String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the postalCode.
     * 
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the state.
     * 
     * @return String
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the address1.
     * 
     * @param address1 The address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Sets the address2.
     * 
     * @param address2 The address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Sets the city.
     * 
     * @param city The city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the country.
     * 
     * @param country The country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the emailId.
     * 
     * @param emailId The emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Sets the firstName.
     * 
     * @param firstName The firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName.
     * 
     * @param lastName The lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the phoneNumber.
     * 
     * @param phoneNumber The phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the postalCode.
     * 
     * @param postalCode The postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the state.
     * 
     * @param state The state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the userType.
     * 
     * @return String
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the description.
     * 
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the userType.
     * 
     * @param userType The userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Returns the status.
     * 
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status The status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the confirmPassword.
     * 
     * @return String
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Returns the password.
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the confirmPassword.
     * 
     * @param confirmPassword The confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Sets the password.
     * 
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the userStatus.
     * 
     * @return String
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * Sets the userStatus.
     * 
     * @param userStatus The userStatus to set
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * Returns the passwordStatus.
     * 
     * @return String
     */
    public String getPasswordStatus() {
        return passwordStatus;
    }

    /**
     * Sets the passwordStatus.
     * 
     * @param passwordStatus The passwordStatus to set
     */
    public void setPasswordStatus(String passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    /**
     * Returns the userRoles.
     * 
     * @return ArrayList
     */
    public String[] getUserRoles() {
        return userRoles;
    }

    /**
     * Sets the userRoles.
     * 
     * @param userRoles The userRoles to set
     */
    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * Returns the partyId.
     * 
     * @return String
     */
    public long getPartyId() {
        return partyId;
    }

    /**
     * Sets the partyId.
     * 
     * @param partyId The partyId to set
     */
    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    /**
     * Returns the phoneCountryCode.
     * 
     * @return String
     */
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    /**
     * Sets the phoneCountryCode.
     * 
     * @param phoneCountryCode The phoneCountryCode to set
     */
    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    /**
     * Returns the designatedUserId.
     * 
     * @return String
     */
    public String getDesignatedUserId() {
        return designatedUserId;
    }

    /**
     * Sets the designatedUserId.
     * 
     * @param designatedUserId The designatedUserId to set
     */
    public void setDesignatedUserId(String designatedUserId) {
        this.designatedUserId = designatedUserId;
    }

    /**
     * Returns the partyName.
     * 
     * @return String
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * 
     * @param partyName The partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns the userLoginStatusId.
     * 
     * @return String
     */
    public String getUserLoginStatusId() {
        return userLoginStatusId;
    }

    /**
     * Sets the userLoginStatusId.
     * 
     * @param userLoginStatusId The userLoginStatusId to set
     */
    public void setUserLoginStatusId(String userLoginStatusId) {
        this.userLoginStatusId = userLoginStatusId;
    }

    /**
     * Returns the phoneAreaCode.
     * 
     * @return String
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     * Sets the phoneAreaCode.
     * 
     * @param phoneAreaCode The phoneAreaCode to set
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    /**
     * Returns the copyrightRole.
     * 
     * @return String
     */
    public String getCopyrightRole() {
        return copyrightRole;
    }

    /**
     * Sets the copyrightRole.
     * 
     * @param copyrightRole The copyrightRole to set
     */
    public void setCopyrightRole(String copyrightRole) {
        this.copyrightRole = copyrightRole;
    }

    /**
     * Returns the userPreference.
     * 
     * @return UserPreference
     */
    public UserPreference getUserPreference() {
        return userPreference;
    }

    /**
     * Sets the userPreference.
     * 
     * @param userPreference The userPreference to set
     */
    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    /**
     * Returns the superParentId.
     * 
     * @return long
     */
    public long getSuperParentId() {
        return superParentId;
    }

    /**
     * Sets the superParentId.
     * 
     * @param superParentId The superParentId to set
     */
    public void setSuperParentId(long superParentId) {
        this.superParentId = superParentId;
    }

    /**
     * Returns the superParentLoginPublisher.
     * 
     * @return String
     */
    public String getSuperParentLoginPublisher() {
        return superParentLoginPublisher;
    }

    /**
     * Sets the superParentLoginPublisher.
     * 
     * @param superParentLoginPublisher The superParentLoginPublisher to set
     */
    public void setSuperParentLoginPublisher(String superParentLoginPublisher) {
        this.superParentLoginPublisher = superParentLoginPublisher;
    }

    /**
     * Returns the groups.
     * 
     * @return ArrayList
     */
    public List<Serializable> getGroups() {
        return groups;
    }

    /**
     * Sets the groups.
     * 
     * @param groups The groups to set
     */
    public void setGroups(List<Serializable> groups) {
        this.groups = groups;
    }

    /**
     * Returns the prepOrEwaUser.
     * 
     * @return String
     */
    public String getPrepOrEwaUser() {
        return prepOrEwaUser;
    }

    /**
     * Sets the prepOrEwaUser.
     * 
     * @param prepOrEwaUser The prepOrEwaUser to set
     */
    public void setPrepOrEwaUser(String prepOrEwaUser) {
        this.prepOrEwaUser = prepOrEwaUser;
    }

    /**
     * Returns the childUserProfiles.
     * 
     * @return ArrayList
     */
    public List<Serializable> getChildUserProfiles() {
        return childUserProfiles;
    }

    /**
     * Sets the childUserProfiles.
     * 
     * @param childUserProfiles The childUserProfiles to set
     */
    public void setChildUserProfiles(List<Serializable> childUserProfiles) {
        this.childUserProfiles = childUserProfiles;
    }

    /**
     * Returns the isSecurityEnabled.
     * 
     * @return boolean
     */
    public boolean isSecurityEnabled() {
        return isSecurityEnabled;
    }

    /**
     * Sets the isSecurityEnabled.
     * 
     * @param isSecurityEnabled The isSecurityEnabled to set
     */
    public void setIsSecurityEnabled(boolean isSecurityEnabled) {
        this.isSecurityEnabled = isSecurityEnabled;
    }

    /**
     * Returns the partyTypeCode.
     * 
     * @return String
     */
    public String getPartyTypeCode() {
        return partyTypeCode;
    }

    /**
     * Sets the partyTypeCode.
     * 
     * @param partyTypeCode The partyTypeCode to set
     */
    public void setPartyTypeCode(String partyTypeCode) {
        this.partyTypeCode = partyTypeCode;
    }

    /**
     * Returns the designatedUserIdArr.
     * 
     * @return String[]
     */
    public String[] getDesignatedUserIdArr() {
        return designatedUserIdArr;
    }

    /**
     * Sets the designatedUserIdArr.
     * 
     * @param designatedUserIdArr The designatedUserIdArr to set
     */
    public void setDesignatedUserIdArr(String[] designatedUserIdArr) {
        this.designatedUserIdArr = designatedUserIdArr;
    }

    /**
     * Returns the duParty.
     * 
     * @return String
     */
    public String getDuParty() {
        return duParty;
    }

    /**
     * Sets the duParty.
     * 
     * @param duParty The duParty to set
     */
    public void setDuParty(String duParty) {
        this.duParty = duParty;
    }

    /**
     * Returns the middleName.
     * 
     * @return String
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middleName.
     * 
     * @param middleName The middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns the newPassword.
     * 
     * @return String
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the newPassword.
     * 
     * @param newPassword The newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Returns the userStatusDescription.
     * 
     * @return String
     */
    public String getUserStatusDescription() {
        return userStatusDescription;
    }

    /**
     * Returns the userStatusId.
     * 
     * @return String
     */
    public String getUserStatusId() {
        return userStatusId;
    }

    /**
     * Sets the userStatusDescription.
     * 
     * @param userStatusDescription The userStatusDescription to set
     */
    public void setUserStatusDescription(String userStatusDescription) {
        this.userStatusDescription = userStatusDescription;
    }

    /**
     * Sets the userStatusId.
     * 
     * @param userStatusId The userStatusId to set
     */
    public void setUserStatusId(String userStatusId) {
        this.userStatusId = userStatusId;
    }

    /**
     * Returns the departmentDescription.
     * 
     * @return String
     */
    public String getDepartmentDescription() {
        return departmentDescription;
    }

    /**
     * Returns the departmentId.
     * 
     * @return String
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the departmentDescription.
     * 
     * @param departmentDescription The departmentDescription to set
     */
    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    /**
     * Sets the departmentId.
     * 
     * @param departmentId The departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Returns the availableGroups.
     * 
     * @return ArrayList
     */
    public List<Serializable> getAvailableGroups() {
        return availableGroups;
    }

    /**
     * Sets the availableGroups.
     * 
     * @param availableGroups The availableGroups to set
     */
    public void setAvailableGroups(List<Serializable> availableGroups) {
        this.availableGroups = availableGroups;
    }

    /**
     * Returns the inquiryAssignToFlag.
     * 
     * @return String
     */
    public String getInquiryAssignToFlag() {
        return inquiryAssignToFlag;
    }

    /**
     * Returns the inquiryOwnerFlag.
     * 
     * @return String
     */
    public String getInquiryOwnerFlag() {
        return inquiryOwnerFlag;
    }

    /**
     * Sets the inquiryAssignToFlag.
     * 
     * @param inquiryAssignToFlag The inquiryAssignToFlag to set
     */
    public void setInquiryAssignToFlag(String inquiryAssignToFlag) {
        this.inquiryAssignToFlag = inquiryAssignToFlag;
    }

    /**
     * Sets the inquiryOwnerFlag.
     * 
     * @param inquiryOwnerFlag The inquiryOwnerFlag to set
     */
    public void setInquiryOwnerFlag(String inquiryOwnerFlag) {
        this.inquiryOwnerFlag = inquiryOwnerFlag;
    }

    /**
     * Returns the delFlagInd.
     * 
     * @return String
     */
    public String getDelFlagInd() {
        return delFlagInd;
    }

    /**
     * Sets the delFlagInd.
     * 
     * @param delFlagInd The delFlagInd to set
     */
    public void setDelFlagInd(String delFlagInd) {
        this.delFlagInd = delFlagInd;
    }

    /**
     * Returns the salutation.
     * 
     * @return String
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * Returns the suffix.
     * 
     * @return String
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Returns the suffixOther.
     * 
     * @return String
     */
    public String getSuffixOther() {
        return suffixOther;
    }

    /**
     * Sets the salutation.
     * 
     * @param salutation The salutation to set
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * Sets the suffix.
     * 
     * @param suffix The suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Sets the suffixOther.
     * 
     * @param suffixOther The suffixOther to set
     */
    public void setSuffixOther(String suffixOther) {
        this.suffixOther = suffixOther;
    }

    /**
     * Returns the paymentWaiverInformation.
     * 
     * @return PaymentWaiverInformation
     */
    public PaymentWaiverInformation getPaymentWaiverInformation() {
        return paymentWaiverInformation;
    }

    /**
     * Sets the paymentWaiverInformation.
     * 
     * @param paymentWaiverInformation The paymentWaiverInformation to set
     */
    public void setPaymentWaiverInformation(PaymentWaiverInformation paymentWaiverInformation) {
        this.paymentWaiverInformation = paymentWaiverInformation;
    }

    /**
     * Returns the isTriggerUpdateRequired.
     * 
     * @return boolean
     */
    public boolean isTriggerUpdateRequired() {
        return isTriggerUpdateRequired;
    }

    /**
     * Sets the isTriggerUpdateRequired.
     * 
     * @param isTriggerUpdateRequired The isTriggerUpdateRequired to set
     */
    public void setIsTriggerUpdateRequired(boolean isTriggerUpdateRequired) {
        this.isTriggerUpdateRequired = isTriggerUpdateRequired;
    }

    /**
     * @return Returns the maAccountActivated.
     */
    public boolean isMaAccountActivated() {
        return maAccountActivated;
    }

    /**
     * @param maAccountActivated The maAccountActivated to set.
     */
    public void setMaAccountActivated(boolean maAccountActivated) {
        this.maAccountActivated = maAccountActivated;
    }

    /**
     * @return Returns the promoEmailOpt.
     */
    public String getPromoEmailOpt() {
        return promoEmailOpt;
    }

    /**
     * @param promoEmailOpt The promoEmailOpt to set.
     */
    public void setPromoEmailOpt(String promoEmailOpt) {
        this.promoEmailOpt = promoEmailOpt;
    }

    /**
     * @return Returns the profileGroup.
     */
    public List<Serializable> getProfileGroups() {
        return profileGroups;
    }

    /**
     * @param profileGroup The profileGroup to set.
     */
    public void setProfileGroups(List<Serializable> profileGroups) {
        this.profileGroups = profileGroups;
    }

    /**
     * @return Returns the profileId.
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * @param profileId The profileId to set.
     */
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    /**
     * @return Returns the profileName.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName The profileName to set.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return Returns the province.
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param deceasedInd The deceasedInd to set.
     */
    public void setDeceasedInd(String deceasedInd) {
        this.deceasedInd = deceasedInd;
    }

    /**
     * @return Returns the partyStatus.
     */
    public String getPartyStatus() {
        return partyStatus;
    }

    /**
     * @param partyStatus The partyStatus to set.
     */
    public void setPartyStatus(String partyStatus) {
        this.partyStatus = partyStatus;
    }

    /**
     * @return Returns the userAccessPermissionCode.
     */
    public String getUserAccessPermissionCode() {
        return userAccessPermissionCode;
    }

    /**
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return Returns the deceasedInd.
     */
    public String getDeceasedInd() {
        return deceasedInd;
    }

    /**
     * @param userAccessPermissionCode The userAccessPermissionCode to set.
     */
    public void setUserAccessPermissionCode(String userAccessPermissionCode) {
        this.userAccessPermissionCode = userAccessPermissionCode;
    }

    /**
     * @return Returns the secretAnswer1.
     */
    public String getSecretAnswer1() {
        return secretAnswer1;
    }

    /**
     * @param secretAnswer1 The secretAnswer1 to set.
     */
    public void setSecretAnswer1(String secretAnswer1) {
        this.secretAnswer1 = secretAnswer1;
    }

    /**
     * @return Returns the secretAnswer2.
     */
    public String getSecretAnswer2() {
        return secretAnswer2;
    }

    /**
     * @param secretAnswer2 The secretAnswer2 to set.
     */
    public void setSecretAnswer2(String secretAnswer2) {
        this.secretAnswer2 = secretAnswer2;
    }

    /**
     * @return Returns the secretQuestion1.
     */
    public String getSecretQuestion1() {
        return secretQuestion1;
    }

    /**
     * @param secretQuestion1 The secretQuestion1 to set.
     */
    public void setSecretQuestion1(String secretQuestion1) {
        this.secretQuestion1 = secretQuestion1;
    }

    /**
     * @return Returns the secretQuestion2.
     */
    public String getSecretQuestion2() {
        return secretQuestion2;
    }

    /**
     * @param secretQuestion2 The secretQuestion2 to set.
     */
    public void setSecretQuestion2(String secretQuestion2) {
        this.secretQuestion2 = secretQuestion2;
    }

    /**
     * @return Returns the partyRoleTypeCode.
     */
    public String getPartyRoleTypeCode() {
        return partyRoleTypeCode;
    }

    /**
     * @param partyRoleTypeCode The partyRoleTypeCode to set.
     */
    public void setPartyRoleTypeCode(String partyRoleTypeCode) {
        this.partyRoleTypeCode = partyRoleTypeCode;
    }

    /**
     * @return Returns the fullName.
     */
    public String getFullName() {
        StringBuilder fullNameContent = new StringBuilder("");
        if (this.salutation != null && !this.salutation.equals("")) {
            fullNameContent.append(this.salutation);
        }
        if (this.firstName != null && !this.firstName.equals("")) {
            fullNameContent.append(" " + this.firstName);
        }
        fullNameContent.append(" " + this.lastName);
        if (this.suffix != null && !this.suffix.equals("") && this.suffixOther != null && !this.suffixOther.equals("")
            && this.suffix.equals(MembershipConstants.PARTY_NAME_SUFFIX_TYPE_OTHER)) {
            fullNameContent.append(" " + this.suffixOther);
        }
        if (this.suffix != null && !this.suffix.equals("")
            && !this.suffix.equals(MembershipConstants.PARTY_NAME_SUFFIX_TYPE_OTHER)) {
            fullNameContent.append(" " + this.suffix);
        }

        return fullNameContent.toString();
    }

    /**
     * @return Returns the partyFirstName.
     */
    public String getPartyFirstName() {
        return partyFirstName;
    }

    /**
     * @param partyFirstName The partyFirstName to set.
     */
    public void setPartyFirstName(String partyFirstName) {
        this.partyFirstName = partyFirstName;
    }

    /**
     * @return Returns the partyLastNameOrCompanyName.
     */
    public String getPartyLastNameOrCompanyName() {
        return partyLastNameOrCompanyName;
    }

    /**
     * @param partyLastNameOrCompanyName The partyLastNameOrCompanyName to set.
     */
    public void setPartyLastNameOrCompanyName(String partyLastNameOrCompanyName) {
        this.partyLastNameOrCompanyName = partyLastNameOrCompanyName;
    }

    /**
     * @return Returns the isPartyMerged.
     */
    public String getIsPartyMerged() {
        return isPartyMerged;
    }

    /**
     * @param isPartyMerged The isPartyMerged to set.
     */
    public void setIsPartyMerged(String isPartyMerged) {
        this.isPartyMerged = isPartyMerged;
    }

    /**
     * @return Returns the accountLockedFlag.
     */
    public String getAccountLockedFlag() {
        return accountLockedFlag;
    }

    /**
     * @param accountLockedFlag The accountLockedFlag to set.
     */
    public void setAccountLockedFlag(String accountLockedFlag) {
        this.accountLockedFlag = accountLockedFlag;
    }

    /**
     * @return Returns the userIdForAudit.
     */
    public String getUserIdForAudit() {
        return userIdForAudit;
    }

    /**
     * @param userIdForAudit The userIdForAudit to set.
     */
    public void setUserIdForAudit(String userIdForAudit) {
        this.userIdForAudit = userIdForAudit;
    }

    /**
     * @return Returns the leAllocationPartyId.
     */
    public String getLeAllocationPartyId() {
        return leAllocationPartyId;
    }

    /**
     * @param leAllocationPartyId The leAllocationPartyId to set.
     */
    public void setLeAllocationPartyId(String leAllocationPartyId) {
        this.leAllocationPartyId = leAllocationPartyId;
    }

    /**
     * @return Returns the parentPartyFirstName.
     */
    public String getParentPartyFirstName() {
        return parentPartyFirstName;
    }

    /**
     * @param parentPartyFirstName The parentPartyFirstName to set.
     */
    public void setParentPartyFirstName(String parentPartyFirstName) {
        this.parentPartyFirstName = parentPartyFirstName;
    }

    /**
     * @return Returns the parentPartyId.
     */
    public String getParentPartyId() {
        return parentPartyId;
    }

    /**
     * @param parentPartyId The parentPartyId to set.
     */
    public void setParentPartyId(String parentPartyId) {
        this.parentPartyId = parentPartyId;
    }

    /**
     * @return Returns the parentPartyLastNameOrCompanyName.
     */
    public String getParentPartyLastNameOrCompanyName() {
        return parentPartyLastNameOrCompanyName;
    }

    /**
     * @param parentPartyLastNameOrCompanyName The parentPartyLastNameOrCompanyName to set.
     */
    public void setParentPartyLastNameOrCompanyName(String parentPartyLastNameOrCompanyName) {
        this.parentPartyLastNameOrCompanyName = parentPartyLastNameOrCompanyName;
    }

    /**
     * @return Returns the parentPartyName.
     */
    public String getParentPartyName() {
        return parentPartyName;
    }

    /**
     * @param parentPartyName The parentPartyName to set.
     */
    public void setParentPartyName(String parentPartyName) {
        this.parentPartyName = parentPartyName;
    }

    /**
     * @return Returns the leAllocationPartyFirstName.
     */
    public String getLeAllocationPartyFirstName() {
        return leAllocationPartyFirstName;
    }

    /**
     * @param leAllocationPartyFirstName The leAllocationPartyFirstName to set.
     */
    public void setLeAllocationPartyFirstName(String leAllocationPartyFirstName) {
        this.leAllocationPartyFirstName = leAllocationPartyFirstName;
    }

    /**
     * @return Returns the leAllocationPartyLastNameOrCompanyName.
     */
    public String getLeAllocationPartyLastNameOrCompanyName() {
        return leAllocationPartyLastNameOrCompanyName;
    }

    /**
     * @param leAllocationPartyLastNameOrCompanyName The leAllocationPartyLastNameOrCompanyName to set.
     */
    public void setLeAllocationPartyLastNameOrCompanyName(String leAllocationPartyLastNameOrCompanyName) {
        this.leAllocationPartyLastNameOrCompanyName = leAllocationPartyLastNameOrCompanyName;
    }

    /**
     * @return Returns the leAllocationPartyName.
     */
    public String getLeAllocationPartyName() {
        return leAllocationPartyName;
    }

    /**
     * @param leAllocationPartyName The leAllocationPartyName to set.
     */
    public void setLeAllocationPartyName(String leAllocationPartyName) {
        this.leAllocationPartyName = leAllocationPartyName;
    }

    /**
     * @return Returns the parentPartyStatus.
     */
    public String getParentPartyStatus() {
        return parentPartyStatus;
    }

    /**
     * @param parentPartyStatus The parentPartyStatus to set.
     */
    public void setParentPartyStatus(String parentPartyStatus) {
        this.parentPartyStatus = parentPartyStatus;
    }

    /**
     * @return Returns the partyHasAllocRecGreaterThanZeroIndicator.
     */
    public String getPartyHasAllocRecGreaterThanZeroIndicator() {
        return partyHasAllocRecGreaterThanZeroIndicator;
    }

    /**
     * @param partyHasAllocRecGreaterThanZeroIndicator The partyHasAllocRecGreaterThanZeroIndicator to set.
     */
    public void setPartyHasAllocRecGreaterThanZeroIndicator(String partyHasAllocRecGreaterThanZeroIndicator) {
        this.partyHasAllocRecGreaterThanZeroIndicator = partyHasAllocRecGreaterThanZeroIndicator;
    }

    /**
     * @return Returns the successorActivationFlag.
     */
    public String getSuccessorActivationFlag() {
        return successorActivationFlag;
    }

    /**
     * @param successorActivationFlag The successorActivationFlag to set.
     */
    public void setSuccessorActivationFlag(String successorActivationFlag) {
        this.successorActivationFlag = successorActivationFlag;
    }

    /**
     * @return Returns the parentPartyUserAccessPermissionCode.
     */
    public String getParentPartyUserAccessPermissionCode() {
        return parentPartyUserAccessPermissionCode;
    }

    /**
     * @param parentPartyUserAccessPermissionCode The parentPartyUserAccessPermissionCode to set.
     */
    public void setParentPartyUserAccessPermissionCode(String parentPartyUserAccessPermissionCode) {
        this.parentPartyUserAccessPermissionCode = parentPartyUserAccessPermissionCode;
    }

    /**
     * @return Returns the isParentPartyMerged.
     */
    public boolean isParentPartyMerged() {
        return isParentPartyMerged;
    }

    /**
     * @param isParentPartyMerged The isParentPartyMerged to set.
     */
    public void setParentPartyMerged(boolean isParentPartyMerged) {
        this.isParentPartyMerged = isParentPartyMerged;
    }

    /**
     * @return Returns the loginSessionInfo.
     */
    public UserLoginHistory getLoginSessionInfo() {
        return this.loginSessionInfo;
    }

    /**
     * @param loginSessionInfo The loginSessionInfo to set.
     */
    public void setLoginSessionInfo(UserLoginHistory loginSessionInfo) {
        this.loginSessionInfo = loginSessionInfo;
    }

    /**
     * @return Returns the finalPartyStatus.
     */
    public String getFinalPartyStatus() {
        return finalPartyStatus;
    }

    /**
     * @param finalPartyStatus The finalPartyStatus to set.
     */
    public void setFinalPartyStatus(String finalPartyStatus) {
        this.finalPartyStatus = finalPartyStatus;
    }

    /**
     * @return Returns the firstTimeLoginInd.
     */
    public String getFirstTimeLoginInd() {
        return firstTimeLoginInd;
    }

    /**
     * @param firstTimeLoginInd The firstTimeLoginInd to set.
     */
    public void setFirstTimeLoginInd(String firstTimeLoginInd) {
        this.firstTimeLoginInd = firstTimeLoginInd;
    }

    /**
     * @return Returns the activationSource.
     */
    public String getActivationSource() {
        return activationSource;
    }

    /**
     * @param activationSource The activationSource to set.
     */
    public void setActivationSource(String activationSource) {
        this.activationSource = activationSource;
    }

    /**
     * @return Returns the numberOfAccountsFound.
     */
    public int getNumberOfAccountsFound() {
        return numberOfAccountsFound;
    }

    /**
     * @param numberOfAccountsFound The numberOfAccountsFound to set.
     */
    public void setNumberOfAccountsFound(int numberOfAccountsFound) {
        this.numberOfAccountsFound = numberOfAccountsFound;
    }

    /**
     * @return Returns the ssnTaxId.
     */
    public String getSsnTaxId() {
        return ssnTaxId;
    }

    /**
     * @param ssnTaxId The ssnTaxId to set.
     */
    public void setSsnTaxId(String ssnTaxId) {
        this.ssnTaxId = ssnTaxId;
    }

    /**
     * @return Returns the payDirectIndicator.
     */
    public String getPayDirectIndicator() {
        return payDirectIndicator;
    }

    /**
     * @param payDirectIndicator The payDirectIndicator to set.
     */
    public void setPayDirectIndicator(String payDirectIndicator) {
        this.payDirectIndicator = payDirectIndicator;
    }

    public String getChildPublisherPartyId() {
        return childPublisherPartyId;
    }

    public void setChildPublisherPartyId(String childPublisherPartyId) {
        this.childPublisherPartyId = childPublisherPartyId;
    }

    public String getSuperParentPartyId() {
        return superParentPartyId;
    }

    public void setSuperParentPartyId(String superParentPartyId) {
        this.superParentPartyId = superParentPartyId;
    }
}
