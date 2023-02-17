package com.evault.eb.service;

import com.evault.eb.entity.AppPehchanDocView;

import java.time.LocalDateTime;

import org.json.JSONException;

public interface AppPehchanDocViewService {
    public boolean insertViewDocument(AppPehchanDocView appPehchanDocView) throws JSONException;

    public boolean updateViewDocument(AppPehchanDocView appPehchanDocView)throws JSONException;
}
