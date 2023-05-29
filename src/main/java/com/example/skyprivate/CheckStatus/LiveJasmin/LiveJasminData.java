package com.example.skyprivate.CheckStatus.LiveJasmin;

import java.math.BigDecimal;

public class LiveJasminData {
    private String public_show_status, become_online_at, has_event_show, p_id, profile_picture_url, toy_connection_status,
            display_name, performer_id;
    private Integer is_on_private, has_vip_show, status, rate_average, has_hot_show, person_age, last_activity_time,
            msc_id, originalStatus, sum_of_online_performers, status_order, last_online_at;
    private double price;

    public LiveJasminAchievement getAchievement() {
        return achievement;
    }

    public void setAchievement(LiveJasminAchievement achievement) {
        this.achievement = achievement;
    }

    private LiveJasminAchievement achievement;

    public void setPublic_show_status(String public_show_status) {
        this.public_show_status = public_show_status;
    }

    public void setBecome_online_at(String become_online_at) {
        this.become_online_at = become_online_at;
    }

    public void setHas_event_show(String has_event_show) {
        this.has_event_show = has_event_show;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setProfile_picture_url(String profile_picture_url) {
        this.profile_picture_url = profile_picture_url;
    }

    public void setToy_connection_status(String toy_connection_status) {
        this.toy_connection_status = toy_connection_status;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setPerformer_id(String performer_id) {
        this.performer_id = performer_id;
    }

    public void setIs_on_private(Integer is_on_private) {
        this.is_on_private = is_on_private;
    }

    public void setHas_vip_show(Integer has_vip_show) {
        this.has_vip_show = has_vip_show;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRate_average(Integer rate_average) {
        this.rate_average = rate_average;
    }

    public void setHas_hot_show(Integer has_hot_show) {
        this.has_hot_show = has_hot_show;
    }

    public void setPerson_age(Integer person_age) {
        this.person_age = person_age;
    }

    public void setLast_activity_time(Integer last_activity_time) {
        this.last_activity_time = last_activity_time;
    }

    public void setMsc_id(Integer msc_id) {
        this.msc_id = msc_id;
    }

    public void setOriginalStatus(Integer originalStatus) {
        this.originalStatus = originalStatus;
    }

    public void setSum_of_online_performers(Integer sum_of_online_performers) {
        this.sum_of_online_performers = sum_of_online_performers;
    }

    public void setStatus_order(Integer status_order) {
        this.status_order = status_order;
    }

    public void setLast_online_at(Integer last_online_at) {
        this.last_online_at = last_online_at;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrivate_price_per_minute(BigDecimal private_price_per_minute) {
        this.private_price_per_minute = private_price_per_minute;
    }

    public void setIn_exclusive_private(Boolean in_exclusive_private) {
        this.in_exclusive_private = in_exclusive_private;
    }

    private BigDecimal private_price_per_minute;
    private Boolean in_exclusive_private;

    public String getPublic_show_status() {
        return public_show_status;
    }

    public String getBecome_online_at() {
        return become_online_at;
    }

    public String getHas_event_show() {
        return has_event_show;
    }

    public String getP_id() {
        return p_id;
    }

    public String getProfile_picture_url() {
        return profile_picture_url;
    }

    public String getToy_connection_status() {
        return toy_connection_status;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getPerformer_id() {
        return performer_id;
    }

    public Integer getIs_on_private() {
        return is_on_private;
    }

    public Integer getHas_vip_show() {
        return has_vip_show;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getRate_average() {
        return rate_average;
    }

    public Integer getHas_hot_show() {
        return has_hot_show;
    }

    public Integer getPerson_age() {
        return person_age;
    }

    public Integer getLast_activity_time() {
        return last_activity_time;
    }

    public Integer getMsc_id() {
        return msc_id;
    }

    public Integer getOriginalStatus() {
        return originalStatus;
    }

    public Integer getSum_of_online_performers() {
        return sum_of_online_performers;
    }

    public Integer getStatus_order() {
        return status_order;
    }

    public Integer getLast_online_at() {
        return last_online_at;
    }

    public double getPrice() {
        return price;
    }

    public BigDecimal getPrivate_price_per_minute() {
        return private_price_per_minute;
    }

    public Boolean getIn_exclusive_private() {
        return in_exclusive_private;
    }
}
