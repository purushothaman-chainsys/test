package com.ascap.apm.common.utils.constants;

import java.util.Hashtable;

/**
 * Holds UI Groupings of a Music User Type.
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision:   1.5  $ $Date:   Nov 19 2009 15:26:02  $
 */
public class PerformanceMusicUserUIGroups {
	private static java.util.Hashtable<String, String> radioUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> tvUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> sreUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> livePopUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> internetUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> internetAudioVisualUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> generalBackgroundForegroundUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> tvPayPerViewUIGroup = new Hashtable<String, String>();
	private static java.util.Hashtable<String, String> tvPerProgramUIGroup = new Hashtable<String, String>();

	public static final int RADIO_PERF_UI_GROUP = 1;
	public static final int TV_PERF_UI_GROUP = 2;
	public static final int SRE_PERF_UI_GROUP = 3;
	public static final int LIVE_POP_PERF_UI_GROUP = 4;
	public static final int INTERNET_PERF_UI_GROUP = 5;
	public static final int BACKGOUND_FOREGROUND_PERF_UI_GROUP = 6;
	public static final int TV_PAY_PER_VIEW_PERF_UI_GROUP = 7;
	public static final int TV_PER_PROGRAM_PERF_UI_GROUP = 8;
	public static final int INTERNET_AUDIO_VISUAL_PERF_UI_GROUP = 9;

	/***
	 * @todo: THESE ARE NOT THE TRUE MEMBERS OF THE GROUPS PLEASE FILL IN members LATER
	 */
	static {
		//setup radioUIGroup
		//radioUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_CLASSICAL, MusicUserTypeConstants.RADIO_CLASSICAL);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_COUNTRY, MusicUserTypeConstants.RADIO_COUNTRY);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_ETHNIC, MusicUserTypeConstants.RADIO_ETHNIC);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_JAZZ, MusicUserTypeConstants.RADIO_JAZZ);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_NCR, MusicUserTypeConstants.RADIO_NCR);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_NPR, MusicUserTypeConstants.RADIO_NPR);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_POP, MusicUserTypeConstants.RADIO_POP);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_RELIGIOUS, MusicUserTypeConstants.RADIO_RELIGIOUS);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_SATELLITE, MusicUserTypeConstants.RADIO_SATELLITE);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_SPANISH, MusicUserTypeConstants.RADIO_SPANISH);
		radioUIGroup.put(MusicUserTypeConstants.RADIO_URBAN_CONTEMPORARY, MusicUserTypeConstants.RADIO_URBAN_CONTEMPORARY);
		
		//setup tvUIGroup
		//tvUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		tvUIGroup.put(MusicUserTypeConstants.TV_CABLE, MusicUserTypeConstants.TV_CABLE);
		tvUIGroup.put(MusicUserTypeConstants.TV_LOCAL_CABLE, MusicUserTypeConstants.TV_LOCAL_CABLE); //Added following Joshs Emails dated 10/14/2004 with Subjects 'Re: Validate Online vs. Cuff Usage Validations' and 'Re: TV Local Cable'
		tvUIGroup.put(MusicUserTypeConstants.TV_LOCAL, MusicUserTypeConstants.TV_LOCAL);
		tvUIGroup.put(MusicUserTypeConstants.TV_CABLE_INTERNATIONAL, MusicUserTypeConstants.TV_CABLE_INTERNATIONAL);		
		tvUIGroup.put(MusicUserTypeConstants.TV_NETWORK, MusicUserTypeConstants.TV_NETWORK);
		tvUIGroup.put(MusicUserTypeConstants.TV_PAY_PER_VIEW, MusicUserTypeConstants.TV_PAY_PER_VIEW);
		tvUIGroup.put(MusicUserTypeConstants.TV_PBS, MusicUserTypeConstants.TV_PBS);
		tvUIGroup.put(MusicUserTypeConstants.TV_SATELLITE, MusicUserTypeConstants.TV_SATELLITE);
		tvUIGroup.put(MusicUserTypeConstants.TV_SECONDARY_NETWORK, MusicUserTypeConstants.TV_SECONDARY_NETWORK);
		tvUIGroup.put(MusicUserTypeConstants.TV_UNIVISION, MusicUserTypeConstants.TV_UNIVISION);		
		//setup sreUIGroup
		//sreUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		sreUIGroup.put(MusicUserTypeConstants.SYMPHONIC, MusicUserTypeConstants.SYMPHONIC);
		sreUIGroup.put(MusicUserTypeConstants.RECITAL, MusicUserTypeConstants.RECITAL);
		sreUIGroup.put(MusicUserTypeConstants.EDUCATIONAL, MusicUserTypeConstants.EDUCATIONAL);

		//setup livePopUIGroup
		//livePopUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		livePopUIGroup.put(MusicUserTypeConstants.GENERAL_LIVE_POP_CONCERTS, MusicUserTypeConstants.GENERAL_LIVE_POP_CONCERTS);

		//setup internetUIGroup
		//internetUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_INTERNET_STREAMING, MusicUserTypeConstants.GENERAL_INTERNET_STREAMING);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_SELF_REPRESENTING, MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_SELF_REPRESENTING);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_NON_SELF_REPRESENTING, MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_NON_SELF_REPRESENTING);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_RINGTONES, MusicUserTypeConstants.GENERAL_RINGTONES);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_NEW_MEDIA_ASCAP_MEMBER, MusicUserTypeConstants.GENERAL_NEW_MEDIA_ASCAP_MEMBER);
		internetUIGroup.put(MusicUserTypeConstants.GENERAL_NEW_MEDIA_MEMBER, MusicUserTypeConstants.GENERAL_NEW_MEDIA_MEMBER);

		//setup generalBackgroundForegroundUIGroup
		//generalBackgroundForegroundUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		generalBackgroundForegroundUIGroup.put(MusicUserTypeConstants.GENERAL_BACKGROUND_FOREGROUND, MusicUserTypeConstants.GENERAL_BACKGROUND_FOREGROUND);

		//setup tvPayPerViewUIGroup
		//tvPayPerViewUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		tvPayPerViewUIGroup.put(MusicUserTypeConstants.TV_PAY_PER_VIEW, MusicUserTypeConstants.TV_PAY_PER_VIEW);

		//setup tvPerProgramUIGroup
		//tvPerProgramUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		tvPerProgramUIGroup.put(MusicUserTypeConstants.TV_LOCAL, MusicUserTypeConstants.TV_LOCAL);
		tvPerProgramUIGroup.put(MusicUserTypeConstants.TV_LOCAL_CABLE, MusicUserTypeConstants.TV_LOCAL_CABLE);
		
//		setup internetAudioVisualUIGroup
		//internetAudioVisualUIGroup.put(MusicUserTypeConstants, MusicUserTypeConstants);
		internetAudioVisualUIGroup.put(MusicUserTypeConstants.GENERAL_INTERNET_AUDIOVISUAL, MusicUserTypeConstants.GENERAL_INTERNET_AUDIOVISUAL);
	}
	
	/**
	 * Returns if the Music User Type given belongs to the UI Group
	 */
	public static boolean isMusicUserTypeMemberOftheUIGroup (int uiGroup, String musicUserTypeCode) {
		try {
			switch (uiGroup) {
			case RADIO_PERF_UI_GROUP :
			return (radioUIGroup.containsKey(musicUserTypeCode));
			case TV_PERF_UI_GROUP :
			return (tvUIGroup.containsKey(musicUserTypeCode));
			case SRE_PERF_UI_GROUP :
			return (sreUIGroup.containsKey(musicUserTypeCode));
			case LIVE_POP_PERF_UI_GROUP :
			return (livePopUIGroup.containsKey(musicUserTypeCode));
			case INTERNET_PERF_UI_GROUP :
			return (internetUIGroup.containsKey(musicUserTypeCode));
			case INTERNET_AUDIO_VISUAL_PERF_UI_GROUP :
				return (internetAudioVisualUIGroup.containsKey(musicUserTypeCode));
			case BACKGOUND_FOREGROUND_PERF_UI_GROUP :
			return (generalBackgroundForegroundUIGroup.containsKey(musicUserTypeCode));
			case TV_PAY_PER_VIEW_PERF_UI_GROUP :
			return (tvPayPerViewUIGroup.containsKey(musicUserTypeCode));				
			case TV_PER_PROGRAM_PERF_UI_GROUP :
			return (tvPerProgramUIGroup.containsKey(musicUserTypeCode));				
			default:
				return false;
			}
		} catch (Exception exception) {
			return false;
		}
	}

	public static String getStringMusicUserTypeMemberOftheUIGroup (int uiGroup, String musicUserTypeCode) {
		return(isMusicUserTypeMemberOftheUIGroup(uiGroup, musicUserTypeCode) ?"Y":"N");
	}
	
}
