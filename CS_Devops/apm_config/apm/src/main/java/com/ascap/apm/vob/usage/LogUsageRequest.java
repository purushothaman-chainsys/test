package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class LogUsageRequest extends BaseVOB {

    /**
     * 
     */

    private static final long serialVersionUID = -5843533138102707886L; 


    private String title;   
    private String artist;   
    private String usetype;   
    private String duration;   
    private String instvocal;   
    private String workid;   
    private String status;   
    private String plays;
    private String readonly;  
    private String wrkperfid;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(); 
        builder.append("[ title=");
        builder.append(title );  
        builder.append("]");
        return builder.toString();
    }



    public String getUsetype() {
        return usetype;
    }

    public void setUsetype(String usetype) {
        this.usetype = usetype;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstvocal() {
        return instvocal;
    }

    public void setInstvocal(String instvocal) {
        this.instvocal = instvocal;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }



    public String getPlays() {
        return plays;
    }



    public void setPlays(String plays) {
        this.plays = plays;
    }



    public String getReadonly() {
        return readonly;
    }



    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }



    public String getWrkperfid() {
        return wrkperfid;
    }



    public void setWrkperfid(String wrkperfid) {
        this.wrkperfid = wrkperfid;
    }











}
