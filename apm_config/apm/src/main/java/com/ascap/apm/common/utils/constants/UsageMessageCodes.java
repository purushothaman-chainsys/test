package com.ascap.apm.common.utils.constants;

/**
 * @author Jaya Shyam Narayana Lingamchetty Usage Error Warning Codes
 */
public class UsageMessageCodes {

    private UsageMessageCodes() {

    }

    public static final String RESOURCE_RULE_GRP_TV_PER_PROGRAM = "TVR";

    public static final String RESOURCE_RULE_GRP_ALL = "ALL";

    public static final String RESOURCE_RULE_GRP_DEFAULT_MUSIC_USER = "DEF";

    public static final String RESOURCE_RULE_GRP_DEFAULT_TV = "DTV";

    public static final String RESOURCE_RULE_GRP_TV_NETWORK = "TVN";

    public static final String RESOURCE_RULE_GRP_TV_SECONDARY_NETWORK = "TVS";

    public static final String RESOURCE_RULE_GRP_TV_PAY_PER_VIEW = "TVP";

    public static final String RESOURCE_RULE_GRP_LIVE_POP_CONCERTS = "POP";

    public static final String RESOURCE_RULE_GRP_INTERNET_STREAMING = "NET";

    public static final String RESOURCE_RULE_GRP_INTERACTIVE = "ACT";

    public static final String RESOURCE_RULE_GRP_SRE = "SRE";

    public static final String RESOURCE_RULE_GRP_GENERAL_BACKGROUND = "BCK";

    public static final String RESOURCE_RULE_GRP_FOREGROUND = "FOR";

    public static final String RESOURCE_RULE_GRP_INTERNATIONAL_INCOMING = "INT";

    public static final String RESOURCE_CATEGORY_CODE_GENERAL = "process";

    public static final String RESOURCE_CATEGORY_CODE_REJECT = "reject";

    public static final String RESOURCE_CATEGORY_CODE_ERROR = "error";

    public static final String RESOURCE_CATEGORY_CODE_WARNING = "warning";

    public static final String RESOURCE_CATEGORY_CODE_INFO = "information";

    public static final String RESOURCE_NOT_DEFINED = "NOT_DEFINED";

    public static final String CATEGORY_CODE_GENERAL = "G";

    public static final String CATEGORY_CODE_REJECT = "R";

    public static final String CATEGORY_CODE_ERROR = "E";

    public static final String CATEGORY_CODE_WARNING = "W";

    public static final String CATEGORY_CODE_INFO = "I";

    public static final String MSG_ALL_R_40101 = "40101";// Duplicate Program Performance and no Overlap Code

    public static final String MSG_ALL_R_40102 = "40102";// Music User ID cannot be Determined

    public static final String MSG_ALL_R_40103 = "40103";// Source System is missing or invalid

    public static final String MSG_ALL_R_40104 = "40104";// Replace Code Present but Performance Not Found

    public static final String MSG_ALL_R_40105 = "40105";// Lgcy Prog Perf ID not Unique, no replacement code

    public static final String MSG_ALL_R_40106 = "40106";// Program Performance is not complete

    public static final String MSG_ALL_R_40107 = "40107";// Performance date/time range are invalid

    public static final String MSG_ALL_R_40108 = "40108";// Survey Type is mandatory for �PCH� or �APP� data

    public static final String MSG_ALL_R_40109 = "40109";// Survey Type Code invalid should be SMP,CNS,LIB

    public static final String MSG_ALL_R_40110 = "40110";// Work performance is not matched to a valid Work Id

    public static final String MSG_ALL_R_40111 = "40111";// Work Performance contains an invalid Use Type

    public static final String MSG_ALL_R_40112 = "40112";// No duration for WrkPerf with UseTyp BG,BC,S,FA,BGA

    public static final String MSG_ALL_R_40113 = "40113";// Perf Start date/time not in Educational Survey Dates

    public static final String MSG_ALL_R_40114 = "40114";// Perf Start date/time not in Library Survey Dates

    public static final String MSG_ALL_R_40115 = "40115";// Perf Start date/time not in Sample Survey Dates

    public static final String MSG_ALL_R_40116 = "40116";// Perf Start date/time not in Sub Sample Survey Dates

    public static final String MSG_ALL_R_40117 = "40117";// Work Performance matched to an Informational Work

    public static final String MSG_ALL_DEF_TV_R_40301 = "40301";// Program Title not supplied

    public static final String MSG_ALL_DEF_TV_R_40302 = "40302";// Series Title not supplied and Series code present

    public static final String MSG_ALL_DEF_TV_R_40303 = "40303";// Program Code has not been supplied

    public static final String MSG_ALL_DEF_TV_R_40304 = "40304";// Duration 0 or null for Wrk Perf of Featured UseTyp

    public static final String MSG_TV_NET_R_40401 = "40401";// Number of Station Hooks has not been supplied

    public static final String MSG_TV_NET_R_40402 = "40402";// Program nof Hooks required Overlap Code �POC004�

    public static final String MSG_TV_SEC_NET_R_40501 = "40501";// Number of Station Hooks has not been supplied

    public static final String MSG_TV_SEC_NET_R_40502 = "40502";// Program nof Hooks required Overlap Code �POC004�

    public static final String MSG_TVPP_R_40601 = "40601";// Per Program Value has not been supplied

    public static final String MSG_TVPP_R_40602 = "40602";// ASCAP Revenue value has not been supplied

    public static final String MSG_TVPP_R_40603 = "40603";// TVPP Perf of PCH has Wrk Perfs of LogoTheme UseTyp

    public static final String MSG_TVPP_R_40604 = "40604";// TVPP Perf of PCH has UseType of F,S,FB,BG,BC,T,NT

    public static final String MSG_TVPAYPERVIEW_R_40701 = "40701";// Program Title not supplied

    public static final String MSG_TVPAYPERVIEW_R_40702 = "40702";// Series Title not supplied and Series code present

    public static final String MSG_TVPAYPERVIEW_R_40703 = "40703";// Program Code has not been supplied

    public static final String MSG_TVPAYPERVIEW_R_40704 = "40704";// Duration 0 or null for Wrk Perf of Featured UseTyp

    public static final String MSG_TVPAYPERVIEW_R_40705 = "40705";// Pay Per View Amount has not been supplied

    public static final String MSG_FOREIGN_R_41401 = "41401";// Society code invalid

    public static final String MSG_FOREIGN_R_41402 = "41402";// Revenue class mandatory

    public static final String MSG_FOREIGN_R_41403 = "41403";// Work Performance has invalid Work ID

    public static final String MSG_FOREIGN_R_41404 = "41404";// Work Performance Share has invalid Party Name Id

    public static final String MSG_FOREIGN_R_41405 = "41405";// Work Performance Share has no Usage Amount

    public static final String MSG_ALL_E_30101 = "30101";// Total duration of all Work Performances is greater than the
                                                         // duration of the program performance

    public static final String MSG_ALL_W_20101 = "20101";// Work Performance of a �Serious Music� Work has a Featured
                                                         // Use Type and a duration of more than 4 minutes

    public static final String MSG_ALL_W_20102 = "20102";// The Work Performance is matched to a Public Domain Work

    public static final String MSG_ALL_W_20103 = "20103";// Work Performance is matched to a Work that is being included
                                                         // in the Distribution for the first time

    public static final String MSG_ALL_W_20104 = "20104";// Work Performance is matched to a Work that is being included
                                                         // in the Distribution for the first time and it is UNID
                                                         // Work or has UNID shares

    public static final String MSG_ALL_E_30102 = "30102";// Work Performance is matched to a Work that has a �Possible
                                                         // Match� Status

    public static final String MSG_ALL_W_20105 = "20105";// Work Performance is matched to a Work that has a �Counter
                                                         // Claim� Status

    public static final String MSG_ALL_W_20106 = "20106";// A Work Performance is matched to a Work that has a
                                                         // �Disputed� Status

    public static final String MSG_ALL_W_20107 = "20107";// A Work Performance is matched to a Work that has a �Adverse
                                                         // Claim� Status
    // public static final String MSG_ALL_W_20108 = "20108";//Copyright Arrangement Percentage of the Work is not
    // Available
    // public static final String MSG_ALL_W_20109 = "20109";//Medley Sequence number missing when work is part of medley

    public static final String MSG_ALL_W_20110 = "20110";// Non Medley Work Performances are having Medley Use Types -
                                                         // TX, SX, JX, CT, CJ, CS

    public static final String MSG_ALL_W_20111 = "20111";// Work Performance with a Featured Use Type is linked to a
                                                         // Work with a Work Type of �Cue� or has the word �Cue� or
                                                         // �Cues� at the end if its title

    public static final String MSG_ALL_E_30103 = "30103";// Program Performance with Survey Type Library should have
                                                         // Work Performances with Use Type - T, BG, SP, SX, J, JX,
                                                         // TC, TX

    public static final String MSG_ALL_E_30104 = "30104";// Library Survey Indicator can be set for the following Use
                                                         // Types only - T, BG, SP, SX, J, JX, TC, TX

    public static final String MSG_ALL_E_30105 = "30105";// Work Performances which are Part of a Medley should have the
                                                         // Following Use Types - TX, SX, JX, CT, CJ, CS

    public static final String MSG_ALL_E_30106 = "30106";// Music User Type is not part of the Rule Group TV-Network &
                                                         // TV � Secondary Network and has a Hook-up Value

    public static final String MSG_ALL_E_30107 = "30107";// Work Id not Unique in Medley

    public static final String MSG_ALL_E_30108 = "30108";// Music user does not have an associated License Type

    public static final String MSG_ALL_E_30109 = "30109";// Survey Type is Mandatory

    public static final String MSG_ALL_E_30110 = "30110";// Copyright Arrangement Percentage of the Work is not
                                                         // Available

    public static final String MSG_ALL_E_30111 = "30111";// Medley Sequence number missing when work is part of medley

    public static final String MSG_ALL_E_30112 = "30112";// Non-SRE Performances have SRE Use Types

    public static final String MSG_ALL_E_30113 = "30113";// Performance Header change accepted without validation, Usage
                                                         // validation pending

    public static final String MSG_ALL_E_30114 = "30114";// Work Performance change accepted without validation, Usage
                                                         // validation pending

    public static final String MSG_ALL_DEF_MUS_USERS_E_30201 = "30201";// Valid Station Weights do not exist for the
                                                                       // Music User

    public static final String MSG_ALL_DEF_MUS_USERS_W_20201 = "20201";// Program Segment elapsed time is more than 6
                                                                       // hours
    // message code changed from 20202 to 20111 since the validation rule group is changed
    // public static final String MSG_ALL_DEF_MUS_USERS_W_20202 = "20202";//Work Performance with a Featured Use Type is
    // linked to a Work with a Work Type of �Cue� or has the word �Cue� or �Cues� at the end if its title

    public static final String MSG_ALL_DEF_MUS_USERS_E_30202 = "30202";// Music User Weight Rule effective during the
                                                                       // Performance Start Date / Time Period for
                                                                       // Music User Type, Survey Type with Sample with
                                                                       // Null Sample Type

    public static final String MSG_TV_NET_E_30401 = "30401";// Work Performance with Theme (T) Use Type is appearing on
                                                            // a program with a TV-Network Music User Type that is being
                                                            // Broadcast in Primetime

    public static final String MSG_TV_SEC_NET_E_30501 = "30501";// Work Performance with Theme (T) Use Type is appearing
                                                                // on a program with a TV-Network Music User Type that
                                                                // is being Broadcast in Primetime

    public static final String MSG_TVPP_E_30601 = "30601";// Call to Copy right gives Compensability as False - results
                                                          // from compensiblity conflict from what is reported

    public static final String MSG_LIVE_POP_E_30801 = "30801";// Performer Name has not been supplied

    public static final String MSG_LIVE_POP_E_30802 = "30802";// Average/Special Set Indicator has not been defined

    public static final String MSG_LIVE_POP_E_30803 = "30803";// Performance is defined as a �Special� set list and the
                                                              // Number of Concerts in Tour has not been specified

    public static final String MSG_LIVE_POP_E_30804 = "30804";// Performance is defined as a �Special� set list and the
                                                              // Number of Plays per Work has not been specified

    public static final String MSG_LIVE_POP_E_30805 = "30805";// Artist Revenue has not been supplied

    public static final String MSG_INTERNET_STREAMING_E_30901 = "30901";// Work Performance does not contain the Number
                                                                        // of Plays

    public static final String MSG_INTERNET_STREAMING_W_20901 = "20901";// Nationality of Streaming company missing

    public static final String MSG_INTERNET_STREAMING_W_20902 = "20902";// ISO Code for destination and source of stream
                                                                        // is missing or invalid

    public static final String MSG_INTERACTIVE_E_31001 = "31001";// Work Performance does not contain the Number of
                                                                 // Plays

    public static final String MSG_INTERACTIVE_W_21001 = "21001";// Nationality of Streaming company missing

    public static final String MSG_INTERACTIVE_W_21002 = "21002";// ISO Code for destination and source of stream is
                                                                 // missing or invalid

    public static final String MSG_SRE_E_31101 = "31101";// SMC Code and points value must be present

    public static final String MSG_SRE_E_31102 = "31102";// Use Type is invalid for the Music User Type of Symphonic

    public static final String MSG_SRE_E_31103 = "31103";// Use Type is invalid for the Music User type of Educational

    public static final String MSG_SRE_E_31104 = "31104";// Use Type is invalid for the Music User Type of Recital

    public static final String MSG_SRE_E_31105 = "31105";// Number of Plays at the Performance header is required

    public static final String MSG_GENERAL_BACKGROUND_E_31201 = "31201";// Work Performance does not contain the Number
                                                                        // of Plays for the Work for 'FM1' and 'MUZAK'

    public static final String MSG_GENERAL_BACKGROUND_E_31202 = "31202";// Work Performance does not contain the Number
                                                                        // of Plays and Survey Type of Performance
                                                                        // Header is Census for 'Music Choice'

    public static final String MSG_GENERAL_BACKGROUND_E_31203 = "31203";// Valid Station Weights do not exist Generala
                                                                        // Background/Foreground Music User

    public static final String MSG_GENERAL_FOREGROUND_E_31301 = "31301";// Work Performance does not contain the Number
                                                                        // of Plays for the Work for 'FM1' and 'MUZAK'

    public static final String MSG_GENERAL_FOREGROUND_E_31302 = "31302";// Work Performance does not contain the Number
                                                                        // of Plays and Survey Type of Performance
                                                                        // Header is Census for 'Music Choice'
    // public static final String MSG_GENERAL_BACKGROUND_E_31303 = "31303";//Valid Station Weights do not exist Generala
    // Background/Foreground Music User

    // public static final String MSG_PREP_ONLINE_R_51636 = "51636";//Music User Id Not Found in music User either
    // Deleted or not present
    // public static final String MSG_PREP_ONLINE_R_51637 = "51637";//Music User Type Cannot be Determined
    // public static final String MSG_PREP_ONLINE_R_51638 = "51638";//Music User License Type Cannot be Determined

    public static final String MSG_ALL_R_51504 = "51504";// Program Start Date is Null

    public static final String MSG_ALL_R_51505 = "51505";// Program End Date, Duration is Null

    public static final String MSG_ALL_R_51506 = "51506";// Music User ID not determined for PREP Party ID

    public static final String MSG_ALL_R_51507 = "51507";// Music User Type not determined for PREP Party ID

    public static final String MSG_ALL_R_51508 = "51508";// Music User ID not determined for Call Letters

    public static final String MSG_ALL_R_51509 = "51509"; // Music User Type not determined for CALL

    public static final String MSG_ALL_R_51510 = "51510"; // More than one Music User Found for CALL

    public static final String MSG_ALL_R_51511 = "51511"; // More than one Music User Type found for PREP

    public static final String MSG_ALL_R_51512 = "51512"; // More than one Music User Type found for CALL

    public static final String MSG_ALL_W_21501 = "21501";// Work Performance Contains Warnings

    public static final String MSG_ALL_E_31501 = "31501";// Work Performance Contains Errors

}
